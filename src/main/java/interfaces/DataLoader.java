/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import java.io.IOException;
import objects.Student;

/**
 *
 * @author Даниил
 */
public interface DataLoader {
    public String[] getHeaders() throws IOException;
    public Student[] getStudents() throws IOException;
}
