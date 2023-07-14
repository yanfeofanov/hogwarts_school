package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.entity.Student;

import java.util.List;

@Repository

public interface StudentRepository extends JpaRepository<Student,Long> {
    List<Student> findAllByAge(int age);

    List<Student>findByAgeBetween(int ageFrom, int ageTo);

    List<Student> findAllByFaculty_Id(long facultyId);

    @Query(value = "select count (*) from students",nativeQuery = true)
    Integer getTotalNumber();

    @Query(value = "select avg (age) from students", nativeQuery = true)
    Integer getAvgAge();

    @Query(value = "select * from students order by id desc limit 5", nativeQuery = true)
    List<Student> getLastFive();

}
