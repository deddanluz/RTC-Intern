/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package ded_technologies.rtc.intern;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Даниил
 */
public class RtcIntern {
    private DataSupplier ds;                                                    //поставщик данных
    private ClassroomDataGroups cdg = new ClassroomDataGroups();                //сортировка по классу
    private PersonAgeDataGroups padg = new PersonAgeDataGroups();               //сортировка по возрасту
    private PersonNameDataGroup pndg = new PersonNameDataGroup();               //сортировка по фамилии
    private String[] headers;                                                   //заголовки таблицы
    
    public RtcIntern() throws FileNotFoundException, IOException{
        ds = new DataSupplier("D:\\YandexDisk\\ARCHIVE\\ДОКУМЕНТЫ\\RTC\\students.csv"); //путь к файлу с данными
        headers = ds.getNextData();                                                         //получаем заголовки (1 строка)
    }
    
    public static void main(String[] args) {
        try {
            RtcIntern rtci = new RtcIntern();
            //выполняем сортировки
            rtci.sorting();
            //выводим все, что требуется
            System.out.println("Средняя оценка 10 и 11 классов: "+rtci.calcAverageGrade());
            System.out.println("Отличники страше 14 лет: ");
            System.out.println("Фамилия\tИмя\tВозраст\tКласс");
            Person[] persons = rtci.calcHonorsPerson();
            for (Person person:persons){
                System.out.println(person.getFamily()+"\t"+person.getName()+"\t"+person.getAge()+"\t"+person.getGroup());
            }
            System.out.println("Выберите кодировку:\n1-windows-1251 (ide)\n2-cp866 (cmd)");
            Scanner in = new Scanner(System.in);
            int code = in.nextInt();
            System.out.print("Поиск по фамилии (введите фамилию): ");
            //запрашиваем фамилию
            in = new Scanner(System.in, code==1 ? "windows-1251" : "cp866");
            String family = in.next();
            persons = rtci.searchByFamily(family);
            if (persons[0]!=null){
                //если хотя бы одна фамилия была найдена
                System.out.println("Фамилия\tИмя\tВозраст\tКласс");
                for (Person person:persons){
                    System.out.println(person.getFamily()+"\t"+person.getName()+"\t"+person.getAge()+"\t"+person.getGroup());
                }
            }else{
                //ничего не нашлось
                System.out.println("Не надено ни одного ученика!");
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RtcIntern.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RtcIntern.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
    //сортировка
    private void sorting() throws IOException{
        String[] line;
        //получаем строки с данными от поставщика
        while((line=ds.getNextData())!=null){
            //создаем объект ученика
            Person person = new Person (line[0], line[1], line[2], line[3], Arrays.copyOfRange(line, 4, line.length));
            cdg.addPerson(person);  //сортировка по классу
            padg.addPerson(person); //сортировка по возрасту
            pndg.addPerson(person); //сортировка по фамилии
        }
    }
    /*
        в задачах приходится идти по всем элементам массива, так как стоит задача сложить все оценки и получить все фамилии
        минимизируем его использование
        используем перебор с О(n) во время работы около 3-х раз
    */
    //счиаем среднюю оценку в 10 и 11 классах
    private double calcAverageGrade(){
        //получаем учеников 10 и 11 классов
        Person[][] personSH = {cdg.getPersons(10),
                               cdg.getPersons(11)};
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
    private Person[] calcHonorsPerson(){
        Person[] persons,
                 honors=new Person[1];
        int age=15,
            currentIndex=0;
        //получаем учеников старше 14 лет
        while((persons=padg.getPersons(age))!=null){
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
    private Person[] searchByFamily(String family){
        //получаем всех учеников с фамилией
        Person[] persons = pndg.getPersons(""+family.charAt(0)),
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
