/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objects;

/**
 *
 * @author Даниил
 */
public class Student {
    private Person person;
    private Group group;
    private Grade[] grades;
    
    public Student(Person person, Group group, Grade[] grades){
        this.person=person;
        this.group=group;
        this.grades=grades;
    }
    
    public Person getPerson(){
        return person;
    }
    
    public Group getGroup(){
        return group;
    }
    
    public Grade[] getGrades(){
        return grades;
    }
    
    public double getAverageGrade(){
        double sum=0;
        for(Grade grade: grades){
            sum=sum+grade.getGrade();
        }
        return sum/grades.length;
    }
}
