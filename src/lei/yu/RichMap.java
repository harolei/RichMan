package lei.yu;

public class RichMap {
    private Land[] landsOnTheMap = new Land[70];
    public RichMap(){
        for(int i=0;i<70;i++){
            landsOnTheMap[i] = new Land();
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
    }
    private String setMap(Land[] lands){
        String map = "";
        for(int i=0;i<29;i++){
            map += lands[i].getLandKind();
        }
        map += "\n" + lands[69].getLandKind() + "                           " + lands[29].getLandKind();
        map += "\n" + lands[68].getLandKind() + "                           " + lands[30].getLandKind();
        map += "\n" + lands[67].getLandKind() + "                           " + lands[31].getLandKind();
        map += "\n" + lands[66].getLandKind() + "                           " + lands[32].getLandKind();
        map += "\n" + lands[65].getLandKind() + "                           " + lands[33].getLandKind();
        map += "\n" + lands[64].getLandKind() + "                           " + lands[34].getLandKind() + "\n";
        for(int i=63;i>34;i--){
            map += lands[i].getLandKind();
        }
        return map;
    }
    public String getMap() {
        return this.setMap(landsOnTheMap);
    }

    public String refreshMapWhenLandsChanged(int landPosition) {
        int landLevel = landsOnTheMap[landPosition].getLevel() + 1;
        String landKind = String.valueOf(landLevel);
        landsOnTheMap[landPosition].setLevel(landLevel);
        landsOnTheMap[landPosition].setLandKind(landKind);
        return this.setMap(landsOnTheMap);
    }
}
