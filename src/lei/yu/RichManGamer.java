package lei.yu;

public class RichManGamer {
    private double balanceOfTheGamer;
    private int positionOfTheGamer;

    public double getBalanceOfTheGamer() {
        return balanceOfTheGamer;
    }

    public void setBalanceOfTheGamer(double balanceOfTheGamer) {
        this.balanceOfTheGamer = balanceOfTheGamer;
    }

    public int getPositionOfTheGamer() {
        return positionOfTheGamer;
    }

    public void setPositionOfTheGamer(int positionOfTheGamer) {
        this.positionOfTheGamer = positionOfTheGamer;
    }

    public RichManGamer(){
        this.balanceOfTheGamer = 10000;
        this.positionOfTheGamer = 0;
    }



}