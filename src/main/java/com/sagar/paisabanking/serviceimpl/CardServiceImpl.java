package com.sagar.paisabanking.serviceimpl;

import com.sagar.paisabanking.model.Account;
import com.sagar.paisabanking.model.Card;
import com.sagar.paisabanking.repo.CardRepo;
import com.sagar.paisabanking.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardRepo cardRepo;

    @Override
    public void addCard(Card card) {
        cardRepo.save(card);
    }

    @Override
    public void deleteCard(long id) {
        cardRepo.deleteById(id);
    }

    @Override
    public void updateCard(Card card) {
        cardRepo.save(card);
    }

    @Override
    public Card getCardById(long id) {
        return cardRepo.findById(id).get();
    }
}
