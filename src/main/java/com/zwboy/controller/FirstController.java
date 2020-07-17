package com.zwboy.controller;

import com.zwboy.service.FirstService;
import com.zwboy.utils.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: LiJiaChang
 * @Date: 2020/7/16 15:34
 */
public class FirstController {

    FirstService firstService;

    public Result<String> getFirstService(HttpServletRequest rep, HttpServletResponse resp){
        return firstService.doService("name");
    }

}
