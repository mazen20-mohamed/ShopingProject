package com.example.productService.file;

import com.example.productService.exception.BadRequestResponseException;
import com.example.productService.exception.NotFoundResponseException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class FileStorageService {

    public Resource loadStoredImgAsResource(String path , String fileName){
        try {
            Path filePath;
            try {
                filePath = Paths.get(path);
                filePath =filePath.resolve(fileName).normalize();
            }
            catch (Exception e){
                throw new BadRequestResponseException(e.getMessage());
            }
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return resource;
            } else {
                throw new NotFoundResponseException("File not found " + fileName);
            }
        }
        catch (MalformedURLException ex){
            throw new NotFoundResponseException("File not found " + fileName);
        }
    }
}
