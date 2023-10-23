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
public class PersonAgeDataGroups {
    private Person[][] agePersons = new Person[12][];                           //массив учеников отсортированных по возрасту
    private int[] lastNNIndex=new int[12];                                      //последний ненулевой индекс каждого одномерного массива
    //сохраняем по индексам массива, а после просто получаем доступ к элементу массива сложность O(1)
    public void addPerson(Person person){
        int currentIndex=person.getAge()-1;
        //если индекс больше длины массива
        if (currentIndex>=agePersons.length){
            agePersons=Arrays.copyOf(agePersons, agePersons.length+(currentIndex+1-agePersons.length));
            lastNNIndex=Arrays.copyOf(lastNNIndex, lastNNIndex.length+(currentIndex+1-lastNNIndex.length));
        }
        //если одномерный массив не инициализирован
        if (agePersons[currentIndex]==null){
            //иницализируем
            agePersons[currentIndex]=new Person[5000];
        }else if (lastNNIndex[currentIndex]>=agePersons[currentIndex].length){
            //если массив полностью заполнен - увеличиваем размер
            //сложность Arrays.copyOf О(n). поэтому неоходимо минимизировать его использование
            //для этого берем достаточно большую добавочную ступень (100%)
            //а при выдаче еще раз используем, отсекая пустые поля
            //всего за время работы используем Arrays.copyOf около 2-5 раз
            agePersons[currentIndex]=Arrays.copyOf(agePersons[currentIndex], agePersons[currentIndex].length+5000);
        }
        //сохраняем ссылку
        agePersons[currentIndex][lastNNIndex[currentIndex]]=person;
        //увеличиваем индекс
        lastNNIndex[currentIndex]=lastNNIndex[currentIndex]+1;
    }
    
    public Person[] getPersons(int age) {
        //выводим в соответсвии с кол-вом ненулевых значений
        //доступ к элементу массива сложность O(1)
        //при выдаче еще раз используем Arrays.copyOf О(n), отсекая пустые поля
        try {
            return Arrays.copyOf(agePersons[age-1], lastNNIndex[age-1]);
        } catch (ArrayIndexOutOfBoundsException e){
            return null;
        }
    }
}
