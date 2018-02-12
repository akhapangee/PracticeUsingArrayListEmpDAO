/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.akhilesh.dao;

import com.akhilesh.entity.Employee;
import java.util.List;

/**
 *
 * @author Akhilesh
 */
public interface EmployeeDAO {
    boolean add(Employee emp);
    boolean delete(int empId);
    List<Employee> getAll();
    Employee searchByEmpId(int empId);
}
