package com.accenture.service;

import com.accenture.exception.PizzaException;
import com.accenture.model.Pizza;
import com.accenture.repository.PizzaDao;
import org.springframework.stereotype.Service;


@Service
public class PizzaServiceImpl implements PizzaService {

    private final PizzaDao pizzaDao;


    public PizzaServiceImpl(PizzaDao pizzaDao) {
        this.pizzaDao = pizzaDao;
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
        if(pizza.getTailles() == null)
            throw new PizzaException("La taille est obligatoire");
    }
}
