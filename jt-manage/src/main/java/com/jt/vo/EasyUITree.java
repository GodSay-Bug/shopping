package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author WL
 * @Date 2020-9-27 16:21
 * @Version 1.0
 */
@Accessors(chain = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EasyUITree implements Serializable {

    private Long id;
    private String text;
    private String state;
}
