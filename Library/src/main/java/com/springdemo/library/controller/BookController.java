package com.springdemo.library.controller;

import com.springdemo.library.model.DanhMuc;
import com.springdemo.library.model.Sach;
import com.springdemo.library.repositories.DanhMucRepository;
import com.springdemo.library.repositories.SachRepository;
import com.springdemo.library.services.GenerateViewService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@AllArgsConstructor
@RequestMapping
public class BookController {
    private GenerateViewService generateViewService;
    private SachRepository sachRepository;
    private DanhMucRepository danhMucRepository;
    @GetMapping("/book")
    public ModelAndView book(
            @RequestParam(name = "id", required = false) Integer bookId,
            @RequestParam(name = "page", required = false) Integer pageNumberParam,
            Authentication authentication
    ) {
        if(bookId==null) {
            int pageNumber = pageNumberParam!=null ? pageNumberParam-1 : 0;
            int pageSize = 16;
            int maxPagesToShow = 3;
            Pageable pageable = PageRequest.of(pageNumber, pageSize);

            Page<Sach> sachList = sachRepository.findAll(pageable);
            List<DanhMuc> danhMucList = danhMucRepository.findAll();
            int totalItems = 30*16; //(int) sachList.getTotalElements();
            int startItem = pageNumber * pageSize + 1;
            int endItem = Math.min(startItem + pageSize - 1, totalItems);

            String breadCrumb = """
            <ul>
                <li><a href="#">Trang chủ</a></li>
                <li><a href="#" class="active">Sách</a></li>
            </ul>""";
            ModelAndView sachViewModel = generateViewService.generateCustomerView("Sách", breadCrumb,"book", authentication);

            Map<String, Object> modelClass = new HashMap<>();
            modelClass.put("sachList", sachList);
            modelClass.put("danhMucList", danhMucList);
            modelClass.put("totalPages", 30);
            modelClass.put("currentPage", pageNumber);
            modelClass.put("pageNumbers", generateViewService.generatePageNumbers(pageNumber, 30, maxPagesToShow));
            modelClass.put("startItem", startItem);
            modelClass.put("endItem", endItem);
            modelClass.put("totalItems", totalItems);
            sachViewModel.addObject("modelClass", modelClass);
            return sachViewModel;
        } else {
//            Sach sach = sachRepository.findById(bookId).orElse(null);
//            if(sach==null) {
//                return new ModelAndView("redirect:/error");
//            } else {
//                //book detail page
//                String breadCrumb = """
//                    <ul>
//                        <li><a href="#">Trang chủ</a></li>
//                        <li><a href="#">Sách</a></li>
//                        <li><a href="#" class="active">""" + sach.getTenSach() +  """
//                    </a></li>
//                    </ul>""";
//                return new ModelAndView("book-details");
//            }
            return generateViewService.generateCustomerView("Thông tin sách", null, "book-details", authentication);
        }
    }
}