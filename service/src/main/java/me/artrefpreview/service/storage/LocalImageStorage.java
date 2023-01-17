package me.artrefpreview.service.storage;

import com.google.common.base.Strings;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LocalImageStorage implements ImageStorage {

    private static final Path BASE_PATH = Path.of("localdata");

    static {
        if (!Files.exists(BASE_PATH)) {
            boolean mkdirs = BASE_PATH.toFile().mkdirs();
            if (!mkdirs) {
                throw new RuntimeException("Failed to create localdata directory");
            }
        }
    }

    @Override
    public void save(String collectionId, List<MultipartFile> files) {
        var path = BASE_PATH.resolve(collectionId);
        for (int i = 0; i < files.size(); i++) {
            var file = files.get(i);
            String filename = "file" + i + ".jpg";
            var originalFilename = Strings.nullToEmpty(file.getOriginalFilename());
            if (!originalFilename.isEmpty()) {
                filename = FilenameUtils.getBaseName(originalFilename) + ".jpg";
            }
            var filePath = path.resolve(filename);
            try {
                if (filePath.toFile().exists()) {
                    Files.delete(filePath);
                }
                filePath.getParent().toFile().mkdirs();
                boolean created = filePath.toFile().createNewFile();
                if (!created) {
                    throw new IOException("Failed to create file " + filePath);
                }
                file.transferTo(filePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<String> listImages(String collectionId) {
        var path = BASE_PATH.resolve(collectionId);
        var files = path.toFile().listFiles();
        if (files == null) {
            return Collections.emptyList();
        }
        return Arrays.stream(files).map(File::getName).toList();
    }

    @Override
    public byte[] getImageData(String collectionId, String filename) {
        var filePath = Paths.get(BASE_PATH.toString(), collectionId, filename);
        try {
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
