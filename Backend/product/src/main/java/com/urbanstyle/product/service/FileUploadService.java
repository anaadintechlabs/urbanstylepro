package com.urbanstyle.product.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.anaadihsoft.common.master.UploadFileResponse;

@Service
public interface FileUploadService {

	UploadFileResponse storeFile(MultipartFile file, long productId, boolean local) throws Exception;

	List<UploadFileResponse> storeFiles(MultipartFile[] files, long productId, boolean local);

	Resource loadFileAsResource(String fileName, HttpServletRequest request);

}
