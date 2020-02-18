package com.hyl.service.impl;


import com.hyl.dao.userDao;
import com.hyl.pojo.userEntity;
import com.hyl.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class userServiceImpl implements userService {
    @Autowired
    userDao userdao;

    @Resource
    private RedisTemplate<Object,Object> template;

    @Cacheable(value = "user",key = "#id") //将返回的内容根据value和key存入到redis中,如果有对应的下次访问直接访问缓存
    @Override
    public userEntity findById(int id) {
        //不使用注解缓存时就如以下使用
        //例子：redisTemplate.opsForValue().set("user",user1);
        //给缓存设置过期时间：
        //      redisTemplate.expire("user",10,TimeUnit.SECONDS);
        //查询缓存
        /*userEntity userentity = (userEntity) template.opsForValue().get("user1");
        if(null == userentity){
            //缓存的如果为空，再查询一遍数据库
            userentity = userdao.selectById(id);
            //数据库查出来的数据存到redis中
            template.opsForValue().set("user1",id);
        }*/
        return userdao.selectById(id);
    }

    @CachePut(value = "user",key = "#user1.getId()") //每次操作修改的时候都将结果来缓存
    @Override
    public userEntity updateEntity(userEntity user1) {
        int i = userdao.updateById(user1);
        return user1;
    }

    @CacheEvict(value = "user",key = "#id") //实时的进行数据缓存的删除
    @Override
    public int delete(int id) {
        return userdao.deleteById(id);
    } //return 1 就是为了不让真删 如果想真删调用删除的方法就可以
}
