package com.jt.service;

import org.springframework.web.multipart.MultipartFile;

import com.jt.vo.FileVo;

public interface FileSerice {

	FileVo upload(MultipartFile fileImage);


}
