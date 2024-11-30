package com.example.big_data_aws.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.example.big_data_aws.interfaces.BucketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BucketServiceImpl implements BucketService {
    private final AmazonS3 s3Client;

    @Override
    public List<Bucket> getBucketList() {
        log.info("getting bucket list... ");
        return s3Client.listBuckets();
    }

    @Override
    public boolean validateBucket(String bucketName) {
        List<Bucket> bucketList = getBucketList();
        log.info("Bucket list:" + bucketList);
        return bucketList.stream().anyMatch(m -> bucketName.equals(m.getName()));
    }

    @Override
    public void getObjectFromBucket(String bucketName, String path, String objectName) throws IOException {
        if (!validateBucket(bucketName)) {
            log.error("Bucket with name {} not found", bucketName);
            throw new RuntimeException("Bucket with name " + bucketName + " not found");
        }

        String filePath = path + objectName;
        S3Object s3Object = s3Client.getObject(bucketName, filePath);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        FileOutputStream fos = new FileOutputStream("/home/ubuntu/Desktop/aws/images/" + objectName);
        byte[] read_buf = new byte[1024];
        int read_len = 0;
        while ((read_len = inputStream.read(read_buf)) > 0) {
            fos.write(read_buf, 0, read_len);
        }
        inputStream.close();
        fos.close();
    }

    @Override
    public void createBucket(String bucketName) {
        s3Client.createBucket(bucketName);
    }

    @Override
    public void putObjectIntoBucket(String bucketName, String objectName, String filePathToUpload) {
        if (!validateBucket(bucketName)) {
            log.error("Bucket with name {} not found", bucketName);
            throw new RuntimeException("Bucket with name " + bucketName + " not found");
        }

        try {
            s3Client.putObject(bucketName, objectName, new File(filePathToUpload));
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
        ;
    }

    @Override
    public void uploadBase64Image(String bucketName, String path, String base64Image) {
//        log.info("Uploading image to S3... with path: " + path + " and bucketName: " + bucketName + " and base64Image: " + base64Image);
        try {
            // Remove the prefix
            String base64ImageData = base64Image.split(",")[1];

            // Decode the base64 string
            byte[] decodedBytes = Base64.getDecoder().decode(base64ImageData);
            InputStream inputStream = new ByteArrayInputStream(decodedBytes);

            // Set metadata
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(decodedBytes.length);
            metadata.setContentType("image/jpeg"); // or appropriate content type

            // Upload to S3
            s3Client.putObject(bucketName, path, inputStream, metadata);

        }catch (AmazonServiceException e){
            log.error("Failed to upload image to S3", e);
        }
    }

    @Override
    public URL getImagePresignedUrl(String bucketName, String path, String objectName) {
        if (!validateBucket(bucketName)) {
            log.error("Bucket with name {} not found", bucketName);
            throw new RuntimeException("Bucket with name " + bucketName + " not found");
        }
        return generatePresignedUrl(bucketName, path + objectName, s3Client);
    }

    public URL generatePresignedUrl(String bucketName, String objectName, AmazonS3 s3Client) {
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 60 * 24; // 15 dakika geçerli
        expiration.setTime(expTimeMillis);

        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, objectName)
                .withMethod(HttpMethod.GET)
                .withExpiration(expiration);

        return s3Client.generatePresignedUrl(generatePresignedUrlRequest);
    }

}
