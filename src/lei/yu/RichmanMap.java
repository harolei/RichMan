package lei.yu;

public class RichManMap {
    private RichManLand[] landsOnTheMap = new RichManLand[70];
    public RichManMap(){
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
    }
    private String setMap(RichManLand[] richManLands){
        String map = "";
        for(int i=0;i<29;i++){
            map += richManLands[i].getLandKind();
        }
        map += "\n" + richManLands[69].getLandKind() + "                           " + richManLands[29].getLandKind();
        map += "\n" + richManLands[68].getLandKind() + "                           " + richManLands[30].getLandKind();
        map += "\n" + richManLands[67].getLandKind() + "                           " + richManLands[31].getLandKind();
        map += "\n" + richManLands[66].getLandKind() + "                           " + richManLands[32].getLandKind();
        map += "\n" + richManLands[65].getLandKind() + "                           " + richManLands[33].getLandKind();
        map += "\n" + richManLands[64].getLandKind() + "                           " + richManLands[34].getLandKind() + "\n";
        for(int i=63;i>34;i--){
            map += richManLands[i].getLandKind();
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
