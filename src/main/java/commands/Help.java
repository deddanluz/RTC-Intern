/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package commands;

import interfaces.Command;
import interfaces.DataLoader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import services.StudentService;

/**
 *
 * @author Даниил
 */ 
public class Help implements Command{
    @Override
    public Object execute(){
        StringBuilder area;
        try(BufferedReader buff = new BufferedReader(new FileReader("help.txt"))){
            String line;
            area = new StringBuilder();
            while ((line=buff.readLine())!=null){
                area.append(line);
            }
            return area.toString();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StudentService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StudentService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void setDataLoader(DataLoader dl) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
