package com.montaury.citadels;


import com.montaury.citadels.district.Card;
import io.vavr.collection.HashSet;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class CityTest {

    @Test
    public void it_should_be_6_for_2_MANOR(){
        // GIVEN
        City city = new City(new Board());
        city.buildDistrict(Card.MANOR_1);
        city.buildDistrict(Card.MANOR_2);
        Possession possession = new Possession(0, HashSet.of(Card.MANOR_1,Card.MANOR_2));
        int score = 0;

        // WHEN
        score = city.score(possession);

        // THEN
        assertThat(score).isEqualTo(6);
    }

    @Test
    public void it_should_be_16_for_wins_with_all_colors(){
        // GIVEN
        City city = new City(new Board());
        // Cout des cartes : Treasury=5, Temple=1, Tavern=1, Prison=2, Castle=4
        Possession possession = new Possession(0, HashSet.of(Card.TREASURY,Card.TEMPLE_1,Card.TAVERN_1,Card.PRISON_1,Card.CASTLE_1));
        int score = 0;

        // WHEN
        score = city.score(possession);

        // THEN
        assertThat(score).isEqualTo(16);
    }

    @Test
    public void it_should_be_10_for_wins_not_first(){
        // GIVEN
        City city = new City(new Board());
        // Cout des cartes : Temple=1*3, Tavern=1*5
        Possession possession = new Possession(0, HashSet.of(Card.TEMPLE_1,Card.TEMPLE_2,Card.TEMPLE_3,Card.TAVERN_1,Card.TAVERN_2,Card.TAVERN_3,Card.TAVERN_4,Card.TAVERN_5));
        int score = 0;

        // WHEN
        score = city.score(possession);

        // THEN
        assertThat(score).isEqualTo(16);
    }




}