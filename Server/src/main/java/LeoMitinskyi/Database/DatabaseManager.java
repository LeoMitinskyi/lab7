package LeoMitinskyi.Database;

import LeoMitinskyi.types.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class DatabaseManager {
    protected static Connection connection;
    protected static Statement statement;
    public static LinkedList<Person> persons = new LinkedList<>();
    public static Map<String,User> users = new HashMap<>();

    public static void connect() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(Config.url,  Config.name, Config.password);
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void disconnect() {
        try {
            statement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static void dropPeople() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DROP TABLE people");
        statement.executeUpdate();
    }


    public static void loadPeople() {
        try {
            persons.clear();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM people");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Integer height = resultSet.getInt("height");
                float x = resultSet.getFloat("x1");
                float y = resultSet.getFloat("y1");
                LocalDate creationDate = LocalDate.now();
                Color eyeColor = Color.valueOf(resultSet.getString("eyeColor"));
                Color hairColor = Color.valueOf(resultSet.getString("hairColor"));
                Country nationality = Country.valueOf(resultSet.getString("nationality"));
                Long X = (long) resultSet.getInt("x2");
                double Y = resultSet.getFloat("y2");
                Float Z = resultSet.getFloat("z2");
                Coordinates coordinates = new Coordinates(x, y);
                Location location = new Location(X, Y, Z);
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                User user = new User(login,password,false);
                users.put(login,user);
                Person p = new Person(id, name, coordinates, creationDate, height, eyeColor, hairColor, nationality, location, login, password);
                persons.add(p);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static void savePeople() throws SQLException{
        deletePeople();
        for(int i = 0; i < persons.size(); i++){
            Person person = persons.get(i);
            PreparedStatement statement = connection.prepareStatement("INSERT INTO people (name,height,x1,y1,creationDate,eyeColor,hairColor,nationality,x2,y2,z2,login,password) VALUES (\'"+ person.getName() + "\'," + person.getHeight() + "," + person.getCoordinates().getX() +","+ person.getCoordinates().getY() + ",\'" + person.getCreationDate() + "\',\'" + person.getEyeColor() +"\',\'"+person.getHairColor() + "\',\'" + person.getNationality() + "\',"+ person.getLocation().getX() + "," + person.getLocation().getY() + "," + person.getLocation().getZ() +",\'"+ person.getLogin() + "\',\'" + person.getPassword() +"\');");
            statement.executeUpdate();
        }
    }

    public static void deletePeople() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM people");
        statement.executeUpdate();
    }

    public static LinkedList<Person> getPeople() {
        return persons;
    }

    public static void CreateTable() throws SQLException {
        statement.executeUpdate("CREATE TABLE people (id serial, name text, x1 float, y1 float, creationDate text, height integer, eyeColor text, hairColor text, nationality text, x2 bigint, y2 float, z2 float, login text, password text);");
    }

}
