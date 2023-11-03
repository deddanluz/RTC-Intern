/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import objects.Student;



/**
 *
 * @author Даниил
 */
@FunctionalInterface
public interface GroupCriterion <T> {
    T defineAGroup(Student student);
}
