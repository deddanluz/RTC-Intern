/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package commands;

import interfaces.Command;
import interfaces.DataLoader;
import interfaces.StorageService;
import java.io.IOException;
import java.util.List;
import objects.Student;
import services.JDBCStorageService;

/**
 *
 * @author Даниил
 */
public class CalcHonorsPersonFromPgSQL implements Command {

    @Override
    public Object execute() throws IOException {
        StorageService <Integer> jdbc = new JDBCStorageService();
        //получаем учеников старше 14 лет
        int age=15;
        List<Student> students;
        StringBuilder output = new StringBuilder();
        while(!(students=jdbc.list(age)).isEmpty()){
            age=age+1;
            for (Student student : students){
                double grade = student.getAverageGrade();
                if (grade==5){
                    output.append(student.getPerson().getFamily()).append("\t").append(student.getPerson().getName()).append("\t")
                    .append(student.getGroup().getGroup()).append("\t").append(student.getAverageGrade()).append("\n");
                }
            }
        }
        return output.toString();
        
    }

    @Override
    public void setDataLoader(DataLoader dl) {
        
    }
    
}
