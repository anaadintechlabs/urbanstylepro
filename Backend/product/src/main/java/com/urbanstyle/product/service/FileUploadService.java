package com.urbanstyle.product.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.anaadihsoft.common.master.Product;
import com.anaadihsoft.common.master.ProductImages;
import com.anaadihsoft.common.util.UploadFileResponse;
import com.urbanstyle.product.util.CustomException;


@Service
public interface FileUploadService {

	UploadFileResponse storeFile(MultipartFile file, long productId, boolean local) throws Exception;

	List<UploadFileResponse> storeFiles(MultipartFile[] files, long productId) throws Exception;

	Resource loadFileAsResource(String fileName) throws UnsupportedEncodingException, CustomException;

	List<ProductImages> storeMediaForProduct(MultipartFile[] files, Product oldProduct, String mainImageUrl);
	
	ProductImages storeSingleMediaForProduct(MultipartFile files, Product oldProduct,boolean firstVariant,String imageUrl);


}
