/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package commands;

import data.groups.DataGroup;
import interfaces.Command;
import interfaces.DataLoader;
import java.io.IOException;
import java.util.Arrays;
import objects.Grade;
import objects.Student;

/**
 *
 * @author Даниил
 */
public class CalcHonorsPerson implements Command{
    private DataLoader dl;
    //вычислить отличников старше 14 лет
    @Override
    public String execute() throws IOException{
        //загружаем с сортировкой по возрасту
        Command cmd = new UploadbyAge();
        cmd.setDataLoader(dl);
        DataGroup dg = (DataGroup) cmd.execute();
        Student[] students,
                 honors=new Student[1];
        int age=15,
            currentIndex=0;
        //получаем учеников старше 14 лет
        while((students=dg.getStudents(age))!=null){
            age=age+1;
            for (Student student : students){
                Grade[] grades = student.getGrades();
                double sum=0;
                //суммируем оценки
                for(Grade grade: grades){
                    sum=sum+grade.getGrade();
                }
                //отличник?
                if (sum/grades.length==5){
                    //если массив заполнен
                    if (currentIndex>=honors.length){
                        //увеличиваем размер массива
                        honors = Arrays.copyOf(honors, honors.length+1);
                    }
                    //сохраняем отдельно
                    honors[currentIndex]=student;
                    currentIndex++;
                }
            }
        }
        StringBuilder output = new StringBuilder();
        for (Student student : honors){
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
