package ru.hogwarts.school.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public class StudentServiceTest {

    private final StudentService studentService = new StudentService();

    @BeforeEach
    public void beforeEach() {
        studentService.createStudent(new Student(1L,"A", 23));
        studentService.createStudent(new Student(2L,"B", 12));
        studentService.createStudent(new Student(3L,"C", 32));
    }

    @Test
    public void createStudentTest() {
        int size = studentService.getAllStudents().size();
        Student expected = new Student(1L,"D",43);
        Assertions.assertEquals(expected,studentService.createStudent(expected));
        Assertions.assertEquals(size+1,studentService.getAllStudents().size());
    }

    @Test
    public void readStudentTest(){
        Assertions.assertEquals(new Student(3L,"C",32),studentService.readStudent(3));
    }

    @Test
    public void updateStudentPositiveTest(){
        int size = studentService.getAllStudents().size();
        Student expected = new Student(3L,"C",32);
        Assertions.assertEquals(expected,studentService.updateStudent(expected));
        Assertions.assertEquals(size,studentService.getAllStudents().size());
    }

    @Test
    public void updateStudentNegativeTest(){
        int size = studentService.getAllStudents().size();
        Student expected = new Student(5L,"C",32);
        Assertions.assertNull(studentService.updateStudent(expected));
        Assertions.assertEquals(size,studentService.getAllStudents().size());
    }

    @Test
    public  void deleteStudentTest(){
        int size = studentService.getAllStudents().size();
        Student expected = new Student(3L,"C",32);
        Assertions.assertEquals(expected,studentService.deleteStudent(3));
        Assertions.assertEquals(size-1,studentService.getAllStudents().size());
    }

    @Test
    public void endpointStudentTest(){
        Student expected = new Student(4L,"C",32);
        studentService.createStudent(expected);
        List<Student> list = List.of(new Student(3L,"C",32),expected);
        Assertions.assertIterableEquals(list,studentService.endpointStudent(32));
    }

}
