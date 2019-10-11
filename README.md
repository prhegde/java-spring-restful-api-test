# Hunter Six - Java Spring RESTful API Test

## How to build
```./gradlew clean build```

## How to test
```./gradlew test```

---------------------------------------------------------------------------------------------------------
## Exercises
### Exercise 1
Make the tests run green (there should be one failing test)

### Solution:
//Usecase: Exercise 1 (Adding static keyword to the counter variable in Person class)
private static final AtomicLong counter = new AtomicLong();

---------------------------------------------------------------------------------------------------------

### Exercise 2
Update the existing `/person/{lastName}/{firstName}` endpoint to return an appropriate RESTful response when the requested person does not exist in the list
- prove your results

### Solution:
//Usecase: Exercise 2

 First change:

  //PersonController.java
  
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
    
 Second change:
 
  // PersonDataService.java
  
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
  
  
  ---------------------------------------------------------------------------------------------------------

### Exercise 3
Write a RESTful API endpoint to retrieve a list of all people with a particular surname
- pay attention to what should be returned when there are no match, one match, multiple matches
- prove your results


### Solution:
//Usecase: Exercise 3

First change:

  //PersonController.java
  
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
    
Second change:

  // PersonDataService.java
  
  public List<Person> findPersonWithSurname(String lastName) {
        return PERSON_DATA.stream()
                .filter(p -> p.getLastName().equalsIgnoreCase(lastName))
                .collect(Collectors.toList());
    }

---------------------------------------------------------------------------------------------------------

### Exercise 4
Write a RESTful API endpoint to add a new value to the list
- pay attention to what should be returned when the record already exists
- prove your resutls


### Solution:
//Usecase: Exercise 4

First change:

  //PersonController.java
  
   @PostMapping("/person/{lastName}/{firstName}")
    public String addPerson(@PathVariable(value="lastName") String lastName,
                          @PathVariable(value="firstName") String firstName) {
        String personDetails = personDataService.addPerson(lastName, firstName);
        return personDetails;
    }
   
Second change:

  // PersonDataService.java
  
  public String addPerson(String lastName, String firstName) {
        String personDetails = null;
        boolean isPersonPresent = PERSON_DATA.stream()
                .anyMatch(p -> p.getFirstName().equalsIgnoreCase(firstName)
                        && p.getLastName().equalsIgnoreCase(lastName));
        if(isPersonPresent) {
            personDetails = "Person with firstName "+firstName+" and lastName "+lastName+" is already present. Please use     PATCH command to update the existing record";
            System.out.println(personDetails);

        } else {
            Person newPerson = new Person(firstName, lastName);
            PERSON_DATA.add(newPerson);
        }
        return personDetails;
    }
