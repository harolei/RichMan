package lei.yu;

public class RichManLand {
    private String owner;
    private int level;
    private double price;

    public RichManLand(){
        this.level = 0;
        this.owner = null;
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
        return landKind;
    }

    private String landKind;

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return this.level;
    }

    public String getOwner() {
        return owner;
    }
}