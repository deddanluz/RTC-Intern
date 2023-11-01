/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package commands;

import data.groups.DataGroup;
import ded_technologies.rtc.intern.RTCIntern;
import objects.Person;
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
    public Object execute() throws IOException{
        //загружаем с сортировкой по первой букве фамилии
        Command cmd = new UploadByFirstLetter();
        cmd.setDataLoader(dl);
        DataGroup dg = (DataGroup) cmd.execute();
        //получаем всех учеников с фамилией
        Person[] persons = dg.getPersons(""+RTCIntern.family.charAt(0)),
                 search=new Person[10];
        int currentIndex=0;
        for (Person person: persons){
            //проверяем на точное совпадение
            if (person.getFamily().equals(RTCIntern.family)){
                if (currentIndex>=search.length){
                    search = Arrays.copyOf(search, search.length+1);
                }
                //сохраняем отдельно
                search[currentIndex]=person;
                currentIndex++;
            }
        }
        return search;
    }
    
    @Override
    public void setDataLoader(DataLoader dl) {
        this.dl = dl;
    }
}



