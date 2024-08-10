package com.danilkompaniets.app.student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class StudentServiceTest {
    @InjectMocks
    private StudentService studentService;
    @Mock
    private StudentRepository repository;
    @Mock
    private StudentMapper studentMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void should_successfully_create_a_student() {
        StudentDto dto = new StudentDto("John",
                "Doe",
                "sdasdsad@gmail.com",
                1);
        Student student = new Student(20,
                "sdasdsad@gmail.com",
                "John",
                "Doe");

        // mocking calls
        Mockito.when(studentMapper.toStudent(dto))
                .thenReturn(student);
        Mockito.when(repository.save(student))
                .thenReturn(student);
        Mockito.when(studentMapper.toStudentResponseDto(student))
                .thenReturn(new StudentResponseDto("John",
                        "Doe",
                        "sdasdsad@gmail.com"));

        StudentResponseDto responseDto = studentService.saveStudent(dto);

        assertEquals(responseDto.firstName(), student.getFirstName());
        assertEquals(responseDto.lastName(), student.getLastName());
        assertEquals(responseDto.email(), student.getEmail());
    }

    @Test
    public void should_successfully_list_all_students() {
        Student student = new Student(20,
                "sdasdsad@gmail.com",
                "John",
                "Doe");

        List<Student> students = new ArrayList<>();

        students.add(student);
        Mockito.when(repository.findAll()).thenReturn(students);
        Mockito.when(studentMapper.toStudentResponseDto(any(Student.class))).thenReturn(new StudentResponseDto("John", "Doe", "sdasdsad@gmail.com"));

        List<StudentResponseDto> responseStudents = studentService.getAllStudents();

        assertEquals(responseStudents.size(), students.size());
    }

    @Test
    public void should_find_student_by_id() {
        List<Student> students = new ArrayList<>();

        Student student = new Student(20,
                "sdasdsad@gmail.com",
                "John",
                "Doe");

        students.add(student);
        String name = "John";
        Mockito.when(repository.findAllByFirstNameContaining(name)).thenReturn(students);
        Mockito.when(studentService.getStudentsByName(name)).thenReturn(students);

        List<Student> studentsRes = studentService.getStudentsByName(name);

        assertEquals(studentsRes.size(), students.size());
    }
}