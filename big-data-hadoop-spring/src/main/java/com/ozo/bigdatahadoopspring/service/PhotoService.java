package com.ozo.bigdatahadoopspring.service;

import lombok.RequiredArgsConstructor;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class PhotoService {
    private final FileSystem fileSystem;

    public String getPhoto(String imageName) throws Exception {
        Path hdfsPath = new Path("/user/root/images/" + imageName);  // For example: photo.jpg
        try (InputStream inputStream = fileSystem.open(hdfsPath)) {
            byte[] imageBytes = inputStream.readAllBytes();
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (Exception e) {
            throw new Exception("Failed to get photo with path: " + imageName, e);
        }
    }
}
