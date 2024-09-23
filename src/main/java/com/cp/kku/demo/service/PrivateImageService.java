package com.cp.kku.demo.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cp.kku.demo.model.PrivateImage;
import com.cp.kku.demo.repository.PrivateImageRepository;
import com.cp.kku.demo.util.ImageUtils;

import java.io.IOException;
import java.util.Optional;
import java.util.zip.DataFormatException;

@Service
@RequiredArgsConstructor
public class PrivateImageService {
    
    @Autowired
    private final PrivateImageRepository privateImageRepository;

    public PrivateImage uploadImage(MultipartFile imageFile) throws IOException {
        var imageToSave = PrivateImage.builder()
                .name(imageFile.getOriginalFilename())
                .type(imageFile.getContentType())
                .imageData(ImageUtils.compressImage(imageFile.getBytes()))
                .build();
        PrivateImage savedImage = privateImageRepository.save(imageToSave);
        return savedImage;
    }

    public byte[] downloadImage(Long imageId) {
        Optional<PrivateImage> dbImage = privateImageRepository.findById(imageId);

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
        privateImageRepository.deleteById(imageId);
    }
}