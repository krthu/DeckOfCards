
import java.util.*;
import java.util.concurrent.TimeUnit;

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
        //BlackJackPlayer player1 = new BlackJackPlayer("Kristian", 1000);
        //BlackJackPlayer player2 = new BlackJackPlayer("Linus", 1000);
        List<BlackJackPlayer> allPlayers = new ArrayList<>();
        int numberOfPlayers = getIntSafe("How many players?",1, 7);
        addPlayers(numberOfPlayers, allPlayers);
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

    public void playRound(List<BlackJackPlayer> allPlayers){
        // Create a list of cards for the dealer
        List<Card> dealerCards = new ArrayList<>();
        // shuffle the deck
        Collections.shuffle(deck.getCards());
        // Place bets
        placeBets(allPlayers);
        // give cards to players
        for (BlackJackPlayer player : allPlayers) {
            player.addCard(deck.getCards().remove(0));
            player.addCard(deck.getCards().remove(0));
        }
        dealerCards.add(deck.drawACard());
        dealerCards.add(deck.drawACard());

        if (calculateScore(dealerCards) != 21){
            dealCardsToPlayers(allPlayers, dealerCards);
            playDealerHand(dealerCards);
            }
        else {
            System.out.println("Dealer has " + printDealerCards(true, dealerCards));
            System.out.println("That is BlackJack!");
        }

        // check game state (checkWinner)

        printDealerCards(false, dealerCards);

        // reveal dealer card
        checkWinner(allPlayers, dealerCards);

        // CLear the hands of all players
        clearPlayerCards(allPlayers);
        dealerCards.clear();

        
        // print result
    }

    private void placeBets(List<BlackJackPlayer> allPlayers) {
        for (BlackJackPlayer player : allPlayers) {
            boolean madeBet;

            do {
                System.out.println(player.getName() + " you have " + player.getMoney() + " How much do you wanna bet?");

                int totalBet = getIntSafe("Enter an amount to bet: ", 1, (int) player.getMoney());

                madeBet = player.setBet(totalBet);

                if (!madeBet) {
                    System.out.println("Not enough money to place bet, try again");
                }
            } while (!madeBet);
        }
    }

    public void checkWinner(List<BlackJackPlayer> allPlayers, List<Card> dealerCards){
        int dealerScore = calculateScore(dealerCards);
        System.out.println("Dealer's cards: " + dealerCards + ". Dealers score: " + dealerScore);

        for (BlackJackPlayer player : allPlayers) {
            int playerScore = calculateScore(player.getCardsInHand());
            System.out.println(player.getName() + " cards: " + player.getCardsInHand() + ". Score " + playerScore);

            // Check for Blackjack
            if (playerScore == 21 && player.getCardsInHand().size() == 2 && dealerScore != 21 && dealerCards.size() == 2) {
                System.out.println(player.getName() + " got Blackjack! You win 2.5x your bet.");
                player.resolveBet(2.5);
                continue;
            }

            if (dealerScore == 21 && dealerCards.size() == 2 && playerScore != 21 && player.getCardsInHand().size() == 2){
                System.out.println("Dealer got Blackjack" + player.getName() + " loses their bet.");
                player.resolveBet(0);
                continue;
            }

            // Check for busts
            if (playerScore > 21) {
                System.out.println(player.getName() + " went over 21. You lose your bet");
                player.resolveBet(0);
                continue;
            }

            if (dealerScore > 21) {
                System.out.println("Dealer went over 21. " + player.getName() + " wins double the bet.");
                player.resolveBet(2);
                continue;
            }

            // Regular outcomes
            if (playerScore > dealerScore){
                System.out.println(player.getName() + " wins double the bet");
                player.resolveBet(2);
            } else if (playerScore == dealerScore) {
                System.out.println("It's a tie! " + player.getName() + " gets the bet back.");
                player.resolveBet(1);
            } else {
                System.out.println(player.getName() + " loses the bet");
                player.resolveBet(0);
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

    public void addPlayers(int numberOfPlayers, List<BlackJackPlayer> allPlayers){
        for (int i = 1; i <= numberOfPlayers; i++){
            System.out.println("Name of player " + i);
            String name = sc.nextLine();
            System.out.println("How much money to put in?");
            int money = sc.nextInt(); // Need to safe check this
            sc.nextLine();
            allPlayers.add(new BlackJackPlayer(name, money));
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

    public void dealCardsToPlayers(List<BlackJackPlayer> allPlayers, List<Card> dealerCards){
        for (BlackJackPlayer player: allPlayers)
        {
            System.out.println();
            System.out.println(printDealerCards(false, dealerCards));
            dealCardsToPlayer(player);
        }
    }


    public void dealCardsToPlayer(BlackJackPlayer player){
        while (true){
            System.out.println(player.getName() + ": " + player.cardsToString() + " Value: " + calculateScore(player.getCardsInHand()));
            if (calculateScore(player.getCardsInHand()) == 21 && player.getCardsInHand().size() == 2) {
                System.out.println("You have Blackjack!");
                return;
            }
            if(calculateScore(player.getCardsInHand()) > 21){
                System.out.println(player.getName() + " you bust!");
                return;
            }
            System.out.println("Do you want to hit?(y/n)");
            if (sc.nextLine().trim().equalsIgnoreCase("y")){
                hit(player);
            }else {
                return;
            }
        }
    }

    public String printDealerCards(boolean all, List<Card> cards){
        StringBuilder builder = new StringBuilder();
        builder.append("Dealer has: ");
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
        return builder.toString();
    }

    public List<Card> playDealerHand(List<Card> dealerCards){
        System.out.println(printDealerCards(true, dealerCards) + " Value " +calculateScore(dealerCards));
        while (!(calculateScore(dealerCards) >= 17)){
            try{
                TimeUnit.SECONDS.sleep(1);
            }catch (Exception e){

            }

            dealerCards.add(deck.drawACard());
            System.out.println(printDealerCards(true, dealerCards) + " Value: " + calculateScore(dealerCards));
        }
        return dealerCards;
    }

    public List<BlackJackPlayer> clearPlayerCards(List<BlackJackPlayer> allPlayers){
        for (BlackJackPlayer player: allPlayers) {
            player.discardHand();
        }
        return allPlayers;
    }


}




