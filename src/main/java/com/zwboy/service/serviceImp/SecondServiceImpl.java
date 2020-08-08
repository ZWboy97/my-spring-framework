package com.zwboy.service.serviceImp;

import com.zwboy.utils.Result;
import org.simple.spring.core.annotations.Service;

/**
 * @Author: LiJiaChang
 * @Date: 2020/8/8 16:25
 */
@Service
public class SecondServiceImpl extends FirstServiceImpl {
    @Override
    public Result<String> doService(String name) {
        Result<String> result = new Result<>();
        result.setData("你好呀" + name + "，我是第二个服务");
        return result;
    }
}
