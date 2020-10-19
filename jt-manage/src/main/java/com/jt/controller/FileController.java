package com.jt.controller;

import com.jt.service.FileService;
import com.jt.vo.ImageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @Author WL
 * @Date 2020-9-29 15:19
 * @Version 1.0
 */
@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * SprinMVC中提供了API 操作流
     * 1.准备文件目录
     * 2.准备文件全名
     * 3.准备文件的路径：
     * 4.输出字节信息
     *
     * @return
     */
    @RequestMapping("/file")
    public String file(MultipartFile fileImage) {
        // 文件保存的路径
        String dirPath = "D:/image";
        //  判断目录是否存在
        File dirFile = new File(dirPath);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        String fileName = fileImage.getOriginalFilename();
        //  文件全路径
        String filePath = dirPath + "/" + fileName;

        File file = new File(filePath);
        try {
            fileImage.transferTo(file); //  将字节信息输出到指定的位置
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传成功";

    }

    /**
     *  http://localhost:8091/pic/upload?dir=image
     * @param uploadFile
     * @return  特定的格式信息{“error”:0,“url”:“图片的保存路径”,“width”:图片的宽度,“height”:图片的高度}
     */

    @RequestMapping("/pic/upload")
    public ImageVo file2(MultipartFile uploadFile) {

        return fileService.upload(uploadFile);
    }
}
