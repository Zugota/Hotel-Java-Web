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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Контроллер, отвечающий за манипуляции с сущностью "Отель"
 */
@Controller
public class HotelController {
    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private OrderRepository orderRepository;

    /**
     *
     * @param model
     * @return - переадресовывает на страницу списка отелей для пользователя
     */
    @GetMapping("/hotels")
    public String getHotels(Model model) {
        Iterable<Hotel> hotels = hotelRepository.findAll();
        model.addAttribute("hotels", hotels);
        //model.addAttribute("firstname", Global.TempUserProfile.getFirstname());
        return "hotels";
    }

    /**
     *
     * @param id - идентификатор отеля
     * @param model
     * @return - переадресовывает на html-страницу описания выбранного отеля
     */
    @GetMapping("/hotel/{id}")
    public String hotelDescription(@PathVariable(value = "id") long id, Model model) {
        if (!hotelRepository.existsById(id)) {
            return "redirect:/hotels";
        }
        Optional<Hotel> hotel = hotelRepository.findById(id);
        ArrayList<Hotel> res = new ArrayList<>();
        hotel.ifPresent(res::add);
        if (hotel.get().getViews() == null) hotel.get().setViews(0L);
        hotel.get().addViews();
        hotelRepository.save(hotel.get());
        model.addAttribute("hotel", res);
        return "hotel-details";
    }
}