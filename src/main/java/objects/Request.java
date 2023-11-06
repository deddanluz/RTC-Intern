/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objects;

/**
 *
 * @author Даниил
 */
public class Request {
    private String family,
                  name,
                  subject;
    private int group,
               grade;
    
    public void setFamily(String family){
        this.family = family;
    }
    
    public String getFamily(){
        return family;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
    
    public void setSubject(String subject){
        this.subject = subject;
    }
    
    public String getSubject(){
        return subject;
    }
    
    public void setGroup(int group){
        this.group = group;
    }
    
    public int getGroup(){
        return group;
    }
    
    public void setGrade(int grade){
        this.grade = grade;
    }
    
    public int getGrade(){
        return grade;
    }
}
