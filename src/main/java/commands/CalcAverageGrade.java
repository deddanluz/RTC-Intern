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
        Command cmd = new UploadbyGroup();
        cmd.setDataLoader(dl);
        DataGroup dg = (DataGroup) cmd.execute();
        //получаем учеников 10 и 11 классов
        Person[][] personSH = {dg.getPersons(10),
                               dg.getPersons(11)};
        double sum=0;
        //суммируем все оценки у всех учеников
        for (Person[] persons : personSH){
            for (Person person : persons){
                int[] grades = person.getGrades();
                for(int grade: grades){
                    sum=sum+grade;
                }
            }
        }
        //делим на общее кол-во оценок и получаем среднее
        return ""+sum/((personSH[0].length+personSH[1].length)*personSH[0][0].getGrades().length);
    }
    
    @Override
    public void setDataLoader(DataLoader dl) {
        this.dl = dl;
    }
}
