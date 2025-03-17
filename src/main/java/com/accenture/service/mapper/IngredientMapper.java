package com.accenture.service.mapper;

import com.accenture.model.Ingredient;
import com.accenture.service.dto.IngredientDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IngredientMapper {

    Ingredient toIngredient(IngredientDto ingredientDto);

    IngredientDto toIngredientDto (Ingredient ingredient);
}
