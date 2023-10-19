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

    public boolean sortDeck(){
        int numberOfCards = cards.size();
        for (int i = 0; i < numberOfCards - 1; i++){

            for (int j = 0; j < numberOfCards - i -1; j++){
                if (cards.get(j).value > cards.get(j + 1).value){
                    Card tempCard = cards.remove(j);
                    cards.add(j+1, tempCard);
                }
            }
        }
        return true;
    }


}
