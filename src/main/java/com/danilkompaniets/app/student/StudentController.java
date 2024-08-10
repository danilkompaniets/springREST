package com.danilkompaniets.app.student;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/students")
    public StudentResponseDto saveStudent(
            @Valid @RequestBody StudentDto dto
    ) {
        return studentService.saveStudent(dto);
    }

    @GetMapping("/students/{id}")
    public Optional<Student> getStudentById(
            @PathVariable Integer id
    ) {
        return studentService.getStudentById(id);
    }

    @GetMapping("/students")
    public List<StudentResponseDto> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/students/{name}")
    public List<Student> getStudentByName(
            @PathVariable String name
    ) {
        return studentService.getStudentsByName(name);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex
    ) {
         var errors = new HashMap<String, String>();
         ex.getBindingResult().getAllErrors().forEach((error) -> {
             var fieldName = ((FieldError) error).getField();
             var errorMessage = error.getDefaultMessage();
             errors.put(fieldName, errorMessage);
         });
         return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }


}
