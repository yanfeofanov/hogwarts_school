package ru.hogwarts.school.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Faculty;

import java.util.List;

public class FacultyServiceTest {

    private final FacultyService facultyService = new FacultyService();

    @BeforeEach
    public void beforeEach(){
        facultyService.createFaculty(new Faculty(1L,"A","red"));
        facultyService.createFaculty(new Faculty(2L,"B","black"));
        facultyService.createFaculty(new Faculty(3L,"C","green"));
    }

    @Test
    public void createFacultyTest(){
        int size = facultyService.getAllFaculty().size();
        Faculty expected = new Faculty(4L,"D","blue");
        Assertions.assertEquals(expected,facultyService.createFaculty(expected));
        Assertions.assertEquals(size+1,facultyService.getAllFaculty().size());
    }

    @Test
    public void readFacultyTest(){
        Faculty expected = new Faculty(3L,"C","green");
        Assertions.assertEquals(expected,facultyService.readFaculty(3));
    }

    @Test
    public void updateFacultyTest(){
        int size = facultyService.getAllFaculty().size();
        Faculty expected = new Faculty(3L,"C","green");
        Assertions.assertEquals(expected,facultyService.updateFaculty(expected));
        Assertions.assertEquals(size,facultyService.getAllFaculty().size());
    }

    @Test
    public void updateFacultyNegativeTest(){
        Faculty expected = new Faculty(4L,"C","green");
        Assertions.assertNull(facultyService.updateFaculty(expected));
    }

    @Test
    public void deleteFacultyTest(){
        int size = facultyService.getAllFaculty().size();
        Faculty expected = new Faculty(3L,"C","green");
        Assertions.assertEquals(expected,facultyService.deleteFaculty(3));
        Assertions.assertEquals(size-1,facultyService.getAllFaculty().size());
    }

    @Test
    public void endpointFacultyTest(){
        Faculty expected = new Faculty(4L,"C","green");
        facultyService.createFaculty(expected);
        List<Faculty> list = List.of(new Faculty(3L,"C","green"),expected);
        Assertions.assertIterableEquals(list,facultyService.endpointFaculty("green"));
    }
}
