/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import objects.Person;
import interfaces.StorageService;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Даниил
 */
public class JDBCStorageService implements StorageService {
    @Override
    public void add(String[] nameSubjects, Person[] persons){
        TransactionScript.getInstance().addPerson(nameSubjects, persons);
    }

    @Override
    public List<Person> list(int group) {
        return TransactionScript.getInstance().listPersons(group);
    }
    
    public static final class TransactionScript{
        private String url = "jdbc:postgresql://localhost/intern",
                       login = "postgres",
                       password = "postgresql";
        
        private Connection connection;
        
        public TransactionScript(){
            try{
                connection = DriverManager.getConnection(url, login, password);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        
        private static final TransactionScript instance = new TransactionScript();
        
        public static TransactionScript getInstance(){
            return instance;
        }
        
        public List<Person> listPersons(int group){
            List<Person> output = new ArrayList<>();
            try{
                try{
                    connection.setAutoCommit(false);
                    PreparedStatement statement = connection.prepareStatement("");
                    statement.setInt(1, group);
                    ResultSet rSet = statement.executeQuery();
                    while (rSet.next()){
                        
                    }
                    connection.commit();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            return output;
        }
        
        public void addPerson(String[] nameSubjects, Person[] persons){
            //!!!не грузятся все персоны, предмет загружается только один
            int num=0;
            for (Person person : persons){
            int group_id=0,
                student_id=0,
                subject_id=0,
                references_subject_id=0;
            try{
                try{
                    connection.setAutoCommit(false);
                    //проверяем, записали ли уже группу
                    PreparedStatement getGroup = connection.prepareStatement("SELECT number FROM intern.groups WHERE number="+person.getGroup());
                    ResultSet rs = getGroup.executeQuery();//для проверки
                    while(rs.next()){
                        //сразу получем id
                        group_id = rs.getInt("id");
                    }
                    if (group_id==0){
                        //если раннее не записали
                        PreparedStatement addGroup = connection.prepareStatement("INSERT INTO intern.groups (number) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
                        //пишем текущую
                        addGroup.setInt(1, person.getGroup());
                        addGroup.execute();
                        //получаем результат
                        ResultSet group_pk = addGroup.getGeneratedKeys();
                        while (group_pk.next()){
                            //получаем текущий id
                            group_id = group_pk.getInt("id");
                        }
                    }
                    //пишем ученика
                    PreparedStatement addPerson = connection.prepareStatement("INSERT INTO intern.students (family,name,age,group_id) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                    //пишем текущего
                    addPerson.setString(1, person.getFamily());
                    addPerson.setString(2, person.getName());
                    addPerson.setInt(3, person.getAge());
                    addPerson.setInt(4, group_id);
                    addPerson.execute();
                    //получаем результат
                    ResultSet person_pk = addPerson.getGeneratedKeys();

                    while(person_pk.next()){
                        //получаем текущий id
                        student_id = person_pk.getInt("id");
                    }
                    //пишем предметы и их связи с группами
                    for (String nameSubject : nameSubjects){
                        //пробуем получить предмет
                        PreparedStatement getSubjects = connection.prepareStatement("SELECT subject FROM intern.subjects WHERE subject='"+nameSubject+"'");
                        rs = getSubjects.executeQuery();
                        while(rs.next()){
                            //сразу получем id
                            subject_id = rs.getInt("id");
                        }
                        if (subject_id==0){
                            //если раннее не записали
                            PreparedStatement addSubjects = connection.prepareStatement("INSERT INTO intern.subjects (subject) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
                            //пишем текущую
                            addSubjects.setString(1, nameSubject);
                            addSubjects.execute();
                            //получаем результат
                            ResultSet subject_pk = addSubjects.getGeneratedKeys();
                            while (subject_pk.next()){
                                //получаем текущий id
                                subject_id = subject_pk.getInt("id");
                            }
                        }
                        //проверяем связь между предметом и группой
                        PreparedStatement getGroups_subjects = connection.prepareStatement("SELECT * FROM intern.groups_subjects WHERE subject_id="+subject_id+" AND group_id="+group_id);
                        rs = getGroups_subjects.executeQuery();
                        while(rs.next()){
                            //сразу получем id
                            references_subject_id = rs.getInt("subject_id");
                        }
                        if (references_subject_id!=subject_id){
                            //связь не существует
                            PreparedStatement addGroups_subjects = connection.prepareStatement("INSERT INTO intern.groups_subjects (group_id, subject_id) VALUES (?, ?)");
                            //пишем текущую
                            addGroups_subjects.setInt(1, group_id);
                            addGroups_subjects.setInt(2, subject_id);
                            addGroups_subjects.execute();
                        }
                    }
                    //пишем оценки
                    for(int grade : person.getGrades()){
                        PreparedStatement addPerformance = connection.prepareStatement("INSERT INTO intern.performance (student_id, subject_id, grade) VALUES (?, ?, ?)");
                            //пишем текущую
                            addPerformance.setInt(1, student_id);
                            addPerformance.setInt(2, subject_id);
                            addPerformance.setInt(3, grade);
                            addPerformance.execute();
                    }
                    //применяем изменения
                    connection.commit();
                } catch (SQLException ex) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            num++;
            System.out.println("Person "+num+" in DB");
            }
        }
    }
}
