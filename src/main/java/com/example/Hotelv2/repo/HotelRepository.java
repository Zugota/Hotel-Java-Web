package com.example.Hotelv2.repo;


import com.example.Hotelv2.models.Hotel;
import com.example.Hotelv2.models.Order;
import com.example.Hotelv2.models.User;
import com.example.Hotelv2.models.UserProfile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface HotelRepository extends CrudRepository<Hotel, Long> {

}
