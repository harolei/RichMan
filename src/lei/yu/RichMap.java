package lei.yu;

public class RichMap {
    private Land[] land = new Land[70];
    public RichMap(){
        for(int i=0;i<70;i++){
            land[i] = new Land();
            land[i].setLandKind("0");
        }
        land[0].setLandKind("S");
        land[14].setLandKind("H");
        land[28].setLandKind("T");
        land[35].setLandKind("G");
        land[49].setLandKind("P");
        land[63].setLandKind("M");
        for(int i=64;i<70;i++){
            land[i].setLandKind("$");
        }
    }
    public String getMap() {
        String map = "";
        for(int i=0;i<29;i++){
            map += land[i].getLandKind();
        }
        map += "\n" + land[69].getLandKind() + "                           " + land[29].getLandKind();
        map += "\n" + land[68].getLandKind() + "                           " + land[30].getLandKind();
        map += "\n" + land[67].getLandKind() + "                           " + land[31].getLandKind();
        map += "\n" + land[66].getLandKind() + "                           " + land[32].getLandKind();
        map += "\n" + land[65].getLandKind() + "                           " + land[33].getLandKind();
        map += "\n" + land[64].getLandKind() + "                           " + land[34].getLandKind() + "\n";
        for(int i=63;i>34;i--){
            map += land[i].getLandKind();
        }
        return map;
    }

    public String refreshMap(int landPosition) {
        String map = "";
        String landLevel = String.valueOf(land[landPosition].getLevel() + 1);
        land[landPosition].setLandKind(landLevel);
        for(int i=0;i<29;i++){
            map += land[i].getLandKind();
        }
        map += "\n" + land[69].getLandKind() + "                           " + land[29].getLandKind();
        map += "\n" + land[68].getLandKind() + "                           " + land[30].getLandKind();
        map += "\n" + land[67].getLandKind() + "                           " + land[31].getLandKind();
        map += "\n" + land[66].getLandKind() + "                           " + land[32].getLandKind();
        map += "\n" + land[65].getLandKind() + "                           " + land[33].getLandKind();
        map += "\n" + land[64].getLandKind() + "                           " + land[34].getLandKind() + "\n";
        for(int i=63;i>34;i--){
            map += land[i].getLandKind();
        }
        return map;
    }
}
