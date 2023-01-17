package me.artrefpreview.service;

import me.artrefpreview.service.storage.ImageStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AppController {

    private final ImageStorage imageStorage;

    @Autowired
    public AppController(ImageStorage imageStorage) {
        this.imageStorage = imageStorage;
    }

    @GetMapping("/health")
    public String health() {
        return "OK";
    }

    @PostMapping(value = "/collection/{collectionId}/upload", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> imageUpload(@PathVariable("collectionId") String collectionId,
                                            @RequestParam("file") List<MultipartFile> files) {
        imageStorage.save(collectionId, files);
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }

    @GetMapping("/collection/{collectionId}")
    public List<String> getImagesInCollection(@PathVariable("collectionId") String collectionId) {
        return imageStorage.listImages(collectionId);
    }

    @GetMapping("/collection/{collectionId}/{filename}")
    public byte[] getImage(@PathVariable("collectionId") String collectionId,
                           @PathVariable("filename") String filename) {
        return imageStorage.getImageData(collectionId, filename);
    }

}
