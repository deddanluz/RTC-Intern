/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package ded_technologies.rtc.intern;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Даниил
 */
public class RtcIntern {
    private static CommandBuilder CB;                                           //класс команд
    public RtcIntern() throws FileNotFoundException{
        //инициализируем
        CB = new CommandBuilder(new StudentService(new DataSupplierCSV("D:\\YandexDisk\\ARCHIVE\\ДОКУМЕНТЫ\\RTC\\students.csv")));
    }
    
    public static void main(String[] args) {
        try {
            RtcIntern rtci = new RtcIntern();
            //выводим все, что требуется
            System.out.println("Средняя оценка 10 и 11 классов: "+CB.calcAverageGrade().execute());
            System.out.println("Отличники страше 14 лет: ");
            Person[] persons = (Person[]) CB.calcHonorsPerson().execute();
            System.out.println("Фамилия\tИмя\tВозраст\tКласс");
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
            persons = (Person[]) CB.searchByFamily(family).execute();
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
}
