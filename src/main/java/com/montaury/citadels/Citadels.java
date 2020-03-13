package com.montaury.citadels;

import com.montaury.citadels.character.Character;
import com.montaury.citadels.character.RandomCharacterSelector;
import com.montaury.citadels.district.Card;
import com.montaury.citadels.district.District;
import com.montaury.citadels.district.DistrictType;
import com.montaury.citadels.player.ComputerController;
import com.montaury.citadels.player.HumanController;
import com.montaury.citadels.player.Player;
import com.montaury.citadels.round.GameRoundAssociations;
import com.montaury.citadels.round.Group;
import com.montaury.citadels.round.action.Action;
import com.montaury.citadels.round.action.DestroyDistrictAction;
import com.montaury.citadels.round.ActionType;
import io.vavr.Tuple;
import io.vavr.collection.HashSet;
import io.vavr.collection.List;
import io.vavr.collection.Set;

import java.util.Collections;
import java.util.Scanner;

public class Citadels {
    private static final int NUMBER_PLAYER_MIN = 2;
    private static final int NUMBER_PLAYER_MAX = 7;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello! Quel est votre nom ? ");
        String playerName = scanner.next();
        System.out.println("Quel est votre age ? ");
        int playerAge = scanner.nextInt();
        Board board = new Board();
        Player humanPlayer = new Player(playerName, playerAge, new City(board), new HumanController());
        humanPlayer.isHuman = true;
        List<Player> players = List.of(humanPlayer);
        int nbPlayer;
        do {
            System.out.println("Saisir le nombre de joueurs total (entre 2 et 7): ");
            nbPlayer = scanner.nextInt();
        } while (nbPlayer < NUMBER_PLAYER_MIN || nbPlayer > NUMBER_PLAYER_MAX);
        for (int numberCurrentPlayer = 1; numberCurrentPlayer < nbPlayer; numberCurrentPlayer += 1) {
            Player computerPlayer = new Player("Computer " + numberCurrentPlayer, 35, new City(board), new ComputerController());
            computerPlayer.isHuman = false;
            players = players.append(computerPlayer);
        }
        CardPile cardDraw = new CardPile(Card.all().toList().shuffle());
        players.forEach(player -> {
            player.add(2);
            player.add(cardDraw.draw(2));
        });
        Player crown = players.maxBy(Player::age).get();

