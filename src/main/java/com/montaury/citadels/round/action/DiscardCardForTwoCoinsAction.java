package com.montaury.citadels.round.action;

import com.montaury.citadels.player.Player;

public class DiscardCardForTwoCoinsAction implements Action {

    @Override
    public void executeAction() {

    }

    @Override
    public boolean isAvailableForPlayer(Player p, GameRoundAssociations associations) {
        return false;
    }

}
