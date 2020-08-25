package com.burak.cafe.controller;

import com.burak.cafe.dao.RoleDAO;
import com.burak.cafe.dao.UserDAO;
import com.burak.cafe.dto.UserDto;
import com.burak.cafe.entity.UserEntity;
import com.burak.cafe.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/user")
public class UserController {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    RoleDAO roleDAO;

    @Autowired
    UserRepository userRepository;

    //@ResponseBody
    @Secured({"ADMIN"})
    @RequestMapping("/form")
    public String showUserForm(Model model)
    {
        UserEntity user=new UserEntity();
        model.addAttribute("user",user);
        return "/admin/user-form";
    }

    //@ResponseBody
    @Secured({"ADMIN"})
    @RequestMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") UserEntity user)
    {
        userDAO.save(user);
        return "redirect:/";
       // return "/admin/home";
    }

    /*
   daha önce oluşturduğumuz user modelini buluyor.
    */
   // @ResponseBody
    @Secured({"ADMIN"})
    @RequestMapping("/list")
    public String listUsers(Model model)
    {
        model.addAttribute("user",userDAO.findAll());//o user modelini ekrana yazdırmak için
        // attribute ekliyoruz ve findAll metodulya dbde olan bütün userları ona atıyoruz.
        return "/admin/user-list";
    }

    //@ResponseBody
    @Secured({"ADMIN"})
    @RequestMapping(value = "/deleteUser/{id}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable int id) {
        UserDto user = userDAO.findOne(id);
        user.getRoles().stream().forEach(i -> roleDAO.deleteRole(i.getId()));
        roleDAO.deleteRole(id);
        userDAO.deleteUser(id);
        return "redirect:/";
    }




    //@PathVariable PathVariable anotasyonu url de bulunan değişkenleri ilgili metodlara aktararak ilgili metodun işlemi yapmasını sağlamaktayız.
    // Bu değişkenler bir ya da birden fazla olabilmektedir.
    //örnek makeReady/1 makeReady/2 makeReady/3

    /*
       @RequestMapping( value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView doEdit(@PathVariable("id") Integer id){
        ModelAndView mv = new ModelAndView("edit");
        mv.addObject("lists",appRepo.findById(id).orElse(null));
        return mv;
    }
     */

    //@ResponseBody
    @RequestMapping( value = "/doEdit/{id}", method = RequestMethod.GET)
    public ModelAndView doEdit(@PathVariable("id") Integer id){
        ModelAndView mv = new ModelAndView("/admin/edit");
        mv.addObject("users",userRepository.findById(id).orElse(null));
        return mv;
    }


   // @ResponseBody
    @RequestMapping(value = "/editSave", method = RequestMethod.POST)
    public ModelAndView doSave(@RequestParam("id") Integer id,@RequestParam("firstname") String firstName,
                               @RequestParam ("lastname") String lastName){//@RequestParam annotation’ini jsp içindeki değeri alıyor yeni oluşturdugun variablea atıyor
        ModelAndView mv = new ModelAndView("redirect:/");
        UserEntity users ;

        if(id != null)
        {
            users=userRepository.findById(id).orElse(null);

        }
        else
        {
            users=new UserEntity();
        }

        users.setFirstName(firstName);
        users.setLastName(lastName);
       // users.setTc(tc);


/*
        UserEntity u = userRepository.findByTc(users.getTc());
        if(u != null)
        {
            ModelAndView v = new ModelAndView("/hataa");
            return v;
        }
        */

        userRepository.save(users);
        return mv;

    }









}
