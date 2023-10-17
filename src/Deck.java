import java.util.ArrayList;
import java.util.List;

public class Deck {

    private List<Card> cards = new ArrayList<>();

    public Deck(){
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        for (String suit : suits) {
            for (int cardValue = 1; cardValue <= 13; cardValue++){
                cards.add(new Card(cardValue, suit));
            }
        }
    }

    public List<Card> getCards() {
        return cards;
    }

}
