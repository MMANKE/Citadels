package com.montaury.citadels;

import com.montaury.citadels.district.Card;
import com.montaury.citadels.district.DestructibleDistrict;
import com.montaury.citadels.player.ComputerController;
import com.montaury.citadels.player.Player;
import io.vavr.collection.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DestroyDistrictShould {

    @Test
    public void return_temple1_and_prison1(){
        Board b = new Board();
        City city1 = new City(b);
        Player p1 = new Player("joueur 1", 25  , city1, new ComputerController());
        City city2 = new City(b);
        Player p2 = new Player("joueur 2", 22  , city2, new ComputerController());
        p1.city().buildDistrict(Card.TEMPLE_1);
        p1.city().buildDistrict(Card.PRISON_1);

        city2.buildDistrict(Card.TEMPLE_2);

        //WHEN
        List<DestructibleDistrict> dd = DestroyDistrict.destructibleDistrictsByPlayer(p2, List.of(p1,p2));


        // THEN
        assertThat(dd.size()).isEqualTo(3);

    }


    @Test
    public void it_should_be_6_for_2_MANOR(){
        // GIVEN
        Board b = new Board();
        City city1 = new City(b);
        Player p1 = new Player("joueur 1", 25  , city1, new ComputerController());
        City city2 = new City(b);
        Player p2 = new Player("joueur 2", 22  , city2, new ComputerController());
        p1.city().buildDistrict(Card.TEMPLE_1);
        p1.city().buildDistrict(Card.PRISON_1);

        city2.buildDistrict(Card.TEMPLE_2);

        p2.add(5);

        // WHEN
        DestroyDistrict.destroyDistrict(p2, List.of(p1,p2));


        // THEN
        assertThat(p1.city().districtAsCards().size()).isEqualTo(1);
    }


}
