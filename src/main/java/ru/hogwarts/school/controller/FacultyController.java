package ru.hogwarts.school.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.dto.FacultyDtoIn;
import ru.hogwarts.school.dto.FacultyDtoOut;
import ru.hogwarts.school.dto.StudentDtoIn;
import ru.hogwarts.school.dto.StudentDtoOut;
import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("faculty")
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


    @PutMapping("{id}")                      // PUT http://localhost:8080/student/1
    public FacultyDtoOut updateFaculty(@PathVariable Long id, @RequestBody FacultyDtoIn facultyDtoIn) {
        return facultyService.updateFaculty(id, facultyDtoIn );
    }

    @DeleteMapping("{id}")                         // DELETE http://localhost:8080/student/1
    public FacultyDtoOut deleteFaculty(@PathVariable Long id) {
        return facultyService.deleteFaculty(id);
    }


    @GetMapping
    public List<FacultyDtoOut> endpointFaculty(@RequestParam(required = false) String color){
        return facultyService.endpointFaculty(color);
    }

}
