package com.accenture.service.mapper;

import com.accenture.model.Pizza;
import com.accenture.service.dto.PizzaRequestDto;
import com.accenture.service.dto.PizzaResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {IngredientMapper.class})
public interface PizzaMapper {

Pizza toPizza(PizzaRequestDto pizzaRequestDto);

PizzaResponseDto toPizzaResponseDto(Pizza pizza);
}
