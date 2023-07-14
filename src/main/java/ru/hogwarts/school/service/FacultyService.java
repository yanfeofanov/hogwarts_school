package ru.hogwarts.school.service;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.dto.FacultyDtoIn;
import ru.hogwarts.school.dto.FacultyDtoOut;
import ru.hogwarts.school.dto.StudentDtoOut;
import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.exception.FacultyNotFindException;
import ru.hogwarts.school.mapper.FacultyMapper;
import ru.hogwarts.school.mapper.StudentMapper;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FacultyService{

    private final FacultyRepository facultyRepository;

    private final StudentRepository studentRepository;
    private final FacultyMapper facultyMapper;

    private final StudentMapper studentMapper;

    public FacultyService(FacultyRepository facultyRepository, StudentRepository studentRepository, FacultyMapper facultyMapper, StudentMapper studentMapper) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
        this.facultyMapper = facultyMapper;
        this.studentMapper = studentMapper;
    }


    public FacultyDtoOut createFaculty(FacultyDtoIn facultyDtoIn) {
        return facultyMapper.toDto(facultyRepository.save(facultyMapper.toEntity(facultyDtoIn)));
    }

    public FacultyDtoOut readFaculty(long id) {
        return facultyRepository.findById(id)
                .map(facultyMapper::toDto)
                .orElseThrow(() -> new FacultyNotFindException(id));
    }

    public FacultyDtoOut updateFaculty(long id, FacultyDtoIn facultyDtoIn) {
        return facultyRepository.findById(id)
                .map(oldFaculty -> {
                    oldFaculty.setColor(facultyDtoIn.getColor());
                    oldFaculty.setName(facultyDtoIn.getName());
                    return facultyMapper.toDto(facultyRepository.save(oldFaculty));
                })
                .orElseThrow(() -> new FacultyNotFindException(id));
    }

    public FacultyDtoOut deleteFaculty(long id) {
        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() -> new FacultyNotFindException(id));
        facultyRepository.delete(faculty);
        return facultyMapper.toDto(faculty);
    }

    public List<FacultyDtoOut> endpointFaculty(@Nullable String color) {
        return Optional.ofNullable(color)
                .map(facultyRepository::findFacultiesByColor)
                .orElseGet(facultyRepository::findAll)
                .stream().map(facultyMapper::toDto)
                .collect(Collectors.toList());
    }


    public List<FacultyDtoOut> findByColorOrName(String color, String name) {
        return facultyRepository.findFacultiesByColorContainingIgnoreCaseOrNameContainingIgnoreCase(color, name).stream()
                .map(facultyMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<StudentDtoOut> findStudents(Long id) {
        return studentRepository.findAllByFaculty_Id(id)
                .stream()
                .map(studentMapper::toDto)
                .collect(Collectors.toList());
    }
}
