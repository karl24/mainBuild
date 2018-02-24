package com.tutorfind.Repositories;

import com.tutorfind.model.StudentDataModel;


import java.util.List;

public interface StudentRepository {//extends CrudRepository<StudentDataModel,Integer>{

    List<StudentDataModel> findByLastName(String lastName);


}
