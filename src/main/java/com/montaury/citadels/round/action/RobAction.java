package com.montaury.citadels.round.action;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.player.Player;
import com.montaury.citadels.round.ActionType;
import com.montaury.citadels.round.GameRoundAssociations;
import com.montaury.citadels.round.Group;
import com.montaury.citadels.character.Character;

import io.vavr.collection.List;

public class RobAction implements Action {

    @Override
    public void executeAction(ActionType actionType, Player p, CardPile cardDraw, GameRoundAssociations groups, List<Player> players, Group group) {
        Character character = group.player().controller.selectAmong(List.of(Character.MAGICIAN, Character.KING, Character.BISHOP, Character.MERCHANT, Character.ARCHITECT, Character.WARLORD)
                .removeAll(groups.associations.find(Group::isMurdered).map(Group::character)));
        groups.associationToCharacter(character).peek(association -> association.stolenBy(group.player()));
    }

    @Override
    public boolean isAvailableForPlayer(ActionType actionType, Player p, CardPile cardDraw, GameRoundAssociations groups, List<Player> players, Group group) {
        return false;
    }

}
