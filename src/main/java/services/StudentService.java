/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import interfaces.Command;
import interfaces.DataLoader;
import java.nio.file.ProviderNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

/**
 *
 * @author Даниил
 */
public class StudentService {
    private final DataLoader DL;                                                //поставщик данных
    private final String CURRENT_COMMAND_PROVIDER;
    //получаем поставщика данных
    public StudentService(DataLoader dl){
        DL = dl;
        CURRENT_COMMAND_PROVIDER="com.ded_technologies.commands.Help";
    }
    
    public DataLoader getDataLoader(){
        return DL;
    }
    
    public class CommandProvider{
        //Все поставщики комманд
        public List<Command> commandProviders() {
            List<Command> services = new ArrayList<>();
            ServiceLoader<Command> loader = ServiceLoader.load(Command.class);
            loader.forEach(services::add);
            return services;
        }
        //Текущий (по умолчанию) поставщик данных
        public Command commandProvider() {
            return commandProvider(CURRENT_COMMAND_PROVIDER);
        }
        //Поставщик комманд по имени
        public Command commandProvider(String providerName) {
            ServiceLoader<Command> loader = ServiceLoader.load(Command.class);
            Iterator<Command> it = loader.iterator();
            while (it.hasNext()) {
                Command provider = it.next();
                if (providerName.toLowerCase().equals(provider.getClass().getName().toLowerCase())) {
                    return provider;
                }
            }
            throw new ProviderNotFoundException("Exchange Rate provider " + providerName + " not found");
        }
    }
}
