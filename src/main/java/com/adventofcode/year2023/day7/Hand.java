package com.adventofcode.year2023.day7;

import static com.adventofcode.year2023.day7.HandType.*;
import static java.lang.Integer.parseInt;
import static java.util.Arrays.stream;
import static org.apache.commons.collections4.CollectionUtils.getCardinalityMap;
import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class Hand implements Comparable<Hand> {

    private boolean isJokerWild;
    private int bid;
    private List<Integer> cards;
    private Map<Integer, Integer> cardFreqMap;
    private HandType handType;

    public Hand(String input, boolean isJokerWild) {
        this.isJokerWild = isJokerWild;
        bid = parseInt(input.split(" ")[1]);
        cards = stream(input.split(" ")[0].split("")).map(this::calcIntValue).toList();

        cardFreqMap = getCardinalityMap(cards);
        if (isJokerWild && cardFreqMap.containsKey(1) && cardFreqMap.get(1) != 5) {
            int jokerCount = cardFreqMap.remove(1);
            int maxFrequency = cardFreqMap.values().stream().mapToInt(Integer::intValue).max().orElseThrow();
            for (Map.Entry<Integer, Integer> entry : cardFreqMap.entrySet()) {
                if (entry.getValue() == maxFrequency) {
                    cardFreqMap.put(entry.getKey(), entry.getValue() + jokerCount);
                    break;
                }
            }
        }
        handType = calcHandType();
    }

    private Integer calcIntValue(String input) {
        return switch (input) {
            case "T" -> 10;
            case "J" -> isJokerWild ? 1 : 11;
            case "Q" -> 12;
            case "K" -> 13;
            case "A" -> 14;
            default -> parseInt(input);
        };
    }

    private HandType calcHandType() {
        if (cardFreqMap.containsValue(5)) {
            return FIVE_OF_A_KIND;
        } else if (cardFreqMap.containsValue(4)) {
            return FOUR_OF_A_KIND;
        } else if (cardFreqMap.containsValue(3) && cardFreqMap.containsValue(2)) {
            return FULL_HOUSE;
        } else if (cardFreqMap.containsValue(3)) {
            return THREE_OF_A_KIND;
        } else if (cardFreqMap.containsValue(2) && cardFreqMap.size() == 3) {
            return TWO_PAIRS;
        } else if (cardFreqMap.containsValue(2)) {
            return ONE_PAIR;
        } else {
            return HIGH_CARD;
        }
    }

    @Override
    public int compareTo(Hand otherHand) {

        if (handType.getValue() < otherHand.getHandType().getValue()) {
            return -1;
        } else if (handType.getValue() > otherHand.getHandType().getValue()) {
            return 1;
        }

        // Otherwise same hand type, compare the cards
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i) < otherHand.getCards().get(i)) {
                return -1;
            } else if (cards.get(i) > otherHand.getCards().get(i)) {
                return 1;
            }
        }

        return 0;
    }
}
