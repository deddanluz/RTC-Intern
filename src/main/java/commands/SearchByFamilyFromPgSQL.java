/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package commands;

import ded_technologies.rtc.intern.RTCIntern;
import interfaces.Command;
import interfaces.DataLoader;
import java.io.IOException;
import java.util.List;
import objects.Student;
import services.JDBCStorageService;

/**
 *
 * @author Даниил
 */
public class SearchByFamilyFromPgSQL implements Command {

    @Override
    public Object execute() throws IOException {
        JDBCStorageService jdbc = new JDBCStorageService();
        List<Student> search = jdbc.list(RTCIntern.family);
        StringBuilder output = new StringBuilder();
        for (Student student : search){
            output.append(student.getPerson().getFamily()).append("\t").append(student.getPerson().getName()).append("\t")
                    .append(student.getGroup().getGroup()).append("\t").append(student.getAverageGrade()).append("\n");
        }
        return output.toString();
    }

    @Override
    public void setDataLoader(DataLoader dl) {
        
    }
    
}
