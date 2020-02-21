package com.montaury.citadels.round;

import io.vavr.collection.HashSet;
import io.vavr.collection.Set;

public enum Action {
    END_ROUND("End round"),
    BUILD_DISTRICT("Build district"),
    DISCARD_CARD_FOR_2_COINS("Discard card for 2 coins"),
    DRAW_3_CARDS_FOR_2_COINS("Draw 3 cards for 2 coins"),
    DRAW_2_CARDS_AND_KEEP_1("Draw 2 cards and keep 1"),
    EXCHANGE_CARD_WITH_PILE("Exchange cards with pile"),
    EXCHANGE_CARD_WITH_OTHER_PLAYER("Exchange cards with other player"),
    KILL("Kill"),
    PICK_2_CARDS("Pick 2 cards"),
    RECEIVE_2_COINS("Receive 2 coins"),
    RECEIVE_1_GOLD("Receive 1 gold"),
    RECEIVE_INCOME("Receive income"),
    DESTROY_DISTRICT("Destroy district"),
    ROB("Rob");


    Action(String nameAction){
        this.nameAction = nameAction;

    }

    public String getNameAction(){
        return nameAction;
    }


    private final String nameAction;


}
