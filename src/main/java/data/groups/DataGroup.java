/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data.groups;

import objects.Student;
import interfaces.GroupCriterion;
import java.util.Arrays;

/**
 *
 * @author Даниил
 */
public class DataGroup {
    private final GroupCriterion CRITERION;                                             //реализация критерия
    private Student[][] students = new Student[12][];                                   //СОРТИРОВАННЫЙ массив учеников
    private int[] lastNNIndex=new int[12];                                              //последние ненулевые индексы
    private final char[] ALPHABET = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ".toCharArray();  //алфавит
    
    //получаем реализацию критерия
    public DataGroup(GroupCriterion criterion) {
        CRITERION = criterion;
    }
    //добавляем и сортируем учеников
    public void addStudents(Student[] st) {
        //для каждого ученика
        for (Student student : st){
            //получаем критерий
            Object criterion = CRITERION.defineAGroup(student);
            int key=-1;
            //вычисляем индекс массива
            if (criterion instanceof Character){
                //если по первой букве фамилии
                key = Arrays.binarySearch(ALPHABET, student.getPerson().getFamily().charAt(0));
            }else if (criterion instanceof Integer){
                //если по классу или возрасту
                key = (int) criterion-1;
            }
            //если индекс больше длины массива
            if (key>=students.length){
                //увеличиваем
                students=Arrays.copyOf(students, students.length+(key+1-students.length));
                lastNNIndex=Arrays.copyOf(lastNNIndex, lastNNIndex.length+(key+1-lastNNIndex.length));
            }
            //если одномерный массив не инициализирован
            if (students[key]==null){
                //иницализируем
                students[key]=new Student[5000];
            }else if (lastNNIndex[key]>=students[key].length){
                //если массив полностью заполнен - увеличиваем размер
                //сложность Arrays.copyOf О(n). поэтому неоходимо минимизировать его использование
                //для этого берем достаточно большую добавочную ступень (100%)
                //а при выдаче еще раз используем, отсекая пустые поля
                //всего за время работы используем Arrays.copyOf около 2-5 раз
                students[key]=Arrays.copyOf(students[key], students[key].length+5000);
            }
            //сохраняем ссылку
            students[key][lastNNIndex[key]]=student;
            //увеличиваем индекс
            lastNNIndex[key]=lastNNIndex[key]+1;
        }
    }
    //получаем отсортированных учеников
    public Student[] getStudents(Object key){
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
            return Arrays.copyOf(students[index], lastNNIndex[index]);
        }catch (ArrayIndexOutOfBoundsException | NullPointerException exc){
            return null;    
        }
    }
}
