package com.ozo.bigdatahadoopspring.controller;

import com.ozo.bigdatahadoopspring.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ImageController {
    private final PhotoService photoService;

    @GetMapping("/images/{imageName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imageName) {
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(photoService.getPhotoAsByteArray(imageName));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
