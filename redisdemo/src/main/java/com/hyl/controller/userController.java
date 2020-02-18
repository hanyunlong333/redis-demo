package com.hyl.controller;


import com.hyl.pojo.userEntity;
import com.hyl.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class userController {
    @Autowired
    private userService service1;

    //运行此接口 会将查询的数据缓存到redis中,可在对应的192.168.1.117数据库中查看
    @RequestMapping("findById/{id}")
    public userEntity findById(@PathVariable("id") int id){
        return service1.findById(id);
    }

    //运行此接口并且访问过后会修改redis中的内容
    @RequestMapping("updateByUser")
    public userEntity update(userEntity user1){
        return service1.updateEntity(user1);
    }

    //可以直接从redis里面删除存入进去的缓存
    @RequestMapping("/deleteByRedis") //删除的是数据库的数据 过后redis数据库也会消失 因为impl注解同步
    public int delete(int id){
        return service1.delete(id);
    }
}
