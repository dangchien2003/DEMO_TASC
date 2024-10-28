package org.example.restcontroller_controller;

import jakarta.validation.Valid;
import org.example.restcontroller_controller.exception.IndexException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Rest_Controller {
    static List<Person> persons = new ArrayList<>(
            List.of(
                    new Person("chien", "hn", 21),
                    new Person("chien1", "hn1", 22))
    );

    @GetMapping(value = "/get/{index}")
    Person getPerson(@PathVariable("index") int index) {
        if (index >= persons.size())
            throw new IndexException("Index không hợp lệ");
        return persons.get(index);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    List<Person> createPerson(@Valid @RequestBody Person person) {
        persons.add(person);
        return persons;
    }

    @DeleteMapping("/delete/{index}")
    List<Person> deletePerson(@PathVariable("index") int index) {
        if (index >= persons.size())
            throw new IndexException("Index không hợp lệ");
        persons.remove(index);
        return persons;
    }

    @PutMapping("/edit/{index}")
    List<Person> editPerson(@Valid @RequestBody Person person, @PathVariable("index") int index) {
        if (index >= persons.size())
            throw new IndexException("Index không hợp lệ");
        persons.add(index, person);
        return persons;
    }
}
