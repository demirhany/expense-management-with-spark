package com.example.big_data_aws.interfaces;

import com.amazonaws.services.s3.model.Bucket;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public interface BucketService {
    List<Bucket> getBucketList();

    boolean validateBucket(String bucketName);

    void getObjectFromBucket(String bucketName, String path, String objectName) throws IOException;

    void putObjectIntoBucket(String bucketName, String objectName, String filePathToUpload);

    void createBucket(String bucket);

    void uploadBase64Image(String bucketName, String path, String base64Image);

    URL getImagePresignedUrl(String bucketName, String path, String objectName);
}
