package com.example.big_data_aws.controller;

import com.example.big_data_aws.interfaces.BucketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.net.URL;

@RestController
@RequestMapping("/bucket")
@RequiredArgsConstructor
@Slf4j
public class BucketController {
    private final BucketService bucketService;

    @GetMapping
    public String getBucket() {
        return bucketService.getBucketList().toString();
    }

    @GetMapping("/downloadObj")
    public void downloadObject(@RequestParam("bucketName") String bucketName, @RequestParam("path") String path, @RequestParam("objName") String objName) throws Exception {
        bucketService.getObjectFromBucket(bucketName, path, objName);
    }

    @GetMapping("/getUrl")
    public URL getUrl(@RequestParam("bucketName") String bucketName, @RequestParam("path") String path, @RequestParam("objName") String objName) {
        return bucketService.getImagePresignedUrl(bucketName, path, objName);
    }

    @PostMapping("/createBucket")
    public void createBucket(@RequestParam("bucketName") String bucketName) {
        bucketService.createBucket(bucketName);
    }

    @PostMapping("/uploadObj")
    public void uploadObject(@RequestParam("bucketName") String bucketName, @RequestParam("objName") String objName, @RequestParam("path") String path) {
        bucketService.putObjectIntoBucket(bucketName, objName, path);
    }

}
