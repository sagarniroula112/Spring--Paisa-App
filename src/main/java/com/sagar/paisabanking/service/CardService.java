package com.sagar.paisabanking.service;

import com.sagar.paisabanking.model.Card;

public interface CardService {
    void addCard(Card card);
    void deleteCard(long id);
    void updateCard(Card card);
    Card getCardById(long id);
}
