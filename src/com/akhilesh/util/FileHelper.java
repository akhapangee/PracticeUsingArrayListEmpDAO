package com.akhilesh.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Akhilesh
 */
public class FileHelper {

    public static boolean writeFile(String filePath, String filename, String fileContent) {
        try (FileWriter fw = new FileWriter((filePath + filename), true);
                BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(fileContent);
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static String readFile(File fileName) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String str = "";
            while ((str = reader.readLine()) != null) {
                sb.append(str).append("\r\n");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return sb.toString();
    }
}
