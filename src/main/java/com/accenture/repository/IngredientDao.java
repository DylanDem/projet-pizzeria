package com.accenture.repository;

import com.accenture.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientDao extends JpaRepository<Ingredient, Integer> {
}
