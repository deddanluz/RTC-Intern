/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package commands;

import data.groups.DataGroup;
import objects.Student;
import ded_technologies.rtc.intern.RTCIntern;
import interfaces.Command;
import interfaces.DataLoader;
import java.io.IOException;
import java.util.Arrays;
/**
 *
 * @author Даниил
 */
public class SearchByFamily implements Command{
    //поиск по фамилии
    private DataLoader dl;       

    @Override
    public String execute() throws IOException{
        //загружаем с сортировкой по первой букве фамилии
        Command <DataGroup> cmd = new UploadByFirstLetter();
        cmd.setDataLoader(dl);
        DataGroup dg = cmd.execute();
        //получаем всех учеников с фамилией
        Student[] students = dg.getStudents(""+RTCIntern.family.charAt(0)),
                 search=new Student[10];
        int currentIndex=0;
        for (Student student: students){
            //проверяем на точное совпадение
            if (student.getPerson().getFamily().equals(RTCIntern.family)){
                if (currentIndex>=search.length){
                    search = Arrays.copyOf(search, search.length+1);
                }
                //сохраняем отдельно
                search[currentIndex]=student;
                currentIndex++;
            }
        }
        StringBuilder output = new StringBuilder();
        for (Student student : search){
            output.append(student.getPerson().getFamily()).append("\t").append(student.getPerson().getName()).append("\t")
                    .append(student.getPerson().getAge()).append("\t").append(student.getGroup().getGroup()).append("\n");
                    
        }
        return output.toString();
    }
    
    @Override
    public void setDataLoader(DataLoader dl) {
        this.dl = dl;
    }
}



