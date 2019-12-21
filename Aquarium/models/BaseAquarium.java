package aquarium.models;

import aquarium.models.aquariums.Aquarium;
import aquarium.models.decorations.Decoration;
import aquarium.models.fish.Fish;

import java.util.ArrayList;
import java.util.Collection;

import static aquarium.common.ConstantMessages.NOT_ENOUGH_CAPACITY;
import static aquarium.common.ExceptionMessages.AQUARIUM_NAME_NULL_OR_EMPTY;

public class BaseAquarium implements Aquarium {
    private String name;
    private int capacity;
    private Collection<Decoration> decorations;
    private Collection<Fish> fish;

    protected BaseAquarium(String name, int capacity) {
        this.setName(name);
        this.setCapacity(capacity);
        this.decorations = new ArrayList<>();
        this.fish = new ArrayList<>();
    }

    @Override
    public int calculateComfort() {
        int sum = 0;
        for (Decoration decoration : decorations) {
            sum += decoration.getComfort();
        }
        return sum;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void addFish(Fish fish) {
        String fishName = fish.getName();
        if (this.capacity < 0) {
            throw new IllegalStateException(NOT_ENOUGH_CAPACITY);
        }

        boolean noMatch = true;
        for (Fish f : this.fish) {
            if (f.getName().equals(fishName)){
                noMatch = false;
            }
        }

        if (noMatch) {
            this.fish.add(fish);
            this.capacity -= 1;
        }
    }

    @Override
    public void removeFish(Fish fish) {
        Fish fishToRemove = this.fish.stream()
                .filter(f -> name.equals(f.getName()))
                .findAny()
                .orElse(null);
        this.fish.remove(fishToRemove);
        this.capacity += 1;
    }

    @Override
    public void addDecoration(Decoration decoration) {
        this.decorations.add(decoration);
    }

    @Override
    public void feed() {
        for (Fish f : this.fish) {
            f.eat();
        }
    }

    @Override
    public String getInfo() {
        if (this.fish.isEmpty()) {
            return String.format("none%n");
        }

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Fish:");
        for (Fish f : fish) {
            stringBuilder.append(" ").append(f.getName());
        }
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append("Decorations: ").append(this.decorations.size());
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append("Comfort: ").append(this.calculateComfort());
        stringBuilder.append(System.lineSeparator());
        return stringBuilder.toString();
    }

    @Override
    public Collection<Fish> getFish() {
        return this.fish;
    }

    @Override
    public Collection<Decoration> getDecorations() {
        return this.decorations;
    }

    private void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new NullPointerException(AQUARIUM_NAME_NULL_OR_EMPTY);
        }
        this.name = name;
    }

    private void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
