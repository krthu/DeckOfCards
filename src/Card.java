import java.util.Objects;

public class Card {
    int value;
    String suit;

    public Card(int value, String suit){
        this.suit = suit;
        this.value = value;

    }

    public int getValue() {
        return value;
    }

    public String getSuit() {
        return suit;
    }

    public int compareTo(Card otherCard) {
        // Compare against value
        if (this.value < otherCard.value) return -1;
        if (this.value < otherCard.value) return 1;

        // Compare against suit
        return this.suit.compareTo(otherCard.suit);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return value == card.value && Objects.equals(suit, card.suit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, suit);
    }

    public String toString() {
        String[] cardNames = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
        return cardNames[value - 1] + " of " + suit;

    }
}


