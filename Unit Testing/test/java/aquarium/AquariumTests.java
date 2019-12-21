package aquarium;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AquariumTests {
    //TODO: TEST ALL THE FUNCTIONALITY OF THE PROVIDED CLASS Aquarium


    private static final String NAME = "TestName";
    private static final int CAPACITY = 2;
    private static final String FISH_NAME = "Fish";
    private Fish fish;
    private Aquarium aquarium;

    @Before
    public void setUp() {
        this.aquarium = new Aquarium(NAME, CAPACITY);
        this.fish = new Fish(FISH_NAME);
    }

    @Test
    public void shouldReturnZeroSizeWhenInitialised() {
        Assert.assertEquals(0, this.aquarium.getCount());
    }

    @Test
    public void getNameShouldReturnCorrectName() {
        Assert.assertEquals(NAME, this.aquarium.getName());
    }

    @Test (expected = NullPointerException.class)
    public void shouldThrowExceptionIfNameIsEmpty(){
        String testName = "";
        this.aquarium = new Aquarium(testName, CAPACITY);
    }

    @Test (expected = NullPointerException.class)
    public void shouldThrowExceptionIfNameIsNull(){
        String testName = null;
        this.aquarium = new Aquarium(testName, CAPACITY);
    }

    @Test (expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenTryingToCreateWithNegativeCapacity() {
        this.aquarium = new Aquarium(NAME, -2);
    }

    @Test
    public void shouldReturnCorrectCapacity() {
        Assert.assertEquals(CAPACITY, this.aquarium.getCapacity());
    }

    @Test (expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenTryingToAddAndCapacityIsEqualsToSize() {
        this.aquarium = new Aquarium(NAME, 0);
        this.aquarium.add(this.fish);
    }

    @Test
    public void shouldIncreaseCountWhenAddFish() {
        this.aquarium.add(this.fish);
        Assert.assertEquals(1, this.aquarium.getCount());
    }

    @Test (expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenRemovingInvalidFish() {
        this.aquarium.remove(this.fish.getName());
    }

    @Test
    public void shouldRemoveSuccessfullyValidFish() {
        this.aquarium.add(fish);
        this.aquarium.remove(fish.getName());
        Assert.assertEquals(0, this.aquarium.getCount());
    }

    @Test (expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenSellingInvalidFish() {
        this.aquarium.sellFish(this.fish.getName());
    }

    @Test
    public void shouldChangeToUnavailableWhenSold() {
        this.aquarium.add(this.fish);
        this.aquarium.sellFish(this.fish.getName());
        Assert.assertFalse(this.fish.isAvailable());
    }

}