        List<Group> roundAssociations;
        do {
            java.util.List<Player> list = players.asJavaMutable();
            Collections.rotate(list, -players.indexOf(crown));
            List<Player> playersInOrder = List.ofAll(list);
            RandomCharacterSelector randomCharacterSelector = new RandomCharacterSelector();
            List<Character> availableCharacters = List.of(Character.ASSASSIN, Character.THIEF, Character.MAGICIAN, Character.KING, Character.BISHOP, Character.MERCHANT, Character.ARCHITECT, Character.WARLORD, Character.ALCHEMIST);

            List<Character> availableCharacters1 = availableCharacters;
            List<Character> discardedCharacters = List.empty();

            Character discardedCharacter = randomCharacterSelector.among(availableCharacters1);
            discardedCharacters = discardedCharacters.append(discardedCharacter);
            availableCharacters1.remove(discardedCharacter);


            Character faceDownDiscardedCharacter = discardedCharacters.head();
            availableCharacters = availableCharacters.remove(faceDownDiscardedCharacter);

            List<Character> availableCharacters11 = availableCharacters.remove(Character.KING);
            List<Character> discardedCharacters1 = List.empty();
            for (int i = 0; i < 7 - playersInOrder.size() - 1; i++) {
                discardedCharacter = randomCharacterSelector.among(availableCharacters11);
                discardedCharacters1 = discardedCharacters1.append(discardedCharacter);
                availableCharacters11 = availableCharacters11.remove(discardedCharacter);
            }
            List<Character> faceUpDiscardedCharacters = discardedCharacters1;
            availableCharacters = availableCharacters.removeAll(faceUpDiscardedCharacters);

            List<Group> associations = List.empty();
            for (Player player : playersInOrder) {
                System.out.println(player.name() + " doit choisir un personnage");
                availableCharacters = availableCharacters.size() == 1 && playersInOrder.size() == 7 ? availableCharacters.append(faceDownDiscardedCharacter) : availableCharacters;
                Character selectedCharacter = player.controller.selectOwnCharacter(availableCharacters, faceUpDiscardedCharacters);
                availableCharacters = availableCharacters.remove(selectedCharacter);
                associations = associations.append(new Group(player, selectedCharacter));
            }

            GameRoundAssociations groups = new GameRoundAssociations(associations);

            for (int iii = 0; iii < 8; iii++) {
                for (int ii = 0; ii < associations.size(); ii++) {
                    if (iii + 1 == associations.get(ii).character.number()) {
                        if (associations.get(ii).isMurdered()) {}
                        else{
                            Group group = associations.get(ii);
                            associations.get(ii).thief().peek(thief -> thief.steal(group.player()));
                            Set<ActionType> baseActions = HashSet.of(ActionType.DRAW_2_CARDS_AND_KEEP_1, ActionType.RECEIVE_2_COINS);
                            List<District> districts = group.player().city().districts();

                            Set<ActionType> availableActions = baseActions;
                            for (District currentDistrict : districts) {
                                if (currentDistrict == District.OBSERVATORY) {
                                    availableActions = availableActions.replace(ActionType.DRAW_2_CARDS_AND_KEEP_1, ActionType.DRAW_3_CARDS_AND_KEEP_1);
                                }
                            }
                            // keep only actions that player can realize
                            List<ActionType> possibleActions = List.empty();
                            for (ActionType action : availableActions) {

                                if (action == ActionType.DRAW_2_CARDS_AND_KEEP_1) {
                                    if (cardDraw.canDraw(2))
                                        possibleActions = possibleActions.append(ActionType.DRAW_2_CARDS_AND_KEEP_1);
                                }
                                else if (action == ActionType.DRAW_3_CARDS_AND_KEEP_1) {
                                    if (cardDraw.canDraw(3))
                                        possibleActions = possibleActions.append(ActionType.DRAW_3_CARDS_AND_KEEP_1);
                                }
                                else {
                                    possibleActions = possibleActions.append(action);
                                }

                            }
                            ActionType actionType = group.player().controller.selectActionTypeAmong(possibleActions.toList());
                            // execute selected action
                            if (actionType == ActionType.DRAW_2_CARDS_AND_KEEP_1) {
                                Set<Card> cardsDrawn = cardDraw.draw(2);
                                if (!group.player().city().has(District.LIBRARY)) {
                                    Card keptCard = group.player().controller.selectAmong(cardsDrawn);
                                    cardDraw.discard(cardsDrawn.remove(keptCard).toList());
                                    cardsDrawn = HashSet.of(keptCard);
                                }
                                group.player().add(cardsDrawn);
                            }
                            else if (actionType == ActionType.RECEIVE_2_COINS) {
                                group.player().add(2);
                            }
                            else if (actionType == ActionType.DRAW_3_CARDS_AND_KEEP_1) {
                                Set<Card> cardsDrawn = cardDraw.draw(3);
                                if (!group.player().city().has(District.LIBRARY)) {
                                    Card keptCard = group.player().controller.selectAmong(cardsDrawn);
                                    cardDraw.discard(cardsDrawn.remove(keptCard).toList());
                                    cardsDrawn = HashSet.of(keptCard);
                                }
                                group.player().add(cardsDrawn);
                            }
                            actionExecuted(group, actionType, associations);

                            // receive powers from the character
                            List<ActionType> powers = group.character.getPowers();

                            List<ActionType>  extraActions = List.empty();
                            for (District d : group.player().city().districts()) {
                                if (d == District.SMITHY) {
                                    extraActions = extraActions.append(ActionType.DRAW_3_CARDS_FOR_2_COINS);
                                }
                                if (d == District.LABORATORY) {
                                    extraActions = extraActions.append(ActionType.DISCARD_CARD_FOR_2_COINS);
                                }
                            }
                            Set<ActionType> availableActions11 = Group.OPTIONAL_ACTIONS
                                    .addAll(powers)
                                    .addAll(extraActions);
                            ActionType actionType1 = null;
                            do {
                                Set<ActionType> availableActions1 = availableActions11;
                                // keep only actions that player can realize
                                List<ActionType> possibleActions2 = List.empty();
                                for (ActionType action : availableActions1) {
                                    if(action.getAction().isAvailableForPlayer(action, group.player(), cardDraw, groups, players, group)){
                                        possibleActions2 = possibleActions2.append(action);
                                    }
                                }

                                actionType1 = group.player().controller.selectActionTypeAmong(possibleActions2.toList());
                                // execute selected action

                                if(actionType1.getAction().isAvailableForPlayer(actionType1, group.player(), cardDraw, groups, players, group)){
                                    actionType1.getAction().executeAction(actionType1, group.player(), cardDraw, groups, players, group);
                                }
                                actionExecuted(group, actionType1, associations);
                                availableActions11 = availableActions11.remove(actionType1);
                            }
                            while (!availableActions11.isEmpty() && actionType1 != ActionType.END_ROUND);
                        }
                    }
                }
            }
            roundAssociations = associations;
            crown = roundAssociations.find(a -> a.character == Character.KING).map(Group::player).getOrElse(crown);
        } while (!players.map(Player::city).exists(City::isComplete));

        // classe les joueurs par leur finalScore
        // si ex-aequo, le premier est celui qui n'est pas assassiné
        // si pas d'assassiné, le gagnant est le joueur ayant eu le personnage avec le numéro d'ordre le plus petit au dernier tour
        System.out.println("Classement: " + roundAssociations.sortBy(a -> Tuple.of(a.player().score(), !a.isMurdered(), a.character))
                .reverse()
                .map(Group::player));
    }

    public static void actionExecuted(Group association, ActionType actionType, List<Group> associations) {
        System.out.println("Player " + association.player().name() + " executed action " + actionType.getLabel());
        associations.map(Group::player).forEach(Citadels::displayStatus);
    }

    private static void displayStatus(Player player) {
        System.out.println("  Player " + player.name() + ":");
        System.out.println("    Gold coins: " + player.gold());
        System.out.println("    City: " + textCity(player));
        System.out.println("    Hand size: " + player.cards().size());
        if (player.controller instanceof HumanController) {
            System.out.println("    Hand: " + textHand(player));
        }
        System.out.println();
    }

    private static String textCity(Player player) {
        List<District> districts = player.city().districts();
        return districts.isEmpty() ? "Empty" : districts.map(Citadels::textDistrict).mkString(", ");
    }

    private static String textDistrict(District district) {
        return district.name() + "(" + district.districtType().name() + ", " + district.cost() + ")";
    }

    private static String textHand(Player player) {
        Set<Card> cards = player.cards();
        return cards.isEmpty() ? "Empty" : cards.map(Citadels::textCard).mkString(", ");
    }

    private static String textCard(Card card) {
        return textDistrict(card.district());
    }

}
