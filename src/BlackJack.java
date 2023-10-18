import java.util.ArrayList;

public class BlackJack {
    private ArrayList<BlackJackPlayer> players;

    private Deck deck;

    private ArrayList<Card> dealersCards;

    public BlackJack(){
        this.deck = new Deck(6);

    }
    public Deck getDeck() {
        return deck;
    }

    public int getBlackJackValue(ArrayList<Card> cards){
        int value = 0;
        for (Card card: cards) {
            if (card.getValue() == 1) {
                value += 11;
            } else if (card.getValue() <= 13 && card.getValue() >= 10) {
                value += 10;
            }else {
                value += card.getValue();
            }
        }
        return value;
    }

    public void hit(Player player){
        player.addCard(deck.drawACard());
    }



}
