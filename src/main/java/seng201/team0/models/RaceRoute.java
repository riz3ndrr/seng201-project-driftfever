package seng201.team0.models;

import java.util.Random;

/**
 * There are 4 RaceRoutes, each having a random chance of a race-ending
 * event, such as a landslide on the mountain route.
 */
public enum RaceRoute {
    MOUNTAIN_ROUTE("Mountain route"),
    DIRT_ROUTE("Dirt route"),
    SCENIC_ROUTE("Scenic route"),
    HIGHWAY_ROUTE("Highway route");

    // Properties
    private String name;


    // Constructor
    RaceRoute(String name) {
        this.name = name;
    }


    // Getters and setters
    public String getName() { return name; }

    /**
     * Obtain the description of a particular route
     * @return the description of that particular route
     */
    public String getDescription() {
        switch (this) {
            case MOUNTAIN_ROUTE:
                return "The mountain route is a beautiful drive but there's a chance of landslides.";
            case DIRT_ROUTE:
                return "The dirt route is fun but gets muddy in heavy rain.";
            case SCENIC_ROUTE:
                return  "The scenic route is a gorgeous drive but watch out for farm vehicles.";
            case HIGHWAY_ROUTE:
                return "The highway route can get busy and jam up during rush hour.";
        }
        return "";
    }

    /**
     * Return the unfortunate event message associated with that particular route.
     * @return
     */

    public String getMessage() {
        switch (this) {
            case MOUNTAIN_ROUTE:
                return "Tragedy has struck! A large landslide has blocked the mountain route.";
            case DIRT_ROUTE:
                return "Oh no! Heavy rain has made parts of the dirt route too muddy to pass.";
            case SCENIC_ROUTE:
                return  "CRASH! A large sheep truck has toppled over in the wind on the scenic route, blocking the road.";
            case HIGHWAY_ROUTE:
                return "HONK HONK! A large traffic jam has the highway route gridlocked. No one is getting through.";
        }
        return "";
    }

    /**
     * Obtain a random route to be used by the opponent.
     * @return RaceRoute
     */

    // Logic
    public static RaceRoute getRandomRoute() {
        Random rand = new Random();
        int index = rand.nextInt(RaceRoute.values().length);
        return RaceRoute.values()[index];
    }
}
