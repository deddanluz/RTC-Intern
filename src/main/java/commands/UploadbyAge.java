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
public class UploadbyAge implements Command {
    private DataLoader dl;
    
    @Override
    public Object execute() throws IOException{   
        //сортировка по возрасту
        GroupCriterion <Integer> ageCriterion = s -> s.getPerson().getAge();
        DataGroup dg = new DataGroup(ageCriterion);
        dg.addStudents(dl.getStudents());
        return dg;
    }
    
    @Override
    public void setDataLoader(DataLoader dl) {
        this.dl = dl;
    }
}
