package com.tutorfind.Repositories;

import com.tutorfind.model.StudentDataModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface StudentRepository extends JpaRepository<StudentDataModel,Integer>{

    List<StudentDataModel> findByLastName(String lastName);


}
