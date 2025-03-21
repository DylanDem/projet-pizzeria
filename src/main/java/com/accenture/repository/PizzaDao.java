package com.accenture.repository;


import com.accenture.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PizzaDao extends JpaRepository<Pizza, Integer> {

    Optional<Pizza> findByNom(String nom);



}
