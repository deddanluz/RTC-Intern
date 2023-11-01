/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package builders;

import services.StudentService;
import interfaces.Command;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


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
    public Command create(String command) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException{
        Class cls = SS.new CommandProvider().commandProvider(command);
        Constructor cnstr = cls.getConstructor();
        Command cmd = (Command) cnstr.newInstance();
        cmd.setDataLoader(SS.getDataLoader());
        return cmd;
    }
}
