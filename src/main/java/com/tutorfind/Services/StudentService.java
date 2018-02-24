package com.tutorfind.Services;

import com.tutorfind.Repositories.StudentRepository;
import com.tutorfind.model.StudentDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service("studentService")
public class StudentService{

    @Autowired
    StudentRepository studentRepository;

    public void createStudent(StudentDataModel s){
        studentRepository.save(s);
    }

    public List<StudentDataModel> findAll(){
        return studentRepository.findAll();
    }
}
