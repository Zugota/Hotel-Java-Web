package com.example.Hotelv2.repo;

import com.example.Hotelv2.models.Hotel;
import com.example.Hotelv2.models.Order;
import com.example.Hotelv2.models.User;
import com.example.Hotelv2.models.UserProfile;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {
    /**
     *
     * @param user - параметр идентификатора пользователя
     * @return - возвращает пользователя по идентификатору
     */
    @Query("Select ord from Order ord WHERE ord.userProfile=:user")
    Iterable<Order> findOrdersByUser(@Param("user") UserProfile user);

    /**
     *
     * @param hotel - параметр идентификатора отеля
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM Order ord WHERE ord.hotel=:hotel")
    void deleteAllOrdersInHotel(@Param("hotel") Hotel hotel);
}
