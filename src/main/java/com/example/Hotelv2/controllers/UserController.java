package com.example.Hotelv2.controllers;

import com.example.Hotelv2.models.Global;
import com.example.Hotelv2.models.Order;
import com.example.Hotelv2.models.User;
import com.example.Hotelv2.models.UserProfile;
import com.example.Hotelv2.repo.OrderRepository;
import com.example.Hotelv2.repo.UserProfileRepository;
import com.example.Hotelv2.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Контроллер, отвечающий за функционал Администратора
 */
@Controller
public class UserController {
    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/main-authorized")
    public String mainAuthorized() {

        return "main-authorized";
    }

    /**
     *
     * @param model
     * @param redirectAttributes объект класса, передающий атрибуты в другой контроллер
     * @return - првоеряет, если пользователь не авторизован, то перенаправляет на контроллер авторизации, если пользователь авторизован, то переадресовывает на контроллер вывода профиля пользователя
     */
    @GetMapping("/userProfiler")
    public String checkUserProfile(Model model, RedirectAttributes redirectAttributes) {
        if (!userProfileRepository.existsById(Global.TempID)) return "redirect:/login";
        redirectAttributes.addAttribute("id", Global.TempID);
        return "redirect:/getProfile/{id}";
    }

    /**
     *
     * @param id - параметр идентификатора пользователя
     * @param model
     * @return - переадресовывает на html-страниу профиля пользователя
     */
    @GetMapping("/getProfile/{id}")
    public String getUserProfile(@PathVariable("id") long id, Model model) {
        Optional<UserProfile> userProfile = userProfileRepository.findById(id);
        Optional<User> user = userRepository.findById(id);


        ArrayList<UserProfile> res = new ArrayList<>();
        userProfile.ifPresent(res::add);

        model.addAttribute("userProfile", res);

        return "userProfile";
    }

    /**
     *
     * @param id - параметр идентификатора пользователя
     * @param model
     * @return - переадресовывает на html-страницу списка броней пользователя
     */
    @GetMapping("/getProfile/{id}/orders")
    public String getUserOrders(@PathVariable("id") long id, Model model) {
        Optional<UserProfile> userProfile = userProfileRepository.findById(id);
        Iterable<Order> orders = orderRepository.findOrdersByUser(userProfile.get());
        model.addAttribute("orders", orders);
        return "userOrders";
    }
}