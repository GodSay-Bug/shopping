package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author WL
 * @Date 2020-9-29 15:51
 * @Version 1.0
 * 上传图片的封装类
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ImageVo implements Serializable {

    private Integer error;//   0正常
    private String url; //  图片访问的虚拟路径
    private Integer width;
    private Integer height;

    // 设定上传失败的方法
    public static ImageVo faile() {
        return new ImageVo(1, null, null, null);
    }

    public static ImageVo success(String url, Integer width, Integer height) {
        return new ImageVo(0, url, width, height);
    }


}
