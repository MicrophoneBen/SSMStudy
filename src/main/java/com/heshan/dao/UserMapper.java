package com.heshan.dao;

import com.heshan.bean.User;
import org.springframework.stereotype.Repository;

/**
 * @author zhangbingquan
 * @desc 用户Mapper
 * @time 2019-10-07 2:36
 */
@Repository
public interface UserMapper {

    User findByUserName(String uname);
}
