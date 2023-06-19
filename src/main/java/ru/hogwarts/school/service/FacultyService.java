package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    Map<Long, Faculty> facultyMap = new HashMap<>();

    private long counter = 0;


    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(++counter);
        facultyMap.put(counter, faculty);
        return faculty;
    }

    public Faculty readFaculty(long id) {
        return facultyMap.get(id);
    }

    public Faculty updateFaculty(Faculty faculty) {
        if (facultyMap.containsKey(faculty.getId())) {
            facultyMap.put(faculty.getId(), faculty);
            return faculty;
        }
        return null;
    }

    public Faculty deleteFaculty(long id){
        return facultyMap.remove(id);
    }

    public Collection<Faculty> endpointFaculty(String color){
        return facultyMap.values().stream()
                .filter(faculty -> faculty.getColor() == color )
                .collect(Collectors.toList());
    }

    public Collection<Faculty> getAllFaculty(){
        return facultyMap.values();
    }

}
