package com.montaury.citadels.character;

import com.montaury.citadels.district.DistrictType;
import com.sun.javafx.binding.StringFormatter;
import io.vavr.control.Option;



public enum Character {
    ASSASSIN(1, "Assassin","Kill"),
    THIEF(2, "Thief","Rob"),
    MAGICIAN(3, "Magician","Exchange cards with other player,Exchange cards with pile"),
    KING(4, "King", "Receive income", DistrictType.NOBLE),
    BISHOP(5, "Bishop", "Receive income", DistrictType.RELIGIOUS),
    MERCHANT(6, "Merchant", "Receive income,Receive 1 gold", DistrictType.TRADE),
    ARCHITECT(7, "Architect","Pick 2 cards,Build district,Build district"),
    WARLORD(8, "Warlord", "Receive income,Destroy district", DistrictType.MILITARY);

    Character(int number, String name, String powers)
    {
        this(number, name, powers, null);
    }

    Character(int number, String name, String powers, DistrictType associatedDistrictType)
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

    public String getPowers(){return powers; }

    public Option<DistrictType> associatedDistrictType() {
        return associatedDistrictType;
    }

    private final int number;
    private final String name;
    String powers;
    private final Option<DistrictType> associatedDistrictType;
}