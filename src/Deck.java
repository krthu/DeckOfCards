import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Deck {

    private List<Card> cards = new ArrayList<>();
    private Random rand = new Random();

    public Deck(){
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        for (String suit : suits) {
            for (int cardValue = 1; cardValue <= 13; cardValue++){
                cards.add(new Card(cardValue, suit));
            }
        }
    }

    public Deck(int numberOfDecks){
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        for (int i = 0; i< numberOfDecks; i++){
            for (String suit : suits) {
                for (int cardValue = 1; cardValue <= 13; cardValue++){
                    cards.add(new Card(cardValue, suit));
                }
            }
        }
    }
    public List<Card> getCards() {
        return cards;
    }

    public Card drawACard(){
        return cards.remove(rand.nextInt(cards.size()));
    }



}
