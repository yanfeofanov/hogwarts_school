package ru.hogwarts.school.controller;


import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.dto.FacultyDtoIn;
import ru.hogwarts.school.dto.FacultyDtoOut;

import ru.hogwarts.school.dto.StudentDtoOut;

import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/faculties")
public class FacultyController {
    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    private final FacultyService facultyService;


    @PostMapping                            // POST http://localhost:8080/student
    public FacultyDtoOut createFaculty(@RequestBody FacultyDtoIn facultyDtoIn) {
        return facultyService.createFaculty(facultyDtoIn);
    }

    @GetMapping("/{id}")                             // GET http://localhost:8080/student
    public FacultyDtoOut readFaculty(@PathVariable("id") long id) {
        return facultyService.readFaculty(id);
    }


    @PutMapping("/{id}")                      // PUT http://localhost:8080/student/1
    public FacultyDtoOut updateFaculty(@PathVariable("id") long id, @RequestBody FacultyDtoIn facultyDtoIn) {
        return facultyService.updateFaculty(id, facultyDtoIn);
    }

    @DeleteMapping("/{id}")                         // DELETE http://localhost:8080/student/1
    public FacultyDtoOut deleteFaculty(@PathVariable("id") long id) {
        return facultyService.deleteFaculty(id);
    }


    @GetMapping()
    public List<FacultyDtoOut> endpointFaculty(@RequestParam(required = false) String color) {
        return facultyService.endpointFaculty(color);
    }

    @GetMapping("/filter")
    public List<FacultyDtoOut> findByColorOrName(String color, String name) {
        return facultyService.findByColorOrName(color, name);
    }

    @GetMapping("/{id}/students")
    public List<StudentDtoOut> findStudents(@PathVariable("id") long id) {
        return facultyService.findStudents(id);
    }


    @GetMapping("/word/{words}")
    public Collection<Student> getLengthWords(@PathVariable("words") Character words) {

        return facultyService.getLengthWords(words);
    }

}
