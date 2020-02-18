package com.hyl.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("users")
public class userEntity implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private Integer userage;

}