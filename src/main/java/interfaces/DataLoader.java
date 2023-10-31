/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import objects.Person;
import java.io.IOException;

/**
 *
 * @author Даниил
 */
public interface DataLoader {
    public String[] getHeaders() throws IOException;
    public Person[] getPerson() throws IOException;
}
