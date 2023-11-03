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
public class Person extends idEntity{
    private String family, name;
    private int age;
    
    public String getFamily(){
        return family;
    }
    
    public void setFamily(String family){
        this.family=family;
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name=name;
    }
    
    public int getAge(){
        return age;
    }
    
    public void setAge(int age){
        this.age=age;
    }
    
    public void setAge(String age){
        this.age=Integer.parseInt(age);
    }
}
