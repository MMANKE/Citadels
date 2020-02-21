package com.montaury.citadels;


import com.montaury.citadels.district.Card;
import io.vavr.collection.HashSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class CityTest {
    City city;

    @BeforeEach
    public void setUp(){
        city = new City(new Board());
    }

    // Cout de la construction total des quartiers
    @Test
    public void it_should_be_6_for_2_MANOR(){
        // GIVEN

        // Cout des cartes : Manor=3
        city.buildDistrict(Card.MANOR_1);
        city.buildDistrict(Card.MANOR_2);
        Possession possession = new Possession(0, HashSet.of(Card.MANOR_1));
        Score score;

        // WHEN
        score = city.finalScore(possession);

        // THEN
        assertThat(score.getScore()).isEqualTo(6);
    }


    // +3 si la cité comprend des quartiers de 5 types (couleurs) différents
    @Test
    public void it_should_be_16_for_wins_with_all_colors(){
        // GIVEN
        // Cout des cartes : Treasury=5, Temple=1, Tavern=1, Prison=2, Castle=4
        city.buildDistrict(Card.TREASURY);
        city.buildDistrict(Card.TEMPLE_1);
        city.buildDistrict(Card.TAVERN_1);
        city.buildDistrict(Card.PRISON_1);
        city.buildDistrict(Card.CASTLE_1);
        Possession possession = new Possession(0, HashSet.of(Card.TREASURY));
        Score score;

        // WHEN
        score = city.finalScore(possession);

        // THEN
        assertThat(score.getScore()).isEqualTo(16);
    }


    // +4 pour le premier joueur qui a complété sa cité (+2 car 1er et +2 car cité complétée)
    @Test
    public void it_should_be_12_for_wins_first(){
        // GIVEN
        Board leBoard = new Board();
        leBoard.mark(city); // la cité est la première finie
        // Cout des cartes : Temple=1*3, Tavern=1*5
        city.buildDistrict(Card.TEMPLE_1);
        city.buildDistrict(Card.TEMPLE_2);
        city.buildDistrict(Card.TEMPLE_3);
        city.buildDistrict(Card.TAVERN_1);
        city.buildDistrict(Card.TAVERN_2);
        city.buildDistrict(Card.TAVERN_3);
        city.buildDistrict(Card.TAVERN_4);
        city.buildDistrict(Card.TAVERN_5);
        Possession possession = new Possession(0, HashSet.of(Card.TEMPLE_1));
        Score score;

        // WHEN
        score = city.finalScore(possession);

        // THEN
        assertThat(score.getScore()).isEqualTo(12);
    }


    // +2 pour les joueurs qui ont complété leur cité (cité complété)
    @Test
    public void it_should_be_12_for_wins_not_first(){
        // GIVEN
        Board leBoard = new Board();
        City cityFirst = new City(leBoard);
        leBoard.mark(cityFirst); // cityFirst est la premiere ville finie
        // Cout des cartes : Temple=1*3, Tavern=1*5
        city.buildDistrict(Card.TEMPLE_1);
        city.buildDistrict(Card.TEMPLE_2);
        city.buildDistrict(Card.TEMPLE_3);
        city.buildDistrict(Card.TAVERN_1);
        city.buildDistrict(Card.TAVERN_2);
        city.buildDistrict(Card.TAVERN_3);
        city.buildDistrict(Card.TAVERN_4);
        city.buildDistrict(Card.TAVERN_5);
        Possession possession = new Possession(0, HashSet.empty());

        // WHEN
        Score score = city.finalScore(possession);

        // THEN
        assertThat(score.getScore()).isEqualTo(12);
    }


    // Bonus merveille : cas de la carte DRAGON_GATE
    @Test
    public void it_should_be_8_for_the_DRAGON_GATE_card(){
        // GIVEN
        // Cout des cartes : DRAGON_GATE=6
        city.buildDistrict(Card.DRAGON_GATE);
        Possession possession = new Possession(0, HashSet.of(Card.MANOR_1));
        Score score;

        // WHEN
        score = city.finalScore(possession);

        // THEN
        assertThat(score.getScore()).isEqualTo(8);
    }


    // Bonus merveille : cas de la carte UNIVERSITY
    @Test
    public void it_should_be_8_for_the_UNIVERSITY_card(){
        // GIVEN
        // Cout des cartes : UNIVERSITY=6
        city.buildDistrict(Card.UNIVERSITY);
        Possession possession = new Possession(0, HashSet.of(Card.MANOR_1));
        Score score;

        // WHEN
        score = city.finalScore(possession);

        // THEN
        assertThat(score.getScore()).isEqualTo(8);
    }


    // Bonus merveille : cas de la carte TREASURY
    @Test
    public void it_should_be_10_for_the_TREASURY_card_and_5_of_gold(){
        // GIVEN
        // Cout des cartes : TREASURY=5
        city.buildDistrict(Card.TREASURY);
        Possession possession = new Possession(5, HashSet.of(Card.MANOR_1));
        Score score;

        // WHEN
        score = city.finalScore(possession);

        // THEN
        assertThat(score.getScore()).isEqualTo(10);
    }


    // Bonus merveille : cas de la carte TREASURY
    @Test
    public void it_should_be_8_for_the_MAP_ROOM_card_and_3_cards_in_hand(){
        // GIVEN
        // Cout des cartes : MAP_ROOM=5, MANOR=1*3
        city.buildDistrict(Card.MAP_ROOM);
        Possession possession = new Possession(5, HashSet.of(Card.MANOR_1,Card.MANOR_2,Card.MANOR_3));
        Score score;

        // WHEN
        score = city.finalScore(possession);

        // THEN
        assertThat(score.getScore()).isEqualTo(8);
    }
}