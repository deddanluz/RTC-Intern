/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data.suppliers;

import objects.Person;
import interfaces.DataLoader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import objects.Grade;
import objects.Group;
import objects.Student;

/**
 *
 * @author Даниил
 */
public class DataSupplierCSV implements DataLoader{
    private final BufferedReader READER;                                        //буффер чтения
    private Student[] students = new Student[10000];                               //несортированный массив
    private int lastNNIndex=0;                                                  //последний ненулевой индекс массива
    private String[] headers;                                                   //заголовки
    private int iLine=-1;                                                       //позиция по строке
    
    public DataSupplierCSV(String file) throws FileNotFoundException, NullPointerException{
        //инициализируем
        READER = new BufferedReader(new FileReader(file));
    }
    //получаем строки с данными в виде массива
    private String[] getNextData() throws IOException {
        try {
            iLine++;
            //делим строку на части
            return READER.readLine().split(";");
        } catch (NullPointerException ex) {
            //Logger.getLogger(DataSupplierCSV.class.getName()).log(Level.SEVERE, null, ex);
        }
        //когда данные закончились
        return null;
    }
    
    @Override
    public String[] getHeaders() throws IOException{
        //если заголовки не были прочитаны
        if (iLine==-1){
            //сохраняем
            headers = getNextData();
        }
        //взвращаем после чтения и при повторных обращениях
        return headers;
    }
    
    @Override
    public Student[] getStudents() throws IOException{
        String[] line;
        //получаем все строки с данными
        while ((line = getNextData()) != null) {
            //увеличиваем размер
            if (iLine>=students.length){
                students = Arrays.copyOf(students, students.length+5000);
            }
            //если строка с заголовкам прочитана
            if (iLine>0){
                //добавляем ученика
                Person person = new Person();
                person.setFamily(line[0]);
                person.setName(line[1]);
                person.setAge(line[2]);
                Group group = new Group();
                group.setGroup(line[3]);
                String[] sGrades = Arrays.copyOfRange(line, 4, line.length);
                Grade[] grades=new Grade[sGrades.length];
                for (int i=0; i<grades.length;i++){
                    grades[i] = new Grade();
                    grades[i].setGrade(sGrades[i]);
                }
                students[lastNNIndex]=new Student (person, group, grades);
                //увеличиваем индекс
                lastNNIndex=lastNNIndex+1;
            }else{
                //иначе сохраняем заголовки
                headers = line;
            }
	}
        //взвращаем после чтения и при повторных обращениях
        return Arrays.copyOf(students, lastNNIndex);
    }
}
