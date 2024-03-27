package com.example.Hotelv2.repo;

import com.example.Hotelv2.models.User;
import com.example.Hotelv2.models.UserProfile;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserProfileRepository extends CrudRepository<UserProfile, Long> {
    /**
     *
     * @param user - параметр идентификатора пользователя
     * @return
     */
    @Query("Select up from UserProfile up WHERE up.user=:user")
    UserProfile findByUserKey(@Param("user") User user);
}