package com.cgi.demoproject.service;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;


import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;
import org.springframework.stereotype.Service;

import java.io.StringWriter;


@Service
public class XMLHandling {

    private static String UPLOAD_FOLDER = "E:\\demoproject\\src\\main\\resources\\properties\\";
    private static  String htmlFolder = "E:\\demoproject\\src\\main\\webapp\\WEB-INF\\jsp\\";
    public void xmlRunner()
    {


        try {
            Velocity.init();

            SAXBuilder builder;
            Document root = null;
            try {
                builder =
                        new SAXBuilder();
                root = builder.build(UPLOAD_FOLDER + "Pfile.xml");
                System.out.println("root            "+ root.toString());
            } catch (Exception e) {
                System.out.println(e);
            }

            VelocityContext context = new VelocityContext();
            context.put("root", root);


            Template template =
                    Velocity.getTemplate("properties\\"+"Cfile.vm");

            StringWriter writer = new StringWriter();
            template.merge(context, writer);

            System.out.println(writer);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
