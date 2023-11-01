/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import java.io.IOException;

/**
 *
 * @author Даниил
 * @param <T>
 */
public interface Command <T> {
    T execute() throws IOException;
    public void setDataLoader(DataLoader dl);
}
