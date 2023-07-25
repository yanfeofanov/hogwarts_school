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


import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;



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
                .orElseThrow(() -> new StudentNotFindException(id));
    }

    public StudentDtoOut updateStudent(long id, StudentDtoIn studentDtoIn) {
        logger.info("Requesting updateStudent : {} and {}", studentDtoIn, id);
        return studentRepository.findById(id)
                .map(oldFaculty -> {
                    oldFaculty.setAge(studentDtoIn.getAge());
                    oldFaculty.setName(studentDtoIn.getName());
                    return studentMapper.toDto(studentRepository.save(oldFaculty));
                })
                .orElseThrow(() -> new StudentNotFindException(id));
    }

    public StudentDtoOut deleteStudent(long id) {
        logger.info("Requesting deleteStudent : {}", id);
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFindException(id));
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
        logger.info("Requesting findByAgeBetween : {} and {}", ageFrom, ageTo);
        return studentRepository.findByAgeBetween(ageFrom, ageTo).stream()
                .map(studentMapper::toDto)
                .collect(Collectors.toList());
    }

    public StudentDtoOut uploadAvatar(MultipartFile multipartFile, long id) {
        logger.info("Requesting uploadAvatar : {} ", id);
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFindException(id));
        Avatar avatar = avatarService.create(student, multipartFile);
        StudentDtoOut studentDtoOut = studentMapper.toDto(student);
        studentDtoOut.setAvatarUrl("http://localhost:8080/avatars/" + avatar.getId() + "/from-db");
        return studentDtoOut;
    }

    public FacultyDtoOut findFaculty(Long id) {
        logger.info("Requesting findFaculty : {} ", id);
        return studentRepository.findById(id)
                .map(Student::getFaculty)
                .map(facultyMapper::toDto)
                .orElseThrow(() -> new FacultyNotFindException(id));
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

    public List<String> getSortName() {

        return studentRepository.findAll().stream()
                .sorted(Comparator.comparing(Student::getName))
                .map(student -> student.getName().toUpperCase())
                .collect(Collectors.toList());
    }

    public double getAverageAgeStudent() {

        return studentRepository.findAll().stream()
                .mapToInt(Student::getAge).average().orElse(0.0);
    }


    public Integer getSum() {

        int sum = 0;
        sum = Stream.iterate(1, a -> a + 1).parallel().limit(1_000_000).sorted().reduce(0, (a, b) -> a + b);
        System.currentTimeMillis();
        return sum;
        // Выполнение быстрее происходит без ввода parallel().
    }


    public void firstListThread() {
        List<Student> students = studentRepository.findAll();

        printStudent(students.get(0));
        printStudent(students.get(1));
        new Thread(()->{

          printStudent(students.get(1));
          printStudent(students.get(2));
        }).start();
        new Thread(()->{
            printStudent(students.get(4));
            printStudent(students.get(5));
        }).start();

    }

    private void printStudent(Student student){
        try {
            Thread.sleep(1000);
            logger.info(student.toString());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public void nextListThread() {
        List<Student> students = studentRepository.findAll();

        printStudentSync(students.get(0));
        printStudentSync(students.get(1));
        new Thread(()->{

            printStudentSync(students.get(2));
            printStudentSync(students.get(3));
        }).start();
        new Thread(()->{
            printStudentSync(students.get(4));
            printStudentSync(students.get(5));
        }).start();

    }
    private synchronized void printStudentSync(Student student){
        try {
            Thread.sleep(1000);
            logger.info(student.toString());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
