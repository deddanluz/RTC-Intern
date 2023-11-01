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
//calcAverageGrade -p "D:\YandexDisk\ARCHIVE\ДОКУМЕНТЫ\RTC\students.csv" -f Попов
/**
 *
 * @author Даниил
 */
public class RTCIntern {
    private static CommandBuilder CB;                                           //класс команд
    private static String path;
    public static String family;                                                //фамилия для поиска
    
    public static void main(String[] args) {
        try {
            RTCIntern rtci = new RTCIntern();
            rtci.getParams(args);
            CB = new CommandBuilder(new StudentService(new DataSupplierCSV(path)));
            rtci.view(args[0]);
        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException ex) {
            Logger.getLogger(RTCIntern.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ArrayIndexOutOfBoundsException exp){
            Logger.getLogger(RTCIntern.class.getName()).log(Level.SEVERE, "Команда не указана! Введите 'help' для просмотра списка команд.", exp);
        } catch (NullPointerException exp){
            if (!args[0].toLowerCase().equals("help")){
                Logger.getLogger(RTCIntern.class.getName()).log(Level.SEVERE, "Не указаны обязательные аргументы! Введите 'help' для просмотра списка команд.", exp);
            }else{
                //при запросе 'help' вызываем команду напрямую
                //т.к. не можем создать DataLoader, потому что
                //Поставщик помощи отличается от поставщика персон
                System.out.println(new commands.Help().execute());
            }
        }
    }
    //метод вывода в консоль
    private void view(String command) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException{
        //получаем результат команды
        Object output = CB.create(command).execute();
        //печатаем
        System.out.println(output);
    }
    //парсим аргументы консоли
    private void getParams(String[] args) throws FileNotFoundException{
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
