/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.akhilesh.main;

import com.akhilesh.controller.EmployeeController;
import com.akhilesh.dao.EmployeeDAO;
import com.akhilesh.dao.impl.EmployeeDAOImpl;

/**
 *
 * @author Akhilesh
 */
public class Program {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        EmployeeDAO employeeDAO = new EmployeeDAOImpl();
        EmployeeController controller = new EmployeeController(employeeDAO);
        controller.process();
    }

}
