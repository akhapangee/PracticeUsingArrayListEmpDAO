/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.akhilesh.controller;

import com.akhilesh.constants.SystemConstants;
import com.akhilesh.dao.EmployeeDAO;
import com.akhilesh.entity.Employee;
import com.akhilesh.util.FileHelper;
import java.io.File;
import javax.swing.JOptionPane;

/**
 *
 * @author Akhilesh
 */
public class EmployeeController {

    private EmployeeDAO employeeDAO;
    private StringBuilder displayContent;

    public EmployeeController(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
        displayContent = new StringBuilder();
    }

    public void process() {
        String ch = "";
        while (ch != null) {
            try {
                ch = displayMenuAndGetChoice();
                switch (ch) {
                    case "1":
                        addEmployee();
                        String addMore = JOptionPane.showInputDialog("Add more Employee? Y = Yes N = NO");
                        while (true && addMore != null) {
                            if (addMore.equalsIgnoreCase("Y")) {
                                addEmployee();
                                addMore = JOptionPane.showInputDialog("Add more Employee? Y = Yes N = NO");
                            } else if (addMore.equalsIgnoreCase("N")) {
                                break;
                            } else {
                                JOptionPane.showMessageDialog(null, "Input Y = Yes, N = NO", "Error", JOptionPane.ERROR_MESSAGE);
                                addMore = JOptionPane.showInputDialog("Add more Employee? Y = Yes N = NO");
                            }
                        }
                        break;
                    case "2":
                        displayEmployee();
                        break;
                    case "3":
                        int deleteId = Integer.parseInt(JOptionPane.showInputDialog("Enter empId to delete"));
                        deleteEmployee(deleteId);
                        break;
                    case "4":
                        int searchId = Integer.parseInt(JOptionPane.showInputDialog("Enter empId to search"));
                        displaySearchResult(searchId);
                        break;
                    case "5":
//                        Employee tempObj;
//                        for (int i = 0; i < myList.size(); i++) {
//                            for (int j = i + 1; j < myList.size(); j++) {
//                                Employee e = myList.get(i);
//
//                                if (myList.get(j).getEmpId() < myList.get(i).getEmpId()) {
//                                    tempObj = myList.get(i);
////                                    myList[i] = tempObj;
//
//                                }
//                            }
//                        }

                        break;
                    case "6":
                        for (Employee obj : employeeDAO.getAll()) {
                            displayContent.append(obj.getEmpId() + ","
                                    + obj.getFirstName() + ","
                                    + obj.getLastName() + ","
                                    + obj.getEmail()).append("\r\n");
                        }
                        if (!displayContent.toString().isEmpty()) {

                            String fileName = validateFileName();
                            while (!fileName.endsWith(".csv")) {
                                JOptionPane.showMessageDialog(null, "Filename must end with '.csv' extenstion", "Error", JOptionPane.ERROR_MESSAGE);
                                fileName = validateFileName();
                            }
                            boolean success = FileHelper.writeFile(SystemConstants.EXPORT_FILE_PATH,
                                    fileName, displayContent.toString());

                            if (success) {
                                displayContent.delete(0, displayContent.length());
                                JOptionPane.showMessageDialog(null, "Record exported to a file: "
                                        + new File(SystemConstants.EXPORT_FILE_PATH).getAbsolutePath() + "\\" + fileName);
                            } else {
                                JOptionPane.showMessageDialog(null, "Error in file export !", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "No records found to export", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                    case "7":
                        System.exit(0);
                    default:
                        JOptionPane.showMessageDialog(null, "Invalid choice!", "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private String displayMenuAndGetChoice() {
        String strMenu = "Enter your choice:\n"
                + "1. Add employee\n"
                + "2. View employee\n"
                + "3. Delete employee\n"
                + "4. Search by empId\n"
                + "5. Sort\n"
                + "6. Export to CSV file\n"
                + "7. Exit or Hit 'Cancel";
        String ch = JOptionPane.showInputDialog(strMenu);
        return ch;
    }

    private void addEmployee() {
//        int id = Integer.parseInt(JOptionPane.showInputDialog("Enter you empId"));

        String firstName = JOptionPane.showInputDialog("Enter first name:");
        while (firstName.isEmpty()) {
            firstName = JOptionPane.showInputDialog(null, "First name is empty! Please enter first name:", "Error!", JOptionPane.ERROR_MESSAGE);
        }

        String lastName = JOptionPane.showInputDialog(null, " Enter last name:");
        while (lastName.isEmpty()) {
            lastName = JOptionPane.showInputDialog(null, "Last name is empty! Please last name:", "Error!", JOptionPane.ERROR_MESSAGE);
        }

        String email = JOptionPane.showInputDialog(null, " Please enter email:");
        while (email.isEmpty() || !email.contains("@")) {
            if (email.isEmpty()) {
                email = JOptionPane.showInputDialog(null, "Email is empty! Please enter email:", "Error!", JOptionPane.ERROR_MESSAGE);
            } else if (!email.contains("@")) {
                email = JOptionPane.showInputDialog(null, "Invalid Email! Please enter valid email:", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (!(firstName.isEmpty() && lastName.isEmpty() && email.isEmpty())) {
            Employee emp = new Employee();

            emp.setEmpId(employeeDAO.getAll().size() + 1);
            emp.setFirstName(firstName.trim().substring(0, 1).toUpperCase() + firstName.trim().substring(1));
            emp.setLastName(lastName.trim().substring(0, 1).toUpperCase() + lastName.trim().substring(1));
            emp.setEmail(email.trim());

            employeeDAO.add(emp);
        }

    }

    private void displayEmployee() {
        for (Employee e : employeeDAO.getAll()) {
            displayContent.append(e.getEmpId() + ","
                    + e.getFirstName() + ","
                    + e.getLastName() + ","
                    + e.getEmail()).append("\r\n");
        }
        if (!displayContent.toString().isEmpty()) {
            JOptionPane.showMessageDialog(null, displayContent.toString());
        } else {
            JOptionPane.showMessageDialog(null, "No records found!");
        }
        displayContent.setLength(0);
    }

    private void deleteEmployee(int deleteId) {
        if (employeeDAO.delete(deleteId)) {
            JOptionPane.showMessageDialog(null, "Employee " + deleteId + " deleted successfully");
        } else {
            JOptionPane.showMessageDialog(null, "No record found to delete");
        }
    }

    private void displaySearchResult(int searchId) {
        boolean found = false;
        for (Employee e : employeeDAO.getAll()) {
            if (searchId == e.getEmpId()) {
                found = true;
                displayContent.append("Found!\n").
                        append(e.getEmpId() + ","
                                + e.getFirstName() + ","
                                + e.getLastName() + ","
                                + e.getEmail()).append("\r\n");
                break;
            }
        }
        if (found) {
            JOptionPane.showMessageDialog(null, displayContent.toString());
            displayContent.setLength(0);
        } else {
            JOptionPane.showMessageDialog(null, "Not found!");
        }
    }

    private String validateFileName() {
        String fileName = JOptionPane.showInputDialog("Enter filename:");
        File file = new File(SystemConstants.EXPORT_FILE_PATH + fileName);
        while (file.exists()) {
            System.out.println("filename: " + fileName);
            if (fileName.isEmpty()) {
                fileName = JOptionPane.showInputDialog(null, "Filename is empty! Enter filename:", "Error", JOptionPane.ERROR_MESSAGE);
                file = new File(SystemConstants.EXPORT_FILE_PATH + fileName);
            } else if (file.exists()) {
                fileName = JOptionPane.showInputDialog(null, "Filename' " + fileName + "' already exists! Enter new filename:", "Error", JOptionPane.ERROR_MESSAGE);
                file = new File(SystemConstants.EXPORT_FILE_PATH + fileName);
            } else if (fileName == null) {
                break;
            }
        }
        return fileName;
    }
}
