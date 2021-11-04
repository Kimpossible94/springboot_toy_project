package com.kh.spring.common.util;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.kh.spring.common.code.Config;
import com.kh.spring.common.code.ErrorCode;
import com.kh.spring.common.exception.HandlableException;

public class FileUtil {
	
	public FileInfo fileUpload(MultipartFile mf) {
		FileInfo file = createFileDTO(mf);
		
		try {
			mf.transferTo(new File(getSavePath() + file.getRenameFileName()));
		} catch (IllegalStateException | IOException e) {
			throw new  HandlableException(ErrorCode.FAILED_FILE_UPLOAD_ERROR, e);
		}
		
		return file;
	}
	
	private String getSavePath() {

		String subPath = getSubPath();
		String savePath = Config.UPLOAD_PATH.DESC + subPath;

		File dir = new File(savePath);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		return savePath;
	}
	
	
	private String getSubPath() {
		Calendar today = Calendar.getInstance();
		int year = today.get(Calendar.YEAR);
		int month = today.get(Calendar.MONTH) + 1;
		int date = today.get(Calendar.DATE);
		return year + "/" + month + "/" + date + "/";
	}
	
	
	private FileInfo createFileDTO(MultipartFile mf) {
		FileInfo fileDTO = new FileInfo();
		String originFileName = mf.getOriginalFilename();
		String renameFileName = UUID.randomUUID().toString();
		
		if(originFileName.contains(".")) {
			renameFileName = renameFileName += originFileName.substring(originFileName.lastIndexOf("."));
		}
		String savePath = getSavePath();

		fileDTO.setOriginFileName(mf.getOriginalFilename());
		fileDTO.setRenameFileName(renameFileName);
		fileDTO.setSavePath(savePath);

		return fileDTO;
	}
}
