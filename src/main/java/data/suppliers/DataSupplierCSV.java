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

/**
 *
 * @author Даниил
 */
public class DataSupplierCSV implements DataLoader{
    private final BufferedReader READER;                                        //буффер чтения
    private Person[] persons = new Person[10000];                               //несортированный массив
    private int lastNNIndex=0;                                                  //последний ненулевой индекс массива
    private String[] headers;                                                   //заголовки
    private int iLine=-1;                                                       //позиция по строке
    
    public DataSupplierCSV(String file) throws FileNotFoundException{
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
    public Person[] getPerson() throws IOException{
        String[] line;
        //получаем все строки с данными
        while ((line = getNextData()) != null) {
            //увеличиваем размер
            if (iLine>=persons.length){
                persons = Arrays.copyOf(persons, persons.length+5000);
            }
            //если строка с заголовкам прочитана
            if (iLine>0){
                //добавляем ученика
                persons[lastNNIndex]=new Person (line[0], line[1], line[2], line[3], Arrays.copyOfRange(line, 4, line.length));
                //увеличиваем индекс
                lastNNIndex=lastNNIndex+1;
            }else{
                //иначе сохраняем заголовки
                headers = line;
            }
	}
        //взвращаем после чтения и при повторных обращениях
        return Arrays.copyOf(persons, lastNNIndex);
    }
}
