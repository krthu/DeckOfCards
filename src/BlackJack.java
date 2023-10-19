import java.util.ArrayList;
import java.util.Scanner;

public class BlackJack {
    private ArrayList<BlackJackPlayer> players;

    private Deck deck;

    private ArrayList<Card> dealersCards;

    private Scanner sc = new Scanner(System.in);

    public BlackJack(){
        this.deck = new Deck(6);
        players = new ArrayList<>();

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

    public void gameLoop(){
        System.out.println("Welcome to BlackJack!");
        int numberOfPlayers = getIntSafe("How many players?", 1, 7); // Apparently 2-7 players for a table
        addPlayers(numberOfPlayers);
        

    }

    public void addPlayers(int numberOfPlayers){
        for (int i = 1; i <= numberOfPlayers; i++){
            System.out.println("Name of player " + i);
            String name = sc.nextLine();
            System.out.println("How much money to put in?");
            int money = sc.nextInt(); // Need to safe check this
            sc.nextLine();
            players.add(new BlackJackPlayer(name, money));
        }
    }

    public int getIntSafe(String questionToRepeat, int notUnder, int notOver) {

        String errorMessage = (notOver == notUnder ? "Has to be " + notUnder : "Has to be between " + notUnder + "-" + notOver + ".");

        while (true) {
            System.out.println(questionToRepeat);
            String input = sc.nextLine();
            try {
                int safeInt = Integer.parseInt(input);
                if (safeInt >= notUnder && safeInt <= notOver) {
                    return safeInt;
                } else {
                    System.out.println(errorMessage);
                }
            } catch (Exception e) {
                System.out.println(errorMessage);
            }
        }
    }



}
