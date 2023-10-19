
import java.util.*;

public class BlackJack {
    private Deck deck;
    private Scanner sc;

    private ArrayList<BlackJackPlayer> players;

    public BlackJack() {
        deck = new Deck(6);
        sc = new Scanner(System.in);
        players = new ArrayList<>();
    }

    public void play() {
        Player player1 = new BlackJackPlayer("Kristian", 1000);
        Player player2 = new BlackJackPlayer("Linus", 1000);
        List<Player> allPlayers = Arrays.asList(player1, player2);

        while (true) {
            playRound(allPlayers);

            // ask user if they want to play again
            System.out.println("Do you want to play again? (y/n)");
            String userInput = sc.nextLine().trim().toLowerCase();

            if (!"y".equals(userInput)) {
                break;
            }
        }

    }

    public void playRound(List<Player> allPlayers){
        // Create a list of cards for the dealer
        List<Card> dealerCards = new ArrayList<>();
        // shuffle the deck
        Collections.shuffle(deck.getCards());
        // Place bets
        placeBets();
        // give cards to players
        for (BlackJackPlayer player : players) {
            player.addCard(deck.getCards().remove(0));
            player.addCard(deck.getCards().remove(0));
        }
        dealerCards.add(deck.drawACard());
        dealerCards.add(deck.drawACard());
        printDealerCards(false, dealerCards);
        // reveal dealer card
        // check game state (checkWinner)
        
        // print result
    }

    private void placeBets() {
        for (BlackJackPlayer player : players) {
            int totalBet = 0;
            do {
                System.out.println(player.getName() + " you have " + player.getMoney() + " How much do you wanna bet?");

                totalBet = getIntSafe("Enter an amount to bet: ", 1, (int) player.getMoney());
                if (!player.setBet(totalBet)) {
                    System.out.println("Not enough money to place bet, try again");
                }
            } while (!player.setBet(totalBet));

        }
    }

    public void checkWinner(List<BlackJackPlayer> players, List<Card> dealerCards){
        int dealerScore = calculateScore(dealerCards);

        System.out.println("Dealer's cards: " + dealerCards + ". Dealer's score : " + dealerScore);

        for (BlackJackPlayer player : players) {
            int playerScore = calculateScore(player.getCardsInHand());
            System.out.println(player.getName() + " 's cards: " + player.cardsToString());

            if (playerScore > 21) {
                System.out.println(player.getName() + " loses (You're busted)!");
            } else if (dealerScore > 21 || playerScore < dealerScore) {
                System.out.println(player.getName() + " wins!");
            } else if (playerScore == dealerScore) {
                System.out.println("It's a tie for " + player.getName() + "!");
            } else {
                System.out.println(player.getName() + " loses!");
            }
        }
    }

    private int calculateScore(List<Card> cards) {
        int total = 0;
        int aceCount = 0;

        for (Card card : cards) {
            if (card.getValue() == 1) {
                aceCount++;
                total += 11;
            } else if (card.getValue() >= 10) {
                total += 10;
            } else {
                total += card.getValue();
            }
        }
        while (total > 21 && aceCount > 0) {
            total -= 10;
            aceCount--;
        }

        return total;
    }


    public void hit(Player player){
        player.addCard(deck.drawACard());
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

    public void printDealerCards(boolean all, List<Card> cards){
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < cards.size(); i++) {
            if (i == 0 && !all){
                builder.append("Hidden");
            }else {
                builder.append(cards.get(i));
            }
            if (i != cards.size() -1){
                builder.append(", ");
            }
        }
        System.out.println(builder.toString());
    }
}




