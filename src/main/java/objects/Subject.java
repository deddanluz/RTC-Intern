/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objects;

import dto.idEntity;

/**
 *
 * @author Даниил
 */
public class Subject extends idEntity{
    private String title;
    
    public void setTitle(String title){
        this.title=title;
    }
    
    public String getTitle(){
        return title;
    }
}
