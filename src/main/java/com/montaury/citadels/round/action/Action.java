package com.montaury.citadels.round.action;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.player.Player;
import com.montaury.citadels.round.ActionType;
import com.montaury.citadels.round.GameRoundAssociations;
import com.montaury.citadels.round.Group;
import io.vavr.collection.List;

public interface Action {

    void executeAction(ActionType actionType, Player p, CardPile cardDraw, GameRoundAssociations groups, List<Player> players, Group group);

    boolean isAvailableForPlayer(ActionType actionType, Player p, CardPile cardDraw, GameRoundAssociations groups, List<Player> players, Group group);

}
