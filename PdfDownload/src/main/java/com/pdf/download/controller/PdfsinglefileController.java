//package com.pdf.download.controller;
//
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.*;
//import org.springframework.http.ContentDisposition;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//@Controller
//@RequestMapping("/download")  //single file download
//public class PdfsinglefileController {
//	
//	@GetMapping("/zip")
//	public void downloadfile(HttpServletRequest request,HttpServletResponse response)throws IOException{
//
//			Path filep= Paths.get("C:\\Users\\rajku\\OneDrive\\Documents\\1.pdf");
//			String contentType = Files.probeContentType(filep);
//			
//			if(contentType == null) {
//				contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
//			}
//			
//			response.setContentType(contentType);
//			
//			response.setContentLengthLong(Files.size(filep));
//			
//			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment()
//											.filename(filep.getFileName().toString(),StandardCharsets.UTF_8)
//											.build()
//											.toString());
//			
//			Files.copy(filep, response.getOutputStream());
//
//	}
//
//}
