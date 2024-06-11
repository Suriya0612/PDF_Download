package com.pdf.download.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.pdf.download.service.FileService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class PdfController {
	
	@Autowired private FileService fileService;

    @Value("${pdf.storage.path}")
    private String pdfStoragePath;
    
    private static final Logger logger= LoggerFactory.getLogger(PdfController.class);
    
    @GetMapping("/zip")
	public void downloadfile(HttpServletRequest request,HttpServletResponse response)throws IOException{
		
    		//The file to be downloaded
			Path filep= Paths.get("C:\\Users\\rajku\\OneDrive\\Documents\\1.pdf");
    		
			//Get the media type of the file (file formats)
			String contentType = Files.probeContentType(filep);
			
			if(contentType == null) {
    			//Use the default media type
				contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
			}
			
			response.setContentType(contentType);
			//file size
			response.setContentLengthLong(Files.size(filep));
			
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment()
											.filename(filep.getFileName().toString(),StandardCharsets.UTF_8)
											.build()
											.toString());
			//Response data to the client
			Files.copy(filep, response.getOutputStream());

	}

    
    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> downloadfile(@PathVariable String filename)throws IOException {
  
    		//The file to be downloaded
    		Path file= Paths.get(pdfStoragePath).resolve(filename).normalize();
    		Resource resource= new UrlResource(file.toUri());
    		
    		if(!resource.exists()) {
    			logger.error("File not found:" + file.toString());
    			throw new MalformedURLException("File not found");
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

    }
    
    @GetMapping("downloadfile/{filename}")
    public ResponseEntity<InputStreamResource> downloadFiledb(@PathVariable String filename) throws Exception{
    	
        	Path filepath= fileService.downloadFile(filename);
        	
        	InputStreamResource resource = new InputStreamResource(Files.newInputStream(filepath));
        	String contentType= Files.probeContentType(filepath);
    		
    		if(contentType == null) {
    			//Use the default media type
    			contentType = "application/pdf";
    		}
        	return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + filename + "\"")
                    .contentLength(Files.size(filepath))
                    .contentType(org.springframework.http.MediaType.parseMediaType(contentType))
                    .body(resource);

    }

}
