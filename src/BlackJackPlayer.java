public class BlackJackPlayer extends Player{
    private double money;
    private double bet;

    public BlackJackPlayer(String name, int money){
        super(name);
        this.money = money;

    }
    public double getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public double getBet() {
        return bet;
    }

    public boolean setBet(int bet) {
        if (bet <= money){
            money -= bet;
            this.bet = bet;
            return true;
        }
        return false;
    }

    // Takes a multiplier and adds winnings to money. 2 för regular win 2.5 for BJ. 1 för returning the bet
    public void resolveBet(double winMultiplier){
        money += (bet * winMultiplier);
        bet = 0;
    }

    public String toString(){
        return "Name: " + getName() + " Money " + money + " Bet: " + bet + " Cards: " + getCardsInHand();
    }
}
