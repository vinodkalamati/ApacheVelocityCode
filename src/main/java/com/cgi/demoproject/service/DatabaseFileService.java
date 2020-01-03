package com.cgi.demoproject.service;

import com.cgi.demoproject.model.DatabaseFile;
import com.cgi.demoproject.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class DatabaseFileService {

    @Autowired
    private DataRepository dbrepository;

    public DatabaseFile storeFile(MultipartFile file) throws IOException {

        String fileName= StringUtils.cleanPath(file.getOriginalFilename());

        DatabaseFile databaseFile=new DatabaseFile(fileName,file.getContentType(),file.getBytes());

        return dbrepository.save(databaseFile);
    }


    public DatabaseFile getFile(String fileId){
        return dbrepository.findById(fileId).get();
    }
}
