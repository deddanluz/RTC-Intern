/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ded_technologies.rtc.intern;

/**
 *
 * @author Даниил
 */
public class CommandBuilder {
    private final StudentService SS;                                            //бизнес-логика
    //получаем объект
    public CommandBuilder(StudentService ss){
        SS = ss;
    }
    //вычислить ср. оценку старших классов
    public Command calcAverageGrade(){
        Command calcAverageGrade = ()->{
            SS.uploadbyGroup();
            return SS.calcAverageGrade();//double
        };
        return calcAverageGrade;
    }
    //вычислить отличников старше 14 лет
    public Command calcHonorsPerson(){
        Command calcHonorsPerson = ()->{
            SS.uploadbyAge();
            return SS.calcHonorsPerson();//Person[]
        };
        return calcHonorsPerson;
    }
    //поиск по фамилии
    public Command searchByFamily(String family){
        Command searchByFamily = ()->{
            SS.uploadByFirstLetter();
            return SS.searchByFamily(family);//Person[]
        };
        return searchByFamily;
    }
}
