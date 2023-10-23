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
public class PersonNameDataGroup {
    private Person[][] namePersons = new Person[16][];                          //массив учеников отсортированных по возрасту
    private int[] lastNNIndex=new int[16];                                      //последний ненулевой индекс каждого одномерного массива
    private char[] alphabet = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ".toCharArray();//алфавит
    //сохраняем по индексам массива, а после просто получаем доступ к элементу массива сложность O(1)
    public void addPerson(Person person) {
        //получаем индекс первой буквы фамилии в алфавите
        //используем бинарный поиск а не перебор, т.к. у бинарного О(log(n))< O(n) у перебора
        int currentIndex = Arrays.binarySearch(alphabet, person.getFamily().charAt(0));
        //если индекс больше длины массива
        if (currentIndex>=namePersons.length){
            namePersons=Arrays.copyOf(namePersons, namePersons.length+(currentIndex+1-namePersons.length));
            lastNNIndex=Arrays.copyOf(lastNNIndex, lastNNIndex.length+(currentIndex+1-lastNNIndex.length));
        }
        //если одномерный массив не инициализирован
        if (namePersons[currentIndex]==null){
            //иницализируем
            namePersons[currentIndex]=new Person[5000];
        }else if (lastNNIndex[currentIndex]>=namePersons[currentIndex].length){
            //если массив полностью заполнен - увеличиваем размер
            //сложность Arrays.copyOf О(n). поэтому неоходимо минимизировать его использование
            //для этого берем достаточно большую добавочную ступень (100%)
            //а при выдаче еще раз используем, отсекая пустые поля
            //всего за время работы используем Arrays.copyOf около 2-5 раз
            namePersons[currentIndex]=Arrays.copyOf(namePersons[currentIndex], namePersons[currentIndex].length+5000);
        }
        //сохраняем ссылку
        namePersons[currentIndex][lastNNIndex[currentIndex]]=person;
        //увеличиваем индекс
        lastNNIndex[currentIndex]=lastNNIndex[currentIndex]+1;
    }
    // возвращает студентов с фамилией, начинающейся на указанную букву
    public Person[] getPersons(String firstlLetter) {
        //при вводе несущ. фамилии
        try{
            //выводим в соответсвии с кол-вом ненулевых значений
            //используем бинарный поиск а не перебор, т.к. у бинарного О(log(n))< O(n) у перебора
            int index = Arrays.binarySearch(alphabet, firstlLetter.charAt(0));
            //доступ к элементу массива сложность O(1)
            //при выдаче еще раз используем Arrays.copyOf О(n), отсекая пустые поля
            return Arrays.copyOf(namePersons[index], lastNNIndex[index]);
        }catch (ArrayIndexOutOfBoundsException exc){
            return null;    
        }
    }
}
