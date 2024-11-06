package com.ozo.bigdatahadoopspring.service;

import lombok.RequiredArgsConstructor;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class PhotoService {
    private final FileSystem fileSystem;

    public String getPhotoAsBase64(String imageName) throws Exception {
        return Base64.getEncoder().encodeToString(getPhotoAsByteArray(imageName));
    }

    public byte[] getPhotoAsByteArray(String imageName) {
        // Prepare the path to the image in HDFS. imageName example: photo.jpg
        Path hdfsPath = new Path("/user/root/images/" + imageName);

        // Read the image from HDFS and return it as byte array
        try (InputStream inputStream = fileSystem.open(hdfsPath)) {
            return inputStream.readAllBytes();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get photo with path: " + imageName, e);
        }
    }

    public void deletePhoto(String imageName) {
        // Prepare the path to the image in HDFS. imageName example: photo.jpg
        Path hdfsPath = new Path("/user/root/images/" + imageName);
        try {
            fileSystem.delete(hdfsPath, false);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete photo with path: " + imageName, e);
        }
    }
}
