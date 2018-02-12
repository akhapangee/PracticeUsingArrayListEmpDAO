/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.akhilesh.dao.impl;

import com.akhilesh.dao.EmployeeDAO;
import com.akhilesh.entity.Employee;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Akhilesh
 */
public class EmployeeDAOImpl implements EmployeeDAO {

    private List<Employee> empList = new ArrayList<>();

    @Override
    public boolean add(Employee emp) {
        return empList.add(emp);
    }

    @Override
    public boolean delete(int empId) {
        Employee employee = searchByEmpId(empId);
        if (employee != null) {
            empList.remove(employee);
            return true;
        }
        return false;
    }

    @Override
    public List<Employee> getAll() {
        return empList;
    }

    @Override
    public Employee searchByEmpId(int empId) {
        for (Employee emp : empList) {
            if (empId == emp.getEmpId()) {
                return emp;
            }
        }
        return null;
    }

}
