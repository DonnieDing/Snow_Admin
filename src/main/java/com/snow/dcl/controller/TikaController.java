package com.snow.dcl.controller;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@RequestMapping("/tika")
public class TikaController {
    @PostMapping("/parse")
    public ResponseEntity<String> parseDocument(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        try {
//            File toFile = ZipUtils.multipartFileToFile(file);
            StringBuilder content = new StringBuilder();
            try (InputStream stream = file.getInputStream()) {
                BodyContentHandler handler = new BodyContentHandler();
                Metadata metadata = new Metadata();
                AutoDetectParser parser = new AutoDetectParser();
                parser.parse(stream, handler, metadata);
                content.append(handler);
            } catch (Exception e) {
                e.printStackTrace();
                content.append("Error: ").append(e.getMessage());
            }
            String parsedContent = content.toString();
            return ResponseEntity.ok(parsedContent);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
