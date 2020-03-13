package com.montaury.citadels.round.action;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.district.Card;
import com.montaury.citadels.district.District;
import com.montaury.citadels.player.Player;
import com.montaury.citadels.round.ActionType;
import com.montaury.citadels.round.GameRoundAssociations;
import com.montaury.citadels.round.Group;
import io.vavr.collection.HashSet;
import io.vavr.collection.List;
import io.vavr.collection.Set;

public class DrawThreeCardsAndKeepOneAction implements Action {

    @Override
    public void executeAction(ActionType actionType, Player p, CardPile cardDraw, GameRoundAssociations groups, List<Player> players, Group group) {
        Set<Card> cardsDrawn = cardDraw.draw(3);
        if (!p.city().has(District.LIBRARY)) {
            Card keptCard = p.controller.selectAmong(cardsDrawn);
            cardDraw.discard(cardsDrawn.remove(keptCard).toList());
            cardsDrawn = HashSet.of(keptCard);
        }
        p.add(cardsDrawn);
    }

    @Override
    public boolean isAvailableForPlayer(ActionType actionType, Player p, CardPile cardDraw, GameRoundAssociations groups, List<Player> players, Group group) {
        return true;
    }
}
