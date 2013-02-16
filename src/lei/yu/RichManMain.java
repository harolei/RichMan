package lei.yu;

import java.util.Scanner;

public class RichManMain {
    public static void main(String[] args){
        System.out.println("请选择玩家，输入编号即可：");
        System.out.println("1.钱夫人; 2.阿土伯; 3.孙小美; 4.金贝贝");
        String[] gamerNames = {"Q","A","X","J"};
        Scanner sc = new Scanner(System.in);
        int indexOfGamerName = sc.nextInt();
        String gamerName = gamerNames[indexOfGamerName-1];
        RichManGamer gamer = new RichManGamer();
        RichManMap map = new RichManMap();
        gamer.setGamerName(gamerName);
        System.out.println("谢谢！您的玩家名字缩写为：" + gamerName + ", 初始资金：10000.");
        while(gamer.getBalanceOfTheGamer()>0){
            System.out.println("请输入命令：");
            Scanner scanner = new Scanner(System.in);
            String command = scanner.nextLine();
            int stepsGamerMoves;

            if(command.equals("roll")){
                stepsGamerMoves = gamer.getRandomStepsBetween1and6();
                gamer.setPositionOfTheGamer(stepsGamerMoves);
                System.out.println("您骰到的点数为："+stepsGamerMoves+", 您可以移动"+stepsGamerMoves+"步。");
                RichManLand currentLand = map.getTheCurrentLandGamerIsOn(gamer.getPositionOfTheGamer());
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
                         Scanner tempScanner = new Scanner(System.in);
                         String ifBuy = tempScanner.nextLine();
                         if(ifBuy.equalsIgnoreCase("Y")){
                             gamer.minusBalanceOfTheGamer(currentLand.getPrice());
                             if(gamer.getBalanceOfTheGamer()>0){
                                 System.out.println("购买成功！");
                                 currentLand.setOwner(gamerName);
                                 map.setTheCurrentGamerOnTheLand(gamer.getPositionOfTheGamer(),gamerName);
                                 System.out.println(map.refreshMapWhenLandsChanged(gamer.getPositionOfTheGamer()));
                                 currentLand.setGamerIsOnThisLandOrNot(false);
                                 map.refreshMapWhenLandsChanged(gamer.getPositionOfTheGamer());
                             }
                             else{
                                 System.out.println("余额不足，购买失败！");
                             }

                         }
                     }
                    if(currentLand.getOwner().equals(gamerName)){
                        System.out.println("您移动到了您的土地，该土地目前等级为："+currentLand.getLevel()+"，升级需要"+currentLand.getPrice()+". 是否进行升级？(是[Y],否[N])");
                        Scanner tempScanner = new Scanner(System.in);
                        String ifUpgrade = tempScanner.nextLine();
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
                    }
                }

            }
        }
    }
}
