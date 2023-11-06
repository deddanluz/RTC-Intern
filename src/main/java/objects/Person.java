/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objects;

import java.util.Arrays;

/**
 *
 * @author Даниил
 */
public class Person {
    private String family, name;
    private int age, group;
    private int[] grades;
    private double averageGrade;
    
    public Person (String family, String name, String age, String group, String[] grades){
        this.family = family;
        this.name = name;
        this.age = Integer.parseInt(age);
        this.group = Integer.parseInt(group);
        this.grades = Arrays.stream(grades).mapToInt(Integer::parseInt).toArray();
    }
    
    public Person (String family, String name, int age, int group, int[] grades){
        this.family = family;
        this.name = name;
        this.age = age;
        this.group = group;
        this.grades = grades;
    }
    
    public String getFamily(){
        return family;
    }
    
    public String getName(){
        return name;
    }
    
    public int getAge(){
        return age;
    }
    
    public int getGroup(){
        return group;
    }
    
    public int[] getGrades(){
        return grades;
    }
    
    public double getAverageGrade(){
        double sum=0;
        for (int grade : grades){
            sum = sum+grade;
        }
        averageGrade = sum/grades.length;
        return averageGrade;
    }
}
