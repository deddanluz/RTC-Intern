/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package commands;

import interfaces.Command;
import interfaces.DataLoader;

/**
 *
 * @author Даниил
 */ 
public class Help implements Command{
    @Override
    public Object execute(){
        return "Список команд:\nhelp - получение списка команд и полной информации о том,"
                + "как вводить данные для получения правильного результата.\ncalcAverageGrade - вычисляет среднюю оценку в 10-11 классах на основе информации, полученной из файла формата csv."
                + "\ncalcHonorsPerson - получает учеников старше 14 лет на основе информации, полученной из файла CSV."
                + "\nsearchByFamily - поиск по фамилии по данным, полученным из CSV-файла."
                + "\nuploadByPgSQL - загрузка из CSV в PostgreSQL."
                +"\nsearchByFamilyFromPgSQL - поиск по фамилии в базе данных."
                +"\ncalcAverageGradeFromPgSQL - получить среднюю оценку в 10-11 классах."
                +"\ncalcHonorsPersonFromPgSQL - получить отличников старше 14 лет."
                + "\nОбщий вид: java -jar rtc-intern-X.X-SNAPSHOT.jar calcAverageGrade -p students.csv"
                + "\njava -jar rtc-intern-X.X-SNAPSHOT.jar     command      -p     path"
                + "\nАргументы:"
                + "\n-p path"
                + "\nпуть к файлу с данными по ученикам.\nНапример: -p students.csv"
                + "\n-f family"
                + "\nфамилия для поиска при вызове команды searchByFamily."
                + "\nНапример: -f Попов"
                + "\nАргументы могут следовать в любом порядке, однако каждый аргумент должен сначала предваряться указателем на аргумент,"
                + "за которым должно следовать его значение. Перед аргументами должно быть указано имя команды.";
    }

    @Override
    public void setDataLoader(DataLoader dl) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
