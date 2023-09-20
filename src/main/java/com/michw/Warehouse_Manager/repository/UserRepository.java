package com.michw.Warehouse_Manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.michw.Warehouse_Manager.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
