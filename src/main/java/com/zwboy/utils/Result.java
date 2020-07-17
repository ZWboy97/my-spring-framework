package com.zwboy.utils;

import lombok.Data;

/**
 * @Author: LiJiaChang
 * @Date: 2020/7/16 15:35
 */
@Data
public class Result<D> {

    private int code;

    private String msg;

    private D data;

}
