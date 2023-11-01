/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package builders;

import services.StudentService;
import interfaces.Command;
import interfaces.DataLoader;
import java.io.IOException;


/**
 *
 * @author Даниил
 */
public class CommandBuilder {
    private final StudentService SS;                                            //бизнес-логика
    //получаем объект
    public CommandBuilder(StudentService ss){
        SS = ss;
    }
    //получаем команду
    public Command create(String command) throws IOException{
        DataLoader dl = SS.getDataLoader();
        Command cmd = SS.new CommandProvider().commandProvider(command);
        return cmd;
    }
}
