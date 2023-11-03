/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package commands;

import data.groups.DataGroup;
import interfaces.Command;
import interfaces.DataLoader;
import java.io.IOException;
import objects.Grade;
import objects.Student;

/**
 *
 * @author Даниил
 */
public class CalcAverageGrade implements Command{
    private DataLoader dl;   
    //вычислить ср. оценку старших классов
    @Override
    public String execute() throws IOException{
        //загружаем с сортировкой по группе
        Command <DataGroup> cmd = new UploadbyGroup();
        cmd.setDataLoader(dl);
        DataGroup dg = cmd.execute();
        //получаем учеников 10 и 11 классов
        Student[][] studentSH = {dg.getStudents(10),
                               dg.getStudents(11)};
        double sum=0;
        //суммируем все оценки у всех учеников
        for (Student[] students : studentSH){
            for (Student student : students){
                Grade[] grades = student.getGrades();
                for(Grade grade: grades){
                    sum=sum+grade.getGrade();
                }
            }
        }
        //делим на общее кол-во оценок и получаем среднее
        return ""+sum/((studentSH[0].length+studentSH[1].length)*studentSH[0][0].getGrades().length);
    }
    
    @Override
    public void setDataLoader(DataLoader dl) {
        this.dl = dl;
    }
}
