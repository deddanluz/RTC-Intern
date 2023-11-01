/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ded_technologies.rtc.intern;

import builders.CommandBuilder;
import data.suppliers.DataSupplierCSV;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import services.StudentService;

/**
 *
 * @author Даниил
 */
public class RTCIntern {
    private CommandBuilder CB;                                                  //класс команд
    public static String family;                                                //фамилия для поиска
    
    public static void main(String[] args) {
        try {
            RTCIntern rtci = new RTCIntern();
            rtci.getParams(args);
            rtci.view(args[0]);
        } catch (IOException ex) {
            System.out.println("Error: "+ex.getMessage());
        }
    }
    
    private void view(String command) throws IOException{
        try{
            switch (command){
                case "help":
                    System.out.println(CB.create(command).execute());
                    break;
                case "calcAverageGrade":
                    //средняя оценка 10-11 классов
                    System.out.println("Средняя оценка 10 и 11 классов: "+CB.create(command).execute());
                    break;
                case "calcHonorsPerson":
                    //отличники старше 14 лет
                    System.out.println("Отличники страше 14 лет: ");
                    objects.Person[] persons = (objects.Person[]) CB.create(command).execute();
                    System.out.println("Фамилия\tИмя\tВозраст\tКласс");
                    for (objects.Person person:persons){
                        System.out.println(person.getFamily()+"\t"+person.getName()+"\t"+person.getAge()+"\t"+person.getGroup());
                    }
                    break;
                case "searchByFamily":
                    //поиск по фамилии
                    persons = (objects.Person[]) CB.create(command).execute();
                    if (persons[0]!=null){
                        //если хотя бы одна фамилия была найдена
                        System.out.println("Фамилия\tИмя\tВозраст\tКласс");
                        for (objects.Person person:persons){
                            System.out.println(person.getFamily()+"\t"+person.getName()+"\t"+person.getAge()+"\t"+person.getGroup());
                        }
                    }else{
                        //ничего не нашлось
                        System.out.println("Не надено ни одного ученика!");
                    }
                    break;
                default:
                    System.out.println("Error: invalid arguments! Please, input 'help' for command arguments.");
            }
        } catch (ClassNotFoundException e){
            System.out.println("Error: invalid arguments! Please, input 'help' for command arguments.");
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException ex) {
            Logger.getLogger(RTCIntern.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void getParams(String[] args) throws FileNotFoundException{
        for (int i=1; i<args.length;){
            switch (args[i]) {
                case "-p":
                    CB = new CommandBuilder(new StudentService(new DataSupplierCSV(args[i+1])));
                    i=i+2;
                    break;
                case "-f":
                    family = args[i+1];
                    i=i+2;
                    break;
                default:
                    i++;
            }
        }
    }
}
