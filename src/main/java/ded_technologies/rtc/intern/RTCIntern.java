/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ded_technologies.rtc.intern;

import builders.CommandBuilder;
import java.io.IOException;

/**
 *
 * @author Даниил
 */
public class RTCIntern {
    private static CommandBuilder CB;                                           //класс команд
    private String path;                                                        //путь к файлу с данными
    private String family;                                                      //фамилия для поиска
    
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
        switch (command){
            case "help":
                System.out.println(CB.create(command).execute());
                break;
            case "calcAverageGrade":
                CB = new CommandBuilder(null); //new StudentService(new DataSupplierCSV(path))
                //средняя оценка 10-11 классов
                System.out.println("Средняя оценка 10 и 11 классов: "+CB.create(command).execute());
                break;
            case "calcHonorsPerson":
                CB = new CommandBuilder(null); //new StudentService(new DataSupplierCSV(path))
                //отличники старше 14 лет
                System.out.println("Отличники страше 14 лет: ");
                objects.Person[] persons = (objects.Person[]) CB.create(command).execute();
                System.out.println("Фамилия\tИмя\tВозраст\tКласс");
                for (objects.Person person:persons){
                    System.out.println(person.getFamily()+"\t"+person.getName()+"\t"+person.getAge()+"\t"+person.getGroup());
                }
                break;
            case "searchByFamily":
                CB = new CommandBuilder(null); //new StudentService(new DataSupplierCSV(path))
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
    }
    
    private void getParams(String[] args){
        for (int i=1; i<args.length;){
            switch (args[i]) {
                case "-p":
                    path = args[i+1];
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
