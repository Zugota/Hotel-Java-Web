package com.example.Hotelv2.repo;

import com.example.Hotelv2.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    /**
     *
     * @param username - параметр логина пользователя
     * @return
     */
    @Query("Select u from User u WHERE u.login=:username")
    User findByUsername(@Param("username") String username);
}
