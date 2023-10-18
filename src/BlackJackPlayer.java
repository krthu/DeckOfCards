public class BlackJackPlayer extends Player{
    private int money;
    private int bet;

    public BlackJackPlayer(String name, int money){
        super(name);
        this.money = money;

    }
    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getBet() {
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
    public String toString(){
        return "Name: " + getName() + " Money " + money + " Bet: " + bet + " Cards: " + getCardsInHand();
    }
}
