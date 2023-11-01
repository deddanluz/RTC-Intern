/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data.groups;

import objects.Person;
import interfaces.GroupCriterion;
import java.util.Arrays;

/**
 *
 * @author Даниил
 */
public class DataGroup {
    private final GroupCriterion CRITERION;                                             //реализация критерия
    private Person[][] persons = new Person[12][];                                      //СОРТИРОВАННЫЙ массив учеников
    private int[] lastNNIndex=new int[12];                                              //последние ненулевые индексы
    private final char[] ALPHABET = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ".toCharArray();  //алфавит
    
    //получаем реализацию критерия
    public DataGroup(GroupCriterion criterion) {
        CRITERION = criterion;
    }
    //добавляем и сортируем учеников
    public void addPersons(Person[] ps) {
        //для каждого ученика
        for (Person person : ps){
            //получаем критерий
            Object criterion = CRITERION.defineAGroup(person);
            int key=-1;
            //вычисляем индекс массива
            if (criterion instanceof Character){
                //если по первой букве фамилии
                key = Arrays.binarySearch(ALPHABET, person.getFamily().charAt(0));
            }else if (criterion instanceof Integer){
                //если по классу или возрасту
                key = (int) criterion-1;
            }
            //если индекс больше длины массива
            if (key>=persons.length){
                //увеличиваем
                persons=Arrays.copyOf(persons, persons.length+(key+1-persons.length));
                lastNNIndex=Arrays.copyOf(lastNNIndex, lastNNIndex.length+(key+1-lastNNIndex.length));
            }
            //если одномерный массив не инициализирован
            if (persons[key]==null){
                //иницализируем
                persons[key]=new Person[5000];
            }else if (lastNNIndex[key]>=persons[key].length){
                //если массив полностью заполнен - увеличиваем размер
                //сложность Arrays.copyOf О(n). поэтому неоходимо минимизировать его использование
                //для этого берем достаточно большую добавочную ступень (100%)
                //а при выдаче еще раз используем, отсекая пустые поля
                //всего за время работы используем Arrays.copyOf около 2-5 раз
                persons[key]=Arrays.copyOf(persons[key], persons[key].length+5000);
            }
            //сохраняем ссылку
            persons[key][lastNNIndex[key]]=person;
            //увеличиваем индекс
            lastNNIndex[key]=lastNNIndex[key]+1;
        }
    }
    //получаем отсортированных учеников
    public Person[] getPersons(Object key){
        //при вводе несущ. фамилии
        try{
            //выводим в соответсвии с кол-вом ненулевых значений
            int index=-1;
            //вычисляем индекс
            if (key instanceof String str){
                //используем бинарный поиск а не перебор, т.к. у бинарного О(log(n))< O(n) у перебора
                //если по первой букве фамилии
                index = Arrays.binarySearch(ALPHABET, str.charAt(0));
            }else if (key instanceof Integer){
                //если по классу или возрасту
                index=(int) key-1;
            }
            //доступ к элементу массива сложность O(1)
            //при выдаче еще раз используем Arrays.copyOf О(n), отсекая пустые поля
            return Arrays.copyOf(persons[index], lastNNIndex[index]);
        }catch (ArrayIndexOutOfBoundsException | NullPointerException exc){
            return null;    
        }
    }
}
