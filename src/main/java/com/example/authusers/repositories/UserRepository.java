package com.example.authusers.repositories;


import com.example.authusers.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    boolean existsUserByEmail(String email);
    boolean existsUserByEmailAndAndPassword(String email, String password);

    Optional<User> findUserByEmail(String email);
}
