package lei.yu;

import enigma.console.Console;
import enigma.console.TextAttributes;
import enigma.core.Enigma;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RichManMap {
    private List<RichManLand> landsOnTheMap;
    public RichManMap(){
        landsOnTheMap = new ArrayList<RichManLand>();
        for(int i=0;i<70;i++){
            landsOnTheMap.add(new RichManLand());
            landsOnTheMap.get(i).setLandKind("0");
            landsOnTheMap.get(i).setOwner(null);
            }
        landsOnTheMap.get(0).setLandKind("S");
        landsOnTheMap.get(14).setLandKind("H");
        landsOnTheMap.get(28).setLandKind("T");
        landsOnTheMap.get(35).setLandKind("G");
        landsOnTheMap.get(49).setLandKind("P");
        landsOnTheMap.get(63).setLandKind("M");
        for(int i=64;i<70;i++){
            landsOnTheMap.get(i).setLandKind("$");
        }
        this.setThePriceOfTheLands();
    }
    private String setMap(){
        String map = "";
        for(int i=0;i<29;i++){
            map += landsOnTheMap.get(i).getLandKind();
        }
        map += "\n" + landsOnTheMap.get(69).getLandKind() + "                           " + landsOnTheMap.get(29).getLandKind();
        map += "\n" + landsOnTheMap.get(68).getLandKind() + "                           " + landsOnTheMap.get(30).getLandKind();
        map += "\n" + landsOnTheMap.get(67).getLandKind() + "                           " + landsOnTheMap.get(31).getLandKind();
        map += "\n" + landsOnTheMap.get(66).getLandKind() + "                           " + landsOnTheMap.get(32).getLandKind();
        map += "\n" + landsOnTheMap.get(65).getLandKind() + "                           " + landsOnTheMap.get(33).getLandKind();
        map += "\n" + landsOnTheMap.get(64).getLandKind() + "                           " + landsOnTheMap.get(34).getLandKind() + "\n";

        for(int i=63;i>34;i--) {
            map += landsOnTheMap.get(i).getLandKind();
        }
        return map;
    }

    public void printMap(){
        TextAttributes attributes = new TextAttributes(Color.WHITE);
        console.setTextAttributes(attributes);
        for(int i=0;i<29;i++){
            attributes = getTextColor(i);
            console.setTextAttributes(attributes);
            System.out.print(landsOnTheMap.get(i).getLandKind());
        }
        System.out.print("\n");
        for(int i=0;i<6;i++){
            attributes = getTextColor(69-i);
            console.setTextAttributes(attributes);
            System.out.print(landsOnTheMap.get(69-i).getLandKind()+"                           ");
            attributes = getTextColor(29+i);
            console.setTextAttributes(attributes);
            System.out.print(landsOnTheMap.get(29+i).getLandKind()+"\n");
        }
        for(int i=63;i>34;i--) {
            attributes = getTextColor(i);
            console.setTextAttributes(attributes);
            System.out.print(landsOnTheMap.get(i).getLandKind());
        }
        System.out.print("\n");
    }
    public String getMap() {
        return this.setMap();
    }

    public String refreshMapWhenLandsChanged(int landPosition) {
        if(this.landsOnTheMap.get(landPosition).isGamerIsOnThisLandOrNot()){

        }
        else{
            String landKind = this.landsOnTheMap.get(landPosition).getLandKind();
            if(landKind.equals("S")||landKind.equals("H")||landKind.equals("T")||landKind.equals("G")||landKind.equals("P")
                    ||landKind.equals("M")||landKind.equals("$")){

            }
            else{
               int landLevel = this.landsOnTheMap.get(landPosition).getLevel();
               landKind = String.valueOf(landLevel);
               this.landsOnTheMap.get(landPosition).setLandKind(landKind);
               this.landsOnTheMap.get(landPosition).setLevel(landLevel);
            }
        }
        return this.setMap();
    }

    private void setThePriceOfTheLands(){
        for(int i=1;i<28;i++){
            landsOnTheMap.get(i).setPrice(200);
            if(13==i){
                i++;
            }
        }
        for(int i=29;i<35;i++){
            landsOnTheMap.get(i).setPrice(500);
        }
        for(int i=36;i<63;i++){
            landsOnTheMap.get(i).setPrice(300);
            if(48==i){
                i++;
            }
        }
    }
    public RichManLand getTheCurrentLandGamerIsOn(int gamerPosition){
        return landsOnTheMap.get(gamerPosition);
    }
    public void setTheCurrentGamerOnTheLand(int gamerPosition,String gamerName){
        landsOnTheMap.get(gamerPosition).setLandKind(gamerName);
    }
    private static final Console console;
    static
    {
        console = Enigma.getConsole("大富翁游戏");
    }

    private TextAttributes getTextColor(int textIndex){
        TextAttributes attributes = new TextAttributes(Color.WHITE);
        RichManLand land = landsOnTheMap.get(textIndex);
        if(land.getOwner() == null){

        }
        else{
            if(land.isGamerIsOnThisLandOrNot()){
                if(land.getLandKind().equals("Q")){
                    attributes = new TextAttributes(Color.RED);
                }
                else if(land.getLandKind().equals("X")){
                    attributes = new TextAttributes(Color.GREEN);
                }
                else if(land.getLandKind().equals("A")){
                    attributes = new TextAttributes(Color.YELLOW);
                }
                else if(land.getLandKind().equals("J")){
                    attributes = new TextAttributes(Color.BLUE);
                }
            }
            else{
                if(land.getOwner().getGamerName().equals("Q")){
                    attributes = new TextAttributes(Color.RED);
                }
                else if(land.getOwner().getGamerName().equals("X")){
                    attributes = new TextAttributes(Color.GREEN);
                }
                else if(land.getOwner().getGamerName().equals("A")){
                    attributes = new TextAttributes(Color.YELLOW);
                }
                else if(land.getOwner().getGamerName().equals("J")){
                    attributes = new TextAttributes(Color.BLUE);
                }
            }
        }
        return attributes;
    }
}
