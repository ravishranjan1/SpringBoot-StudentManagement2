package com.EduTrack.student.service;

import com.EduTrack.student.dto.AddStudentDto;
import com.EduTrack.student.dto.StudentDto;
import com.EduTrack.student.exception.StudentNotFoundException;

import java.util.List;
import java.util.Map;

public interface StudentService {

    StudentDto saveStudent(AddStudentDto dto);

    StudentDto getStudentById(Long id) throws StudentNotFoundException;
    List<StudentDto> getAllStudents();
    void deleteStudent(Long id) throws StudentNotFoundException;
    StudentDto partialUpdate(Long id,  Map<String, Object> updates) throws StudentNotFoundException;
    StudentDto fullUpdate(Long id, AddStudentDto updates) throws StudentNotFoundException;
    List<StudentDto>findByName(String name);
    List<StudentDto> findByCourse(String course);
}
