package ru.hogwarts.school.mapper;

import org.springframework.stereotype.Component;
import ru.hogwarts.school.dto.FacultyDtoIn;
import ru.hogwarts.school.dto.FacultyDtoOut;
import ru.hogwarts.school.dto.StudentDtoIn;
import ru.hogwarts.school.dto.StudentDtoOut;
import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.entity.Student;

@Component
public class FacultyMapper {
    public FacultyDtoOut toDto(Faculty faculty){
        FacultyDtoOut facultyDtoOut = new FacultyDtoOut();
        facultyDtoOut.setId(faculty.getId());
        facultyDtoOut.setName(faculty.getName());
        facultyDtoOut.setColor(faculty.getColor());
        return facultyDtoOut;
    }

    public Faculty toEntity(FacultyDtoIn facultyDtoIn){
        Faculty faculty = new Faculty();
        faculty.setColor(facultyDtoIn.getColor());
        faculty.setName(facultyDtoIn.getName());
        return faculty;
    }
}
