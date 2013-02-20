package lei.yu;

import java.awt.*;
import java.util.Scanner;
import enigma.console.Console;
import enigma.console.TextAttributes;
import enigma.core.Enigma;

public class RichManMain {
    private static RichManGamer gamer = new RichManGamer();
    private static RichManMap map = new RichManMap();

    public static void main(String[] args){
        TextAttributes attributes = new TextAttributes(Color.BLUE);
        console.setTextAttributes(attributes);
        System.out.println("请选择玩家，输入编号即可：");
        System.out.println("1.钱夫人; 2.阿土伯; 3.孙小美; 4.金贝贝");
        String[] gamerNames = {"Q","A","X","J"};
        int indexOfGamerName = Integer.valueOf(getCommand());
        String gamerName = gamerNames[indexOfGamerName-1];
        gamer.setGamerName(gamerName);
        System.out.println("谢谢！您的玩家名字缩写为：" + gamerName + ", 初始资金：10000.");
        while(gamer.getBalanceOfTheGamer()>0){
            System.out.println("请输入命令：");
            String command = getCommand();
            int stepsGamerMoves;

            if(command.equals("roll")){
                stepsGamerMoves = gamer.getRandomStepsBetween1and6();
                gamer.setPositionOfTheGamer(stepsGamerMoves);
                System.out.println("您骰到的点数为："+stepsGamerMoves+", 您可以移动"+stepsGamerMoves+"步。");
                RichManLand currentLand = getSpecifiedLand(gamer.getPositionOfTheGamer());
                String landKind = currentLand.getLandKind();
                currentLand.setGamerIsOnThisLandOrNot(true);
                if(landKind.equals("S")||landKind.equals("H")||landKind.equals("T")||landKind.equals("G")||landKind.equals("P")
                        ||landKind.equals("M")||landKind.equals("$")){
                    currentLand.setLandKind(gamerName);
                    System.out.println(map.refreshMapWhenLandsChanged(gamer.getPositionOfTheGamer()));
                    currentLand.setGamerIsOnThisLandOrNot(false);
                    currentLand.setLandKind(landKind);
                }
                else{
                     if(0==currentLand.getLevel()){
                         System.out.println("您目前移动到一块空地，价值："+currentLand.getPrice()+",是否购买？(是[Y],否[N])");
                         String ifBuy = getCommand();
                         if(ifBuy.equalsIgnoreCase("Y")){
                             gamer.minusBalanceOfTheGamer(currentLand.getPrice());
                             if(gamer.getBalanceOfTheGamer()>0){
                                 System.out.println("购买成功！");
                                 currentLand.setOwner(gamerName);
                                 map.setTheCurrentGamerOnTheLand(gamer.getPositionOfTheGamer(),gamerName);
                                 System.out.println(map.refreshMapWhenLandsChanged(gamer.getPositionOfTheGamer()));
                                 map.printMap();
                                 currentLand.setGamerIsOnThisLandOrNot(false);
                                 map.refreshMapWhenLandsChanged(gamer.getPositionOfTheGamer());
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
                                 gamer.minusBalanceOfTheGamer(currentLand.getPrice());
                                 if(gamer.getBalanceOfTheGamer()>0){
                                     System.out.println("升级成功！");
                                     currentLand.setOwner(gamerName);
                                     map.setTheCurrentGamerOnTheLand(gamer.getPositionOfTheGamer(),gamerName);
                                     System.out.println(map.refreshMapWhenLandsChanged(gamer.getPositionOfTheGamer()));
                                     currentLand.setGamerIsOnThisLandOrNot(false);
                                     map.refreshMapWhenLandsChanged(gamer.getPositionOfTheGamer());
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
                        gamer.addBalanceOfTheGamer(currentLandCost);
                        sellLand.setLandKind("0");
                        sellLand.setLevel(0);
                        System.out.println("出售成功！您的余额增加了："+currentLandCost+".");
                        System.out.println(map.refreshMapWhenLandsChanged(gamer.getPositionOfTheGamer()));
                    }
                }

            }
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
}
