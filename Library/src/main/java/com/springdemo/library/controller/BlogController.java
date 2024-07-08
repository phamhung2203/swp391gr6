package com.springdemo.library.controller;

import com.springdemo.library.services.GenerateViewService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
@AllArgsConstructor
@RequestMapping
public class BlogController {
    private GenerateViewService generateViewService;
    @GetMapping("/blog")
    public ModelAndView blog(Authentication authentication) {
        String breadCrumb =  """
            <ul>
                <li><a href="#">Trang chủ</a></li>
                <li><a href="#" class="active">Blog</a></li>
            </ul>""";
        return generateViewService.generateCustomerView("Blog", breadCrumb, "blog", authentication);
    }

    @GetMapping("/blogDetail")
    public ModelAndView blogDetail(Authentication authentication) {
        String breadCrumb = """
            <ul>
                <li><a href="#">Blog</a></li>
                <li><a href="#" class="active">Chi tiết blog</a></li>
            </ul>""";
        return generateViewService.generateCustomerView("Chi tiết blog", breadCrumb, "blog-details", authentication);

    }
}

