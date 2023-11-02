/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package commands;

import objects.Person;
import interfaces.Command;
import interfaces.DataLoader;
import java.io.IOException;
import java.util.Arrays;
import services.JDBCStorageService;

/**
 *
 * @author Даниил
 */
public class UploadByPgSQL implements Command {
    private DataLoader dl;
    @Override
    public Object execute() throws IOException {
        Person[] persons = dl.getPerson();
        String[] headers = Arrays.copyOfRange(dl.getHeaders(), 4, dl.getHeaders().length);
        JDBCStorageService jdbc = new JDBCStorageService();
        jdbc.add(headers, persons);
        System.out.println("Person in DB!");
        return jdbc;
    }

    @Override
    public void setDataLoader(DataLoader dl) {
        this.dl=dl;
    }
    
}
