package ru.hogwarts.school.controller;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import ru.hogwarts.school.dto.FacultyDtoIn;
import ru.hogwarts.school.dto.FacultyDtoOut;
import ru.hogwarts.school.dto.StudentDtoIn;
import ru.hogwarts.school.dto.StudentDtoOut;
import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.mapper.FacultyMapper;
import ru.hogwarts.school.mapper.StudentMapper;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//@WebMvcTest(controllers = StudentController.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private StudentService studentService;
    @SpyBean
    private StudentMapper studentMapper;

    @SpyBean
    private FacultyMapper facultyMapper;

    private final Faker faker = new Faker();

    @Test
    public void createTest() throws Exception {

        StudentDtoIn studentDtoIn = generateDto();
        Student student = new Student();
        student.setId(3L);
        student.setName(studentDtoIn.getName());
        student.setAge(studentDtoIn.getAge());
        when(studentRepository.save(any())).thenReturn(student);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/students")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(studentDtoIn))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()));
    }

    @Test
    public void deleteTest() throws Exception {
        Student student = new Student();
        student.setId(1L);
        when(studentRepository.findById(eq(1L))).thenReturn(Optional.of(student));
        mockMvc.perform(MockMvcRequestBuilders.delete("/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId()));
    }

    @Test
    public void readTest() throws Exception {
        Student student = new Student();
        student.setId(1L);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        mockMvc.perform(MockMvcRequestBuilders.get("/students/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId()));
    }

    @Test
    void getFacultyTest() throws Exception {
        Faculty faculty = new Faculty(1L, "A", "red");
        Student student = new Student(1L, "B", 15);
        student.setFaculty(faculty);

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders.get("/students/1/faculty")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty.getId()))
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));
    }


    private StudentDtoIn generateDto() {
        StudentDtoIn studentDtoIn = new StudentDtoIn();
        studentDtoIn.setAge(faker.random().nextInt(10,50));
        studentDtoIn.setName(faker.name().fullName());
        return studentDtoIn;
    }








}
