package com.EduTrack.student.service.impl;

import com.EduTrack.student.dto.AddStudentDto;
import com.EduTrack.student.dto.StudentDto;
import com.EduTrack.student.entity.Student;
import com.EduTrack.student.exception.StudentNotFoundException;
import com.EduTrack.student.repository.StudentRepository;
import com.EduTrack.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;


    @Override
    public StudentDto saveStudent(AddStudentDto dto) {
        Student newStudent = modelMapper.map(dto, Student.class);
        Student student = studentRepository.save(newStudent);
        return modelMapper.map(student, StudentDto.class);
    }

    @Override
    public StudentDto getStudentById(Long id) throws StudentNotFoundException {
        Student student = studentRepository.findById(id).orElseThrow(() ->new StudentNotFoundException("Student not found by id "+id));
        return modelMapper.map(student, StudentDto.class);
    }

    @Override
    public List<StudentDto> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream().map(student -> new StudentDto(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getCourse()))
                .toList();
    }

    @Override
    public void deleteStudent(Long id) throws StudentNotFoundException {
        Student student = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException("Student not found by id : "+id));
        studentRepository.delete(student);
    }

    @Override
    public StudentDto partialUpdate(Long id,  Map<String, Object> updates) throws StudentNotFoundException {
        Student student = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException("Student not found by Id  "+id));
        updates.forEach((field, value) ->{
            switch(field){
                case "name" : student.setName((String) value);
                break;
                case "email" : student.setEmail((String) value);
                break;
                case "course" : student.setCourse((String) value);
                break;
                default : throw new IllegalArgumentException("Field is not supported");
            }
        });
        Student newStudent = studentRepository.save(student);
        return modelMapper.map(newStudent, StudentDto.class);
    }

    @Override
    public StudentDto fullUpdate(Long id, AddStudentDto updates) throws StudentNotFoundException {
        Student student = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException("Student not found by Id  "+id));
        modelMapper.map(updates, student);
        student = studentRepository.save(student);
        return modelMapper.map(student, StudentDto.class);
    }
}
