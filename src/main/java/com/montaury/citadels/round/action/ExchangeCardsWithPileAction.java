package com.montaury.citadels.round.action;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.district.Card;
import com.montaury.citadels.player.Player;
import com.montaury.citadels.round.ActionType;
import com.montaury.citadels.round.GameRoundAssociations;
import com.montaury.citadels.round.Group;
import io.vavr.collection.List;
import io.vavr.collection.Set;

public class ExchangeCardsWithPileAction implements Action {

    @Override
    public void executeAction(ActionType actionType, Player p, CardPile cardDraw, GameRoundAssociations groups, List<Player> players, Group group) {
        Set<Card> cardsToSwap = p.controller.selectManyAmong(p.cards());
        p.cards = p.cards().removeAll(cardsToSwap);
        p.add(cardDraw.swapWith(cardsToSwap.toList()));
    }

    @Override
    public boolean isAvailableForPlayer(ActionType actionType, Player p, CardPile cardDraw, GameRoundAssociations groups, List<Player> players, Group group) {
        return !p.cards().isEmpty() && cardDraw.canDraw(1);
    }
}
