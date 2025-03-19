package com.accenture.service;

import com.accenture.exception.PizzaException;
import com.accenture.model.Pizza;
import com.accenture.repository.PizzaDao;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PizzaServiceImpl implements PizzaService {
    private static final String PIZZA_N_EXISTE_PAS = "La pizza n'existe pas";
    private final PizzaDao pizzaDao;

    public PizzaServiceImpl(PizzaDao pizzaDao) {
        this.pizzaDao = pizzaDao;
    }

    @Override
    public Pizza ajouter(Pizza pizza) {
        verifierPizza(pizza);
        return pizzaDao.save(pizza);
    }

    @Override
    public List<Pizza> trouverTous() {
        return pizzaDao.findAll();
    }

    @Override
    public Pizza trouver(int idPizza) {
        return pizzaDao.findById(idPizza).orElseThrow(() -> new EntityNotFoundException("La pizza n'existe pas"));
    }

    @Override
    public void supprimer(int idPizza) {
        if (pizzaDao.existsById(idPizza)) pizzaDao.deleteById(idPizza);
        else throw new EntityNotFoundException(PIZZA_N_EXISTE_PAS);
    }

 //    =====================================================================================================////
//
//                                               METHODES PRIVEES////
//
//    =====================================================================================================


    private static void verifierPizza(Pizza pizza) {
        if (pizza == null)
            throw new PizzaException("Merci de saisir votre pizza");
        if (pizza.getIngredients() == null)
            throw new PizzaException("Les ingrédients sont obligatoires");
        if (pizza.getNom() == null)
            throw new PizzaException("Le nom est obligatoire");
        if (pizza.getTarifs() == null) throw new PizzaException("Le tarif est obligatoire");
    }
}