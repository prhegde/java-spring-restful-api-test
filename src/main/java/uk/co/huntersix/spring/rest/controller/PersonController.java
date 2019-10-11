package uk.co.huntersix.spring.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.huntersix.spring.rest.model.Person;
import uk.co.huntersix.spring.rest.referencedata.PersonDataService;

import java.util.List;

@RestController
public class PersonController {
    private PersonDataService personDataService;

    public PersonController(@Autowired PersonDataService personDataService) {
        this.personDataService = personDataService;
    }

    //Usecase: Exercise 2
    @GetMapping("/person/{lastName}/{firstName}")
    public Person person(@PathVariable(value="lastName") String lastName,
                         @PathVariable(value="firstName") String firstName) {
        Person person = personDataService.findPerson(lastName, firstName);
        if(person != null)
            return person;
        else {
            System.out.println("The requested person does not exist in the list");
            return null;
        }
    }

    //Usecase: Exercise 3
    @GetMapping("/person/{lastName}")
    public List<Person> personWithSurname(@PathVariable(value="lastName") String lastName) {
        List<Person> personList = personDataService.findPersonWithSurname(lastName);
        if(personList.isEmpty())
            System.out.println("The requested person with given Surname does not exist");
        else if(personList.size() == 1)
            System.out.println("1 person with given Surname exist");
        else
            System.out.println("Multiple people with given Surname exists");
        return personList;
    }

    //Usecase: Exercise 4
    @PostMapping("/person/{lastName}/{firstName}")
    public String addPerson(@PathVariable(value="lastName") String lastName,
                          @PathVariable(value="firstName") String firstName) {
        String personDetails = personDataService.addPerson(lastName, firstName);
        return personDetails;
    }
}