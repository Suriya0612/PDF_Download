package com.pdf.download.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PdfController {

    @Value("${pdf.storage.path}")
    private String pdfStoragePath;
    
    @GetMapping("/download/{filename}")
    public ResponseEntity<?> downloadfile(@PathVariable String filename) {
    	
    	try {
    		//The file to be downloaded
    		Path file= Paths.get(pdfStoragePath).resolve(filename).normalize();
    		Resource resource= new UrlResource(file.toUri());
    		
    		if(!resource.exists()) {
    			return new ResponseEntity<>("file not found", HttpStatus.NOT_FOUND);
    		}
    		
    		//Get the media type of the file (file formats)
    		String contentType= Files.probeContentType(file);
    		
    		if(contentType == null) {
    			//Use the default media type
    			contentType = "application/pdf";
    		}
    		
    		return ResponseEntity.ok()
    				.contentType(org.springframework.http.MediaType.parseMediaType(contentType))
    				.header(HttpHeaders.CONTENT_DISPOSITION, "attachement; filename=\"" + resource.getFilename() + "\"")
    				.body(resource);
    	}catch(MalformedURLException e) {
    		return new ResponseEntity<>("file not found", HttpStatus.NOT_FOUND);
    	}catch(IOException e) {
    		return new ResponseEntity<>("Error occurred while reading the file", HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    }

}
