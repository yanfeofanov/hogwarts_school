package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.entity.Faculty;

import java.util.List;

@Repository

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    List<Faculty> findFacultiesByColor(String color);

    List<Faculty> findFacultiesByColorContainingIgnoreCaseOrNameContainingIgnoreCase(String color, String name);
}
