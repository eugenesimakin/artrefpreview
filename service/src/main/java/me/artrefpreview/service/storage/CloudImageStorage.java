package me.artrefpreview.service.storage;

import com.google.cloud.storage.*;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import jakarta.annotation.PostConstruct;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public class CloudImageStorage implements ImageStorage {

    public static final String PROJECT_ID = "concise-foundry-348313";
    public static final String BUCKET_NAME = PROJECT_ID;

    private final Storage storage;

    public CloudImageStorage() {
        storage = StorageOptions.newBuilder().setProjectId(PROJECT_ID).build().getService();
    }

    @PostConstruct
    public void init() {
        if (storage.get(BUCKET_NAME) == null) {
            storage.create(BucketInfo.of(BUCKET_NAME));
        }
    }

    @Override
    public void save(String collectionId, List<MultipartFile> files) {
        for (int i = 0; i < files.size(); i++) {
            var file = files.get(i);
            String filename = "file" + i + ".jpg";
            var originalFilename = Strings.nullToEmpty(file.getOriginalFilename());
            if (!originalFilename.isEmpty()) {
                filename = FilenameUtils.getBaseName(originalFilename) + ".jpg";
            }
            var blobId = BlobId.of(BUCKET_NAME, collectionId + "/" + filename);
            var blobInfo = BlobInfo.newBuilder(blobId).build();
            if (storage.get(blobId) != null) {
                storage.delete(blobId);
            }
            try {
                storage.create(blobInfo, file.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<String> listImages(String collectionId) {
        var imgIterable = storage.list(
                BUCKET_NAME,
                Storage.BlobListOption.prefix(collectionId + "/"),
                Storage.BlobListOption.currentDirectory()
        ).iterateAll();
        var blobNames = ImmutableList.copyOf(imgIterable).stream()
                .map(Blob::getName)
                .toList();
        return blobNames.stream().map(FilenameUtils::getName).toList();
    }

    @Override
    public byte[] getImageData(String collectionId, String filename) {
        var blobId = BlobId.of(BUCKET_NAME, collectionId + "/" + filename);
        return storage.readAllBytes(blobId);
    }
}
