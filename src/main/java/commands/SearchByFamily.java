/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package commands;

import data.groups.DataGroup;
import objects.Person;
import interfaces.Command;
import java.io.IOException;
import java.util.Arrays;
/**
 *
 * @author Даниил
 */
public class SearchByFamily implements Command{
    //поиск по фамилии
    private final String FAMILY;
    private final DataGroup DG;
    
    public SearchByFamily(DataGroup dg, String family){
        DG=dg;
        FAMILY=family;
    }        

    @Override
    public Object execute() throws IOException{
        //получаем всех учеников с фамилией
        Person[] persons = DG.getPersons(""+FAMILY.charAt(0)),
                 search=new Person[10];
        int currentIndex=0;
        for (Person person: persons){
            //проверяем на точное совпадение
            if (person.getFamily().equals(FAMILY)){
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
}



