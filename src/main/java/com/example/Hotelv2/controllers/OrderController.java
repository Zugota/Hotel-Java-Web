package com.example.Hotelv2.controllers;

import com.example.Hotelv2.models.Global;
import com.example.Hotelv2.models.Hotel;
import com.example.Hotelv2.models.Order;
import com.example.Hotelv2.models.UserProfile;
import com.example.Hotelv2.repo.HotelRepository;
import com.example.Hotelv2.repo.OrderRepository;
import com.example.Hotelv2.repo.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

/**
 * Контроллер, отвечающий за манипуляции с сущностью "Бронь"
 */
@Controller
public class    OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private HotelRepository hotelRepository;

    /**
     *
     * @param id - параметр идентификатора отеля
     * @param summ - параметр общей стоимости брони
     * @param totalDays - параметр количества суток брони
     * @param date1 - параметр даты заселения
     * @param date2 - параметр даты выселения
     * @param redirectAttributes - объект класса, передающий атрибуты в другой контроллер
     * @param model
     * @return - создает и сохраняет бронь в базу данных и переадресовывает на контроллер подробностей заказа
     */
    @PostMapping("/hotel/{id}/order")
    public String orderPost(@PathVariable(value = "id") long id, @RequestParam String summ, @RequestParam Integer totalDays,
                            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date1,
                            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date2,
                            RedirectAttributes redirectAttributes, Model model) {
        Optional<Hotel> hotel = hotelRepository.findById(id);
        Double totalCost = Double.parseDouble(summ.replaceAll(" ", ""));
        if (!userProfileRepository.existsById(Global.TempID)) return "redirect:/login";
        Optional<UserProfile> up = userProfileRepository.findById(Global.TempID);


        Order order = new Order(up.get(), hotel.get(), totalCost, date1, date2, totalDays);
        orderRepository.save(order);

        model.addAttribute(order);
        redirectAttributes.addAttribute("orderId", order.getOrderID());

        return "redirect:/order/{orderId}";
        /*return "order-details";*/
    }

    /**
     *
     * @param orderId - параметр идентификатора брони
     * @param model
     * @return - переадресовывает на страницу подробностей заказа
     */
    @GetMapping("/order/{orderId}")
    public String orderDetails(@PathVariable(value = "orderId") long orderId, Model model) {
        Optional<Order> order = orderRepository.findById(orderId);
        ArrayList<Order> res = new ArrayList<>();
        order.ifPresent(res::add);
        model.addAttribute("order", res);
        return "order-details";
    }

    /**
     *
     * @param orderId - параметр идентификатора брони
     * @param redirectAttributes - объект класса, передающий атрибуты в другой контроллер
     * @param model
     * @return - удаляет бронь и переадресовывает на контроллер списка броней
     */
    @PostMapping("/order/{orderId}/delete")
    public String orderDelete(@PathVariable(value = "orderId") long orderId,
                             RedirectAttributes redirectAttributes, Model model) {
        if (!checkAuth()) return "redirect:/login";
        Optional<UserProfile> userProfile = userProfileRepository.findById(Global.TempID);

        Optional<Order> order = orderRepository.findById(orderId);
        orderRepository.delete(order.get());

        redirectAttributes.addAttribute("id", userProfile.get().getUserProfileId());
        return "redirect:/getProfile/{id}/orders";
    }

    /**
     *
     * @return - метод проверяет, авторизован ли пользователь
     */
    public boolean checkAuth() {
        return userProfileRepository.existsById(Global.TempID);
    }
}