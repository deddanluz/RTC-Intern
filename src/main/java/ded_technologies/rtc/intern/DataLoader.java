/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ded_technologies.rtc.intern;

import java.io.IOException;

/**
 *
 * @author Даниил
 */
public interface DataLoader {
    public String[] getHeaders() throws IOException;
    public Person[] getPerson() throws IOException;
}
