package lei.yu;

import java.awt.*;
import java.util.*;
import java.util.List;

import enigma.console.Console;
import enigma.console.TextAttributes;
import enigma.core.Enigma;

public class RichManMain {
    private static RichManMap map = new RichManMap();
    private static int numOfGamers = 0;
    private static List<RichManGamer> richManGamers = new ArrayList<RichManGamer>();

    public static void main(String[] args){
        TextAttributes attributes = new TextAttributes(Color.BLUE);
        console.setTextAttributes(attributes);
        String[] gamerNames = {"Q","A","X","J"};
        System.out.println("请选择2~4位不重复玩家，输入编号即可。(1.钱夫人; 2.阿土伯; 3.孙小美; 4.金贝贝):");
        int[] gamerIndexes = getGamers();

        for(int i=0;i<numOfGamers;i++){
            richManGamers.add(new RichManGamer());
            richManGamers.get(i).setGamerName(gamerNames[gamerIndexes[i]-1]);
        }
        while(numOfGamers>1){
            for(int i=0;i<numOfGamers;i++){
                RichManGamer actionGamer = richManGamers.get(i);
                System.out.println("玩家"+actionGamer.getGamerName()+"行动，请输入命令：");
                gamerAction(actionGamer);
                if(actionGamer.getBalanceOfTheGamer()<=0 && actionGamer.getLandNumOfGamer()==0){
                    System.out.println("对不起，您已经破产，游戏失败。");
                    richManGamers.remove(i);
                }
            }
            numOfGamers = richManGamers.size();
        }

    }
    private static final Console console;
    static
    {
        console = Enigma.getConsole("Hello World!");
    }

    private static String getCommand(){
        String command;
        Scanner scanner = new Scanner(System.in);
        command = scanner.nextLine();
        return command;
    }

    private static RichManLand getSpecifiedLand(int indexOfLand){
        return map.getTheCurrentLandGamerIsOn(indexOfLand);
    }

    private static int[] getGamers(){
        String indexOfGamers = getCommand();
        numOfGamers = indexOfGamers.length();
        int[] gamerIndexes = new int[numOfGamers];
        for(int i=0;i<numOfGamers;i++){
           gamerIndexes[i] = Integer.valueOf(indexOfGamers.substring(i,i+1));
        }
        return gamerIndexes;
    }

    private static void gamerAction(RichManGamer actionGamer){
        String command = getCommand();
        String gamerName = actionGamer.getGamerName();
        int stepsGamerMoves;

        if(command.equals("roll")){
            stepsGamerMoves = actionGamer.getRandomStepsBetween1and6();
            actionGamer.setPositionOfTheGamer(stepsGamerMoves);
            System.out.println("您骰到的点数为："+stepsGamerMoves+", 您可以移动"+stepsGamerMoves+"步。");
            RichManLand currentLand = getSpecifiedLand(actionGamer.getPositionOfTheGamer());
            String landKind = currentLand.getLandKind();
            currentLand.setGamerIsOnThisLandOrNot(true);
            if(landKind.equals("S")||landKind.equals("H")||landKind.equals("T")||landKind.equals("G")||landKind.equals("P")
                    ||landKind.equals("M")||landKind.equals("$")){
                currentLand.setLandKind(gamerName);
                currentLand.setGamerIsOnThisLandOrNot(false);
                currentLand.setLandKind(landKind);
            }
            else{
                if(0==currentLand.getLevel()){
                    System.out.println("您目前移动到一块空地，价值："+currentLand.getPrice()+",是否购买？(是[Y],否[N])");
                    String ifBuy = getCommand();
                    if(ifBuy.equalsIgnoreCase("Y")){
                        actionGamer.minusBalanceOfTheGamer(currentLand.getPrice());
                        if(actionGamer.getBalanceOfTheGamer()>0){
                            System.out.println("购买成功！");
                            currentLand.setOwner(gamerName);
                            map.setTheCurrentGamerOnTheLand(actionGamer.getPositionOfTheGamer(),gamerName);
                            map.printMap();
                            currentLand.setGamerIsOnThisLandOrNot(false);
                            map.refreshMapWhenLandsChanged(actionGamer.getPositionOfTheGamer());
                            actionGamer.setLandNumOfGamer(1);
                        }
                        else{
                            System.out.println("余额不足，购买失败！");
                        }

                    }
                }
                else{
                    if(currentLand.getOwner().equals(gamerName)){
                        System.out.println("您移动到了您的土地，该土地目前等级为："+currentLand.getLevel()+"，升级需要"+currentLand.getPrice()+". 是否进行升级？(是[Y],否[N])");
                        String ifUpgrade = getCommand();
                        if(ifUpgrade.equalsIgnoreCase("Y")){
                            actionGamer.minusBalanceOfTheGamer(currentLand.getPrice());
                            if(actionGamer.getBalanceOfTheGamer()>0){
                                System.out.println("升级成功！");
                                currentLand.setOwner(gamerName);
                                map.setTheCurrentGamerOnTheLand(actionGamer.getPositionOfTheGamer(),gamerName);
                                currentLand.setGamerIsOnThisLandOrNot(false);
                                map.refreshMapWhenLandsChanged(actionGamer.getPositionOfTheGamer());
                            }
                            else{
                                System.out.println("余额不足，升级失败！");
                            }
                        }
                        else{
                            //不升级土地
                        }
                    }
                    else{
                        //移动到别人的土地
                    }
                }

            }

        }
        if(command.startsWith("sell")){
            int indexOfLand = Integer.valueOf(command.substring(5));
            RichManLand sellLand = getSpecifiedLand(indexOfLand);
            if(sellLand.getOwner().equals(gamerName)){
                System.out.println("您是否准备出售该块土地？(是[Y],否[N])");
                String ifSell = getCommand();
                if(ifSell.equalsIgnoreCase("Y")){
                    double currentLandCost = sellLand.getLevel()*sellLand.getPrice();
                    actionGamer.addBalanceOfTheGamer(currentLandCost);
                    sellLand.setLandKind("0");
                    sellLand.setLevel(0);
                    System.out.println("出售成功！您的余额增加了："+currentLandCost+".");
                    System.out.println(map.refreshMapWhenLandsChanged(actionGamer.getPositionOfTheGamer()));
                }
            }

        }

    }
}
