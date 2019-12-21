package aquarium.models;

public class FreshwaterAquarium extends BaseAquarium {
    private static final int CAPACITY = 50;
    public FreshwaterAquarium(String name) {
        super(name, CAPACITY);
    }

    @Override
    public String getInfo() {
        String firstLine = String.format("%s (%s):%n", getName(), getClass().getSimpleName());
        return firstLine + super.getInfo();
    }
}
