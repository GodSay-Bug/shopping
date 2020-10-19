package com.jt.service;

import com.jt.vo.ImageVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @Author WL
 * @Date 2020-9-29 16:15
 * @Version 1.0
 */
@Service
@PropertySource("classpath:/properties/image.properties")   //容器动态的加载配置文件
public class FileServiceImpl implements FileService {

    @Value("${image.urlPat}")
    private  String urlPath; //= "http://image.jt.com/"; //值会发生变化
    @Value("${image.rootPat}")
    private  String rootPath; //= "D:/image/";
    private static Set<String> img;

    static {
        img = new HashSet<>();
        img.add(".png");
        img.add(".jpg");
        img.add(".gif");
    }


    /**
     * 上传的文件要有完善的校验功能
     * 1.校验后缀是否为图片
     * 2.校验是否为恶意程序
     * 3.防止文件数量太多，分目录存储
     * 4.防止文件重名
     * 5.实现文件上传
     *
     * @param uploadFile
     * @return
     */
    @Override
    public ImageVo upload(MultipartFile uploadFile) {
        urlPath.trim();
        rootPath.trim();

//        //1.校验图片类型
//
//        //1.1获取当前名称 之后截取其中的类型
//        String fileName = uploadFile.getOriginalFilename();
//        //1.2 准备图片的集合，包含了所有的图片类型
//
//        //1.3 判断图片类型是否正确
//        int index = fileName.lastIndexOf(".");
//        String fileType = fileName.substring(index);
//        // 转化小写
//        if (!img.contains(fileType.toLowerCase())) {
//            return ImageVo.faile();
//        }
//
//        //2.校验恶意程序
//
//        try {
//            //2.1 利用API对象 读取字节信息，获取图片对象类型
//            BufferedImage read = ImageIO.read(uploadFile.getInputStream());
//            //2.2 校验宽和高
//            int width = read.getWidth();
//            int height = read.getHeight();
//            if (width == 0 || height == 0) {
//                return ImageVo.faile();
//            }
//
//            //3. 目录存储
//            //3.1 将时间按照指定的格式要求，转化为字符串
//            String newPath = new SimpleDateFormat("/yyyy/MM/dd").format(new Date());
//            //3.2 拼接文件存储的目录对象
//            String filePath = rootPath + newPath;
//            File filePatha = new File(filePath);
//            //3.3 动态的创建目录
//            if (!filePatha.exists()) {
//                filePatha.mkdirs();
//            }
//
//            // 4. 防止重名
//            // 4.1UUID
//            String UUID = java.util.UUID.randomUUID().toString().replace("-", "");
//            String imageName = UUID + fileType;
//
//            // 5.上传
//            String realFilePath = rootPath + imageName;
//            File realFile = new File(realFilePath);
//            uploadFile.transferTo(realFile);
//            return ImageVo.success("http://img30.360buyimg.com/sku/jfs/t1/107693/18/18443/466040/5ec22793Eacc2d45c/f9431c7d8f3f0723.jpg", width, height);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ImageVo.faile();
//        }
        // 校验是否是图片
        String imageName = uploadFile.getOriginalFilename();
        int index = imageName.lastIndexOf(".");
        String imageType = imageName.substring(index).toLowerCase();
        if (!img.contains(imageType.toLowerCase())) {
            return ImageVo.faile();
        }

        // 校验是否为恶意程序

        BufferedImage read = null;
        try {
            read = ImageIO.read(uploadFile.getInputStream());
            int width = read.getWidth();
            int height = read.getHeight();
            if (width == 0 || height == 0) {
                return ImageVo.faile();
            }
            //防止文件数量太多，分目录存储 yyyy/MM/dd
            String fileCreated = new SimpleDateFormat("/yyyy/MM/dd/").format(new Date());
            String filePath = rootPath + fileCreated;
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            //  防止重名UUID
            String UUID = java.util.UUID.randomUUID().toString().replace("-", "");
           //   图片名称
            String fileName = UUID + imageType;
            String realFilePath = filePath + fileName;
            File file1 = new File(realFilePath);
            //  上传
            //  http://image.jt.com///// 虚拟路径
            String url = urlPath + fileCreated + fileName;
            uploadFile.transferTo(file1);
            return ImageVo.success(url, width, height);


        } catch (IOException e) {
            e.printStackTrace();
            return ImageVo.faile();
        }
    }
}
