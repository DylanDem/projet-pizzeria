INSERT INTO INGREDIENT (nom, quantite) VALUES
('chèvre', 5),('basilic', 5), ('beurre-sale', 100), ('galette', 3);

INSERT INTO PIZZA (nom) VALUES
('Margherita'),('Chèvre_miel');

INSERT INTO PIZZA_INGREDIENTS (pizza_id, ingredients_id) VALUES
(1,1),
(2,2);

INSERT INTO PIZZAS_PRIX (pizza_id, prix, taille) VALUES
(1, 10.5, 'PETITE'),
(2, 15.3, 'MOYENNE');
