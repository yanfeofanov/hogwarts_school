package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.dto.StudentDtoIn;
import ru.hogwarts.school.dto.StudentDtoOut;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @PostMapping                            // POST http://localhost:8080/student
    public StudentDtoOut createStudent(@RequestBody StudentDtoIn studentDtoIn) {
        return studentService.createStudent(studentDtoIn);
    }

    @GetMapping("/{id}")                             // GET http://localhost:8080/student
    public StudentDtoOut readStudent(@PathVariable("id") long id) {
        return studentService.readStudent(id);
    }


    @PutMapping("{id}")                      // PUT http://localhost:8080/student/1
    public StudentDtoOut updateStudent(@PathVariable Long id, @RequestBody StudentDtoIn studentDtoIn) {
        return studentService.updateStudent(id, studentDtoIn);
    }

    @DeleteMapping("{id}")                         // DELETE http://localhost:8080/student/1
    public StudentDtoOut deleteStudent(@PathVariable Long id) {
        return studentService.deleteStudent(id);
    }


    @GetMapping
    public List<StudentDtoOut> endpointStudent(@RequestParam(required = false) Integer age){
        return studentService.endpointStudent(age);
    }

}
