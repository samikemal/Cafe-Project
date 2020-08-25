package com.burak.cafe.controller;

import com.burak.cafe.dao.CategoryDAO;
import com.burak.cafe.dao.Imp.UserDAOImp;
import com.burak.cafe.dao.ProductDAO;
import com.burak.cafe.dto.CategoryDto;
import com.burak.cafe.dto.ProductDto;
import com.burak.cafe.dto.UserDto;
import com.burak.cafe.entity.OrderEntity;
import com.burak.cafe.entity.UserEntity;
import com.burak.cafe.repositories.OrderRepository;
import com.burak.cafe.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
@RequestMapping("/user")
public class User2 {

    @Autowired
    ProductDAO productDAO;

    @Autowired
    private UserDAOImp userDao;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CategoryDAO categoryDAO;

    @Autowired
    UserRepository userRepository;

    UserEntity userEntity=new UserEntity();

    // @ResponseBody
    @RequestMapping(value="/home", method = RequestMethod.GET)
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        //burası kimlik doğrulama alıyor
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userDao.findUserByEmail(auth.getName());
        modelAndView.addObject("userName", "Welcome " + userEntity.getFirstName() + " " + userEntity.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
        modelAndView.setViewName("user/home");
        return modelAndView;
    }

    //@ResponseBody
    @RequestMapping(value = "/sort",method = RequestMethod.POST)
    public String showProduct(@ModelAttribute("abc") int id, Model model)
    {
        List<ProductDto> productsByCategory;

        if(id==0 ){//eger id sıfırsa listele
            productsByCategory = productDAO.findAll();
        }else {//değilse id sine göre listele
            productsByCategory = productDAO.listByCategory(id);
        }
        List<CategoryDto> categories = categoryDAO.findAll();
        model.addAttribute("product",productsByCategory);
        model.addAttribute("categories",categories);
        model.addAttribute("selectedProduct",id);
        return "/user/product-list";
    }

    //@ResponseBody
    @RequestMapping("/order/list")
    public String orderProductList(Model model){
        model.addAttribute("products", productDAO.findAll());
        return "/user/order";
    }

    //@ResponseBody
    @RequestMapping(value = "/order/giveOrder", method = RequestMethod.POST)
    public String giveOrder(@RequestParam("orders") int[] productList, @AuthenticationPrincipal UserDetails currentUser){
        //@AuthenticationPrincipal ile user‘ın o an sisteme giriş yapmış kullanıcı olduğunu belirtiyor.
        List<ProductDto> products = new ArrayList<ProductDto>();
        //hashset in mantığı arrayle aynı ama hashsetde aynı elamanı iki defa ekliyemiyoruz
        //çünkü aynı orderı birden fazla user sipariş edebilir
        Set<OrderEntity> orderEntities = new HashSet<OrderEntity>();
        userEntity = userRepository.findByEmail(currentUser.getUsername());
        Date date = new Date();

        for(int i=0; i<productList.length; i++){
            OrderEntity orderEntity = new OrderEntity();
            products.add(productDAO.findByID(productList[i]));
            orderEntity.setStatus("preparing");
            orderEntity.setProductName(products.get(i).getProductName());
            orderEntity.setUserID(userEntity.getId());
            orderEntity.setStartTime(date);
            orderEntities.add(orderEntity);
        }

        userEntity.setOrderEntity(orderEntities);
        userDao.update(userEntity);
        return "redirect:/user/home";
    }

    //@ResponseBody
    @RequestMapping("/order/status")
    public String orderStatus(Model model, @AuthenticationPrincipal UserDetails currentUser){
       // String name=String.valueOf(userDao.findUserByEmail(currentUser.getUsername()));
        int id = userDao.findUserByEmail(currentUser.getUsername()).getId();
        model.addAttribute("orders", orderRepository.findByUser(id));
       // model.addAttribute("orders",String.valueOf(orderRepository.findByName(name)));
        return "/user/order-status";
    }

   // @ResponseBody
    @RequestMapping( value = "/orders/completeOrder/{id}", method = RequestMethod.GET)
    public ModelAndView completeOrder(@PathVariable("id") int id){
        ModelAndView mv = new ModelAndView("redirect:/user/order/status");//requestmapping yolu
        orderRepository.deleteById(id);
        return mv;
    }

}
