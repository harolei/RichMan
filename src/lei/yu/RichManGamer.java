package lei.yu;

public class RichManGamer {
    private double balanceOfTheGamer;
    private int positionOfTheGamer;
    private String gamerName;

    public RichManGamer(){
        this.balanceOfTheGamer = 10000;
        this.positionOfTheGamer = 0;
    }

    public double getBalanceOfTheGamer() {
        return balanceOfTheGamer;
    }

    public void addBalanceOfTheGamer(double balanceOfTheGamer) {
        this.balanceOfTheGamer += balanceOfTheGamer;
    }

    public void minusBalanceOfTheGamer(double cost){
        this.balanceOfTheGamer -= cost;
    }

    public int getPositionOfTheGamer() {
        if(positionOfTheGamer>69)
            positionOfTheGamer -=69;
        return positionOfTheGamer;
    }

    public void setPositionOfTheGamer(int positionOfTheGamer) {
        this.positionOfTheGamer += positionOfTheGamer;
    }

    public String getGamerName() {
        return gamerName;
    }

    public void setGamerName(String gamerName) {
        this.gamerName = gamerName;
    }

    public int getRandomStepsBetween1and6() {
        return (int)(Math.random()*6)+1;
    }
}
