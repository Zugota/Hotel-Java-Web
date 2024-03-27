package com.example.Hotelv2.controllers;

import com.example.Hotelv2.models.Global;
import com.example.Hotelv2.models.User;
import com.example.Hotelv2.models.UserProfile;
import com.example.Hotelv2.repo.UserProfileRepository;
import com.example.Hotelv2.repo.UserRepository;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Контроллер, отвечающий за регистрацию и авторизацию"
 */
@Controller
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    /**
     *
     * @param model
     * @return - если пользователь не зарегистрирован - переадресовывает на общую главную страницу, если пользователь зарегистрирован - переадресовывает на главную страницу зарегистрированного пользователя
     */
    @GetMapping("/")
    public String greeting(Model model) {
        if (!checkAuth()) return "main";
        model.addAttribute("firstname", Global.TempUserProfile.getFirstname());
        return "main-authorized";
    }

    /**
     *
     * @param model
     * @return - переадресовывает на страницу регистрации
     */
    @GetMapping("/signUp")
    public String signUp(Model model) {
        Global.TempID = 0L;
        Global.TempUserProfile = null;
        Global.TempUser = null;
        return "signUp";
    }

    /**
     *
     * @param model
     * @return - переадресовывает на страницу авторизации
     */
    @GetMapping("/login")
    public String login(Model model) {
        Global.TempID = 0L;
        Global.TempUserProfile = null;
        Global.TempUser = null;
        return "login";
    }

    /**
     *
     * @param login - параметр логина пользователя
     * @param password - параметр пароля пользователя
     * @param model
     * @return - если пользователь есть в базе данных и введённе данные соответствуют введённым данным из базы данных, то переадресовывает на главную страницу, иначе - снова на страницу авторизации
     */
    @PostMapping("/login")
    public String userLogin(@RequestParam String login, @RequestParam String password, Model model) {
        if (login.equals("admin") && password.equals("admin")) return "/admin/admin-main";
        User user = userRepository.findByUsername(login);
        if (user != null) {

            if (!user.getPassword().equals(password)) return "failed-login";
            UserProfile userProfile = userProfileRepository.findByUserKey(user);
            Global.TempUserProfile = userProfile;
            Global.TempID = user.getUserID();
            model.addAttribute("user", user);
            model.addAttribute("firstname", Global.TempUserProfile.getFirstname());
        }
        else return "failed-login";

        return "main-authorized";
    }

    /**
     *
     * @param firstname - параметр имени пользователя
     * @param login - параметр логина пользователя
     * @param password - параметр пароля пользователя
     * @param model
     * @return - сохраняет нового пользователя в базу данных и переадресовывает на страницу авторизации
     */
    @PostMapping("/signUp")
    public String userPostSignUp(@RequestParam String firstname, @RequestParam String login,
                                 @RequestParam String password, Model model) {

        User user = new User(login, password);
        UserProfile userProfile = new UserProfile(user, firstname);

        userRepository.save(user);
        userProfileRepository.save(userProfile);


        return "/login";
    }

    /**
     *
     * @return - метод проверяет, авторизован ли пользователь
     */
    public boolean checkAuth() {
        return userProfileRepository.existsById(Global.TempID);
    }
}