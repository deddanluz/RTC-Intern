/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ded_technologies.rtc.intern;

import java.io.IOException;
import java.util.Arrays;

/**
 *
 * @author Даниил
 */
public class StudentService {
    private final DataLoader DL;                                                //поставщик данных
    private DataGroup dg;                                                       //сортировка
    //получаем поставщика данных
    public StudentService(DataLoader dl){
        DL = dl;
    }
    
    public void uploadbyGroup() throws IOException{
        //сортировка по классу
        GroupCriterion classroomCriterion = p -> p.getGroup();
        dg = new DataGroup(classroomCriterion);
        dg.addPersons(DL.getPerson());
    }
    
    public void uploadbyAge() throws IOException{   
        //сортировка по возрасту
        GroupCriterion ageCriterion = p -> p.getAge();
        dg = new DataGroup(ageCriterion);
        dg.addPersons(DL.getPerson());
    }
    
    public void uploadByFirstLetter() throws IOException{
        //сортировка по первой букве фамилии
        GroupCriterion firstLetterCriterion = p -> p.getFamily().charAt(0);
        dg = new DataGroup(firstLetterCriterion);
        dg.addPersons(DL.getPerson());
    }
    /*
        в задачах приходится идти по всем элементам массива, так как стоит задача сложить все оценки и получить все фамилии
        минимизируем его использование
        используем перебор с О(n) во время работы около 3-х раз
    */
    //счиаем среднюю оценку в 10 и 11 классах
    public double calcAverageGrade(){
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
        return sum/((personSH[0].length+personSH[1].length)*personSH[0][0].getGrades().length);
    }
    //вычисляем отличников старше 14 лет
    public Person[] calcHonorsPerson(){
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
    //поиск по фамилии
    public Person[] searchByFamily(String family){
        //получаем всех учеников с фамилией
        Person[] persons = dg.getPersons(""+family.charAt(0)),
                 search=new Person[10];
        int currentIndex=0;
        for (Person person: persons){
            //проверяем на точное совпадение
            if (person.getFamily().equals(family)){
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
