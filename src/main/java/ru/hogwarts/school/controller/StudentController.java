package ru.hogwarts.school.controller;

import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.dto.FacultyDtoOut;
import ru.hogwarts.school.dto.StudentDtoIn;
import ru.hogwarts.school.dto.StudentDtoOut;

import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.service.StudentService;


import java.util.List;

@RestController
@RequestMapping("/students")
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


    @PutMapping("/{id}")                      // PUT http://localhost:8080/student/1
    public StudentDtoOut updateStudent(@PathVariable("id") long id, @RequestBody StudentDtoIn studentDtoIn) {
        return studentService.updateStudent(id, studentDtoIn);
    }

    @DeleteMapping("/{id}")                         // DELETE http://localhost:8080/student/1
    public StudentDtoOut deleteStudent(@PathVariable("id") long id) {
        return studentService.deleteStudent(id);
    }


    @GetMapping
    public List<StudentDtoOut> endpointStudent(@RequestParam(required = false) Integer age) {
        return studentService.endpointStudent(age);
    }

    @GetMapping("/filter")
    public List<StudentDtoOut> findByAgeBetween(@RequestParam int ageFrom, @RequestParam int ageTo) {
        return studentService.findByAgeBetween(ageFrom, ageTo);
    }

    @GetMapping("/{id}/faculty")
    public FacultyDtoOut findFaculty(@PathVariable("id")     long id) {
        return studentService.findFaculty(id);
    }

    @PatchMapping(value = "/{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public StudentDtoOut uploadAvatar(@PathVariable long id,
                                      @RequestParam("avatar") MultipartFile multipartFile) {
        return studentService.uploadAvatar(multipartFile, id);

    }
    @GetMapping("/count")
    public int getCountOfStudenet(){
        return studentService.getTotalNumber();
    }

    @GetMapping("/averaeAge")
    public double getAvereeAge(){
        return studentService.getAvgAge();
    }
    @GetMapping("/last-five")
    public List<Student> getLsastStudenetss(){
        return studentService.getLsastStudenetss();
    }

}
