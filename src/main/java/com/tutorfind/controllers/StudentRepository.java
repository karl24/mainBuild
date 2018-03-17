package com.tutorfind.controllers;

import com.tutorfind.model.StudentDataModel;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<StudentDataModel,Integer> {


}
