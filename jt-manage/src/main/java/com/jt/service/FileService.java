package com.jt.service;

import com.jt.vo.ImageVo;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author WL
 * @Date 2020-9-29 16:13
 * @Version 1.0
 */
public interface FileService {

    ImageVo upload(MultipartFile uploadFile);
}
