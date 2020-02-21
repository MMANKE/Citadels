package com.montaury.citadels.player;

import com.montaury.citadels.City;
import com.montaury.citadels.Possession;
import com.montaury.citadels.district.Card;
import io.vavr.collection.HashSet;
import io.vavr.collection.Set;

public class Player {
    private final String name;
    private final int age;
    private final City city;
    public final PlayerController controller;
    private int gold;
    public Set<Card> cards = HashSet.empty();
    public boolean isHuman = false;

    public Player(String name, int age, City city, PlayerController controller) {
        this.name = name;
        this.age = age;
        this.city = city;
        this.gold = 0;
        this.controller = controller;
    }

    public String name() {
        return name;
    }

    public int age() {
        return age;
    }

    public City city() {
        return city;
    }

    public void add(int add) {
        gold+= (add);
    }

    public void add(Set<Card> cards) {
        this.cards = this.cards.addAll(cards);
    }

    public int gold() {
        return gold;
    }

    public boolean canAfford(int cost) {
        return cost <= gold;
    }

    private boolean canBuildDistrict(Card card) {
        return gold >=(card.district().cost()) && !city.has(card.district());
    }

    public Set<Card> buildableDistrictsInHand() {
        return cards.filter(this::canBuildDistrict);
    }

    public void buildDistrict(Card card) {
        if (!canBuildDistrict(card)) {
            return;
        }
        cards = cards.remove(card);
        city.buildDistrict(card);
        gold -= (card.district().cost());
    }

    public int score() {
        return city.finalScore(new Possession(gold, cards)).getScore();
    }

    public void exchangeHandWith(Player otherPlayer) {
        Set<Card> swappingHand = cards;
        cards = otherPlayer.cards;
        otherPlayer.cards = swappingHand;
    }

    public Set<Card> cards() {
        return cards;
    }

    public void steal(Player otherPlayer) {
        gold += otherPlayer.gold;
        otherPlayer.gold = 0;
    }

    public void pay(int cost) {
        gold -= ((cost));
    }

}
