package com.danilkompaniets.app.student;


import com.danilkompaniets.app.school.School;
import org.springframework.stereotype.Service;

@Service
public class StudentMapper {
    public Student toStudent(StudentDto dto) {
        if (dto == null) {
            throw new NullPointerException("The student dto cannot be null");
        }
        Student student = new Student();

        student.setEmail(dto.email());
        student.setFirstName(dto.firstName());
        student.setLastName(dto.lastName());

        School school = new School();
        school.setId(dto.schoolId());

        student.setSchool(school);

        return student;
    }

    public StudentResponseDto toStudentResponseDto(Student student) {
        return new StudentResponseDto(
                student.getFirstName(),
                student.getLastName(),
                student.getEmail());
    }

}
