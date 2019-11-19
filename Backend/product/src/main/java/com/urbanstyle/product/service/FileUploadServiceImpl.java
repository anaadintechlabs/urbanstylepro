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

import com.anaadihsoft.common.master.Product;
import com.anaadihsoft.common.master.UploadFileResponse;
import com.urbanstyle.product.repository.ProductRepository;
import com.urbanstyle.product.util.CustomException;

@Service
public class FileUploadServiceImpl implements FileUploadService {

	
//	@Value("${upload.document.path}")
	private String documentPath="";

	//@Value("${upload.document.path}")
	private Path fileStoragePath=null;

	//@Value("${application.public.domain}")
	private String applicationPublicDomain="";
	
	@Autowired
	private ProductRepository productRepository; 







	/**
	 * @author DB-0066
	 * @description this method is used to upload single document
	 * @param file
	 * @param organizationId
	 * @param local
	 * @return
	 * @throws Exception
	 */
	public UploadFileResponse storeFile(MultipartFile file, long productId, boolean local) throws Exception {
		// Normalize file name
		//log.info("storeFile-------------------------fileStoragePath----" + fileStoragePath);
		Product product = productRepository.findByProductId(productId);
		String orgName = product.getProductId()+"";
		String fileName = StringUtils.cleanPath(generateFileName(orgName, file));
		try {
			if (fileName.contains("..")) {
			//	throw new CustomException("Sorry! Filename contains invalid path sequence " + fileName);
			}
			Path rootDirName = Paths.get(documentPath);
			Path rootDirectory = Paths.get(rootDirName + "//" + orgName).toAbsolutePath().normalize();
			Path targetLocation = Files.createDirectories(rootDirectory).resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			String fileUri = generateFileUri(orgName, fileName);
			if (local) {
				fileUri = targetLocation.toString();
			}
			return new UploadFileResponse(fileName, fileUri, file.getContentType(), file.getSize());
		} catch (IOException ex) {
			throw new Exception("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

	/**
	 * @author DB-0051
	 * @param orgName
	 * @param fileName
	 * @return
	 */
	private String generateFileUri(String organizationName, String fileName) {
		return applicationPublicDomain + "/downloadFile/" + organizationName + "/" + fileName;
	}

	/**
	 * @author DB-0066
	 * @description this method is used to upload multiple documents
	 * @param files
	 * @param organizationId
	 * @return
	 * @throws Exception
	 */
	public List<UploadFileResponse> storeFiles(MultipartFile[] files, long organizationId, boolean local)
			throws Exception {
		List<UploadFileResponse> uploadMultipleFiles = new ArrayList<>();
		for (MultipartFile file : files) {
			uploadMultipleFiles.add(storeFile(file, organizationId, local));
		}
		return uploadMultipleFiles;
	}

	/**
	 * @author DB-0066
	 * @description this method is used to generate document name
	 * @param orgName
	 * @param file
	 * @return
	 */
	private String generateFileName(String orgName, MultipartFile file) {
		return orgName + "-" + new Date().getTime() + FilenameUtils.EXTENSION_SEPARATOR
				+ FilenameUtils.getExtension(file.getOriginalFilename());
	}

	/**
	 * @author DB-0066
	 * @description this method is used to create directories for the document
	 * @param orgName
	 * @return
	 * @throws IOException
	 */
	public Path createDirectoryToUpload(String orgName) throws IOException {
		Path rootDirName = Paths.get(documentPath);
		Path rootDirectory = rootDirName.toAbsolutePath().normalize();
		if (!Files.exists(rootDirectory)) {
		//	log.info("------------------if");
			Path subDirName = Paths.get(rootDirName + "/" + orgName).toAbsolutePath().normalize();
			rootDirectory = Files.createDirectories(subDirName);
		} else {
		//	log.info("------------------else");

			Path subDirName = Paths.get(rootDirName + "/" + orgName).toAbsolutePath().normalize();
			if (!Files.exists(subDirName)) {
				rootDirectory = Files.createDirectories(subDirName);
			}
		}
		return rootDirectory;
	}

	public String generateFileUri(String organizationName, String workOrderNumber, String fileName) {
		return applicationPublicDomain + "/downloadFile/" + organizationName + "/" + workOrderNumber + "/" + fileName;
	}

	/**
	 * @author DB-0051
	 * @param file
	 * @param organizationName
	 * @param workOrderNumber
	 * @param local
	 * @return
	 * @throws Exception
	 */
	public UploadFileResponse uploadReport(MultipartFile file, String organizationName, String workOrderNumber,
			boolean local) throws Exception {
		String fileName = StringUtils.cleanPath(generateFileName(file));
		try {
			if (fileName.contains("..")) {
				//throw new CustomException("Sorry! Filename contains invalid path sequence " + fileName);
			}
			Path rootDirName = Paths.get(documentPath);
			Path rootDirectory = Paths.get(rootDirName + "//" + organizationName + "//" + workOrderNumber)
					.toAbsolutePath().normalize();
			Path targetLocation = Files.createDirectories(rootDirectory).resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			String fileUri = generateFileUri(organizationName, workOrderNumber, fileName);
			if (local) {
				fileUri = targetLocation.toString();
			}
		
			return new UploadFileResponse(fileName, fileUri, file.getContentType(), file.getSize());
		} catch (IOException ex) {
			throw new Exception("Could not store file " + fileName + ". Please try again!", ex);
		}
	}




	public UploadFileResponse saveBirtReport(MultipartFile file, String organizationName, String workOrderNumber,
			String consignmentNumber,
			String fileName,
			boolean local) throws Exception {
		if (consignmentNumber != null) {
			fileName = consignmentNumber + "-" + fileName;
		}
		fileName = StringUtils.cleanPath(fileName);
		try {
			if (fileName.contains("..")) {
			//	throw new CustomException("Sorry! Filename contains invalid path sequence " + fileName);
			}
			Path rootDirName = Paths.get(documentPath);
			Path rootDirectory = Paths.get(rootDirName + "//" + organizationName + "//" + workOrderNumber)
					.toAbsolutePath().normalize();
			Path targetLocation = Files.createDirectories(rootDirectory).resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			String fileUri = generateFileUri(organizationName, workOrderNumber, fileName);
			if (local) {
				fileUri = targetLocation.toString();
			}
			return new UploadFileResponse(fileName, fileUri, file.getContentType(), file.getSize());
		} catch (IOException ex) {
			throw new Exception("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

	/**
	 * @author DB-0051
	 * @param file
	 * @return
	 */
	private String generateFileName(MultipartFile file) {
		return new Date().getTime() + "-"+ file.getOriginalFilename();
	}

	/**
	 * @author DB-0051
	 * @param fileName
	 * @param request
	 * @return
	 * @throws CustomException
	 * @throws UnsupportedEncodingException
	 */
	public Resource loadFileAsResource(String fileName, HttpServletRequest request) throws CustomException, UnsupportedEncodingException {
		try {
			final Path fileStorageLocation = fileStoragePath.toAbsolutePath().normalize();
			Path fileStorage = Paths.get(fileStorageLocation.toString(), getFilePath(request));
			Path filePath = fileStorage.resolve(fileName).normalize();
			//log.info("filePath : " + filePath.toString());
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				//throw new CustomException("File not found " + fileName);
			}
		} catch (MalformedURLException ex) {
			//throw new CustomException("File not found " + fileName);
		}
		return null;
	}

	/**
	 * @author DB-0051
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String[] getFilePath(HttpServletRequest request) throws UnsupportedEncodingException {
		String[] arr = request.getRequestURI().split("/");
		StringBuilder strBuilder = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			if (i != 0 && i != 1 && i != arr.length - 1) {
				strBuilder.append(URLDecoder.decode(arr[i],"UTF-8")).append("/");
			}
		}
		return strBuilder.toString().split("/");
	}





}
