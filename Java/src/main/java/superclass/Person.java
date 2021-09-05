package superclass;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author moku
 */
@Data
@Setter
@Getter
public class Person {
    private String name;
    private Integer age;

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Person() {

    }

    public static int compareByAge(Person a, Person b) {
        return a.age.compareTo(b.age);
    }

    public void greet() {
        System.out.println("Hello, world!");
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
