/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import commands.CalcAverageGrade;
import commands.CalcHonorsPerson;
import commands.SearchByFamily;
import interfaces.Command;
import interfaces.DataLoader;

/**
 *
 * @author Даниил
 */
public class StudentService {
    private final DataLoader DL;
    //получаем поставщика данных
    public StudentService(DataLoader dl){
        DL = dl;
    }
    
    public DataLoader getDataLoader(){
        return DL;
    }
    
    public class CommandProvider{
        private final Class[] providers = {CalcAverageGrade.class,
             CalcHonorsPerson.class,
             SearchByFamily.class};
        //Текущий (по умолчанию) поставщик команд
        public Class<Command> commandProvider() throws ClassNotFoundException {
            return commandProvider("help");
        }
        //Поставщик комманд по имени
        public Class<Command> commandProvider(String providerName) throws ClassNotFoundException {
            for (Class provider: providers){
                if (provider.getName().split("\\.")[1].toLowerCase().equals(providerName.toLowerCase())){
                    return provider;
                }
            }
            throw new ClassNotFoundException(providerName+" not found!");
        }
    }
}
