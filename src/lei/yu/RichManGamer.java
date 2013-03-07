package lei.yu;

public class RichManGamer {
    private double balanceOfTheGamer = 10000;
    private int positionOfTheGamer = 0;
    private String gamerName;
    private int level0LandNumOfGamer = 0;
    private int level1LandNumOfGamer = 0;
    private int level2LandNumOfGamer = 0;
    private int level3LandNumOfGamer = 0;
    private int point = 0;
    private int numOfBlock = 1;
    private int numOfBomb = 1;
    private int numOfRobot = 1;
    private int roundsInTheHospital = 0;
    private int roundsInThePrison= 0;
    private int roundsWithLuckyGod = 0;

    public int getRoundsWithLuckyGod() {
        return roundsWithLuckyGod;
    }

    public void setRoundsWithLuckyGod(int roundsWithLuckyGod) {
        this.roundsWithLuckyGod = roundsWithLuckyGod;
    }

    public int getRoundsInThePrison() {
        return roundsInThePrison;
    }

    public void setRoundsInThePrison(int roundsInThePrison) {
        this.roundsInThePrison = roundsInThePrison;
    }

    public int getRoundsInTheHospital() {
        return roundsInTheHospital;
    }

    public void setRoundsInTheHospital(int roundsInTheHospital) {
        this.roundsInTheHospital = roundsInTheHospital;
    }

    public int getNumOfBlock() {
        return numOfBlock;
    }

    public void addNumOfBlock(int numOfBlock){
        this.numOfBlock += numOfBlock;
    }

    public void minusNumOfBlock(int numOfBlock){
        this.numOfBlock -= numOfBlock;
    }

    public int getNumOfBomb() {
        return numOfBomb;
    }

    public void addNumOfBomb(int numOfBomb){
        this.numOfBomb += numOfBomb;
    }

    public void minusNumOfBomb(int numOfBomb){
        this.numOfBomb -= numOfBomb;
    }

    public int getNumOfRobot() {
        return numOfRobot;
    }

    public void addNumOfRobot(int numOfRobot){
        this.numOfRobot += numOfRobot;
    }

    public void minusNumOfRobot(int numOfRobot){
        this.numOfRobot -= numOfRobot;
    }

    public int getPoint() {
        return point;
    }

    public void addPoint(int point) {
        this.point += point;
    }

    public void minusPoint(int point){
        this.point -= point;
    }

    public int getLevel0LandNumOfGamerLandNumOfGamer() {
        return level0LandNumOfGamer;
    }

    public void addLevel0LandNumOfGamer(int level0LandNumOfGamer) {
        this.level0LandNumOfGamer += level0LandNumOfGamer;
    }

    public void minusLevel0LandNumOfGamer(int level0LandNumOfGamer) {
        this.level0LandNumOfGamer -= level0LandNumOfGamer;
    }

    public int getLevel1LandNumOfGamerLandNumOfGamer() {
        return level1LandNumOfGamer;
    }

    public void addLevel1LandNumOfGamer(int level1LandNumOfGamer) {
        this.level1LandNumOfGamer += level1LandNumOfGamer;
    }

    public void minusLevel1LandNumOfGamer(int level1LandNumOfGamer) {
        this.level1LandNumOfGamer -= level1LandNumOfGamer;
    }

    public int getLevel2LandNumOfGamerLandNum2fGamer() {
        return level2LandNumOfGamer;
    }

    public void addLevel2LandNumOfGamer(int level2LandNumOfGamer) {
        this.level2LandNumOfGamer += level2LandNumOfGamer;
    }

    public void minusLevel2LandNumOfGamer(int level2LandNumOfGamer) {
        this.level2LandNumOfGamer -= level2LandNumOfGamer;
    }

    public int getLevel3LandNumOfGamerLandNumOfGamer() {
        return level3LandNumOfGamer;
    }

    public void addLevel3LandNumOfGamer(int level3LandNumOfGamer) {
        this.level3LandNumOfGamer += level3LandNumOfGamer;
    }

    public void minusLevel3LandNumOfGamer(int level3LandNumOfGamer) {
        this.level3LandNumOfGamer -= level3LandNumOfGamer;
    }

    public int getTotalLandNumOfGamer(){
        return this.level0LandNumOfGamer+this.level1LandNumOfGamer+this.level2LandNumOfGamer+this.level3LandNumOfGamer;
    }

    public RichManGamer(){
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
