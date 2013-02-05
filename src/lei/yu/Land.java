package lei.yu;

public class Land {
    private String owner = null;
    private int level = 0;
    private double price = 0;

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

        return level;
    }

    public String getOwner() {
        return owner;
    }
}
