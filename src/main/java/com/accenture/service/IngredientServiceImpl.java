package com.accenture.service;

import com.accenture.exception.IngredientException;
import com.accenture.model.Ingredient;
import com.accenture.repository.IngredientDao;
import org.springframework.stereotype.Service;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientDao ingredientDao;

    public IngredientServiceImpl(IngredientDao ingredientDao) {
        this.ingredientDao = ingredientDao;
    }

    private static void verifierIngredient(Ingredient ingredient) {
        if (ingredient == null)
            throw new IngredientException("Merci de saisir un ingrédient");
        if(ingredient.getNom() == null)
            throw new IngredientException("Le nom des ingrédients est obligatoire");
        if(ingredient.getQuantite() == null)
            throw new IngredientException("La quantité des ingrédients est obligatoire");
    }

    @Override
    public Ingredient ajouterIngredient(Ingredient ingredient) {
        verifierIngredient(ingredient);

        return ingredientDao.save(ingredient);
    }
}
