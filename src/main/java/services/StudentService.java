/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import interfaces.Command;
import interfaces.DataLoader;

/**
 *
 * @author Даниил
 */
public class StudentService {
    private final Class[] providers = {commands.CalcAverageGrade.class,         //перечисляем все реализации интерфейса Command
                commands.CalcHonorsPerson.class,
                commands.SearchByFamily.class,
                commands.UploadByFirstLetter.class,
                commands.UploadbyAge.class,        
                commands.UploadbyGroup.class,
                commands.Help.class,
                commands.UploadByPgSQL.class
        };
    private final DataLoader DL;                                                //поставщик данных
    //получаем поставщика данных
    public StudentService(DataLoader dl){
        DL = dl;
    }
    //возвращаем текущую реализацию поставщика
    public DataLoader dataLoaderProvider(){
        return DL;
    }
    //поставщик комманд по имени
    public Class<Command> commandProvider(String providerName) throws ClassNotFoundException {
        for (Class provider: providers){
            if (provider.getName().split("\\.")[1].toLowerCase().equals(providerName.toLowerCase())){
                return provider;
            }
        }
        throw new ClassNotFoundException(providerName+" not found!");
    }
}
