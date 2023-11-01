/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package commands;

import data.groups.DataGroup;
import objects.Person;
import interfaces.Command;
import interfaces.DataLoader;
import java.io.IOException;
import java.util.Arrays;

/**
 *
 * @author Даниил
 */
public class CalcHonorsPerson implements Command{
    private DataLoader dl;
    
    //вычислить отличников старше 14 лет
    @Override
    public Object execute() throws IOException{
        //загружаем с сортировкой по возрасту
        Command cmd = new UploadbyAge();
        cmd.setDataLoader(dl);
        DataGroup dg = (DataGroup) cmd.execute();
        Person[] persons,
                 honors=new Person[1];
        int age=15,
            currentIndex=0;
        //получаем учеников старше 14 лет
        while((persons=dg.getPersons(age))!=null){
            age=age+1;
            for (Person person : persons){
                int[] grades = person.getGrades();
                double sum=0;
                //суммируем оценки
                for(int grade: grades){
                    sum=sum+grade;
                }
                //отличник?
                if (sum/grades.length==5){
                    //если массив заполнен
                    if (currentIndex>=honors.length){
                        //увеличиваем размер массива
                        honors = Arrays.copyOf(honors, honors.length+1);
                    }
                    //сохраняем отдельно
                    honors[currentIndex]=person;
                    currentIndex++;
                }
            }
        }
        return honors;
    }
    
    @Override
    public void setDataLoader(DataLoader dl) {
        this.dl = dl;
    }
}
