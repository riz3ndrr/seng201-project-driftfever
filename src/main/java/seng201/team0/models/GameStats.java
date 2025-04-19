package seng201.team0.models;


import java.util.ArrayList;

public class GameStats {
    private static final GameStats instance = new GameStats();

    private int raceCount = 3;
    private String userName;
    private String raceDifficulty;
    private float bal = 5000F;

    private ArrayList<Car> carCollection = new ArrayList<>();


    public boolean selectedCarInCollection(Car car) {
        for (Car c : carCollection) {
            if (c.getName().equals(car.getName())) {
                return true;
            }
        }
        return false;
    }



    public ArrayList<Car> getCarCollection() {
        return carCollection;
    }

    public int getCarCollectionSize() {
        return carCollection.size();
    }

    public void addCar(Car car) {
        carCollection.add(car);
    }
    public void removeCar(Car car) {
        boolean removed = false;
        int i = 0;
        while (i < carCollection.size() || !removed) {
            if (carCollection.get(i).getName().equals(car.getName())) {
                carCollection.remove(i);
                removed = true;
            }
            i++;
        }
    }

    public Car searchCarAtIndex(int i) {
        return carCollection.get(i);
    }


    public void printCars() {
        System.out.println("You have these cars in your collection");
        for (Car car : carCollection) {
            System.out.println(car.getName());
        }
    }

    public static GameStats getInstance() {
        return instance;
    }

    public void setBal(float bal) {
        this.bal = bal;
    }
    public float getBal() {
        return bal;
    }

    public void setRaceCount(int raceCount) {
        this.raceCount = raceCount;
    }
    public int getRaceCount() {
        return raceCount;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserName() {
        return userName;
    }

    public void setRaceDifficulty(String raceDifficulty) {
        this.raceDifficulty = raceDifficulty;
    }
    public String getRaceDifficulty() {
        return raceDifficulty;
    }

}
