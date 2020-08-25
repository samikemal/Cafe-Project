package com.burak.cafe.controller;

import com.burak.cafe.dao.CategoryDAO;
import com.burak.cafe.dao.UserDAO;
import com.burak.cafe.dto.CategoryDto;
import com.burak.cafe.dto.ProductDto;
import com.burak.cafe.entity.CategoryEntity;
import com.burak.cafe.entity.OrderEntity;
import com.burak.cafe.entity.ProductEntity;
import com.burak.cafe.dao.ProductDAO;
import com.burak.cafe.repositories.CategoryRepository;
import com.burak.cafe.repositories.OrderRepository;
import com.burak.cafe.repositories.ProductRepository;
import com.burak.cafe.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.util.Date;
import java.util.List;

import static java.lang.Integer.parseInt;

@Controller
@RequestMapping("/")
public class ProductController {

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserRepository userRepository;


    //@ResponseBody
    @Secured({"ADMIN"})
    @RequestMapping("/admin/product/add")
    public String addProduct(Model model)
    {
        ProductEntity productEntity=new ProductEntity();
        //categoryleri listelere ekliyor
        List<CategoryDto> categories=categoryDAO.findAll();
        model.addAttribute("product",productEntity);
        model.addAttribute("categories",categories);
        return "/admin/product-form";
    }

    //@ResponseBody
    @Secured({"ADMIN"})
    @PostMapping("/admin/product/save")
    public String saveProduct(@ModelAttribute("product") ProductEntity product)
    {
        productRepository.save(product);
        return "redirect:/admin/home";
    }

    //@ResponseBody
    @Secured({"ADMIN"})
    @RequestMapping(value = "admin/orders/makeReady/{id}", method = RequestMethod.GET)
    public String makeReady(@PathVariable int id) {
        //@PathVariable PathVariable anotasyonu url de bulunan değişkenleri ilgili metodlara aktararak ilgili metodun işlemi yapmasını sağlamaktayız.
        // Bu değişkenler bir ya da birden fazla olabilmektedir.
        //örnek makeReady/1 makeReady/2 makeReady/3
        OrderEntity orderEntity = orderRepository.findByID(id);
        //UserEntity userEntity=userRepository.findByName()
        Date date = new Date();
        orderEntity.setUpdateTime(date);
        orderEntity.setStatus("ready to send");
        orderRepository.save(orderEntity);
        return "redirect:/admin/orderss";
    }

    //@ResponseBody
    @Secured({"ADMIN"})
    @RequestMapping( value = "admin/orders/deleteOrder/{id}", method = RequestMethod.GET)
    public ModelAndView deleteOrder(@PathVariable("id") int id){
        ModelAndView mv = new ModelAndView("redirect:/admin/orderss");
        orderRepository.deleteById(id);
        return mv;
    }

    //@ResponseBody
    @Secured({"ADMIN"})
    @RequestMapping("admin/orderss")
    public String showOrders(Model model, @AuthenticationPrincipal UserDetails currentUser){
        //@AuthenticationPrincipal ile user‘ın o an sisteme giriş yapmış kullanıcı olduğunu belirtiyor
        // ve bu user‘ı ana sayfada kullanacağımız model olarak döndürüyoruz.

        //şuan giren userı al
        userDAO.findUserByEmail(currentUser.getUsername());

        model.addAttribute("orders", orderRepository.findAll());
        //şuan girmiş olan user ı modele ekliyor ve onun siparişlerini gösteriyor
        model.addAttribute("products",userDAO.findUserByEmail(currentUser.getUsername()).getOrderEntities());
        return "/admin/orderss";

    }

    //@ResponseBody
    @Secured({"ADMIN","USER"})
    @RequestMapping("/user/product/list")
    public String listProduct(Model model){
        List<ProductDto> productList =  productDAO.findAll();
        for (int i=0; i < productList.size(); i++){
            CategoryEntity categoryEntity = categoryRepository.findByID(productList.get(i).getProductCategory());
            productList.get(i).setProductCategory(parseInt(categoryEntity.getCategory()));
            productDAO.save(productList.get(i));
        }

        model.addAttribute("categories", categoryDAO.findAll());
        model.addAttribute("product", productRepository.findAll());
        return "/user/product-list";
    }

    //@ResponseBody
    @Secured({"ADMIN"})
    @RequestMapping("/admin/product/list")
    public String showProduct(Model model)
    {
       List<CategoryDto> categoryList=categoryDAO.findAll();
        model.addAttribute("categories",categoryList);

        model.addAttribute("urunler",productDAO.findAll());
        return "/admin/product-list";
    }
}
