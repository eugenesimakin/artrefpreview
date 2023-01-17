package me.artrefpreview.service.storage;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageStorage {
    void save(String collectionId, List<MultipartFile> files);

    List<String> listImages(String collectionId);

    byte[] getImageData(String collectionId, String filename);
}
