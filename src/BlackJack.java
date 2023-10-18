import java.util.*;

public class BlackJack {
    private Deck deck;
    private Scanner scanner;

    public BlackJack() {
        deck = new Deck();
        scanner = new Scanner(System.in);
    }

    public void play() {
        List<Card> playerCards = new ArrayList<>();
        List<Card> dealerCards = new ArrayList<>();

        // Shuffle the deck
        Collections.shuffle(deck.getCards());

        // Draw cards for player and dealer
        playerCards.add(deck.getCards().remove(0));
        playerCards.add(deck.getCards().remove(0));
        dealerCards.add(deck.getCards().remove(0));
        dealerCards.add(deck.getCards().remove(0));

        while (true) {
            System.out.println("Your cards: " + playerCards + " Value " + calculateScore(playerCards));
            System.out.println("Dealer's first card: " + dealerCards.get(0));

            System.out.println("Do you want to hit (y/n)");
            String answer = scanner.nextLine().trim().toLowerCase();

            if ("y".equals(answer)) {
                playerCards.add(deck.getCards().remove(0));
                if (calculateScore(playerCards) > 21) {
                    System.out.println("Your cards: " + playerCards + ". Your score is over 21. You lose!");
                    break;
                }
            }  else {
                break; // Break the loop if player choose not to draw a new card.
            }
        }

        while (calculateScore(dealerCards) < 17) {
            dealerCards.add(deck.getCards().remove(0));
        }

        int playerScore = calculateScore(playerCards);
        int dealerScore = calculateScore(dealerCards);

        System.out.println("Your cards: " + playerCards + ". Your scores: " + playerScore);
        System.out.println("Dealer's cards " + dealerCards + " Dealer's score: " + dealerScore);

        if (playerScore > 21) {
            System.out.println("You lose!");
        } else if (dealerScore > 21 || playerScore > dealerScore) {
            System.out.println("You win!");
        } else if (playerScore == dealerScore) {
            System.out.println("It's a tie!");
        } else {
            System.out.println("Dealer wins!");
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
}
