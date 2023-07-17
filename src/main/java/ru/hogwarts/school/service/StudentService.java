package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.dto.FacultyDtoOut;
import ru.hogwarts.school.dto.StudentDtoIn;
import ru.hogwarts.school.dto.StudentDtoOut;
import ru.hogwarts.school.entity.Avatar;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.exception.FacultyNotFindException;
import ru.hogwarts.school.exception.StudentNotFindException;
import ru.hogwarts.school.mapper.FacultyMapper;
import ru.hogwarts.school.mapper.StudentMapper;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    private final FacultyRepository facultyRepository;
    private final StudentMapper studentMapper;

    private final AvatarService avatarService;

    private final FacultyMapper facultyMapper;

    Logger logger = LoggerFactory.getLogger(StudentService.class);
    public StudentService(StudentRepository studentRepository, FacultyRepository facultyRepository, StudentMapper studentMapper, AvatarService avatarService, FacultyMapper facultyMapper) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
        this.studentMapper = studentMapper;
        this.avatarService = avatarService;
        this.facultyMapper = facultyMapper;
    }

    public StudentDtoOut createStudent(StudentDtoIn studentDtoIn) {
        logger.info("Requesting createStudent : {}", studentDtoIn);
        return studentMapper.toDto(studentRepository.save(studentMapper.toEntity(studentDtoIn)));
    }

    public StudentDtoOut readStudent(long id) {
        logger.info("Requesting readStudent : {}", id);
        return studentRepository.findById(id)
                .map(studentMapper::toDto)
                .orElseThrow(()-> new StudentNotFindException(id));
    }

    public StudentDtoOut updateStudent(long id,StudentDtoIn studentDtoIn) {
        logger.info("Requesting updateStudent : {} and {}", studentDtoIn, id);
        return studentRepository.findById(id)
                .map(oldFaculty -> {
                    oldFaculty.setAge(studentDtoIn.getAge());
                    oldFaculty.setName(studentDtoIn.getName());
                    return studentMapper.toDto(studentRepository.save(oldFaculty));
                })
                .orElseThrow(()->new StudentNotFindException(id));
    }

    public StudentDtoOut deleteStudent(long id) {
        logger.info("Requesting deleteStudent : {}",id);
        Student student = studentRepository.findById(id)
                .orElseThrow(()-> new StudentNotFindException(id));
        studentRepository.delete(student);
        return studentMapper.toDto(student);
    }

    public List<StudentDtoOut> endpointStudent(@Nullable Integer age) {
        logger.info("Requesting endpointStudent : {}", age);
        return Optional.ofNullable(age)
                .map(studentRepository::findAllByAge)
                .orElseGet(studentRepository::findAll)
                .stream().map(studentMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<StudentDtoOut> findByAgeBetween(int ageFrom, int ageTo) {
        logger.info("Requesting findByAgeBetween : {} and {}",ageFrom,ageTo);
        return studentRepository.findByAgeBetween(ageFrom,ageTo).stream()
                .map(studentMapper::toDto)
                .collect(Collectors.toList());
    }

    public StudentDtoOut uploadAvatar(MultipartFile multipartFile, long id) {
        logger.info("Requesting uploadAvatar : {} ",id);
        Student student = studentRepository.findById(id)
                .orElseThrow(()-> new StudentNotFindException(id));
       Avatar avatar = avatarService.create(student,multipartFile);
       StudentDtoOut studentDtoOut = studentMapper.toDto(student);
       studentDtoOut.setAvatarUrl("http://localhost:8080/avatars/"+ avatar.getId() + "/from-db");
       return studentDtoOut;
    }

    public FacultyDtoOut findFaculty(Long id) {
        logger.info("Requesting findFaculty : {} ",id);
        return studentRepository.findById(id)
                .map(Student::getFaculty)
                .map(facultyMapper::toDto)
                .orElseThrow(()-> new FacultyNotFindException(id));
    }
    public Integer getTotalNumber() {
        logger.info("Requesting getTotalNumber");
        return studentRepository.getTotalNumber();
    }

    public Integer getAvgAge() {
        logger.info("Requesting getAvgAge");
        return studentRepository.getAvgAge();
    }

    public List<Student> getLsastStudenetss() {
        logger.info("Requesting getLsastStudenets");
        return studentRepository.getLastFive();
    }
}
