package com.montaury.citadels.character;

import com.montaury.citadels.district.DistrictType;
import com.montaury.citadels.round.ActionType;
import io.vavr.collection.List;
import io.vavr.control.Option;



public enum Character {
    ASSASSIN(1, "Assassin", List.of(ActionType.KILL)),
    THIEF(2, "Thief", List.of(ActionType.ROB)),
    MAGICIAN(3, "Magician", List.of(ActionType.EXCHANGE_CARD_WITH_OTHER_PLAYER, ActionType.EXCHANGE_CARD_WITH_PILE)),
    KING(4, "King", List.of(ActionType.RECEIVE_INCOME), DistrictType.NOBLE),
    BISHOP(5, "Bishop", List.of(ActionType.RECEIVE_INCOME), DistrictType.RELIGIOUS),
    MERCHANT(6, "Merchant",  List.of(ActionType.RECEIVE_1_GOLD, ActionType.RECEIVE_INCOME), DistrictType.TRADE),
    ARCHITECT(7, "Architect", List.of(ActionType.PICK_2_CARDS, ActionType.BUILD_DISTRICT, ActionType.BUILD_DISTRICT)),
    WARLORD(8, "Warlord",  List.of(ActionType.DESTROY_DISTRICT, ActionType.RECEIVE_INCOME), DistrictType.MILITARY);

    Character(int number, String name, List<ActionType> powers)
    {
        this(number, name, powers, null);
    }

    Character(int number, String name, List<ActionType> powers, DistrictType associatedDistrictType)
    {
        this.number = number;
        this.name = name;
        this.powers = powers;
        this.associatedDistrictType = Option.of(associatedDistrictType);
    }

    public int number() {
        return number;
    }

    public String getName() {
        return name;
    }

    public List<ActionType> getPowers(){return powers; }

    public Option<DistrictType> associatedDistrictType() {
        return associatedDistrictType;
    }

    private final int number;
    private final String name;
    private final List<ActionType> powers;
    private final Option<DistrictType> associatedDistrictType;
}