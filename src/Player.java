import java.util.ArrayList;

public class Player {
    private String name;
    private ArrayList<Card> cardsInHand;

    public Player(String name){
        this.name = name;
        cardsInHand = new ArrayList<>();
    }
    public String getName() {
        return name;
    }

    public ArrayList<Card> getCardsInHand() {
        return cardsInHand;
    }

    // Add cards to players hand. Override if there is some limits on handsize.
    public boolean addCard(Card card){
        cardsInHand.add(card);
        return true;
    }

    // Given a index removes the card from hand and returns the card.
    public Card playCard(int index){
        return cardsInHand.remove(index);
    }

    public void discardHand(){
        cardsInHand.clear();
    }

    public String cardsToString(){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < cardsInHand.size(); i++) {
            builder.append(cardsInHand.get(i));
            if (i != cardsInHand.size() -1){
                builder.append(", ");
            }
        }
        return builder.toString();
    }

}
