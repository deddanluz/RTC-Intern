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
public class Grade extends idEntity {
    private int grade;
    
    public int getGrade(){
        return grade;
    }
    
    public void setGrade(int grade){
        this.grade=grade;
    }
    
    public void setGrade(String grade){
        this.grade=Integer.parseInt(grade);
    }
}
