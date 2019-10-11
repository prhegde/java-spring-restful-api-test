package uk.co.huntersix.spring.rest.referencedata;

import org.springframework.stereotype.Service;
import uk.co.huntersix.spring.rest.model.Person;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonDataService {
    public static final List<Person> PERSON_DATA = Arrays.asList(
        new Person("Mary", "Smith"),
        new Person("Brian", "Archer"),
        new Person("Collin", "Brown")
    );

    public Person findPerson(String lastName, String firstName) {
        List<Person> person = PERSON_DATA.stream()
            .filter(p -> p.getFirstName().equalsIgnoreCase(firstName)
                && p.getLastName().equalsIgnoreCase(lastName))
            .collect(Collectors.toList());
        if(person.size() > 0)
            return person.get(0);
        else
            return null;
    }

    public List<Person> findPersonWithSurname(String lastName) {
        return PERSON_DATA.stream()
                .filter(p -> p.getLastName().equalsIgnoreCase(lastName))
                .collect(Collectors.toList());
    }

    public String addPerson(String lastName, String firstName) {
        String personDetails = null;
        boolean isPersonPresent = PERSON_DATA.stream()
                .anyMatch(p -> p.getFirstName().equalsIgnoreCase(firstName)
                        && p.getLastName().equalsIgnoreCase(lastName));
        if(isPersonPresent) {
            personDetails = "Person with firstName "+firstName+" and lastName "+lastName+" is already present. Please use PATCH command to update the existing record";
            System.out.println(personDetails);

        } else {
            Person newPerson = new Person(firstName, lastName);
            PERSON_DATA.add(newPerson);
        }
        return personDetails;
    }
}
