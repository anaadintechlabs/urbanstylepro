package com.urbanstyle.product.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.anaadihsoft.common.master.Product;
import com.anaadihsoft.common.master.ProductImages;
import com.anaadihsoft.common.util.UploadFileResponse;
import com.urbanstyle.product.exception.FileStorageException;
import com.urbanstyle.product.exception.MyFileNotFoundException;
import com.urbanstyle.product.property.FileStorageProperties;
import com.urbanstyle.product.repository.ProductRepository;
import com.urbanstyle.product.util.CustomException;

@Service
public class FileUploadServiceImpl implements FileUploadService {

	

	
	private final Path fileStoragePath;
    private final Path fileStorageLocation;
	private static final String SLASH = "/";
    @Value("${application.public.domain}")
	private String applicationPublicDomain;
    
	@Autowired
	private ProductRepository productRepository; 


	
	 public String generateFileUri(String fileName) {
	    	return applicationPublicDomain+"/downloadFile/"+fileName;

	    }
	    
	
	
	    
	    public String generateFileNameFromMultipart(MultipartFile multiPart) {
	    	String fileName = multiPart.getOriginalFilename().replace("\\",SLASH).replace(" ", "_");
	    	int lastIndex = fileName.lastIndexOf(SLASH);
	    	if(lastIndex!=-1) {
	    		fileName = fileName.substring(lastIndex+1);
	    	}
	        return new Date().getTime() + "-" + fileName;
	    }
	    

	    

	    public UploadFileResponse storeFile(MultipartFile file) {
	        // Normalize file name
	        String fileName = StringUtils.cleanPath(generateFileNameFromMultipart(file));

	        try {
	            // Check if the file's name contains invalid characters
	            if(fileName.contains("..")) {
	                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
	            }

	            // Copy file to the target location (Replacing existing file with the same name)
	            Path targetLocation = this.fileStorageLocation.resolve(fileName);
	            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

	            return new UploadFileResponse(fileName, generateFileUri(fileName),file.getContentType(), file.getSize());
	        } catch (IOException ex) {
	            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
	        }
	    }


	 
	 
	    public List<UploadFileResponse> getFileList(){
	    	try {
	    		List<UploadFileResponse> uploadFileResponseList = new ArrayList<>();
	    		Files.walk(this.fileStoragePath).filter(Files::isRegularFile).forEach(file->{
	    			try {
						uploadFileResponseList.add(
						new UploadFileResponse(file.toFile().getName(), this.generateFileUri(file.toFile().getName()),
								Files.probeContentType(file), file.toFile().length()));
					} catch (IOException ex) {
						throw new FileStorageException("Could not find files. Please try again!", ex);
					}
	    		});
	            return uploadFileResponseList;
	        } catch (IOException ex) {
	            throw new FileStorageException("Could not find files. Please try again!", ex);
	        }
	    }
	    
	    /**
	     * 
	     * @param fileUrl
	     * @return Boolean
	     */
	    public Boolean deleteFile(String fileUrl) {
	    	if(!StringUtils.isEmpty(fileUrl) && fileUrl.indexOf("downloadFile/")!=-1) {
	    		try {
	    			Path fileLocation = this.fileStorageLocation.resolve(fileUrl.substring(fileUrl.indexOf("downloadFile/")+13));
					return Files.deleteIfExists(fileLocation);
				} catch (IOException e) {
					
					return false;
				}
	    	}
	    	return false;
	    }
	    
	    /**
	     * 
	     * @param List<String> fileUrls
	     * @return List<Boolean>
	     */
	    public List<Boolean> deleteFiles(List<String> fileUrls) {
	    	List<Boolean> response = new ArrayList<>();
	    	for(String fileUrl:fileUrls) {
	    		response.add(deleteFile(fileUrl));
	    	}
	    	return response;
	    }
	    
	    
    @Autowired
    public FileUploadServiceImpl(FileStorageProperties fileStorageProperties) {
    	this.fileStoragePath = Paths.get(fileStorageProperties.getUploadDir());
        this.fileStorageLocation = this.fileStoragePath.toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }


	@Override
	public UploadFileResponse storeFile(MultipartFile file, long productId, boolean local) throws Exception {
		   // Normalize file name
        String fileName = StringUtils.cleanPath(generateFileNameFromMultipart(file));

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return new UploadFileResponse(fileName, generateFileUri(fileName),file.getContentType(), file.getSize());
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
	}


	@Override
	public List<UploadFileResponse> storeFiles(MultipartFile[] files, long productId) throws Exception {
		List<UploadFileResponse> uploadMultipleFiles = new ArrayList<>();
    	for(MultipartFile file : files){
    		 uploadMultipleFiles.add(storeFile(file));
    	}
    	return uploadMultipleFiles;
	}


	@Override
	public Resource loadFileAsResource(String fileName)
			throws UnsupportedEncodingException, CustomException {
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





//Just a check bexause original image was not getting displayed due to millisecond issye
	@Override
	public List<ProductImages> storeMediaForProduct(MultipartFile[] files, Product oldProduct,String mainImageUrl) {
		List<ProductImages> productImages = new ArrayList<>();
		int i =0;
		for(MultipartFile file : files){
			if(i==0)
			{
				productImages.add(storeSingleMediaForProduct(file,oldProduct,true,mainImageUrl));
				i++;

			}
			else
			{
				productImages.add(storeSingleMediaForProduct(file,oldProduct,false,null));

			}
		}
		System.out.println("product images");
		return productImages;
	}




	@Override
	public ProductImages storeSingleMediaForProduct(MultipartFile file, Product oldProduct, boolean firstVariant, String mainImageUrl) {
	    String fileName="";
		if(firstVariant)
	    {
	    	 fileName = mainImageUrl;
	    }
	    else
	    {
		 fileName = StringUtils.cleanPath(generateFileNameFromMultipart(file));
	    }
        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
           
            return new ProductImages(fileName,generateFileUri(fileName),file.getContentType(),file.getSize(),oldProduct);
          //  return new UploadFileResponse(fileName, generateFileUri(fileName),file.getContentType(), file.getSize());
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
	}








}
