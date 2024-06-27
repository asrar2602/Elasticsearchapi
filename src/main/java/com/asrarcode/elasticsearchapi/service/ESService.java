package com.asrarcode.elasticsearchapi.service;


import com.asrarcode.elasticsearchapi.exception.RecordNotFoundException;
import com.asrarcode.elasticsearchapi.model.Employee;
import com.asrarcode.elasticsearchapi.model.USEmployee;

import java.io.IOException;
import java.util.List;

public interface ESService {

    public Employee fetchEmployeeById(String id) throws RecordNotFoundException, IOException;

    public String insertEmployee(Employee employee) throws IOException;

    public boolean bulkInsertEmployees(List<USEmployee> employees) throws IOException;

    boolean bulkInsertUSEmployees(List<USEmployee> employees) throws IOException;

    public List<Employee> fetchEmployeesWithMustQuery(Employee employee) throws IOException;
    public List<Employee> fetchEmployeesWithShouldQuery(Employee employee) throws IOException;

    public String deleteEmployeeById(String id) throws IOException;

    public String updateEmployee(Employee employee) throws IOException;

    public String bulkIndexAllDBUsers() throws IOException;

}
