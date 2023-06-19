package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;

import ru.hogwarts.school.model.Student;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentService {

    Map<Long, Student> studentMap = new HashMap<>();

    private long counter = 0;

    public Student createStudent(Student student) {        // Создание нового студента
        student.setId(++counter);
        studentMap.put(counter, student);
        return student;
    }

    public Student readStudent(long id) {                // Получение студента по ID
        return studentMap.get(id);
    }

    public Student updateStudent(Student student) {     // Редактирование студента по его ID
        if (studentMap.containsKey(student.getId())) {
            studentMap.put(student.getId(), student);
            return student;
        }
        return null;
    }

    public Student deleteStudent(long id) {             // Удаление студента по его ID
        return studentMap.remove(id);
    }

    public Collection<Student> endpointStudent(int age) {
        return studentMap.values().stream()
                .filter(student -> student.getAge() == age)
                .collect(Collectors.toList());
    }

    public Collection<Student> getAllStudents(){
        return studentMap.values();
    }

}
