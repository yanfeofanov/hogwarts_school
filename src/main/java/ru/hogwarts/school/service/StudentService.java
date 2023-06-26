package ru.hogwarts.school.service;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import ru.hogwarts.school.dto.FacultyDtoIn;
import ru.hogwarts.school.dto.FacultyDtoOut;
import ru.hogwarts.school.dto.StudentDtoIn;
import ru.hogwarts.school.dto.StudentDtoOut;
import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.exception.FacultyNotFindException;
import ru.hogwarts.school.exception.StudentNotFindException;
import ru.hogwarts.school.mapper.FacultyMapper;
import ru.hogwarts.school.mapper.StudentMapper;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;


import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;


    private final StudentMapper studentMapper;

    public StudentService(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    public StudentDtoOut createStudent(StudentDtoIn studentDtoIn) {
        return studentMapper.toDto(studentRepository.save(studentMapper.toEntity(studentDtoIn)));
    }

    public StudentDtoOut readStudent(long id) {
        return studentRepository.findById(id)
                .map(studentMapper::toDto)
                .orElseThrow(()-> new StudentNotFindException(id));
    }

    public StudentDtoOut updateStudent(long id,StudentDtoIn studentDtoIn) {
        return studentRepository.findById(id)
                .map(oldFaculty -> {
                    oldFaculty.setAge(studentDtoIn.getAge());
                    oldFaculty.setName(studentDtoIn.getName());
                    return studentMapper.toDto(studentRepository.save(oldFaculty));
                })
                .orElseThrow(()->new StudentNotFindException(id));
    }

    public StudentDtoOut deleteStudent(long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(()-> new StudentNotFindException(id));
        studentRepository.delete(student);
        return studentMapper.toDto(student);
    }

    public List<StudentDtoOut> endpointStudent(@Nullable Integer age) {
        return Optional.ofNullable(age)
                .map(studentRepository::findAllByAge)
                .orElseGet(studentRepository::findAll)
                .stream().map(studentMapper::toDto)
                .collect(Collectors.toList());
    }

}
