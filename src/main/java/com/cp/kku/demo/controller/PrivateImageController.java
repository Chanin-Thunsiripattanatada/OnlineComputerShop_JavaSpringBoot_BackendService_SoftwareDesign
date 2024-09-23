package com.cp.kku.demo.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
// import org.springframework.web.multipart.MultipartFile;

// import com.cp.kku.demo.model.Image;
import com.cp.kku.demo.service.PrivateImageService;

// import java.io.IOException;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PrivateImageController {
    @Autowired
    private PrivateImageService privateImageService;

    // @PostMapping("/image")
    // public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
    //     Image uploadImage = privateImageService.uploadImage(file);
    //     return ResponseEntity.status(HttpStatus.OK).body(uploadImage);
    // }

    // @GetMapping("/{fileName}")
    // public ResponseEntity<?> downloadImage(@PathVariable String fileName) {
    //     byte[] imageData = privateImageService.downloadImage(fileName);
    //     return ResponseEntity.status(HttpStatus.OK)
    //             .contentType(MediaType.valueOf(IMAGE_PNG_VALUE))
    //             .body(imageData);
    // }
    @GetMapping("/privateimage/{imageId}")
    public ResponseEntity<?> downloadImage(@PathVariable("imageId") Long imageId) {
        byte[] imageData = privateImageService.downloadImage(imageId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(IMAGE_PNG_VALUE))
                .body(imageData);
    }
    @DeleteMapping("/admin/privateimage/{imageId}")
    public void deleteImage(@PathVariable("imageId") Long imageId){
        privateImageService.deleteImage(imageId);
    }
}