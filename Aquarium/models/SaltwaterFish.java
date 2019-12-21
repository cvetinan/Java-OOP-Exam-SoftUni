package aquarium.models;

public class SaltwaterFish extends BaseFish {
    private static final int SIZE = 5;
    private static final int INCREASE_SIZE = 2;

    public SaltwaterFish(String name, String species, double price) {
        super(name, species, price);
        this.setSize(SIZE);
    }

    @Override
    public void eat() {
        this.setSize(this.getSize() + INCREASE_SIZE);
    }
}
