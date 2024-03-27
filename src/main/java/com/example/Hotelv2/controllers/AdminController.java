package com.example.Hotelv2.controllers;

import com.example.Hotelv2.models.Hotel;
import com.example.Hotelv2.models.Order;
import com.example.Hotelv2.models.User;
import com.example.Hotelv2.models.UserProfile;
import com.example.Hotelv2.repo.HotelRepository;
import com.example.Hotelv2.repo.OrderRepository;
import com.example.Hotelv2.repo.UserProfileRepository;
import com.example.Hotelv2.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * Контроллер, отвечающий за функционал Администратора
 */
@Controller
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private OrderRepository orderRepository;

    /**
     * @param model
     * @return - передаёт список всех пользователей в html-страницу
     */
    @GetMapping("/checkUsers")
    public String checkUsers(Model model) {
        Iterable<UserProfile> userProfiles = userProfileRepository.findAll();
        model.addAttribute("users", userProfiles);
        return "/admin/check-users";
    }

    /**
     * @param id - идентификатор для сущности "UserProfile" и "User"
     * @param model
     * @return - удаляет пользователя и возвращает на контроллер вывода списка пользователей
     */
    @PostMapping("/checkUsers/{id}/delete")
    public String deleteUser(@PathVariable(value = "id") long id, Model model) {
        userProfileRepository.deleteById(id);
        return "redirect:/checkUsers";
    }

    /**
     *
     * @param model
     * @return - передаёт список всех броней в html-страницу
     */
    @GetMapping("/checkOrders")
    public String checkOrders(Model model) {
        Iterable<Order> orders = orderRepository.findAll();
        model.addAttribute("orders", orders);
        return "/admin/check-orders";
    }

    /**
     *
     * @param id - идентификатор для сущности "UserProfile" и "User"
     * @param model
     * @return - удаляет бронь и возвращает на контроллер вывода списка броней
     */
    @PostMapping("/checkOrders/{id}/delete")
    public String deleteOrder(@PathVariable(value = "id") long id, Model model) {
        orderRepository.deleteById(id);
        return "redirect:/checkOrders";
    }

    /**
     *
     * @param model
     * @return - передаёт список всех отелей в html-страницу
     */
    @GetMapping("/checkHotels")
    public String checkHotels(Model model) {
        Iterable<Hotel> hotels = hotelRepository.findAll();
        model.addAttribute("hotels", hotels);
        return "/admin/check-hotels";
    }

    /**
     *
     * @param id - идентификатор для сущности "UserProfile" и "User"
     * @param model
     * @return - удаляет отель и возвращает на контроллер вывода списка отелей
     */
    @PostMapping("/checkHotels/{id}/delete")
    public String deleteHotel(@PathVariable(value = "id") long id, Model model) {
        orderRepository.deleteAllOrdersInHotel(hotelRepository.findById(id).get());
        hotelRepository.deleteById(id);
        return "redirect:/checkHotels";
    }

    /**
     *
     * @return - переадресовывает на html-форму добавления отеля
     */
    @GetMapping("/addHotel")
    public String redirectaddHotel() {
        return "/admin/add-hotel";
    }

    /**
     *
     * @param file - параметр файла изображения отеля
     * @param description - параметр описания отеля
     * @param name - параметр названия отеля
     * @param price - параметр цены за сутки
     * @return - переадресовывает на контроллер вывода списка отелей
     */
    @PostMapping("/addHotel")
    public String addHotel(@RequestParam("fileInput") MultipartFile file, @RequestParam("description") String description, @RequestParam("name") String name,
                                   @RequestParam("price") Double price) {

        if (!file.isEmpty()) {
            try {
                file.transferTo(new File("D:\\Учёба\\3 курс\\Enterprise\\бИЦ-211_Минкин_Курсовой_Enterprise\\Hotelv2\\src\\main\\resources\\static\\img\\hotels\\", file.getOriginalFilename()));

                Hotel hotel = new Hotel(name, description, price, "/img/hotels/" + file.getOriginalFilename(), 0L);
                hotelRepository.save(hotel);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return "redirect:/checkHotels";
        } else {
            return "redirect:/checkHotels";
        }
    }
}