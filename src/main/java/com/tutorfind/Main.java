/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tutorfind;

import com.tutorfind.Repository.StudentRepository;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.tutorfind.model.*;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

//@SpringBootApplication
//public class Main {
//
//    private static final Logger log = LoggerFactory.getLogger(Main.class);
//
//    public static void main(String[] args) {
//        SpringApplication.run(Main.class);
//    }
//
//    @Bean
//    public CommandLineRunner demo(StudentRepository repository) {
//        return (args) -> {
//            // save a couple of customers
//            repository.save(new StudentDataModel(100, "Joe","Test","bio","major","minor","img",true,new Timestamp(System.currentTimeMillis())));
//
//            // fetch all customers
//            log.info("Customers found with findAll():");
//            log.info("-------------------------------");
//            for (StudentDataModel customer : repository.findAll()) {
//                log.info(customer.toString());
//            }
//            log.info("");
//
//            // fetch an individual customer by ID
//            StudentDataModel customer = repository.findOne(100);
//            log.info("Customer found with findOne(1L):");
//            log.info("--------------------------------");
//            log.info(customer.toString());
//            log.info("");
//
//            // fetch customers by last name
//            log.info("Customer found with findByLastName('Bauer'):");
//            log.info("--------------------------------------------");
//            for (StudentDataModel bauer : repository.findByLegalLastName("Test")) {
//                log.info(bauer.toString());
//            }
//            log.info("");
//        };
//    }
//
//}

@Controller
@SpringBootApplication
public class Main {

  @Value("${spring.datasource.url}")
  private String dbUrl;


  private static final Logger log = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) throws Exception {
    SpringApplication.run(Main.class, args);
  }

  @RequestMapping("/")
  String index() {
    return "index";
  }

//    @Bean
//    public CommandLineRunner demo(StudentRepository repository) throws SQLException {
//        return (args) -> {
//            // save a couple of customers
//            repository.save(new StudentDataModel(1, "Joe","Test","bio","major","minor","img",true,new Timestamp(System.currentTimeMillis())));
////            repository.save(new Customer("Chloe", "O'Brian"));
// //           repository.save(new Customer("Kim", "Bauer"));
//  //          repository.save(new Customer("David", "Palmer"));
//    //        repository.save(new Customer("Michelle", "Dessler"));
//
//            // fetch all customers
//            log.info("Customers found with findAll():");
//            log.info("-------------------------------");
//            for (StudentDataModel student : repository.findAll()) {
//                log.info(student.toString());
//            }
//            log.info("");
//
//            // fetch an individual customer by ID
//            StudentDataModel student = repository.findOne(1);
//            log.info("Customer found with findOne(1L):");
//            log.info("--------------------------------");
//            log.info(student.toString());
//            log.info("");
//
//            // fetch customers by last name
//            log.info("Customer found with findByLastName('Bauer'):");
//            log.info("--------------------------------------------");
//            for (StudentDataModel test : repository.findByLegalLastName("test")) {
//                log.info(test.toString());
//            }
//            log.info("");
//        };
//    }

  @RequestMapping("/db")
  String db(Map<String, Object> model, StudentRepository repository) {
    try (Connection connection = dataSource().getConnection()) {
      Statement stmt = connection.createStatement();

      ResultSet rs = stmt.executeQuery("SELECT * FROM students");

     // ArrayList<String> output = new ArrayList<String>();

      while (rs.next()) {
        repository.save(new StudentDataModel(rs.getInt("userId"),rs.getString("legalFirstName"), rs.getString("legalLastName"),rs.getString("bio"),rs.getString("major"), rs.getString("minor"), rs.getString("img"),rs.getBoolean("active"), rs.getTimestamp("creationDate")));
        //output.add(rs.getString("email"));
      }
      model.put("records",repository);
      //model.put("records", output);
      return "db";
    } catch (Exception e) {
      model.put("message", e.getMessage());
      return "error";
    }
  }


  @Bean
  public DataSource dataSource() throws SQLException {
    if (dbUrl == null || dbUrl.isEmpty()) {
      return new HikariDataSource();
    } else {
      HikariConfig config = new HikariConfig();
      config.setJdbcUrl(dbUrl);
      return new HikariDataSource(config);
    }
  }

}
