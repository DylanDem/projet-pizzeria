package com.accenture.service;

import com.accenture.exception.IngredientException;
import com.accenture.model.Ingredient;
import com.accenture.repository.IngredientDao;
import com.accenture.service.dto.IngredientRequestDto;
import com.accenture.service.dto.IngredientResponseDto;
import com.accenture.service.mapper.IngredientMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientDao ingredientDao;
    private final IngredientMapper ingredientMapper;

    public static final String INGREDIENT_N_EXISTE_PAS = "L'ingrédient n'existe pas";

    public IngredientServiceImpl(IngredientDao ingredientDao, IngredientMapper ingredientMapper) {
        this.ingredientDao = ingredientDao;
        this.ingredientMapper = ingredientMapper;
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

    public List<Ingredient> trouverTous() {
        return ingredientDao.findAll();
    }

    @Override
    public Ingredient trouver(int idIngredient) {
        return ingredientDao.findById(idIngredient)
                .orElseThrow(() -> new EntityNotFoundException(INGREDIENT_N_EXISTE_PAS));
    }

    @Override
    public void supprimer(int idIngredient) {
        if (ingredientDao.existsById(idIngredient))
            ingredientDao.deleteById(idIngredient);
        else
            throw new EntityNotFoundException(INGREDIENT_N_EXISTE_PAS);
    }

    public IngredientResponseDto modifierPartiellement(int idIngredient, IngredientRequestDto ingredientRequestDto) {
        Optional<Ingredient> ingredientOptional = ingredientDao.findById(idIngredient);
        if(ingredientOptional.isEmpty())
            throw new EntityNotFoundException("id non valide");

        Ingredient ingredientExistant = ingredientOptional.get();

        Ingredient ingredient = ingredientMapper.toIngredient(ingredientRequestDto);

        remplacer(ingredient, ingredientExistant);
        ingredientExistant.setId(idIngredient);
        Ingredient ingredientEnreg = ingredientDao.save(ingredientExistant);
        return ingredientMapper.toIngredientResponseDto(ingredientEnreg);

    }

    private void remplacer(Ingredient ingredient, Ingredient ingredientExistant) {
        if(ingredient.getQuantite() != null)
            ingredientExistant.setQuantite(ingredient.getQuantite());
    }
}
