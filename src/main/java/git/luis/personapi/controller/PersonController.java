package git.luis.personapi.controller;


import git.luis.personapi.dto.request.PersonDTO;
import git.luis.personapi.dto.response.MessageResponseDTO;
import git.luis.personapi.entity.Person;
import git.luis.personapi.exception.PersonNotFoundException;
import git.luis.personapi.repository.PersonRepository;
import git.luis.personapi.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/people")
public class PersonController {



   private PersonService personService;

   @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseDTO createPerson(@RequestBody @Valid PersonDTO personDTO){
            return personService.createPerson(personDTO);
    }

    @GetMapping
    public List<PersonDTO> lisAll(){
          return   personService.listAll();
    }

    @GetMapping("/{id}")
    public PersonDTO findById(@PathVariable Long id) throws PersonNotFoundException {

       return personService.findById(id);
    }

    @PutMapping("/{id}")
    public MessageResponseDTO updateById(@PathVariable Long id,@RequestBody @Valid PersonDTO personDTO) throws PersonNotFoundException {
            return personService.updateById(id, personDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) throws PersonNotFoundException {
       personService.delete(id);
    }

}
