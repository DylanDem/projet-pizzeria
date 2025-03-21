package com.accenture.service;

import com.accenture.exception.PizzaException;
import com.accenture.model.Ingredient;
import com.accenture.model.Pizza;
import com.accenture.repository.IngredientDao;
import com.accenture.repository.PizzaDao;
import com.accenture.service.dto.PizzaRequestDto;
import com.accenture.service.dto.PizzaResponseDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PizzaServiceImpl implements PizzaService {
    private static final String PIZZA_N_EXISTE_PAS = "La pizza n'existe pas";
    private final PizzaDao pizzaDao;
    private final IngredientDao ingredientDao;

    public PizzaServiceImpl(PizzaDao pizzaDao, IngredientDao ingredientDao) {
        this.pizzaDao = pizzaDao;
        this.ingredientDao = ingredientDao;
    }

    @Override
    public Pizza trouverParNom(String pizza) {
        return (pizzaDao.findByNom(pizza)
                .orElseThrow(() -> new EntityNotFoundException("La pizza n'existe pas")));}



    @Override
    public PizzaResponseDto ajouter(PizzaRequestDto pizzaRequestDto) throws PizzaException {
        verifierPizza(pizzaRequestDto);
        Pizza pizza = toPizza(pizzaRequestDto);
        Pizza pizzaRetour = pizzaDao.save(pizza);
        return toPizzaResponse(pizzaRetour);
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


    public Pizza toPizza(PizzaRequestDto pizzaRequestDto) {
        Pizza pizza = new Pizza();
        pizza.setIngredients(ingredientDao.findAllById(pizzaRequestDto.idIngredient()));
        pizza.setNom(pizzaRequestDto.nom());
        pizza.setTarifs(pizzaRequestDto.tarifs());
        return pizza;
    }

    public PizzaResponseDto toPizzaResponse(Pizza pizza) {
        return new PizzaResponseDto(
                pizza.getId(),
                pizza.getNom(),
                pizza.getIngredients()
                        .stream()
                        .map(Ingredient::getNom)
                        .toList(), pizza.getTarifs());
    }

    //    =====================================================================================================////
//
//                                               METHODES PRIVEES////
//
//    =====================================================================================================


        private static void verifierPizza(PizzaRequestDto pizzaRequestDto) {
            if (pizzaRequestDto == null)
                throw new PizzaException("Merci de saisir votre pizza");
            if (pizzaRequestDto.idIngredient() == null)
                throw new PizzaException("Les ingrédients sont obligatoires");
            if (pizzaRequestDto.nom() == null)
                throw new PizzaException("Le nom est obligatoire");
            if (pizzaRequestDto.nom().isBlank())
                throw new PizzaException("Le nom est obligatoire");
            if (pizzaRequestDto.tarifs() == null) throw new PizzaException("Le tarif est obligatoire");
        }
}