package com.montaury.citadels.round.action;

import com.montaury.citadels.player.Player;
import com.montaury.citadels.round.GameRoundAssociations;

public class RobAction implements Action {

    @Override
    public void executeAction() {

    }

    @Override
    public boolean isAvailableForPlayer(Player p, GameRoundAssociations associations) {
        return false;
    }

}
