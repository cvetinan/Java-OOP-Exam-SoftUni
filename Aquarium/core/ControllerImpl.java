package aquarium.core;

import aquarium.models.aquariums.Aquarium;
import aquarium.models.FreshwaterAquarium;
import aquarium.models.SaltwaterAquarium;
import aquarium.models.decorations.Decoration;
import aquarium.models.Ornament;
import aquarium.models.Plant;
import aquarium.models.fish.Fish;
import aquarium.models.FreshwaterFish;
import aquarium.models.SaltwaterFish;
import aquarium.repositories.DecorationRepository;

import java.util.ArrayList;
import java.util.Collection;

import static aquarium.common.ConstantMessages.*;
import static aquarium.common.ExceptionMessages.*;

public class ControllerImpl implements Controller {
    private DecorationRepository decorations;
    private Collection<Aquarium> aquariums;

    public ControllerImpl() {
        this.decorations = new DecorationRepository();
        this.aquariums = new ArrayList<>();
    }

    @Override
    public String addAquarium(String aquariumType, String aquariumName) {
        Aquarium aquarium;
        if ("FreshwaterAquarium".equals(aquariumType)) {
            aquarium = new FreshwaterAquarium(aquariumName);
        } else if ("SaltwaterAquarium".equals(aquariumType)) {
            aquarium = new SaltwaterAquarium(aquariumName);
        } else {
            throw new NullPointerException(INVALID_AQUARIUM_TYPE);
        }

        this.aquariums.add(aquarium);
        return String.format(SUCCESSFULLY_ADDED_AQUARIUM_TYPE, aquariumType);
    }


    @Override
    public String addDecoration(String type) {
        Decoration decoration;
        if ("Ornament".equals(type)) {
            decoration = new Ornament();
        } else if ("Plant".equals(type)) {
            decoration = new Plant();
        } else {
            throw new IllegalArgumentException(INVALID_DECORATION_TYPE);
        }
        this.decorations.add(decoration);
        return String.format(SUCCESSFULLY_ADDED_DECORATION_TYPE, type);
    }

    @Override
    public String insertDecoration(String aquariumName, String decorationType) {
        Decoration decoration = this.decorations.findByType(decorationType);
        Aquarium currentAquarium = null;
        for (Aquarium aquarium : aquariums) {
            if (aquarium.getName().equals(aquariumName)) {
                currentAquarium = aquarium;
            }
        }

        if (decoration == null) {
            throw new IllegalArgumentException(String.format(NO_DECORATION_FOUND, decorationType));
        }

        assert currentAquarium != null;
        currentAquarium.addDecoration(decoration);

        this.decorations.remove(decoration);
        return String.format(SUCCESSFULLY_ADDED_DECORATION_IN_AQUARIUM, decorationType, aquariumName);
    }

    @Override
    public String addFish(String aquariumName, String fishType, String fishName, String fishSpecies, double price) {
        Fish fish;
        Aquarium currentAquarium = null;
        for (Aquarium aquarium : aquariums) {
            if (aquarium.getName().equals(aquariumName)) {
                currentAquarium = aquarium;
            }
        }


        if ("FreshwaterFish".equals(fishType)) {
            fish = new FreshwaterFish(fishName, fishSpecies, price);
        } else if ("SaltwaterFish".equals(fishType)) {
            fish = new SaltwaterFish(fishName, fishSpecies, price);
        } else {
            throw new IllegalArgumentException(INVALID_FISH_TYPE);
        }


        assert currentAquarium != null;
        if (currentAquarium.getClass().getSimpleName().equals("FreshwaterAquarium") && fishType.equals("FreshwaterFish")) {
            currentAquarium.addFish(fish);
        } else if (currentAquarium.getClass().getSimpleName().equals("SaltwaterAquarium") && fishType.equals("SaltwaterFish")) {
            currentAquarium.addFish(fish);
        } else {
            return WATER_NOT_SUITABLE;
        }
        return String.format(SUCCESSFULLY_ADDED_FISH_IN_AQUARIUM, fishType, aquariumName);
}

    @Override
    public String feedFish(String aquariumName) {
        Aquarium currentAquarium = null;
        for (Aquarium aquarium : aquariums) {
            if (aquarium.getName().equals(aquariumName)) {
                currentAquarium = aquarium;
            }
        }

        assert currentAquarium != null;
        int count = currentAquarium.getFish().size();
        currentAquarium.feed();
        return String.format(FISH_FED, count);
    }

    @Override
    public String calculateValue(String aquariumName) {
        Aquarium currentAquarium = null;
        for (Aquarium aquarium : aquariums) {
            if (aquarium.getName().equals(aquariumName)) {
                currentAquarium = aquarium;
            }
        }

        assert currentAquarium != null;
        double price = 0;
        for (Decoration decoration : currentAquarium.getDecorations()) {
            price += decoration.getPrice();
        }
        for (Fish fish : currentAquarium.getFish()) {
            price += fish.getPrice();
        }
        return String.format(VALUE_AQUARIUM, aquariumName, price);
    }

    @Override
    public String report() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Aquarium aquarium : aquariums) {
            stringBuilder.append(System.lineSeparator()).append(aquarium.getInfo());
        }
        return stringBuilder.toString().trim();
    }
}
