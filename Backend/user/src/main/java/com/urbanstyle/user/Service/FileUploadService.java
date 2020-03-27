package com.urbanstyle.user.Service;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.anaadihsoft.common.master.User;
import com.urbanstyle.user.Repository.UserRepository;
import com.urbanstyle.user.exception.FileStorageException;
import com.urbanstyle.user.exception.MyFileNotFoundException;
import com.urbanstyle.user.property.FileUploadProperties;
@Service
public class FileUploadService {


	private final Path fileStoragePath;
    private final Path fileStorageLocation;
    
	@Autowired
	private UserRepository userRepository;
     
    @Value("${application.public.domain}")
	private String applicationPublicDomain;
    
    @Autowired
    public FileUploadService(FileUploadProperties fileStorageProperties) {
    	this.fileStoragePath = Paths.get(fileStorageProperties.getUploadDir());
        this.fileStorageLocation = this.fileStoragePath.toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }
    
    public String generateFileUri(String fileName) {
    	return applicationPublicDomain+"/downloadFile/"+fileName;

    }
    

   
	private String generateFileNameForUser(Long long1) {
		return "USER_"+long1+"_"+String.valueOf(new Date().getTime());
	}


	public Resource loadFileAsResource(String fileName) {
		try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
	}




	public void saveImageofUser(MultipartFile[] files, User user) {
		String fileName = StringUtils.cleanPath(generateFileNameForUser(user.getId()));
		 try {
	            // Check if the file's name contains invalid characters
	            if(fileName.contains("..")) {
	                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
	            }
	            for(MultipartFile file : files){
	            	Path targetLocation = this.fileStorageLocation.resolve(fileName);
		            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
		            user.setImageUrl(fileName);
	            }
	            // Copy file to the target location (Replacing existing file with the same name)
	            userRepository.save(user);
	        } catch (IOException ex) {
	            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
	        }
		
	}

}
