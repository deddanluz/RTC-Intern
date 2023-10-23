/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ded_technologies.rtc.intern;

import java.util.Arrays;

/**
 *
 * @author Даниил
 */
public class ClassroomDataGroups {
    private Person[][] classroomPersons = new Person[12][];                     //массив учеников отсортированных по классу
    private int[] lastNNIndex=new int[12];                                      //последний ненулевой индекс каждого одномерного массива
    //сохраняем по индексам массива, а после просто получаем доступ к элементу массива сложность O(1)
    public void addPerson(Person person){
        //если одномерный массив не инициализирован
        if (classroomPersons[person.getGroup()-1]==null){
            //иницализируем
            classroomPersons[person.getGroup()-1]=new Person[5000];
        }else if (lastNNIndex[person.getGroup()-1]>=classroomPersons[person.getGroup()-1].length){
            //если массив полностью заполнен - увеличиваем размер
            //сложность Arrays.copyOf О(n). поэтому неоходимо минимизировать его использование
            //для этого берем достаточно большую добавочную ступень (100%)
            //а при выдаче еще раз используем, отсекая пустые поля
            //всего за время работы используем Arrays.copyOf около 2-5 раз
            classroomPersons[person.getGroup()-1]=Arrays.copyOf(classroomPersons[person.getGroup()-1], classroomPersons[person.getGroup()-1].length+5000);
        }
        //сохраняем ссылку
        classroomPersons[person.getGroup()-1][lastNNIndex[person.getGroup()-1]]=person;
        //увеличиваем индекс
        lastNNIndex[person.getGroup()-1]=lastNNIndex[person.getGroup()-1]+1;
    }
    
    public Person[] getPersons(int groupNum) {
        //выводим в соответсвии с кол-вом ненулевых значений
        //доступ к элементу массива сложность O(1)
        //при выдаче еще раз используем Arrays.copyOf О(n), отсекая пустые поля
        return Arrays.copyOf(classroomPersons[groupNum-1], lastNNIndex[groupNum-1]);
    }
}
