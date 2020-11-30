package com.xliu.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xliu.common.lang.Result;
import com.xliu.entity.Blog;
import com.xliu.service.BlogService;
import com.xliu.util.ShiroUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  BlogController
 * </p>
 *
 * @author Xiaoke Liu
 * @since 2020-11-20
 */
@RestController
//@RequestMapping("/blog")
public class BlogController {

    @Autowired
    BlogService blogService;

    @GetMapping("/blogs")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage) {
        Page page = new Page(currentPage,5);
        IPage pageData = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("created"));
        return Result.success(pageData);
    }

    @GetMapping("/blog/findByTitle")
    public Result findByTitle(@RequestParam String title) {
//        System.out.println(title);
        List<Blog> blogs = blogService.list(new QueryWrapper<Blog>()
                .like("title", title)
                .orderByDesc("created"));
        Assert.notNull(blogs,"Not found");
        return Result.success(blogs);
    }

    @DeleteMapping("/blog/{id}")
    public Result delete(@PathVariable(name = "id") Long id) {
        boolean b = blogService.removeById(id);
        if (b) {
            return Result.success("Success");
        }
        return Result.fail("Fail to delete");
    }

    @GetMapping("/blog/{id}")
    public Result detail(@PathVariable(name = "id") Long id) {

        Blog blog = blogService.getById(id);
        Assert.notNull(blog,"This blog is deleted");
        return Result.success(blog);
    }
    @RequiresAuthentication
    @PostMapping("/blog/edit")
    public Result edit(@Validated @RequestBody Blog blog) {
        Blog temp = null;
        if(blog.getId()!=null) {
            temp = blogService.getById(blog.getId());
            Assert.isTrue(temp.getUserId().longValue() == ShiroUtil.getProfile().getId().longValue(),"No authorization to edit");
        } else {
            temp = new Blog();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
        }

        BeanUtil.copyProperties(blog,temp,"id","userId","created","status");
        blogService.saveOrUpdate(temp);
        return Result.success(null);
    }

}
