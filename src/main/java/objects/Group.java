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
public class Group extends idEntity{
    private int group;
    
    public int getGroup(){
        return group;
    }
    
    public void setGroup(int group){
        this.group=group;
    }
    
    public void setGroup(String group){
        this.group=Integer.parseInt(group);
    }
}
