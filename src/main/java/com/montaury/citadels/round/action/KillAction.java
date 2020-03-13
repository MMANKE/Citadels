package com.montaury.citadels.round.action;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.character.Character;
import com.montaury.citadels.player.Player;
import com.montaury.citadels.round.ActionType;
import com.montaury.citadels.round.GameRoundAssociations;
import com.montaury.citadels.round.Group;
import io.vavr.collection.List;

public class KillAction implements Action {

    @Override
    public void executeAction(ActionType actionType, Player p, CardPile cardDraw, GameRoundAssociations groups, List<Player> players, Group group) {
        Character characterToMurder = p.controller.selectAmong(List.of(Character.values()));
        groups.associationToCharacter(characterToMurder).peek(Group::murder);
    }

    @Override
    public boolean isAvailableForPlayer(ActionType actionType, Player p, CardPile cardDraw, GameRoundAssociations groups, List<Player> players, Group group) {
        return false;
    }

}
