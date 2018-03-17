package com.tutorfind.controllers;

import com.tutorfind.model.StudentDataModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends CrudRepository<StudentDataModel,Integer> {


}
