/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import objects.Person;
import java.util.List;

/**
 *
 * @author Даниил
 */
public interface StorageService {
    void add(String [] nameSubjects, Person[] person);
    
    List<Person> list(int group);
}
