package com.burak.cafe.controller;

import com.burak.cafe.dao.UserDAO;
import com.burak.cafe.dto.UserDto;
import com.burak.cafe.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    private UserDAO userDao;
    /*
//başlangıçta ki açılan sayfa
    @RequestMapping(value={"/"}, method = RequestMethod.GET)
    public ModelAndView intro(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");//o htmle yönlenmemizi sağlıyo
        return modelAndView;
    }*/


    //@ResponseBody
    //burda value da "/" da ekleeyebiliriz
    @RequestMapping(value={"/","/login"}, method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");//o htmle yönlenmemizi sağlıyo
        return modelAndView;
    }

    //ilk açıldıgında registration sayfası o metod
    //@ResponseBody
    @RequestMapping(value="/registration", method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        UserEntity userEntity = new UserEntity();
        modelAndView.addObject("user", userEntity);//userı ekliyor
        modelAndView.setViewName("registration");//registration.html e yönlendiriyo
        return modelAndView;
    }


    //@ResponseBody
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid UserEntity user, BindingResult bindingResult) {
        //@Valid Temel olarak, yönteme gönderdiğiniz verilerin geçerli olup olmadığını kontrol eder
        //BindingResult doğrulanmış model nesnesinin hemne arkasından gelmelidir Springin nesneyi doğrulamasını sağlar
        ModelAndView modelAndView = new ModelAndView();
        //mailleri alıyor girilen mailleri
        UserDto userExists = userDao.findUserByEmail(user.getEmail());

        if (userExists != null) {//aynı e mailde kullanıcı varmı
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            userDao.save(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new UserEntity());
            modelAndView.setViewName("registration");//registratgion html sayfasına yönlendiriyı
        }
        return modelAndView;
    }

    //@ResponseBody
    @Secured({"ADMIN"})
    @RequestMapping(value="/admin/home", method = RequestMethod.GET)
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        //burası kimlik doğrulama alıyor
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //isimleri alıyor
        UserDto user = userDao.findUserByEmail(auth.getName());
        modelAndView.addObject("userName", "Welcome " + user.getFirstName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }
}
