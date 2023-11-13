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
import objects.Group;
import objects.Student;
import services.JDBCStorageService;

/**
 *
 * @author Даниил
 */
public class CalcAverageGradeFromPgSQL implements Command {

    @Override
    public Object execute() throws IOException {
        StorageService <Group> jdbc = new JDBCStorageService();
        Group group10 = new Group();
        group10.setGroup(10);
        List<Student> list10 = jdbc.list(group10);
        Group group11 = new Group();
        group11.setGroup(10);
        List<Student> list11 = jdbc.list(group11);
        list10.addAll(list11);
        double sum=0;
        //суммируем все оценки у всех учеников
        for (Student student : list11){
            sum=sum+student.getAverageGrade();
        }
        //делим на общее кол-во оценок и получаем среднее
        return ""+sum/list11.size();
    }

    @Override
    public void setDataLoader(DataLoader dl) {
        
    }
    
}
