/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import me.tongfei.progressbar.ProgressBar;
import interfaces.DataLoader;
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
import objects.Grade;
import objects.Group;
import objects.Person;
import objects.Student;
import objects.Subject;

/**
 *
 * @author Даниил
 */
public class JDBCStorageService implements StorageService {
    @Override
    public void add(DataLoader dl){
        try {
            TransactionScript.getInstance().addStudents(dl);
        } catch (IOException ex) {
            Logger.getLogger(JDBCStorageService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Student> list(Object obj) {
        if (obj instanceof String){
            return TransactionScript.getInstance().listStudents(obj.toString());
        }else if (obj instanceof Integer){
            
        }else if (obj instanceof Group){
            
        }
        return null;
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
        //получаем по группе
        public List<Student> listStudents(Group group) throws RuntimeException{
            List<Student> output = new ArrayList<>();
            try{
                try(ProgressBar pgb = new ProgressBar("Searching in PgSQL", output.size()+1)){
                    connection.setAutoCommit(false);
                    //тело метода
                    connection.commit();
                } catch (SQLException ex) {
                    connection.rollback();
                    Logger.getLogger(JDBCStorageService.class.getName()).log(Level.SEVERE, null, ex);
                    throw new RuntimeException(ex);
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            return output;
        }
        //получаем по возрасту
        public List<Student> listStudents(int age) throws RuntimeException{
            List<Student> output = new ArrayList<>();
            try{
                try(ProgressBar pgb = new ProgressBar("Searching in PgSQL", output.size()+1)){
                    connection.setAutoCommit(false);
                    //тело метода
                    connection.commit();
                } catch (SQLException ex) {
                    connection.rollback();
                    Logger.getLogger(JDBCStorageService.class.getName()).log(Level.SEVERE, null, ex);
                    throw new RuntimeException(ex);
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            return output;
        }
        //получаем по фамилии
        public List<Student> listStudents(String family) throws RuntimeException{
            List<Student> output = new ArrayList<>();
            try{
                try(ProgressBar pgb = new ProgressBar("Searching in PgSQL", output.size()+1)){
                    connection.setAutoCommit(false);
                    //запрашиваем всех по фамилии
                    PreparedStatement statement = connection.prepareStatement("SELECT * FROM intern.students WHERE family='"+family+"'");
                    ResultSet rSet = statement.executeQuery();
                    //текущая персона
                    Person person=null;
                    //текущая група
                    Group group = new Group();
                    //получаем информацию для персон и создаем их
                    //группу и оценки пока null
                    while (rSet.next()){
                        person = new Person();
                        person.setId(rSet.getInt("id"));
                        person.setFamily(rSet.getString("family"));
                        person.setName(rSet.getString("name"));
                        person.setAge(rSet.getInt("age"));
                        //получаем индекс группы
                        group.setId(rSet.getInt("group_id"));
                        
                        //запрашиваем группу
                        statement = connection.prepareStatement("SELECT * FROM intern.groups WHERE id="+group.getId());
                        ResultSet rSetGroup = statement.executeQuery();
                        //получаем
                        while (rSetGroup.next()){
                            group.setGroup(rSetGroup.getInt("number"));
                        }
                        
                        //запрашиваем предметы
                        statement = connection.prepareStatement("SELECT * FROM intern.groups_subjects WHERE group_id="+group.getId());
                        ResultSet rSetSubjects = statement.executeQuery();
                        Grade grade;
                        List<Grade> grades = new ArrayList<>();
                        //по предмету получаем
                        while (rSetSubjects.next()){
                            //запрашиваем оценки
                            statement = connection.prepareStatement("SELECT * FROM intern.performance WHERE student_id="+person.getId()+" AND subject_id="+rSetSubjects.getInt("subject_id"));
                            ResultSet rSetGrades = statement.executeQuery();
                            //по оценке получаем
                            while (rSetGrades.next()){
                                grade = new Grade();
                                grade.setGrade(rSetGrades.getInt("grade"));
                                grades.add(grade);
                            }   
                        }
                        //создаем ученика
                        Grade[] gradesArray = new Grade[grades.size()];
                        grades.toArray(gradesArray);
                        output.add(new Student(person, group, gradesArray));
                        pgb.step();
                    }
                    connection.commit();
                } catch (SQLException ex) {
                    connection.rollback();
                    throw new RuntimeException(ex);
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            return output;
        }
        
        public void addStudents(DataLoader dl) throws IOException, RuntimeException{
            int i, references_subject_id;
            try(ProgressBar pgb = new ProgressBar("Uploading to PgSQL", dl.getStudents().length)){
                for (i=0;i<dl.getStudents().length;){
                    references_subject_id=0;
                    try{
                        try{
                            connection.setAutoCommit(false);
                            //проверяем, записали ли уже группу
                            PreparedStatement getGroup = connection.prepareStatement("SELECT * FROM intern.groups WHERE number="+dl.getStudents()[i].getGroup().getGroup());
                            ResultSet rs = getGroup.executeQuery();//для проверки
                            while(rs.next()){
                                //сразу получем id
                                dl.getStudents()[i].getGroup().setId(rs.getInt("id"));
                            }
                            if (dl.getStudents()[i].getGroup().getId()==0){
                                //если раннее не записали
                                PreparedStatement addGroup = connection.prepareStatement("INSERT INTO intern.groups (number) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
                                //пишем текущую
                                addGroup.setInt(1, dl.getStudents()[i].getGroup().getGroup());
                                addGroup.execute();
                                //получаем результат
                                ResultSet group_pk = addGroup.getGeneratedKeys();
                                while (group_pk.next()){
                                    //получаем текущий id
                                    dl.getStudents()[i].getGroup().setId(group_pk.getInt("id"));
                                }
                            }
                            //пишем ученика
                            PreparedStatement addPerson = connection.prepareStatement("INSERT INTO intern.students (family,name,age,group_id) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                            //пишем текущего
                            addPerson.setString(1, dl.getStudents()[i].getPerson().getFamily());
                            addPerson.setString(2, dl.getStudents()[i].getPerson().getName());
                            addPerson.setInt(3, dl.getStudents()[i].getPerson().getAge());
                            addPerson.setInt(4, dl.getStudents()[i].getGroup().getId());
                            addPerson.execute();
                            //получаем результат
                            ResultSet person_pk = addPerson.getGeneratedKeys();

                            while(person_pk.next()){
                                //получаем текущий id
                                dl.getStudents()[i].getPerson().setId(person_pk.getInt("id"));
                            }
                            //пишем предметы и их связи с группами
                            String[] headers = Arrays.copyOfRange(dl.getHeaders(), 4, dl.getHeaders().length);
                            Subject[] subjects = new Subject[headers.length];
                            for (int s=0; s<headers.length;s++){
                                subjects[s] = new Subject();
                                subjects[s].setTitle(headers[s]);
                            }
                            for (int j=0; j<subjects.length; j++){
                                //пробуем получить предмет
                                PreparedStatement getSubjects = connection.prepareStatement("SELECT * FROM intern.subjects WHERE subject='"+subjects[j].getTitle()+"'");
                                rs = getSubjects.executeQuery();
                                while(rs.next()){
                                    //сразу получем id
                                    subjects[j].setId(rs.getInt("id"));
                                }
                                if (subjects[j].getId()==0){
                                    //если раннее не записали
                                    PreparedStatement addSubjects = connection.prepareStatement("INSERT INTO intern.subjects (subject) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
                                    //пишем текущую
                                    addSubjects.setString(1, subjects[j].getTitle());
                                    addSubjects.execute();
                                    //получаем результат
                                    ResultSet subject_pk = addSubjects.getGeneratedKeys();
                                    while (subject_pk.next()){
                                        //получаем текущий id
                                        subjects[j].setId(subject_pk.getInt("id"));
                                    }
                                }
                                //проверяем связь между предметом и группой
                                PreparedStatement getGroups_subjects = connection.prepareStatement("SELECT * FROM intern.groups_subjects WHERE subject_id="+subjects[j].getId()+" AND group_id="+dl.getStudents()[i].getGroup().getId());
                                rs = getGroups_subjects.executeQuery();
                                while(rs.next()){
                                    //сразу получем id
                                    references_subject_id = rs.getInt("subject_id");
                                }
                                if (references_subject_id!=subjects[j].getId()){
                                    //связь не существует
                                    PreparedStatement addGroups_subjects = connection.prepareStatement("INSERT INTO intern.groups_subjects (group_id, subject_id) VALUES (?, ?)");
                                    //пишем текущую
                                    addGroups_subjects.setInt(1, dl.getStudents()[i].getGroup().getId());
                                    addGroups_subjects.setInt(2, subjects[j].getId());
                                    addGroups_subjects.execute();
                                }
                            }
                            //пишем оценки
                            for(int k=0; k<dl.getStudents()[i].getGrades().length; k++){
                                PreparedStatement addPerformance = connection.prepareStatement("INSERT INTO intern.performance (student_id, subject_id, grade) VALUES (?, ?, ?)");
                                    //пишем текущую
                                    addPerformance.setInt(1, dl.getStudents()[i].getPerson().getId());
                                    addPerformance.setInt(2, subjects[k].getId());
                                    addPerformance.setInt(3, dl.getStudents()[i].getGrades()[k].getGrade());
                                    addPerformance.execute();
                            }
                            connection.commit();
                            pgb.step();
                            i=(int) pgb.getCurrent();
                        } catch (SQLException ex) {
                            connection.rollback();
                            throw new RuntimeException(ex);
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
    }
}
