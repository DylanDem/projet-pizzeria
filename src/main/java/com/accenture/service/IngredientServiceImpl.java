package com.accenture.service;

import com.accenture.exception.IngredientException;
import com.accenture.model.Ingredient;
import com.accenture.repository.IngredientDao;
import com.accenture.service.dto.IngredientRequestDto;
import com.accenture.service.dto.IngredientResponseDto;
import com.accenture.service.mapper.IngredientMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * Service pour gérer les opérations sur les ingrédients.
 * Fournit des méthodes pour ajouter, récupérer, modifier partiellement et supprimer des ingrédients.
 * Utilise un logger pour suivre les actions effectuées.
 */
@Service
@Slf4j
public class IngredientServiceImpl implements IngredientService {

    private final IngredientDao ingredientDao;
    private final IngredientMapper ingredientMapper;

    public static final String INGREDIENT_N_EXISTE_PAS = "L'ingrédient n'existe pas";


    public IngredientServiceImpl(IngredientDao ingredientDao, IngredientMapper ingredientMapper) {
        this.ingredientDao = ingredientDao;
        this.ingredientMapper = ingredientMapper;
    }


    /**
     * Ajoute un nouvel ingrédient après vérification.
     *
     * @param ingredient l'ingrédient à ajouter
     * @return l'ingrédient ajouté
     */
    @Override
    public Ingredient ajouterIngredient(Ingredient ingredient) {
        verifierIngredient(ingredient);

        return ingredientDao.save(ingredient);
    }

    /**
     * Récupère la liste de tous les ingrédients.
     *
     * @return une liste d'ingrédients
     */
    public List<Ingredient> trouverTous() {
        log.info("Récupération de tous les ingrédients.");
        return ingredientDao.findAll();
    }

    /**
     * Récupère un ingrédient par son identifiant.
     *
     * @param idIngredient l'identifiant de l'ingrédient
     * @return l'ingrédient correspondant
     */
    @Override
    public Ingredient trouver(int idIngredient) {
        log.info("Recherche de l'ingrédient avec l'ID : {}", idIngredient);
        return ingredientDao.findById(idIngredient)
                .orElseThrow(() -> new EntityNotFoundException(INGREDIENT_N_EXISTE_PAS));
    }

    @Override
    public void supprimer(int idIngredient) {
        log.info("Suppression de l'ingrédient avec l'ID : {}", idIngredient);
        if (ingredientDao.existsById(idIngredient))
            ingredientDao.deleteById(idIngredient);
        else
            throw new EntityNotFoundException(INGREDIENT_N_EXISTE_PAS);
    }

    /**
     * Modifie partiellement un ingrédient existant.
     *
     * @param idIngredient        l'identifiant de l'ingrédient à modifier
     * @param ingredientRequestDto les modifications à appliquer
     * @return l'objet DTO de l'ingrédient modifié
     */
    public IngredientResponseDto modifierPartiellement(int idIngredient, IngredientRequestDto ingredientRequestDto) {
        log.info("Modification partielle de l'ingrédient avec l'ID : {}", idIngredient);
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

    //    =====================================================================================================////
    //                                                  METHODES PRIVEES////
    //    =====================================================================================================

    private void remplacer(Ingredient ingredient, Ingredient ingredientExistant) {
        if(ingredient.getQuantite() != null)
            ingredientExistant.setQuantite(ingredient.getQuantite());
    }

    private static void verifierIngredient(Ingredient ingredient) {
        if (ingredient == null)
            throw new IngredientException("Merci de saisir un ingrédient");
        if(ingredient.getNom() == null)
            throw new IngredientException("Le nom des ingrédients est obligatoire");
        if(ingredient.getQuantite() == null)
            throw new IngredientException("La quantité des ingrédients est obligatoire");
    }
}
