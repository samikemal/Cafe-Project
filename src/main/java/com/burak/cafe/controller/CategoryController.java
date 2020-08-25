package com.burak.cafe.controller;

import com.burak.cafe.entity.CategoryEntity;
import com.burak.cafe.dao.CategoryDAO;
import com.burak.cafe.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class CategoryController {

    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    private CategoryRepository categoryRepository;

    /*
    Sıfırdan category oluştur bir model oluşturup
    boş category i o modele at form ile bağlamak için
     */
    //@ResponseBody
    @Secured({"ADMIN"})
    @RequestMapping("/admin/category/form")
    public String addCategory(Model model)
    {
        CategoryEntity categoryEntity=new CategoryEntity();
        model.addAttribute("categoryy", categoryEntity);
        return "/admin/category-form";
    }

    //@ResponseBody
    @Secured({"ADMIN"})
    @PostMapping("/admin/category/saveCategory")
    public String saveCategory(@ModelAttribute("categoryy") CategoryEntity categoryEntity)//üstte categoryy i oluşturduk burda o yüzden direk @ModelAttribute annotationunu kullanarak yapıyoruz
    {
        categoryRepository.save(categoryEntity);
        return "redirect:/admin/home";
    }

    //@ResponseBody
    @Secured({"ADMIN"})
    @RequestMapping("/admin/category/list")
    public String showList(Model model)
    {
        model.addAttribute("category1",categoryDAO.findAll());
        return "/admin/category-list";
    }

    //@ResponseBody
    @Secured({"ADMIN","USER"})
    @RequestMapping("/user/category/list")
    public String userShowList(Model model)
    {
        model.addAttribute("category1",categoryDAO.findAll());
        return "/user/category-list";
    }
}
