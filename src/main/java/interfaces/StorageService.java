/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import java.util.List;
import objects.Student;

/**
 *
 * @author Даниил
 */
public interface StorageService <T> {
    void add(DataLoader dl);
    
    List<Student> list(T t);
}
