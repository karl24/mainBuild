package com.tutorfind.controllers;

import com.tutorfind.model.StudentDataModel;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

public interface StudentRepository extends CrudRepository<StudentDataModel,Integer> {


}
