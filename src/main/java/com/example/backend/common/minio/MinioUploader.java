package com.example.backend.common.minio;


import com.example.backend.domain.feed.exception.FileAlreadyExistedException;
import com.example.backend.domain.feed.exception.FileConvertException;
import com.example.backend.global.image.Image;
import com.example.backend.global.util.ImageUtil;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class MinioUploader {

    private final MinioClient minioClient;

    @Value("${minio.put-object-part-size}")
    private Long putSize; // 최대 파일 사이즈

    @Value("${minio.bucket}")
    private String bucket;

    // 
    public Image to(MultipartFile multipartFile, String dir) {

        Image image = ImageUtil.to(multipartFile); // MultipartFile 을 Image 객체로 변환
        String filename = convertToFilename(dir, image); // 경로와 이미지 객체의 정보를 가지고 파일 이름 생성
        String url = upload(multipartFile, filename); // 경로 반환후 설정
        image.setUrl(url);

        return image;
    }

    public Image toImage(MultipartFile multipartFile) {

        Image image = ImageUtil.to(multipartFile);

        return image;
    }

    private String upload(MultipartFile multipartFile, String filename) {
        File localFile = convertMultiToLocal(multipartFile);
        String url = putMinio(localFile, filename);
        deleteLocalFile(localFile);
        return url;
    }

    @SneakyThrows
    private String putMinio(File file, String filename) throws FileConvertException{

        InputStream inputStream = new FileInputStream(file);

        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucket)
                        .object(filename)
                        .stream(inputStream, file.length(), putSize)
                        .contentType("image/" + filename.substring(filename.length() - 3))
                        .build()
        );

        String fileUrl = "http://uncertain.shop:9000"+ "/" + bucket + "/" + filename;
//        System.out.println(fileUrl);

        return fileUrl;
    }

    public void deleteImage(Image image, String dir) {

        if (image.getImageUUID().equals("base-UUID")) { // 기본 설정된 사진일 경우
            return; // 종료 시킨다.
        }

        String filename = convertToFilename(dir, image);

        System.out.println(filename);

        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucket)
                            .object(filename)
                            .build()
            );
        } catch (Exception e) {

        }
    }

    private String convertToFilename(String dir, Image image) {
        return convertToFilename(dir, image.getImageUUID(), image.getImageName(), image.getImageType().toString());
    }

    private String convertToFilename(String dir, String UUID, String name, String type) {
        return dir + "/" + UUID + "_" + name + "." + type;
    }

    // TODO 수정해야함 스프링부트가 작동중일 경우 파일 삭제가 안됌; 오류 발생 생각 필요
    private void deleteLocalFile(File localFile) {

        if (localFile.delete()) {
            return;
        }

        log.error("Local File Delete Failed : " + localFile.getName());
    }



    private File convertMultiToLocal(MultipartFile file) {
        try {

            String path = System.getProperty("user.dir") + File.separator + "upload" + File.separator + file.getOriginalFilename();

            File convertFile = new File(path);

            if (convertFile.createNewFile()) {
                log.info("create local file: " + path);
                try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                    fos.write(file.getBytes());
                }
                log.info("complete write to local file");
                return convertFile;
            }
            throw new FileAlreadyExistedException(); // Custom Exception
        } catch (IOException e) {
            throw new FileConvertException(); // Custom Exception
        }
    }

}
