package LeoMitinskyi.types;

import LeoMitinskyi.Database.DatabaseManager;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class People {

    public static LinkedList<Person> persons = DatabaseManager.getPeople();

    private final LocalDate initializationDate;

    public People() {
        initializationDate = LocalDate.now();
    }

    public void clear() {
        persons.clear();
    }

    public LocalDate getInitializationDate() {
        return initializationDate;
    }

    public int getSize() {
        return persons.size();
    }

    public void add(Person element) {
        persons.add(element);
    }

    public void save() throws SQLException, NoSuchAlgorithmException {
        DatabaseManager.savePeople();
    }

    public void remove(long key) {
        Person element = get(key);
        persons.remove(element);
    }

    public Person get(long id) {
        try{
            return persons.stream().filter(entry -> entry.getId() == id).findFirst().get();
        }catch (NoSuchElementException exception){
            return null;
        }
    }

    public int findMaxHeight(){
        Person max = persons.stream().max(Comparator.comparing(Person::getHeight)).get();
        return max.getHeight();
    }

    public int findMinHeight(){
        Person min = persons.stream().min(Comparator.comparing(Person::getHeight)).get();
        return min.getHeight();
    }

    public String subStringSearcher(String subString){

        return persons
                .stream()
                .filter(entry -> entry.getName().startsWith(subString))
                .map(Person::toString)
                .reduce("", (a, b) -> a + b + "\n");
    }

    public int LocationCounter(Long X, double Y, Float Z){
        int count = 0;
        for (Person person : persons) {
            if (person.getLocation().getX().equals(X) && person.getLocation().getY() == Y && person.getLocation().getZ().equals(Z)){
                count = count + 1;
            }
        }
        return count;
    }

    public String show(){
        if (persons.isEmpty()) return "Коллекция пуста!";
        return persons.stream().reduce("",(sum,m)->sum += m, (a, b) -> a + b).trim();
    }

    public Person MaxName(){
        String minString = "";
        Person p = new Person();
        for (Person person : persons) {
            if(person.getName().compareToIgnoreCase(minString) > 0){
                minString = person.getName();
                p = person;
            }
        }
        return p;
    }

    public void sort() {
        persons.sort(Comparator.comparing(Person::getX));
    }
}
