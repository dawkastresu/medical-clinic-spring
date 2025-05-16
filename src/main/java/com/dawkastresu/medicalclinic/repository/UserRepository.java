package com.dawkastresu.medicalclinic.repository;

import com.dawkastresu.medicalclinic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAll();

}
