package com.urbanstyle.user.Controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.urbanstyle.user.Service.FileUploadService;
import com.urbanstyle.user.util.CustomException;

@RestController
public class FileUploadController {

	private static final String RESPONSE_TIME = " ) and response time: ( ";

	@Autowired
	private FileUploadService fileUploadService;






	/**
	 * @author DB-0051
	 * @param fileName
	 * @param request
	 * @return
	 * @throws CustomException
	 * @throws UnsupportedEncodingException
	 */
	@GetMapping("/downloadFile/**/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request)
			throws  UnsupportedEncodingException, CustomException {
		final Resource resource = fileUploadService.loadFileAsResource(fileName);
		// Try to determine file's content type
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (final IOException ex) {
			//log.info("Could not determine file type.");
		}
		// Fallback to the default content type if type could not be determined
		if (contentType == null) {
			contentType = "application/octet-stream";
		}
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}


}

