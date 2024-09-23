package com.cp.kku.demo.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cp.kku.demo.model.Image;
import com.cp.kku.demo.repository.ImageRepository;
import com.cp.kku.demo.util.ImageUtils;

import java.io.IOException;
import java.util.Optional;
import java.util.zip.DataFormatException;

@Service
@RequiredArgsConstructor
public class ImageService {
    
    @Autowired
    private final ImageRepository imageRepository;

    public Image uploadImage(MultipartFile imageFile) throws IOException {
        var imageToSave = Image.builder()
                .name(imageFile.getOriginalFilename())
                .type(imageFile.getContentType())
                .imageData(ImageUtils.compressImage(imageFile.getBytes()))
                .build();
        Image savedImage = imageRepository.save(imageToSave);
        return savedImage;
    }

    public byte[] downloadImage(Long imageId) {
        Optional<Image> dbImage = imageRepository.findById(imageId);

        return dbImage.map(image -> {
            try {
                return ImageUtils.decompressImage(image.getImageData());
            } catch (DataFormatException | IOException exception) {
                throw new ContextedRuntimeException("Error downloading an image", exception)
                        .addContextValue("Image ID",  image.getId())
                        .addContextValue("Image name", image.getName());
            }
        }).orElse(null);
    }
    public void deleteImage(Long imageId){
        imageRepository.deleteById(imageId);
    }
}