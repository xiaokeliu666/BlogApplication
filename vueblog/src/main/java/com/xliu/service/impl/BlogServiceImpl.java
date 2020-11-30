package com.xliu.service.impl;

import com.xliu.entity.Blog;
import com.xliu.mapper.BlogMapper;
import com.xliu.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  BlogServiceImpl
 * </p>
 *
 * @author Xiaoke Liu
 * @since 2020-11-20
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

}
