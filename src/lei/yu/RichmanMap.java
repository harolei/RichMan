package lei.yu;

public class RichManMap {
    private RichManLand[] landsOnTheMap;
    public RichManMap(){
        landsOnTheMap = new RichManLand[70];
        for(int i=0;i<70;i++){
            landsOnTheMap[i] = new RichManLand();
            landsOnTheMap[i].setLandKind("0");
            }
        landsOnTheMap[0].setLandKind("S");
        landsOnTheMap[14].setLandKind("H");
        landsOnTheMap[28].setLandKind("T");
        landsOnTheMap[35].setLandKind("G");
        landsOnTheMap[49].setLandKind("P");
        landsOnTheMap[63].setLandKind("M");
        for(int i=64;i<70;i++){
            landsOnTheMap[i].setLandKind("$");
        }
        this.setThePriceOfTheLands();
    }
    private String setMap(){
        String map = "";
        for(int i=0;i<29;i++){
            map += landsOnTheMap[i].getLandKind();
        }
        map += "\n" + landsOnTheMap[69].getLandKind() + "                           " + landsOnTheMap[29].getLandKind();
        map += "\n" + landsOnTheMap[68].getLandKind() + "                           " + landsOnTheMap[30].getLandKind();
        map += "\n" + landsOnTheMap[67].getLandKind() + "                           " + landsOnTheMap[31].getLandKind();
        map += "\n" + landsOnTheMap[66].getLandKind() + "                           " + landsOnTheMap[32].getLandKind();
        map += "\n" + landsOnTheMap[65].getLandKind() + "                           " + landsOnTheMap[33].getLandKind();
        map += "\n" + landsOnTheMap[64].getLandKind() + "                           " + landsOnTheMap[34].getLandKind() + "\n";
        for(int i=63;i>34;i--) {
            map += landsOnTheMap[i].getLandKind();
        }
        return map;
    }
    public String getMap() {
        return this.setMap();
    }

    public String refreshMapWhenLandsChanged(int landPosition) {
        if(this.landsOnTheMap[landPosition].isGamerIsOnThisLandOrNot()){

        }
        else{
            int landLevel = this.landsOnTheMap[landPosition].getLevel() + 1;
            String landKind = String.valueOf(landLevel);
            this.landsOnTheMap[landPosition].setLevel(landLevel);
            this.landsOnTheMap[landPosition].setLandKind(landKind);
        }
        return this.setMap();
    }

    private void setThePriceOfTheLands(){
        for(int i=1;i<28;i++){
            landsOnTheMap[i].setPrice(200);
            if(13==i){
                i++;
            }
        }
        for(int i=29;i<35;i++){
            landsOnTheMap[i].setPrice(500);
        }
        for(int i=36;i<63;i++){
            landsOnTheMap[i].setPrice(300);
            if(48==i){
                i++;
            }
        }
    }
    public RichManLand getTheCurrentLandGamerIsOn(int gamerPosition){
        return landsOnTheMap[gamerPosition];
    }
    public void setTheCurrentGamerOnTheLand(int gamerPosition,String gamerName){
        landsOnTheMap[gamerPosition].setLandKind(gamerName);
    }
}
