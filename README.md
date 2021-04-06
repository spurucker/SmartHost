# SmartHost
This API serves to suggest guest distributions given rooms criteria.

## Running the project
    To run this application locally you should
    - Compile the project: mvn clean install
    - Run spring boot: ./mvnw spring-boot:run

## Endpoints documentation
    You can have more technical information at 
    http://127.0.0.1:8080/swagger-ui/#/
  
## Notes and assumptions
- This challenge could be solved without Room and Guest entities, but any enhancement would probably need that this two exists.
- In many places I use list or maps when I only have two categories cause it would be much easier to add categories later.
- I was asked to pay attention at github commits. My commits strategy is to use one commit for task. In every PR use squash & merge to develop, and merge to master (so you don't loose history)
- Unit test are (for the most part) missing, that was for lack of time. I would gladly add them if you give more time. In general, I always work with unit test and integration only for specific cases, but in this case as I was told to use the cases given in the exercise I think it was better this way.
- As the API is very small I didn't believe modules would be practical here, if you would like me to implement it let me know.
- I didn't use any database cause I understand that is not what was asked. If necessary I can add a h2 (an in-memory database) and finish building GuestRepository.
