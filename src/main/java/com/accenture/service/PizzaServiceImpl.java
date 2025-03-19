package com.accenture.service;

import com.accenture.exception.PizzaException;
import com.accenture.model.Ingredient;
import com.accenture.model.Pizza;
import com.accenture.repository.IngredientDao;
import com.accenture.repository.PizzaDao;
import com.accenture.service.dto.PizzaRequestDto;
import com.accenture.service.dto.PizzaResponseDto;
import org.springframework.stereotype.Service;


@Service
public class PizzaServiceImpl implements PizzaService {

    private final PizzaDao pizzaDao;
    private final IngredientDao ingredientDao;


    public PizzaServiceImpl(PizzaDao pizzaDao, IngredientDao ingredientDao) {
        this.pizzaDao = pizzaDao;
        this.ingredientDao = ingredientDao;
    }

    @Override
    public Pizza ajouter(Pizza pizza) {
        verifierPizza(pizza);

        return pizzaDao.save(pizza);
    }




//    =====================================================================================================
//
//                                        METHODES PRIVEES
//
//    =====================================================================================================


    private static void verifierPizza(Pizza pizza) {
        if(pizza == null)
            throw new PizzaException("Merci de saisir votre pizza");
        if(pizza.getIngredients() == null)
            throw new PizzaException("Les ingrédients sont obligatoires");
        if(pizza.getNom() == null)
            throw new PizzaException("Le nom est obligatoire");
        if(pizza.getTarifs() == null)
            throw new PizzaException("Le tarif est obligatoire");

    }

    private Pizza toPizza(PizzaRequestDto pizzaRequestDto) {
        Pizza pizza = new Pizza();

        pizza.setIngredients(ingredientDao.findAllById(pizzaRequestDto.idIngredient()));
        pizza.setNom(pizzaRequestDto.nom());
        pizza.setTarifs(pizzaRequestDto.tarifs());

    return pizza; }

    private PizzaResponseDto toPizzaResponse(Pizza pizza) {
        return new PizzaResponseDto(pizza.getId(), pizza.getNom(),
                pizza.getIngredients().stream().map(Ingredient::getNom).toList()
                , pizza.getTarifs());
    }
}
