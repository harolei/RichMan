package lei.yu;

import java.util.ArrayList;
import java.util.List;

public class RichManLand {
    private RichManGamer owner;
    private int level;
    private double price;
    private String landKind;
    private String specialLandKind = "0";
    private List<String> gamersOnThisLand;

    public String getSpecialLandKind() {
        return specialLandKind;
    }

    public void setSpecialLandKind(String specialLandKind) {
        this.specialLandKind = specialLandKind;
    }

    public List<String> getGamersOnThisLand() {
        return gamersOnThisLand;
    }

    public void addGamersOnThisLand(String gamerOnThisLand) {
        this.gamersOnThisLand.add(gamerOnThisLand);
    }

    public void removeGamerOnThisLand(){
        this.gamersOnThisLand.remove(0);
    }

    public RichManLand(){
        this.level = 0;
        this.owner = null;
        this.gamersOnThisLand = new ArrayList<String>();
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {

        return price;
    }

    public void setLandKind(String landKind) {
        this.landKind = landKind;
    }

    public String getLandKind() {
        if(!this.gamersOnThisLand.isEmpty()){
            return gamersOnThisLand.get(gamersOnThisLand.size()-1);
        }
        else if(this.specialLandKind.equals("@")||this.specialLandKind.equals("#")){
            return this.specialLandKind;
        }
        else if(this.level>0){
            return String.valueOf(this.level);
        }
        else{
            return landKind;
        }

    }

    public void setOwner(RichManGamer owner) {
        this.owner = owner;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return this.level;
    }

    public RichManGamer getOwner() {
        return owner;
    }
}
