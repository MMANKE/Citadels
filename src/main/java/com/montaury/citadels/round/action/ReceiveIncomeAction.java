package com.montaury.citadels.round.action;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.character.Character;
import com.montaury.citadels.district.District;
import com.montaury.citadels.district.DistrictType;
import com.montaury.citadels.player.Player;
import com.montaury.citadels.round.ActionType;
import com.montaury.citadels.round.GameRoundAssociations;
import com.montaury.citadels.round.Group;
import io.vavr.collection.List;

public class ReceiveIncomeAction implements Action {

    @Override
    public void executeAction(ActionType actionType, Player p, CardPile cardDraw, GameRoundAssociations groups, List<Player> players, Group group) {
        DistrictType type = null;
        if (group.character == Character.BISHOP) {
            type = DistrictType.RELIGIOUS;
        }
        else if (group.character == Character.WARLORD) {
            type = DistrictType.MILITARY;
        }
        else if (group.character == Character.KING) {
            type = DistrictType.NOBLE;
        }
        else if (group.character == Character.MERCHANT) {
            type = DistrictType.TRADE;
        }
        if (type != null) {
            for (District d : group.player().city().districts()) {
                if (d.districtType() == type) {
                    group.player().add(1);
                }
                if (d == District.MAGIC_SCHOOL) {
                    group.player().add(1);
                }
            }
        }
    }

    @Override
    public boolean isAvailableForPlayer(ActionType actionType, Player p, CardPile cardDraw, GameRoundAssociations groups, List<Player> players, Group group) {
        return false;
    }

}
