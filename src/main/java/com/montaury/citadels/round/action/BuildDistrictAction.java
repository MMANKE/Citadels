package com.montaury.citadels.round.action;

import com.montaury.citadels.district.Card;

public class BuildDistrictAction extends Action {
    Card card = group.player().controller.selectAmong(group.player().buildableDistrictsInHand());
                                    group.player().buildDistrict(card);
}
