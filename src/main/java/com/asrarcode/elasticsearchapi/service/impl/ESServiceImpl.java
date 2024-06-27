package com.asrarcode.elasticsearchapi.service.impl;

import com.asrarcode.elasticsearchapi.connector.ESClientConnector;
import com.asrarcode.elasticsearchapi.exception.RecordNotFoundException;
import com.asrarcode.elasticsearchapi.model.Employee;
import com.asrarcode.elasticsearchapi.model.USEmployee;
import com.asrarcode.elasticsearchapi.repository.EmployeeRepository;
import com.asrarcode.elasticsearchapi.service.ESService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ESServiceImpl implements ESService {

    @Autowired
    private ESClientConnector esClientConnector;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee fetchEmployeeById(String id) throws RecordNotFoundException, IOException {
        return esClientConnector.fetchEmployeeById(id);
    }

    @Override
    public String insertEmployee(Employee employee) throws IOException {
        return esClientConnector.insertEmployee(employee);
    }

    @Override
    public boolean bulkInsertEmployees(List<USEmployee> employees) throws IOException {
        return false;
    }

    @Override
    public boolean bulkInsertUSEmployees(List<USEmployee> employees) throws IOException {
       return esClientConnector.bulkInsertEmployees(employees);
    }

    @Override
    public List<Employee> fetchEmployeesWithMustQuery(Employee employee) throws IOException {
        return esClientConnector.fetchEmployeesWithMustQuery(employee);
    }

    @Override
    public List<Employee> fetchEmployeesWithShouldQuery(Employee employee) throws IOException {
        return esClientConnector.fetchEmployeesWithShouldQuery(employee);
    }

    @Override
    public String deleteEmployeeById(String id) throws IOException {
        return esClientConnector.deleteEmployeeById(id);
    }

    @Override
    public String updateEmployee(Employee employee) throws IOException {
        return esClientConnector.updateEmployee(employee);
    }
    @Override
    public String bulkIndexAllDBUsers() throws IOException {
        List<Employee> employees = employeeRepository.findAll();
        String msg= "";
        if (employees.size()>1){
            boolean response = esClientConnector.bulkInsertDBEmployees(employees);
            if (response){
                msg = "Employess are indexed to Elastic";
            } else {
                msg = "There is failure in bulk indexing";
            }
        } else {
            msg = "No Employees found in DB to index";
        }
        return msg;
    }
}
