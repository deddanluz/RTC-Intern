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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
            //поиск по фамилии
            return TransactionScript.getInstance().listStudents(obj.toString());
        }else if (obj instanceof Integer){
            //поиск по возрасту
            return TransactionScript.getInstance().listStudents((int) obj);
        }else if (obj instanceof Group){
            //поиск по группе
            return TransactionScript.getInstance().listStudents((Group) obj);
        }
        return null;
    }
    
    public static final class TransactionScript{
        private String url = "jdbc:postgresql://localhost/intern",
                       login = "postgres",
                       password = "postgresql";
        private Connection connection;
        private final String GET_BY_GROUP =  "SELECT students.id, students.family, students.name, students.age, groups.id, groups.number, subjects.id, subjects.subject, performance.id, performance.grade " +
                        "FROM intern.students " +
                        "INNER JOIN intern.groups ON students.group_id = groups.id " +
                        "LEFT JOIN intern.performance ON students.id = performance.student_id " +
                        "LEFT JOIN intern.subjects ON performance.subject_id = subjects.id " +
                        "WHERE groups.number = ?";
        private final String GET_BY_AGE = "SELECT students.id, students.family, students.name, students.age, groups.id, groups.number, subjects.id, subjects.subject, performance.id, performance.grade " +
                        "FROM intern.students " +
                        "INNER JOIN intern.groups ON students.group_id = groups.id " +
                        "LEFT JOIN intern.performance ON students.id = performance.student_id " +
                        "LEFT JOIN intern.subjects ON performance.subject_id = subjects.id " +
                        "WHERE students.age = ?";
        private final String GET_BY_FAMILY = "SELECT students.id, students.family, students.name, students.age, groups.id, groups.number, subjects.id, subjects.subject, performance.id, performance.grade " +
                        "FROM intern.students " +
                        "INNER JOIN intern.groups ON students.group_id = groups.id " +
                        "LEFT JOIN intern.performance ON students.id = performance.student_id " +
                        "LEFT JOIN intern.subjects ON performance.subject_id = subjects.id " +
                        "WHERE students.family = ?";
        private final String GET_GROUP_SQL = "SELECT * FROM intern.groups WHERE number=?";
        private final String ADD_GROUP = "INSERT INTO intern.groups (number) VALUES (?)";
        private final String ADD_PERSON = "INSERT INTO intern.students (family,name,age,group_id) VALUES (?, ?, ?, ?)";
        private final String GET_SUBJECT = "SELECT * FROM intern.subjects WHERE subject=?";
        private final String ADD_SUBJECT = "INSERT INTO intern.subjects (subject) VALUES (?)";
        private final String GET_GROUPS_SUBJECTS = "SELECT * FROM intern.groups_subjects WHERE subject_id=? AND group_id=?";
        private final String ADD_GROUPS_SUBJECTS = "INSERT INTO intern.groups_subjects (group_id, subject_id) VALUES (?, ?)";
        private final String ADD_PERFORMANCE = "INSERT INTO intern.performance (student_id, subject_id, grade) VALUES (?, ?, ?)";
        
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
                connection.setAutoCommit(false);
                ResultSet resultSet;
                try (PreparedStatement statement = connection.prepareStatement(GET_BY_GROUP)) {
                    statement.setInt(1, group.getGroup());
                    resultSet = statement.executeQuery();
                    
                    // Добавляем студентов из Map в список результатов
                    output.addAll(handler(resultSet).values());
                    resultSet.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(JDBCStorageService.class.getName()).log(Level.SEVERE, null, ex);
                throw new RuntimeException(ex);
            }
            return output;
        }
        //получаем по возрасту
        public List<Student> listStudents(int age) throws RuntimeException{
            List<Student> output = new ArrayList<>();
            try{
                connection.setAutoCommit(false);
                ResultSet resultSet;
                try (PreparedStatement statement = connection.prepareStatement(GET_BY_AGE)) {
                    statement.setInt(1, age);
                    resultSet = statement.executeQuery();
                    
                    // Добавляем студентов из Map в список результатов
                    output.addAll(handler(resultSet).values());
                    resultSet.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(JDBCStorageService.class.getName()).log(Level.SEVERE, null, ex);
                throw new RuntimeException(ex);
            }
            return output;
        }
        //получаем по фамилии
        public List<Student> listStudents(String family) throws RuntimeException{
            List<Student> output = new ArrayList<>();
            try{
                connection.setAutoCommit(false);
                ResultSet resultSet;
                try ( // Подготавливаем SQL запрос для получения информации о студентах
                        PreparedStatement statement = connection.prepareStatement(GET_BY_FAMILY)) {
                    statement.setString(1, family);
                    resultSet = statement.executeQuery();
                    
                    // Добавляем студентов из Map в список результатов
                    output.addAll(handler(resultSet).values());
                    resultSet.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(JDBCStorageService.class.getName()).log(Level.SEVERE, null, ex);
                throw new RuntimeException(ex);
            }
            return output;
        }
        
        private Map<Integer, Student> handler(ResultSet resultSet) throws SQLException{
            // Обрабатываем результаты запроса
            Map<Integer, Student> studentMap = new HashMap<>();
            Person person;
            Group group;
            Subject subject;
            while (resultSet.next()) {
                person = new Person();
                person.setId(resultSet.getInt(1));
                person.setFamily(resultSet.getString(2));
                person.setName(resultSet.getString(3));
                person.setAge(resultSet.getInt(4));
                group = new Group();
                group.setId(resultSet.getInt(5));
                group.setGroup(resultSet.getInt(6));
                subject = new Subject();
                subject.setId(resultSet.getInt(7));
                subject.setTitle(resultSet.getString(8));

                // Если студент уже присутствует в Map, обновляем его оценки, иначе создаем нового студента
                if (studentMap.containsKey(person.getId())) {
                    Student student = studentMap.get(person.getId());
                    Grade grade = new Grade();
                    grade.setId(resultSet.getInt(9));
                    grade.setGrade(resultSet.getInt(10));
                    Grade[] grades = new Grade[student.getGrades().length + 1];
                    System.arraycopy(student.getGrades(),0, grades, 0, student.getGrades().length);
                    grades[student.getGrades().length]=grade;
                    student = new Student(student.getPerson(), student.getGroup(), grades);
                    studentMap.replace(person.getId(), student);
                } else {
                    Grade[] grades = new Grade[1];
                    Grade grade = new Grade();
                    grade.setId(resultSet.getInt(9));
                    grade.setGrade(resultSet.getInt(10));
                    grades[0]=grade;
                    Student student = new Student(person, group, grades);
                    studentMap.put(student.getPerson().getId(), student);
                }
            }
            return studentMap;        
        }
        
        public void addStudents(DataLoader dl) throws IOException, RuntimeException{
            int i, references_subject_id;
            try(ProgressBar pgb = new ProgressBar("Uploading to PgSQL", dl.getStudents().length)){
                for (i=0;i<dl.getStudents().length;){
                    references_subject_id=0;
                    try{
                        try{
                            connection.setAutoCommit(false);
                            ResultSet rs;
                            try ( //проверяем, записали ли уже группу
                                    PreparedStatement getGroup = connection.prepareStatement(GET_GROUP_SQL)) {
                                getGroup.setInt(1, dl.getStudents()[i].getGroup().getGroup());
                                rs = getGroup.executeQuery(); //для проверки
                            
                                while(rs.next()){
                                    //сразу получем id
                                    dl.getStudents()[i].getGroup().setId(rs.getInt("id"));
                                }
                                rs.close();
                            }
                            if (dl.getStudents()[i].getGroup().getId()==0){
                                ResultSet group_pk;
                                //пишем текущую
                                try ( //если раннее не записали
                                        PreparedStatement addGroup = connection.prepareStatement(ADD_GROUP, Statement.RETURN_GENERATED_KEYS)) {
                                    //пишем текущую
                                    addGroup.setInt(1, dl.getStudents()[i].getGroup().getGroup());
                                    addGroup.execute();
                                    //получаем результат
                                    group_pk = addGroup.getGeneratedKeys();
                                
                                    while (group_pk.next()){
                                        //получаем текущий id
                                        dl.getStudents()[i].getGroup().setId(group_pk.getInt("id"));
                                    }
                                    group_pk.close();
                                }
                            }
                            ResultSet person_pk;
                            //пишем текущего
                            try ( //пишем ученика
                                    PreparedStatement addPerson = connection.prepareStatement(ADD_PERSON, Statement.RETURN_GENERATED_KEYS)) {
                                //пишем текущего
                                addPerson.setString(1, dl.getStudents()[i].getPerson().getFamily());
                                addPerson.setString(2, dl.getStudents()[i].getPerson().getName());
                                addPerson.setInt(3, dl.getStudents()[i].getPerson().getAge());
                                addPerson.setInt(4, dl.getStudents()[i].getGroup().getId());
                                addPerson.execute();
                                //получаем результат
                                person_pk = addPerson.getGeneratedKeys();
                            

                                while(person_pk.next()){
                                    //получаем текущий id
                                    dl.getStudents()[i].getPerson().setId(person_pk.getInt("id"));
                                }
                                person_pk.close();
                            }
                            
                            //пишем предметы и их связи с группами
                            String[] headers = Arrays.copyOfRange(dl.getHeaders(), 4, dl.getHeaders().length);
                            Subject[] subjects = new Subject[headers.length];
                            for (int s=0; s<headers.length;s++){
                                subjects[s] = new Subject();
                                subjects[s].setTitle(headers[s]);
                            }
                            for (int j=0; j<subjects.length; j++){
                                try ( //пробуем получить предмет
                                        PreparedStatement getSubjects = connection.prepareStatement(GET_SUBJECT)) {
                                    getSubjects.setString(1, subjects[j].getTitle());
                                    rs = getSubjects.executeQuery();
                                
                                    while(rs.next()){
                                        //сразу получем id
                                        subjects[j].setId(rs.getInt("id"));
                                    }
                                    rs.close();
                                }
                                if (subjects[j].getId()==0){
                                    ResultSet subject_pk;
                                    //пишем текущую
                                    try ( //если раннее не записали
                                            PreparedStatement addSubjects = connection.prepareStatement(ADD_SUBJECT, Statement.RETURN_GENERATED_KEYS)) {
                                        //пишем текущую
                                        addSubjects.setString(1, subjects[j].getTitle());
                                        addSubjects.execute();
                                        //получаем результат
                                        subject_pk = addSubjects.getGeneratedKeys();
                                    
                                        while (subject_pk.next()){
                                            //получаем текущий id
                                            subjects[j].setId(subject_pk.getInt("id"));
                                        }
                                        subject_pk.close();
                                    }
                                }
                                try ( //проверяем связь между предметом и группой
                                        PreparedStatement getGroups_subjects = connection.prepareStatement(GET_GROUPS_SUBJECTS)) {
                                    getGroups_subjects.setInt(1, subjects[j].getId());
                                    getGroups_subjects.setInt(2, dl.getStudents()[i].getGroup().getId());
                                    rs = getGroups_subjects.executeQuery();
                                
                                    while(rs.next()){
                                        //сразу получем id
                                        references_subject_id = rs.getInt("subject_id");
                                    }
                                    rs.close();
                                }
                                if (references_subject_id!=subjects[j].getId()){
                                    //пишем текущую
                                    try ( //связь не существует
                                            PreparedStatement addGroups_subjects = connection.prepareStatement(ADD_GROUPS_SUBJECTS)) {
                                        //пишем текущую
                                        addGroups_subjects.setInt(1, dl.getStudents()[i].getGroup().getId());
                                        addGroups_subjects.setInt(2, subjects[j].getId());
                                        addGroups_subjects.execute();
                                    }
                                }
                            }
                            //пишем оценки
                            for(int k=0; k<dl.getStudents()[i].getGrades().length; k++){
                                //пишем текущую
                                try (PreparedStatement addPerformance = connection.prepareStatement(ADD_PERFORMANCE)) {
                                    //пишем текущую
                                    addPerformance.setInt(1, dl.getStudents()[i].getPerson().getId());
                                    addPerformance.setInt(2, subjects[k].getId());
                                    addPerformance.setInt(3, dl.getStudents()[i].getGrades()[k].getGrade());
                                    addPerformance.execute();
                                }
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
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(JDBCStorageService.class.getName()).log(Level.SEVERE, null, ex);
                throw new RuntimeException(ex);
            }
        }
    }
}
