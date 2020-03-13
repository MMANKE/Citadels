package com.montaury.citadels.round;

import com.montaury.citadels.round.action.*;

public enum ActionType {
    END_ROUND("End round", new EndRoundAction()),
    BUILD_DISTRICT("Build district", new BuildDistrictAction()),
    DISCARD_CARD_FOR_2_COINS("Discard card for 2 coins", new DiscardCardForTwoCoinsAction()),
    DRAW_3_CARDS_FOR_2_COINS("Draw 3 cards for 2 coins", new DrawThreeCardsForTwoCoinsAction()),
    DRAW_3_CARDS_AND_KEEP_1("Draw 3 cards and keep 1", new DrawThreeCardsAndKeepOneAction()),
    DRAW_2_CARDS_AND_KEEP_1("Draw 2 cards and keep 1", new DrawTwoCardsAndKeepOneAction()),
    EXCHANGE_CARD_WITH_PILE("Exchange cards with pile", new ExchangeCardsWithPileAction()),
    EXCHANGE_CARD_WITH_OTHER_PLAYER("Exchange cards with other player", new ExchangeCardsWithOtherPlayerAction()),
    KILL("Kill", new KillAction()),
    PICK_2_CARDS("Pick 2 cards", new PickTwoCardsAction()),
    RECEIVE_2_COINS("Receive 2 coins", new ReceiveTwoCoinsAction()),
    RECEIVE_1_GOLD("Receive 1 gold", new ReceiveOneGoldAction()),
    RECEIVE_INCOME("Receive income", new ReceiveIncomeAction()),
    DESTROY_DISTRICT("Destroy district", new DestroyDistrictAction()),
    ROB("Rob", new RobAction());


    ActionType(String label, Action anAction) {
        this.label = label;
        this.action = anAction;
    }

    public String getLabel(){
        return this.label;
    }

    public Action getAction(){
        return this.action;
    }

    private final String label;
    private final Action action;


}
