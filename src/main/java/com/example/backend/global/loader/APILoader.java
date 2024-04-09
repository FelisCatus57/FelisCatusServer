package com.example.backend.global.loader;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class APILoader implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {

        String path = System.getProperty("user.dir") + File.separator + "upload";

        File delFile = new File(path);

        if (delFile.exists()) {
            File[] deleteFiles = delFile.listFiles();

            if (deleteFiles.length == 0) {
            }

            for (File deleteFile : deleteFiles) {
                deleteFile.delete();}
        } else {
            delFile.mkdir();
        }
    }
}

