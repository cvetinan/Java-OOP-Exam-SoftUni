package aquarium.models;

public class SaltwaterAquarium extends BaseAquarium {
    private static final int CAPACITY = 25;

    public SaltwaterAquarium(String name) {
        super(name, CAPACITY);
    }

    @Override
    public String getInfo() {
        String firstLine = String.format("%s (%s):%n", getName(), getClass().getSimpleName());
        return firstLine + super.getInfo();
    }
}
