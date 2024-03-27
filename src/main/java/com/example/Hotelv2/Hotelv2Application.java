package com.example.Hotelv2;

import com.example.Hotelv2.models.Global;
import com.example.Hotelv2.models.Hotel;
import com.example.Hotelv2.models.User;
import com.example.Hotelv2.models.UserProfile;
import com.example.Hotelv2.repo.HotelRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 @author бИЦ-211 Минкин Иван Максимович
 */

@SpringBootApplication
public class Hotelv2Application {

	public static void main(String[] args) {
		Global.TempID = 0L;
		Global.TempUser = new User();
		Global.TempUserProfile = new UserProfile();
		SpringApplication.run(Hotelv2Application.class, args);
	}
}
