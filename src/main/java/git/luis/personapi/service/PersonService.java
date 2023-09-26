package git.luis.personapi.service;

import git.luis.personapi.dto.request.PersonDTO;
import git.luis.personapi.dto.response.MessageResponseDTO;
import git.luis.personapi.entity.Person;
import git.luis.personapi.exception.PersonNotFoundException;
import git.luis.personapi.mapper.PersonMapper;
import git.luis.personapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private PersonRepository personRepository;

    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public MessageResponseDTO createPerson(PersonDTO personDTO){

        Person personToSave = personMapper.toModel(personDTO);


        Person savedPerson =   personRepository.save(personToSave);
        return MessageResponseDTO
                .builder()
                .message("Created person with ID, " + savedPerson.getId())
                .build();
    }
    public List<PersonDTO> listAll(){
     List<Person> allPeople =   personRepository.findAll();
            return allPeople.stream()
                    .map(personMapper::toDTO)
                    .collect(Collectors.toList());
    }

    public PersonDTO findById(Long id) throws PersonNotFoundException {

            Person person =    verifyIfExists(id);



                return personMapper.toDTO(person);
    }


    private Person verifyIfExists(Long id ) throws PersonNotFoundException{
        return personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }

    public void delete(Long id) throws PersonNotFoundException {

        personRepository.findById(id)
                .orElseThrow(()-> new PersonNotFoundException(id));
        personRepository.deleteById(id);
    }

    public MessageResponseDTO updateById(Long id, PersonDTO personDTO) throws PersonNotFoundException {
        verifyIfExists(id);
        Person personToUpdate = personMapper.toModel(personDTO);


        Person savedPerson =   personRepository.save(personToUpdate);
        return MessageResponseDTO
                .builder()
                .message("Update person with ID, " + savedPerson.getId())
                .build();
    }
}
