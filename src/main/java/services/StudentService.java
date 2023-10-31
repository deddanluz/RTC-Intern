/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import interfaces.DataLoader;

/**
 *
 * @author Даниил
 */
public class StudentService {
    private final DataLoader DL;                                                //поставщик данных
    
    //получаем поставщика данных
    public StudentService(DataLoader dl){
        DL = dl;
    }

    public DataLoader getDataLoader(){
        return DL;
    }
}
