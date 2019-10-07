package com.heshan.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @author HeShan
 * @date 2019/10/5 16:13
 */
@Data
public class User implements Serializable {
    /**
     * 生成序列化ID
     */
    private static final long serialVersionUID = -6301972841907614126L;
    // private static final long serialVersionUID =
    private Integer id;
    private String username;
    private String password;
    private String name;
    private Integer age;

    public static String getKeyName(){
        return "user:";
    }

}
