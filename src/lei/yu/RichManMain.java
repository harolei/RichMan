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
                int presentPosition = actionGamer.getPositionOfTheGamer();
                map.refreshMapWhenLandsChanged(presentPosition);
                if(checkIfTheGamerIsInTheHospital(actionGamer)){}
                else if(checkIfGamerIsInThePrison(actionGamer)){}
                else{
                    gamerAction(actionGamer);
                }
                if(actionGamer.getBalanceOfTheGamer()<=0 && actionGamer.getTotalLandNumOfGamer()==0){
                    System.out.println("对不起，您已经破产，游戏失败。");
                    richManGamers.remove(i);
                }
            }
            numOfGamers = richManGamers.size();
        }

        System.out.println("恭喜您，玩家"+richManGamers.get(0).getGamerName()+"获得游戏胜利！");

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
        boolean flagOfRollCommand = true;
        do{
            TextAttributes attributes = new TextAttributes(Color.WHITE);
            console.setTextAttributes(attributes);
            System.out.print("玩家");
            attributes = getTextColor(actionGamer.getGamerName());
            console.setTextAttributes(attributes);
            System.out.print("  "+actionGamer.getGamerName()+" ");
            attributes = new TextAttributes(Color.WHITE);
            console.setTextAttributes(attributes);
            System.out.print("行动，请输入命令："+"\n");
            String command = getCommand();
            int stepsGamerMoves;

            if(command.equals("roll")){
                int gamerPosition = actionGamer.getPositionOfTheGamer();
                RichManLand currentLand = getSpecifiedLand(gamerPosition);
                if(!currentLand.getGamersOnThisLand().isEmpty()){
                   currentLand.removeGamerOnThisLand();
                }
                stepsGamerMoves = actionGamer.getRandomStepsBetween1and6();
                System.out.println("您骰到的点数为："+stepsGamerMoves+", 您可以移动"+stepsGamerMoves+"步。");
                stepsGamerMoves = checkIfTheGamerWillMeetBlock(gamerPosition, stepsGamerMoves);
                if(checkIfTheGamerIsWithTheLuckyGod(actionGamer)){
                    actionGamer.addRoundsWithLuckyGod(1);
                }
                if(checkIfTheGamerWillMeetBomb(gamerPosition,stepsGamerMoves)){
                    setTheGamerKeptInHospital(actionGamer);
                    currentLand.setSpecialLandKind("0");
                    currentLand.setSpecialLandKind(currentLand.getLandKind());
                    map.printMap();
                }
                else{
                    normalMove(actionGamer,stepsGamerMoves);
                }
                flagOfRollCommand = false;
            }
            if(command.startsWith("sell")){
                if(command.substring(4).startsWith("T")){
                    int toolIndex = Integer.valueOf(command.substring(9));
                    sellTool(actionGamer,toolIndex);
                }
                else{
                    int indexOfLand = Integer.valueOf(command.substring(5));
                    sellLand(actionGamer,indexOfLand);
                    map.printMap();
                }
            }
            if(command.equals("query")){
                queryBalanceInformation(actionGamer);
            }
            if(command.startsWith("block")){
                String subCommand = command.substring(6);
                int blockIndex;
                if(subCommand.startsWith("-")){
                    blockIndex = 0-Integer.valueOf(subCommand.substring(1));
                }
                else{
                    blockIndex = Integer.valueOf(subCommand);
                }
                setBlockOnTheLand(actionGamer, blockIndex);
                map.printMap();
            }
            if(command.startsWith("bomb")){
                String subCommand = command.substring(5);
                int bombIndex;
                if(subCommand.startsWith("-")){
                    bombIndex = 0-Integer.valueOf(subCommand.substring(1));
                }
                else{
                    bombIndex = Integer.valueOf(subCommand);
                }
                setBombOnTheLand(actionGamer, bombIndex);
                map.printMap();
            }
            if(command.equals("robot")){
                clearBombsAndBlocksIn10Steps(actionGamer);
                map.printMap();
            }
        }while(flagOfRollCommand);
    }

    private static void normalMove(RichManGamer actionGamer,int stepsGamerMoves) {
        RichManLand currentLand;
        String gamerName = actionGamer.getGamerName();
        actionGamer.setPositionOfTheGamer(stepsGamerMoves);
        currentLand = getSpecifiedLand(actionGamer.getPositionOfTheGamer());
        currentLand.setSpecialLandKind("0");
        currentLand.setSpecialLandKind(currentLand.getLandKind());
        String landKind = currentLand.getSpecialLandKind();
        currentLand.addGamersOnThisLand(gamerName);
        if(landKind.equals("S")||landKind.equals("H")||landKind.equals("T")||landKind.equals("G")||landKind.equals("P")
                ||landKind.equals("M")||landKind.equals("$")){
            if(landKind.equals("$")){
                addPointOfGamer(actionGamer);
            }
            if(landKind.equals("T")&&checkIfTheGamerHasEnoughPoint(actionGamer,30)){
                enterTheToolRoom(actionGamer);
            }
            if(landKind.equals("P")){
                setTheGamerKeptInPrison(actionGamer);
            }
            if(landKind.equals("G")){
                enterTheGiftRoom(actionGamer);
            }
            map.printMap();
        }
        else{
            if(currentLand.getOwner()==null){
                buyLand(actionGamer,currentLand);
            }
            else{
                RichManGamer currentLandOwner = currentLand.getOwner();
                if(currentLandOwner.getGamerName().equals(gamerName)){
                    upGradeLand(actionGamer, currentLand);

                }
                else{
                    if(checkIfTheGamerIsWithTheLuckyGod(actionGamer)){
                        System.out.println("福神附身，免交过路费！");
                    }
                    else{
                        moveToOtherGamersLand(actionGamer,currentLandOwner);
                    }
                    map.printMap();
                }
            }

        }
    }

    private static void addPointOfGamer(RichManGamer actionGamer) {
        int landIndex = actionGamer.getPositionOfTheGamer();
        int point= 0;
        switch(landIndex){
            case 69: point = 60; break;
            case 68: point = 80; break;
            case 67: point = 40; break;
            case 66: point = 100; break;
            case 65: point = 80; break;
            case 64: point = 20; break;
        }
        System.out.println("恭喜您获得"+point+"点！");
        actionGamer.addPoint(point);
    }

    private static boolean checkIfTheGamerIsWithTheLuckyGod(RichManGamer actionGamer) {
        boolean isWithLuckyGod = false;
        int roundsWithLuckyGod = actionGamer.getRoundsWithLuckyGod();
        if(roundsWithLuckyGod>0&&roundsWithLuckyGod<6){
            isWithLuckyGod = true;
            actionGamer.addRoundsWithLuckyGod(1);
        }
        else{
            actionGamer.addRoundsWithLuckyGod(-6);
        }
        return isWithLuckyGod;
    }

    private static void clearBombsAndBlocksIn10Steps(RichManGamer actionGamer) {
        int currentPosition = actionGamer.getPositionOfTheGamer();
        RichManLand specifiedLand;
        if(actionGamer.getNumOfRobot()>0){
            for(int i=0;i<=10;i++){
                specifiedLand = getSpecifiedLand(currentPosition+i);
                specifiedLand.setSpecialLandKind("0");
                specifiedLand.setSpecialLandKind(specifiedLand.getLandKind());
                actionGamer.minusNumOfRobot(1);
            }
            System.out.println("使用机器人道具成功，已成功清理前方10歩内的路障及炸弹！");
        }
        else{
            System.out.println("机器人道具不足，使用道具失败！");
        }
    }

    private static boolean checkIfTheGamerIsInTheHospital(RichManGamer actionGamer) {
        boolean inHospital = false;
        int roundsInHospital = actionGamer.getRoundsInTheHospital();
        if(roundsInHospital>0&&roundsInHospital<4){
            inHospital = true;
            actionGamer.setRoundsInTheHospital(roundsInHospital+1);
        }
        else{
            actionGamer.setRoundsInTheHospital(0);
        }
        return inHospital;
    }

    private static boolean checkIfGamerIsInThePrison(RichManGamer actionGamer) {
        boolean isInPrison = false;
        int roundsInPrison = actionGamer.getRoundsInThePrison();
        if(roundsInPrison>0&&roundsInPrison<3){
            isInPrison = true;
            actionGamer.setRoundsInTheHospital(roundsInPrison+1);
        }
        else{
            actionGamer.setRoundsInTheHospital(0);
        }
        return isInPrison;
    }

    private static void setTheGamerKeptInPrison(RichManGamer actionGamer) {
        actionGamer.setRoundsInThePrison(1);
    }

    private static void setTheGamerKeptInHospital(RichManGamer actionGamer){
        int gamerPosition = actionGamer.getPositionOfTheGamer();
        actionGamer.setPositionOfTheGamer(14-gamerPosition);
        actionGamer.setRoundsInTheHospital(1);
        getSpecifiedLand(14).addGamersOnThisLand(actionGamer.getGamerName());
    }

    private static void buyLand(RichManGamer actionGamer,RichManLand currentLand){
        System.out.println("您目前移动到一块空地，价值：" + currentLand.getPrice() + ",是否购买？(是[Y],否[N])");
        String ifBuy = getCommand();
        if(ifBuy.equalsIgnoreCase("Y")){
            actionGamer.minusBalanceOfTheGamer(currentLand.getPrice());
            if(actionGamer.getBalanceOfTheGamer()>=0){
                System.out.println("购买成功！");
                currentLand.setOwner(actionGamer);
                map.printMap();
                actionGamer.addLevel0LandNumOfGamer(1);
            }
            else{
                System.out.println("余额不足，购买失败！");
            }

        }

    }

    private static void upGradeLand(RichManGamer actionGamer,RichManLand currentLand) {
        int landLevel = currentLand.getLevel()+1;
        if(4==landLevel){
            System.out.println("您移动到了您的土地，该土地目前等级为："+landLevel+"，已经是最高等级，无法继续升级。");
        }
        else{
            System.out.println("您移动到了您的土地，该土地目前等级为："+landLevel+"，升级需要"+currentLand.getPrice()+". 是否进行升级？(是[Y],否[N])");
            String ifUpgrade = getCommand();
            if(ifUpgrade.equalsIgnoreCase("Y")){
                actionGamer.minusBalanceOfTheGamer(currentLand.getPrice());
                if(actionGamer.getBalanceOfTheGamer()>0){
                    System.out.println("升级成功！");
                    currentLand.setOwner(actionGamer);

                    currentLand.setLevel(landLevel);
                    switch(landLevel){
                        case 1:{
                            actionGamer.addLevel1LandNumOfGamer(1);
                            actionGamer.minusLevel0LandNumOfGamer(1);
                            break;
                        }
                        case 2:{
                            actionGamer.addLevel2LandNumOfGamer(1);
                            actionGamer.minusLevel1LandNumOfGamer(1);
                            break;
                        }
                        case 3:{
                            actionGamer.addLevel3LandNumOfGamer(1);
                            actionGamer.minusLevel2LandNumOfGamer(1);
                            break;
                        }
                    }
                    map.printMap();
                }
                else{
                    System.out.println("余额不足，升级失败！");
                }
            }
            else{
                //不升级土地
            }
        }
    }

    private static void moveToOtherGamersLand(RichManGamer actionGamer,RichManGamer currentLandOwner){
        TextAttributes attributes = new TextAttributes(Color.WHITE);
        console.setTextAttributes(attributes);
        RichManLand currentLand = getSpecifiedLand(actionGamer.getPositionOfTheGamer());
        System.out.print("您移动到了玩家");
        attributes = getTextColor(currentLandOwner.getGamerName());
        console.setTextAttributes(attributes);
        System.out.print("      "+currentLandOwner.getGamerName()+" ");
        attributes = new TextAttributes(Color.WHITE);
        console.setTextAttributes(attributes);
        System.out.print("的土地。"+"\n");
        double tolls = currentLand.getPrice()*(currentLand.getLevel()+1)*0.2;
        System.out.println("该土地收取过路费："+ tolls +"元。");
        actionGamer.minusBalanceOfTheGamer(tolls);
        currentLandOwner.addBalanceOfTheGamer(tolls);

    }

    private static boolean checkIfTheGamerWillMeetBomb(int gamerPosition, int stepsGamerMoves) {
        boolean meetBomb = false;
        int checkPosition;
        for(int i=0;i<=stepsGamerMoves;i++){
            checkPosition = gamerPosition+i;
            if(checkPosition>69){
                checkPosition = checkPosition-70;
            }
            RichManLand landOnTheWay = map.getTheCurrentLandGamerIsOn(checkPosition);
            String landKind = landOnTheWay.getSpecialLandKind();
            if(landKind.equals("@")){
                meetBomb = true;
                System.out.println("您遇到了炸弹，将被送到医院医治3轮。");
                landOnTheWay.setSpecialLandKind("0");
                break;
            }
        }
        return meetBomb;
    }

    private static int checkIfTheGamerWillMeetBlock(int gamerPosition, int stepsGamerMoves) {
        int steps = stepsGamerMoves;
        int checkPosition;
        for(int i=0;i<=stepsGamerMoves;i++){
            checkPosition = gamerPosition+i;
            if(checkPosition>69){
                checkPosition = checkPosition-70;
            }
            RichManLand landOnTheWay = map.getTheCurrentLandGamerIsOn(checkPosition);
            String landKind = landOnTheWay.getSpecialLandKind();
            if(landKind.equals("#")){
                steps = i;
                System.out.println("您遇到了路障，只能移动" + steps + "歩。");
                landOnTheWay.setSpecialLandKind("0");
                break;
            }
            if(landKind.equals("@")){
                steps = i;
                break;
            }
        }
        return steps;
    }

    private static void setBombOnTheLand(RichManGamer actionGamer, int bombIndex) {
        int numOfBomb = actionGamer.getNumOfBomb();
        int positionOfBomb = actionGamer.getPositionOfTheGamer()+bombIndex;
        if(positionOfBomb<0){
            positionOfBomb = 70-positionOfBomb;
        }
        if(positionOfBomb>69){
            positionOfBomb = positionOfBomb-70;
        }
        RichManLand currentLand = map.getTheCurrentLandGamerIsOn(positionOfBomb);
        if(!currentLand.getGamersOnThisLand().isEmpty()){
            System.out.println("该位置目前已有玩家，不能放置炸弹。");
        }
        else if(currentLand.getSpecialLandKind().equals("#")){
            System.out.println("该位置已设置路障，不能再设置炸弹。");
        }
        else if(currentLand.getSpecialLandKind().equals("@")){
            System.out.println("该位置已被设置炸弹，不能重复设置。");
        }
        else{
            if(numOfBomb>0){
                map.getTheCurrentLandGamerIsOn(positionOfBomb).setSpecialLandKind("@");
                actionGamer.minusNumOfBomb(1);
                System.out.println("使用炸弹道具成功！已将炸弹设置在距离目前位置"+bombIndex+"的位置处。");
            }
            else{
                System.out.println("对不起，您的炸弹道具不足。设置炸弹失败。");
            }
        }
    }

    private static void sellLand(RichManGamer actionGamer, int indexOfLand) {
        RichManLand sellLand = getSpecifiedLand(indexOfLand);
        String gamerName = actionGamer.getGamerName();
        if(sellLand.getOwner()!=null){
            if(sellLand.getOwner().getGamerName().equals(gamerName)){
                System.out.println("您是否准备出售该块土地？(是[Y],否[N])");
                String ifSell = getCommand();
                if(ifSell.equalsIgnoreCase("Y")){
                    double currentLandCost = (sellLand.getLevel()+1)*sellLand.getPrice()*2;
                    actionGamer.addBalanceOfTheGamer(currentLandCost);
                    sellLand.setLandKind("0");
                    sellLand.setLevel(0);
                    sellLand.setOwner(null);
                    System.out.println("出售成功！您的余额增加了："+currentLandCost+".");
                    switch (sellLand.getLevel()){
                        case 0:actionGamer.minusLevel0LandNumOfGamer(1); break;
                        case 1:actionGamer.minusLevel1LandNumOfGamer(1); break;
                        case 2:actionGamer.minusLevel2LandNumOfGamer(1); break;
                        case 3:actionGamer.minusLevel3LandNumOfGamer(1); break;
                    }
                }
            }
            else{
                System.out.println("这并不是您的土地，不能出售。");
            }
        }
    }

    private static void sellTool(RichManGamer actionGamer, int toolIndex) {
        int sellNum;
        int toolNum;
        int addPoint;
        switch(toolIndex){
            case 1:{
                if(actionGamer.getNumOfBlock()>0){
                    toolNum = actionGamer.getNumOfBlock();
                    System.out.println("您有路障道具"+toolNum+"个。请输入您打算出售的道具个数：");
                    sellNum = Integer.valueOf(getCommand());
                    if(sellNum<toolNum){
                        addPoint = 50*sellNum;
                        System.out.println("出售成功！您的点数增加了"+addPoint+"点！");
                        actionGamer.addPoint(addPoint);
                        actionGamer.minusNumOfBlock(sellNum);
                    }
                    else{
                        System.out.println("出售失败！希望出售的道具数量大于您拥有的道具量。");
                    }
                }
                else{
                    System.out.println("出售失败！您现在没有路障道具，无法出售。");
                }
                break;
            }
            case 2:{
                if(actionGamer.getNumOfBomb()>0){
                    toolNum = actionGamer.getNumOfBomb();
                    System.out.println("您有炸弹道具"+toolNum+"个。请输入您打算出售的道具个数：");
                    sellNum = Integer.valueOf(getCommand());
                    if(sellNum<toolNum){
                        addPoint = 50*sellNum;
                        System.out.println("出售成功！您的点数增加了"+addPoint+"点！");
                        actionGamer.addPoint(addPoint);
                        actionGamer.minusNumOfBomb(sellNum);
                    }
                    else{
                        System.out.println("出售失败！希望出售的道具数量大于您拥有的道具量。");
                    }
                }
                else{
                    System.out.println("出售失败！您现在没有炸弹道具，无法出售。");
                }
                break;
            }
            case 3:{
                if(actionGamer.getNumOfRobot()>0){
                    toolNum = actionGamer.getNumOfRobot();
                    System.out.println("您有机器娃娃道具"+toolNum+"个。请输入您打算出售的道具个数：");
                    sellNum = Integer.valueOf(getCommand());
                    if(sellNum<toolNum){
                        addPoint = 30*sellNum;
                        System.out.println("出售成功！您的点数增加了"+addPoint+"点！");
                        actionGamer.addPoint(addPoint);
                        actionGamer.minusNumOfRobot(sellNum);
                    }
                    else{
                        System.out.println("出售失败！希望出售的道具数量大于您拥有的道具量。");
                    }
                }
                else{
                    System.out.println("出售失败！您现在没有机器娃娃道具，无法出售。");
                }
                break;
            }
        }
    }

    private static void queryBalanceInformation(RichManGamer actionGamer) {
        System.out.println("您的资产信息：");
        System.out.println("资金："+actionGamer.getBalanceOfTheGamer()+"元");
        System.out.println("点数："+actionGamer.getPoint());
        System.out.println("地产：空地"+actionGamer.getLevel0LandNumOfGamerLandNumOfGamer()+"处，"+
                "茅屋"+actionGamer.getLevel1LandNumOfGamerLandNumOfGamer()+"处，"+
                "洋房"+actionGamer.getLevel2LandNumOfGamerLandNum2fGamer()+"处，"+
                "摩天大楼"+actionGamer.getLevel3LandNumOfGamerLandNumOfGamer()+"处");
        System.out.println("道具：路障" + actionGamer.getNumOfBlock() + "个；" +
                "炸弹" + actionGamer.getNumOfBomb() + "个；" +
                "机器娃娃" + actionGamer.getNumOfRobot() + "个");
    }

    private static void setBlockOnTheLand(RichManGamer actionGamer, int blockIndex) {
        int numOfBlock = actionGamer.getNumOfBlock();
        int positionOfBlock = actionGamer.getPositionOfTheGamer()+blockIndex;
        if(positionOfBlock<0){
            positionOfBlock = 70+positionOfBlock;
        }
        if(positionOfBlock>69){
            positionOfBlock = positionOfBlock-70;
        }
        RichManLand currentLand = map.getTheCurrentLandGamerIsOn(positionOfBlock);
        if(!currentLand.getGamersOnThisLand().isEmpty()){
            System.out.println("该位置目前已有玩家，不能放置路障。");
        }
        else if(currentLand.getSpecialLandKind().equals("@")){
            System.out.println("该位置已设置炸弹，不能再设置路障。");
        }
        else if(currentLand.getSpecialLandKind().equals("#")){
            System.out.println("该位置已被设置路障，不能重复设置。");
        }
        else{
            if(numOfBlock>0){
                map.getTheCurrentLandGamerIsOn(positionOfBlock).setSpecialLandKind("#");
                actionGamer.minusNumOfBlock(1);
                System.out.println("使用路障道具成功！已将路障设置在距离目前位置"+blockIndex+"的位置处。");
            }
            else{
                System.out.println("对不起，您的路障道具不足。设置路障失败。");
            }
        }

    }

    private static void enterTheToolRoom(RichManGamer actionGamer) {
        System.out.println("欢迎光临道具屋， 请选择您所需要的道具：");
        System.out.print("道具        编号    价值（点数）    显示方式\n" +
                "路障         1         50             ＃\n" +
                "机器娃娃     2         30\n" +
                "炸弹         3         50             @\n");
        System.out.println("按“F”可手工退出道具屋。");
        String command = getCommand();
        if(command.equals("F")){}
        else{
            int toolIndex = Integer.valueOf(command);
            buyTheTool(actionGamer,toolIndex);
        }
    }

    private static void enterTheGiftRoom(RichManGamer actionGamer) {
        System.out.println("欢迎光临礼品屋， 请选择您想要的礼品：");
        System.out.print("礼品        编号      备注\n" +
                          "奖金         1        2000元\n" +
                          "点数         2        200点\n" +
                          "福神         3        福神附身，路过其它玩家地盘，均可免费。5轮内有效。\n");
        String command = getCommand();
        int giftIndex = Integer.valueOf(command);
        getTheGift(actionGamer, giftIndex);
    }

    private static void getTheGift(RichManGamer actionGamer, int giftIndex) {
        switch(giftIndex){
            case 1:{
                actionGamer.addBalanceOfTheGamer(2000);
                System.out.println("礼物选择成功！您的资金增加了2000元。");
                break;
            }
            case 2:{
                actionGamer.addPoint(200);
                System.out.println("礼物选择成功！您的点数增加了200点。");
                break;
            }
            case 3:{
                actionGamer.addRoundsWithLuckyGod(1);
                System.out.println("礼物选择成功！您现在有福神附身，5轮内路过别人的土地免收过路费。");
                break;
            }
        }
    }

    private static void buyTheTool(RichManGamer actionGamer,int toolIndex) {
        int totalNumOfTool = actionGamer.getNumOfBlock()+actionGamer.getNumOfBomb()+actionGamer.getNumOfRobot();
        if(totalNumOfTool>=10){
            System.out.println("您已经拥有10件道具，不能再继续购买道具。");
        }
        else{
            boolean enoughPoint;
            switch(toolIndex){
                case 1:{
                    enoughPoint = checkIfTheGamerHasEnoughPoint(actionGamer,50);
                    if(enoughPoint){
                        actionGamer.addNumOfBlock(1);
                        actionGamer.minusPoint(50);
                        System.out.println("购买路障道具成功！");
                    }
                    else{
                        System.out.println("您当前剩余的点数为"+actionGamer.getPoint()+"， 不足以购买路障道具。");
                    }
                    break;
                }
                case 2:{
                    actionGamer.addNumOfRobot(1);
                    actionGamer.minusPoint(30);
                    System.out.println("购买机器娃娃道具成功！");
                    break;
                }
                case 3:{
                    enoughPoint = checkIfTheGamerHasEnoughPoint(actionGamer,50);
                    if(enoughPoint){
                        actionGamer.addNumOfBomb(1);
                        actionGamer.minusPoint(50);
                        System.out.println("购买炸弹道具成功！");
                    }
                    else{
                        System.out.println("您当前剩余的点数为"+actionGamer.getPoint()+"， 不足以购买炸弹道具。");
                    }
                    break;
                }
            }
        }
    }

    private static boolean checkIfTheGamerHasEnoughPoint(RichManGamer actionGamer,int point){
        boolean enoughPoint = false;
        if(actionGamer.getPoint()>=point){
            enoughPoint = true;
        }
        return enoughPoint;
    }

    private static TextAttributes getTextColor(String gamerName){
        TextAttributes attributes = new TextAttributes(Color.WHITE);
        if(gamerName.equals("Q")){
            attributes = new TextAttributes(Color.RED);
        }
        else if(gamerName.equals("X")){
            attributes = new TextAttributes(Color.GREEN);
        }
        else if(gamerName.equals("A")){
            attributes = new TextAttributes(Color.YELLOW);
        }
        else if(gamerName.equals("J")){
            attributes = new TextAttributes(Color.BLUE);
        }
        return attributes;
    }
    private static final Console console;
    static
    {
        console = Enigma.getConsole("大富翁游戏");
    }
}
