package ru.hogwarts.school.service;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.dto.FacultyDtoIn;
import ru.hogwarts.school.dto.FacultyDtoOut;
import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.exception.FacultyNotFindException;
import ru.hogwarts.school.mapper.FacultyMapper;
import ru.hogwarts.school.repository.FacultyRepository;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;


    private final FacultyMapper facultyMapper;

    public FacultyService(FacultyRepository facultyRepository, FacultyMapper facultyMapper) {
        this.facultyRepository = facultyRepository;
        this.facultyMapper = facultyMapper;
    }


    public FacultyDtoOut createFaculty(FacultyDtoIn facultyDtoIn) {
        return facultyMapper.toDto(facultyRepository.save(facultyMapper.toEntity(facultyDtoIn)));
    }

    public FacultyDtoOut readFaculty(long id) {
        return facultyRepository.findById(id)
                .map(facultyMapper::toDto)
                .orElseThrow(()-> new FacultyNotFindException(id));
    }

    public FacultyDtoOut updateFaculty(long id,FacultyDtoIn facultyDtoIn) {
        return facultyRepository.findById(id)
                .map(oldFaculty -> {
                    oldFaculty.setColor(facultyDtoIn.getColor());
                    oldFaculty.setName(facultyDtoIn.getName());
                    return facultyMapper.toDto(facultyRepository.save(oldFaculty));
                })
                .orElseThrow(()->new FacultyNotFindException(id));
    }

    public FacultyDtoOut deleteFaculty(long id) {
        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(()-> new FacultyNotFindException(id));
        facultyRepository.delete(faculty);
        return facultyMapper.toDto(faculty);
    }

    public List<FacultyDtoOut> endpointFaculty(@Nullable String color) {
            return Optional.ofNullable(color)
                    .map(facultyRepository::findAllByColor)
                    .orElseGet(facultyRepository::findAll)
                    .stream().map(facultyMapper::toDto)
                .collect(Collectors.toList());
    }


}
