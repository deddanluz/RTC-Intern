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
public class CalcAverageGradeFromPgSQL implements Command {

    @Override
    public Object execute() throws IOException {
        JDBCStorageService jdbc = new JDBCStorageService();
        jdbc.list(null);
        return null;
    }

    @Override
    public void setDataLoader(DataLoader dl) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
