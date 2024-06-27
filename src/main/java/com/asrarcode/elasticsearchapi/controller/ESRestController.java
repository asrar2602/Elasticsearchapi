package com.asrarcode.elasticsearchapi.controller;

import com.asrarcode.elasticsearchapi.exception.RecordNotFoundException;
import com.asrarcode.elasticsearchapi.model.Employee;
import com.asrarcode.elasticsearchapi.model.USEmployee;
import com.asrarcode.elasticsearchapi.service.ESService;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
public class ESRestController {

    private static final Logger log = LoggerFactory.getLogger(ESRestController.class);
    private static final String COMMA_DELIMITER = ",";

    private static final CsvMapper mapper = new CsvMapper();

    @Autowired
    private ESService esService;

    @GetMapping("/index/{id}")
    public ResponseEntity<Employee> fetchEmployeeById(@PathVariable("id") String id) throws RecordNotFoundException, IOException {
        Employee employee = esService.fetchEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    @PostMapping("/index/fetchWithMust")
    public ResponseEntity<List<Employee>> fetchEmployeesWithMustQuery(@RequestBody Employee employeeSearchRequest) throws IOException {
        List<Employee> employees = esService.fetchEmployeesWithMustQuery(employeeSearchRequest);
        return ResponseEntity.ok(employees);
    }

    @PostMapping("/index/fetchWithShould")
    public ResponseEntity<List<Employee>> fetchEmployeesWithShouldQuery(@RequestBody Employee employeeSearchRequest) throws IOException {
        List<Employee> employees = esService.fetchEmployeesWithShouldQuery(employeeSearchRequest);
        return ResponseEntity.ok(employees);
    }

    @PostMapping("/index")
    public ResponseEntity<String> insertRecords(@RequestBody Employee employee) throws IOException {
        String status = esService.insertEmployee(employee);
        return ResponseEntity.ok(status);
    }

    @PostMapping("/index/bulk")
    public ResponseEntity<String> bulkInsertEmployees() throws IOException {
        List<USEmployee> employees = getEmployeesFromExcel();

        boolean isSuccess = esService.bulkInsertEmployees(employees);
        if (isSuccess) {
            return ResponseEntity.ok("Records successfully ingested!");
        } else {
            return ResponseEntity.internalServerError().body("Oops! unable to ingest data");
        }
    }

    private List<USEmployee> getEmployeesFromExcel() throws IOException {
        List<USEmployee> employeeList;
        FileInputStream fin = null;
        try {
            fin = new FileInputStream("employees.csv");
            employeeList = read(USEmployee.class, fin);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            fin.close();
        }
        return employeeList;
    }

    public static <T> List<T> read(Class<T> clazz, InputStream stream) throws IOException {
        CsvSchema schema = mapper.schemaFor(clazz).withHeader().withColumnReordering(true);
        ObjectReader reader = mapper.readerFor(clazz).with(schema);
        return reader.<T>readValues(stream).readAll();
    }


    @DeleteMapping("/index/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") String id) throws IOException {
        String status = esService.deleteEmployeeById(id);
        return ResponseEntity.ok(status);
    }

    @PutMapping("/index")
    public ResponseEntity<String> updateEmployee(@RequestBody Employee employee) throws IOException {
        String status = esService.updateEmployee(employee);
        return ResponseEntity.ok(status);
    }

    @RequestMapping(value = "/dbusers/index/bulk", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> bulkIndexAllDBEmployees() throws Exception {
        String status = esService.bulkIndexAllDBUsers();
        return ResponseEntity.ok(status);
    }

}
