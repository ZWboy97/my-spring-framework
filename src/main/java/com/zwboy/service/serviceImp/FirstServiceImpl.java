package com.zwboy.service.serviceImp;

import com.zwboy.service.FirstService;
import com.zwboy.utils.Result;

/**
 * @Author: LiJiaChang
 * @Date: 2020/7/16 15:33
 */
public class FirstServiceImpl implements FirstService {

    @Override
    public Result<String> doService(String name) {
        Result<String> result = new Result<>();
        result.setData("你好呀" + name + "，我是第一个服务");
        return result;
    }
}
