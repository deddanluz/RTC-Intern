/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import interfaces.DataLoader;
import objects.Person;
import interfaces.StorageService;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Даниил
 */
public class JDBCStorageService implements StorageService {
    @Override
    public void add(DataLoader dl){
        try {
            TransactionScript.getInstance().addPerson(dl);
        } catch (IOException ex) {
            Logger.getLogger(JDBCStorageService.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        
        public void addPerson(DataLoader dl) throws IOException{
            int i;
            for (i=0;i<dl.getPerson().length;){
                int group_id=0,
                    student_id=0,
                    subject_id[],
                    references_subject_id=0;
                try{
                    try{
                        connection.setAutoCommit(false);
                        //проверяем, записали ли уже группу
                        PreparedStatement getGroup = connection.prepareStatement("SELECT * FROM intern.groups WHERE number="+dl.getPerson()[i].getGroup());
                        ResultSet rs = getGroup.executeQuery();//для проверки
                        while(rs.next()){
                            //сразу получем id
                            group_id = rs.getInt("id");//колонки id не найдено в этом sete
                        }
                        if (group_id==0){
                            //если раннее не записали
                            PreparedStatement addGroup = connection.prepareStatement("INSERT INTO intern.groups (number) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
                            //пишем текущую
                            addGroup.setInt(1, dl.getPerson()[i].getGroup());
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
                        addPerson.setString(1, dl.getPerson()[i].getFamily());
                        addPerson.setString(2, dl.getPerson()[i].getName());
                        addPerson.setInt(3, dl.getPerson()[i].getAge());
                        addPerson.setInt(4, group_id);
                        addPerson.execute();
                        //получаем результат
                        ResultSet person_pk = addPerson.getGeneratedKeys();

                        while(person_pk.next()){
                            //получаем текущий id
                            student_id = person_pk.getInt("id");
                        }
                        //пишем предметы и их связи с группами
                        String[] subjects = Arrays.copyOfRange(dl.getHeaders(), 4, dl.getHeaders().length);
                        subject_id = new int[subjects.length];
                        for (int j=0; j<subjects.length; j++){
                            //пробуем получить предмет
                            PreparedStatement getSubjects = connection.prepareStatement("SELECT * FROM intern.subjects WHERE subject='"+subjects[j]+"'");
                            rs = getSubjects.executeQuery();
                            while(rs.next()){
                                //сразу получем id
                                subject_id[j] = rs.getInt("id");
                            }
                            if (subject_id[j]==0){
                                //если раннее не записали
                                PreparedStatement addSubjects = connection.prepareStatement("INSERT INTO intern.subjects (subject) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
                                //пишем текущую
                                addSubjects.setString(1, subjects[j]);
                                addSubjects.execute();
                                //получаем результат
                                ResultSet subject_pk = addSubjects.getGeneratedKeys();
                                while (subject_pk.next()){
                                    //получаем текущий id
                                    subject_id[j] = subject_pk.getInt("id");
                                }
                            }
                            //проверяем связь между предметом и группой
                            PreparedStatement getGroups_subjects = connection.prepareStatement("SELECT * FROM intern.groups_subjects WHERE subject_id="+subject_id[j]+" AND group_id="+group_id);
                            rs = getGroups_subjects.executeQuery();
                            while(rs.next()){
                                //сразу получем id
                                references_subject_id = rs.getInt("subject_id");
                            }
                            if (references_subject_id!=subject_id[j]){
                                //связь не существует
                                PreparedStatement addGroups_subjects = connection.prepareStatement("INSERT INTO intern.groups_subjects (group_id, subject_id) VALUES (?, ?)");
                                //пишем текущую
                                addGroups_subjects.setInt(1, group_id);
                                addGroups_subjects.setInt(2, subject_id[j]);
                                addGroups_subjects.execute();
                            }
                        }
                        //пишем оценки
                        for(int k=0; k<dl.getPerson()[i].getGrades().length; k++){
                            PreparedStatement addPerformance = connection.prepareStatement("INSERT INTO intern.performance (student_id, subject_id, grade) VALUES (?, ?, ?)");
                                //пишем текущую
                                addPerformance.setInt(1, student_id);
                                addPerformance.setInt(2, subject_id[k]);
                                addPerformance.setInt(3, dl.getPerson()[i].getGrades()[k]);
                                addPerformance.execute();
                        }
                        i++;
                        //System.out.println("Person "+i+" in DB!");
                        connection.commit();
                    } catch (SQLException ex) {
                        connection.rollback();
                        //System.out.println(ex.getMessage());
                        //System.exit(0);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}
