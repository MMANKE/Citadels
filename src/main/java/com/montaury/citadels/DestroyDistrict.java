package com.montaury.citadels;

import com.montaury.citadels.district.DestructibleDistrict;
import com.montaury.citadels.player.Player;
import io.vavr.collection.HashMap;
import io.vavr.collection.List;

public class DestroyDistrict {


    public static List<DestructibleDistrict> destructibleDistrictsByPlayer(Player destroyer, List<Player> players){
        List<DestructibleDistrict> districts = List.empty();
        for(Player currentPlayer : players){
           districts.appendAll(currentPlayer.city().districtsDestructibleBy(destroyer));
        }
        return districts;
    }

    public static void destroyDistrict(Player destroyer, List<Player> players){
        HashMap<Player, List<DestructibleDistrict>> destructibleDistrictsByPlayers = HashMap.empty();
        for(Player currentPlayer : players){
            List<DestructibleDistrict> destructibleDistricts = currentPlayer.city().districtsDestructibleBy(destroyer);
            destructibleDistrictsByPlayers.put(currentPlayer, destructibleDistricts);
        }

        DestructibleDistrict districtToDestroy =destroyer.controller.selectDistrictToDestroyAmong(destructibleDistrictsByPlayers);
        for(Player currentPlayer : players){
            if(currentPlayer.city().districtAsCards().contains(districtToDestroy.card())){
                destroyer.pay(districtToDestroy.destructionCost());
                currentPlayer.city().destroyDistrict(districtToDestroy.card());
            }
        }
    }

}
