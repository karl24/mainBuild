package com.tutorfind.Repository;

import com.tutorfind.model.StudentDataModel;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface StudentRepository extends CrudRepository<StudentDataModel,Integer>{

    List<StudentDataModel> findByLegalLastName (String legalLastName);
}
