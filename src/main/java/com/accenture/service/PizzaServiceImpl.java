package com.accenture.service;

import com.accenture.exception.PizzaException;
import com.accenture.model.Pizza;
import org.springframework.stereotype.Service;

@Service
public class PizzaServiceImpl {


    public PizzaServiceImpl() {
    }

    public void ajouter(Pizza pizza) {

        if(pizza == null)
            throw new PizzaException("Merci de saisir votre pizza");

    }
}
