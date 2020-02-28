package com.montaury.citadels.round.action;

import com.montaury.citadels.player.Player;
import com.montaury.citadels.round.GameRoundAssociations;

public interface Action {

    void executeAction();

    boolean isAvailableForPlayer(Player p, GameRoundAssociations associations);

}
