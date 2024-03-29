package logic.jdk8feature.comparator;

import logic.superclass.Person;

import java.util.Comparator;

/**
 * @author moku
 */
public class TraditionalComparator implements Comparator<Person> {
        @Override
        public int compare(Person a, Person b) {
            return a.getAge().compareTo(b.getAge());
        }
}
