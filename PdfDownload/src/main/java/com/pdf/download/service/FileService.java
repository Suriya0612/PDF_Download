package com.pdf.download.service;

import java.nio.file.*;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pdf.download.Entity.*;
import com.pdf.download.repository.*;

@Service
public class FileService {
	
	@Autowired private FilesRepository fileRepository;
	@Autowired private FolderDetailsRepository folderRepository;

	public Path downloadFile(String filename) throws Exception{
		
		Optional<Filedetails> fileopt = fileRepository.findByFilename(filename);

		if(!fileopt.isPresent()) {
			throw new Exception ("File /" + filename +"/does not found");
		}
		
		Filedetails file = fileopt.get();
		if(!file.getIsactive()) {
			throw new Exception("File /" + filename +" does not active");
		}
		
		FolderDetails folder = file.getFolder();
		
		if (!folder.getIsactive()) {
			throw new Exception("Folder for file /" + filename + "/ does not active");
		}
		
		String completepath = folder.getFilepath() + "/" + filename;
		
		Path path = Paths.get(completepath);
		if(!Files.exists(path)) {
			throw new Exception("File /" + filename + " / does not exist at the path" + completepath);
		}
		
		return path;
	}

}
