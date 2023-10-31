/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ded_technologies.rtc.intern;

import builders.CommandBuilder;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import services.StudentService;

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
                System.out.println(getHelp());
                break;
            case "calcAverageGrade":
                CB = new CommandBuilder(null); //new StudentService(new DataSupplierCSV(path))
                //средняя оценка 10-11 классов
                System.out.println("Средняя оценка 10 и 11 классов: "+CB.calcAverageGrade());
                break;
            case "calcHonorsPerson":
                CB = new CommandBuilder(null); //new StudentService(new DataSupplierCSV(path))
                //отличники старше 14 лет
                System.out.println("Отличники страше 14 лет: ");
                objects.Person[] persons = (objects.Person[]) CB.calcHonorsPerson();
                System.out.println("Фамилия\tИмя\tВозраст\tКласс");
                for (objects.Person person:persons){
                    System.out.println(person.getFamily()+"\t"+person.getName()+"\t"+person.getAge()+"\t"+person.getGroup());
                }
                break;
            case "searchByFamily":
                CB = new CommandBuilder(null); //new StudentService(new DataSupplierCSV(path))
                //поиск по фамилии
                persons = (objects.Person[]) CB.searchByFamily(family);
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
    
    private String getHelp(){
            StringBuilder area;
            try(BufferedReader buff = new BufferedReader(new FileReader("help.txt"))){
                String line;
                area = new StringBuilder();
                while ((line=buff.readLine())!=null){
                    area.append(line);
                }
                return area.toString();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(StudentService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StudentService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
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
