package LeoMitinskyi.types;

import java.time.LocalDate;

public class Person {
    private long id;
    private String name;
    private Coordinates coordinates;
    private String dateTimeString;
    private LocalDate creationDate;
    private Integer height;
    private Color eyeColor;
    private Color hairColor;
    private Country nationality;
    private Location location;
    private String login;
    private String password;

    public Person(){}
    public Person(long id, String name, Coordinates coordinates, LocalDate creationDate, Integer height, Color eyeColor, Color hairColor, Country nationality, Location location, String login, String password){
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.height = height;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.nationality = nationality;
        this.location = location;
        this.login = login;
        this.password = password;
    }

    public Person(String name, Coordinates coordinates, LocalDate creationDate, Integer height, Color eyeColor, Color hairColor, Country nationality, Location location, String login, String password){
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.height = height;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.nationality = nationality;
        this.location = location;
        this.login = login;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public float getX(){
        return getCoordinates().getX();
    }

    public LocalDate getCreationDate() {
        return LocalDate.now();
    }

    public Integer getHeight() {
        return height;
    }

    public Color getEyeColor() {
        return eyeColor;
    }

    public Color getHairColor() {
        return hairColor;
    }

    public Country getNationality() {
        return nationality;
    }

    public Location getLocation() {
        return location;
    }

    public String getLogin(){
        return login;
    }

    public String getPassword(){
        return password;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", coordinates=" + getCoordinates() +
                ", creationDate=" + getCreationDate() +
                ", height=" + getHeight() +
                ", eyeColor=" + getEyeColor() +
                ", hairColor=" + getHairColor() +
                ", nationality=" + getNationality() +
                ", location=" + getLocation() +
                ", login=" + getLogin() +
                '}' + '\n';
    }
}
