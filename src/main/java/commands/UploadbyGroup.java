/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package commands;

import data.groups.DataGroup;
import interfaces.Command;
import interfaces.DataLoader;
import interfaces.GroupCriterion;
import java.io.IOException;

/**
 *
 * @author Даниил
 */
public class UploadbyGroup implements Command {
    private final DataLoader DL;
    
    public UploadbyGroup(DataLoader dl){
        DL =dl;
    }
    
    @Override
    public Object execute() throws IOException{
        //сортировка по классу
        GroupCriterion classroomCriterion = p -> p.getGroup();
        DataGroup dg = new DataGroup(classroomCriterion);
        dg.addPersons(DL.getPerson());
        return dg;
    }
}
