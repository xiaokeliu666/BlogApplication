package com.xliu.service.impl;

import com.xliu.entity.User;
import com.xliu.mapper.UserMapper;
import com.xliu.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  UserServiceImpl
 * </p>
 *
 * @author Xiaoke Liu
 * @since 2020-11-20
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
