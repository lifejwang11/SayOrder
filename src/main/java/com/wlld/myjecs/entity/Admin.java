package com.wlld.myjecs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Admin {
    @TableId(type = IdType.AUTO)
    private int id;//id
    private String account;//账号
    private String pass_word;//密码
    private int pass;//账号是否通过，0未审核，1通过
    private String name;//账户拥有者姓名
}
