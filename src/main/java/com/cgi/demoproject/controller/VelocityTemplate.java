package com.cgi.demoproject.controller;


import com.cgi.demoproject.model.DatabaseFile;
import com.cgi.demoproject.service.DatabaseFileService;
import com.cgi.demoproject.service.EmailSender;
import com.cgi.demoproject.service.XMLHandling;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import org.apache.velocity.runtime.RuntimeConstants;
import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;

import java.util.Enumeration;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class VelocityTemplate {

    private static String UPLOAD_FOLDER = "E:\\demoproject\\src\\main\\resources\\properties\\";
    private static String htmlFolder = "E:\\demoproject\\src\\main\\webapp\\WEB-INF\\jsp\\";
    private static Pattern _fileExtn = Pattern.compile("([^\\s]+(\\.(?i)(xml|properties))$)");
    private static Pattern _fileExtn1 = Pattern.compile("([^\\s]+(\\.(?i)(txt|html|vm))$)");
    private String propertiesFileName;
    private String contentFileName;
    private String message;
    private DatabaseFile Cfile;
    private DatabaseFile Pfile;
    private String CUUID;
    private String PUUID;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private DatabaseFileService fileService;

    @Autowired
    private ViewController viewController;

    @Autowired
    private XMLHandling xmlHandling;

    @PostMapping("/upload")
    public ModelAndView FileReader(@RequestParam("propertiesFile") MultipartFile propertiesFile, @RequestParam("contentFile") MultipartFile contentFile) {
        System.out.println(propertiesFile.getOriginalFilename());
        System.out.println(contentFile.getOriginalFilename());
        propertiesFileName = propertiesFile.getOriginalFilename();
        contentFileName = contentFile.getOriginalFilename();
        if (contentFile.isEmpty() || propertiesFile.isEmpty()) {
            return new ModelAndView("index", "message", "Uploaded files are empty");
        }
        Matcher mtch = _fileExtn.matcher((propertiesFile.getOriginalFilename()));
        System.out.println(propertiesFile.getOriginalFilename());
        if (mtch.matches()) {
            try {
                // read and write the file to the selected location-

                Pfile = fileService.storeFile(propertiesFile);
                PUUID = Pfile.getId();
/*                byte[] bytes = propertiesFile.getBytes();
                Path path = Paths.get(UPLOAD_FOLDER + propertiesFile.getOriginalFilename());

                Files.write(path, bytes);*/

            } catch (IOException e) {
                return new ModelAndView("index", "message", "Internal Server Error :" + e.getMessage());
            }
        }
        mtch = _fileExtn1.matcher(contentFile.getOriginalFilename());
        if (mtch.matches()) {
            try {
                // read and write the file to the selected location-

                Cfile = fileService.storeFile(contentFile);
                CUUID = Cfile.getId();

/*                byte[] bytes = contentFile.getBytes();
                Path path = Paths.get(UPLOAD_FOLDER + contentFile.getOriginalFilename());

                Files.write(path, bytes);*/

            } catch (IOException e) {
                return new ModelAndView("index", "message", "Internal Server Error: " + e.getMessage());

            }
        } else {
            return new ModelAndView("index", "message", "Internal Server Error!!Please try after some time");

        }

        return new ModelAndView("index", "message", "Successfully uploaded");
    }


    @GetMapping("/test")
    public void testing() throws IOException, InterruptedException {
        Properties properties = new Properties();
        VelocityContext velocityContext = new VelocityContext();
        OutputStream os;
        try {

            if (propertiesFileName.contains(".xml")) {
                try {
//            properties.load(new FileInputStream(UPLOAD_FOLDER+propertiesFileName));
                    File file = new File(UPLOAD_FOLDER + "Pfile.xml");
                    os = new FileOutputStream(file);
                    os.write(fileService.getFile(PUUID).getData());
                    properties.load(new FileInputStream(file));
                    System.out.println("getfile    " + (fileService.getFile(PUUID)));
                    System.out.println("file name" + file.getName() + "file path   " + file.getAbsolutePath());

                } catch (FileNotFoundException e) {
                    status(e.getMessage());
                } catch (IOException e) {
                    status(e.getMessage());
                }

                SAXBuilder builder;
                Document root = null;
                try {
                    builder =
                            new SAXBuilder();
                    root = builder.build(UPLOAD_FOLDER + "Pfile.xml");
                    System.out.println("root            " + root.getDocument() + root.getContentSize() + root.getDescendants());
                } catch (Exception e) {
                    System.out.println(e);
                    status(e.getMessage());
                }

                velocityContext.put("root", root);

            } else {
                try {
//            properties.load(new FileInputStream(UPLOAD_FOLDER+propertiesFileName));
                    File file = new File(UPLOAD_FOLDER + "Pfile.properties");
                    os = new FileOutputStream(file);
                    os.write(fileService.getFile(PUUID).getData());
                    properties.load(new FileInputStream(file));
                    System.out.println("getfile    " + (fileService.getFile(PUUID)));
                    System.out.println("file name" + file.getName() + "file path   " + file.getAbsolutePath());

                } catch (FileNotFoundException e) {
                    status(e.getMessage());
                    e.printStackTrace();
                } catch (IOException e) {
                    status(e.getMessage());
                    e.printStackTrace();
                }
                Enumeration enumeration = properties.propertyNames();
                System.out.println(properties.toString());
                System.out.println(enumeration.toString());
                while (enumeration.hasMoreElements()) {
                    String key = (String) enumeration.nextElement();
                    System.out.println("key   " + key);
                    String value = properties.getProperty(key);
                    if (value.contains(",")) {
                        String[] values = value.split(",");
                        velocityContext.put(key, values);
                    } else {
                        velocityContext.put(key, value);
                    }

                    System.out.println("value   " + value);

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            VelocityEngine engine = new VelocityEngine();
            engine.setProperty("resource.loader", "file");
            engine.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
            engine.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, UPLOAD_FOLDER);


/*        engine.setProperty("resource.loader", "class");
        engine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");*/
            engine.init();
            System.out.println(contentFileName);
            contentFileName = "test.html";
            File file1 = new File(UPLOAD_FOLDER + "Cfile.vm");
            OutputStream outputStream = new FileOutputStream(file1);
            outputStream.write(fileService.getFile(CUUID).getData());
            Thread thread = new Thread();
            thread.sleep(2000);
            Template template = engine.getTemplate(file1.getName());

            System.out.println("file1 Name   " + file1.getAbsolutePath() + "   " + file1.getName());
            System.out.println(fileService.getFile(CUUID));
            System.out.println(template.getData() + "&&&");
            StringWriter writer = new StringWriter();
            template.merge(velocityContext, writer);


            System.out.println("result      " + writer.toString());
            message = writer.toString();

            File file = new File(htmlFolder + "tempo.jsp");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(writer.toString());
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("reached");
            status(e.getMessage());
        }
    }

    @RequestMapping("/download")
    public String downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException, InterruptedException {


        File file = new File(UPLOAD_FOLDER + "tempo.html");
        if (file.exists()) {
            String mimeType = URLConnection.guessContentTypeFromName(file.getName());
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }

            response.setContentType(mimeType);

//			response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));

            response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));

            response.setContentLength((int) file.length());

            InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

            FileCopyUtils.copy(inputStream, response.getOutputStream());

        }
        return "Success";

    }


    @GetMapping("/email")
    private void sendEmail(@RequestParam("emailId") String to) throws MessagingException {

        if (!message.isEmpty())
            emailSender.sendEmailTemplate(to, message);


    }

    public ModelAndView status(String msg) {
        System.out.println("step2");
        return new ModelAndView("index", "message", msg);
    }


}
