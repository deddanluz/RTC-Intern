/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ded_technologies.rtc.intern;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Даниил
 */
public class DataSupplier {
    private BufferedReader reader;                                              //буффер чтения
    
    public DataSupplier(String file) throws FileNotFoundException{
        //инициализируем
        reader = new BufferedReader(new FileReader(file));
    }
    //получаем строки с данными в виде массива
    public String[] getNextData() throws IOException {
        try {
            //делим строку на части
            return reader.readLine().split(";");
        } catch (NullPointerException ex) {
            Logger.getLogger(DataSupplier.class.getName()).log(Level.SEVERE, null, ex);
        }
        //когда данные закончились
        return null;
    }
}
