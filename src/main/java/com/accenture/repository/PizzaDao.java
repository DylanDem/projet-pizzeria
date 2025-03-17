package com.accenture.repository;


import com.accenture.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaDao extends JpaRepository<Pizza, Integer> {



}
