package ru.wclass.wclass;

/**
 * Created by dave on 02.06.16.
 */
public class GymClass {
    private String id;
    private String name;
    private String division;
    private String gymAddress;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDivision() {
        return this.division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getGymAddress() {
        return this.gymAddress;
    }

    public void setGymAddress(String gymAddress) {
        this.gymAddress = gymAddress;
    }
}
