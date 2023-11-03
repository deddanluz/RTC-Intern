/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package commands;

import interfaces.Command;
import interfaces.DataLoader;
import java.io.IOException;
import services.JDBCStorageService;

/**
 *
 * @author Даниил
 */
public class UploadByPgSQL implements Command {
    private DataLoader dl;
    @Override
    public Object execute() throws IOException { 
        JDBCStorageService jdbc = new JDBCStorageService();
        jdbc.add(dl);
        return jdbc;
    }

    @Override
    public void setDataLoader(DataLoader dl) {
        this.dl=dl;
    }
    
}
