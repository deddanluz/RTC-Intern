/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import commands.UploadByFirstLetter;
import commands.UploadbyGroup;
import data.groups.DataGroup;
import data.suppliers.DataSupplierCSV;
import interfaces.Command;
import interfaces.DataLoader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import objects.Person;
import objects.Request;

/**
 *
 * @author Даниил
 */

@WebServlet(urlPatterns = {"/*"})
public class RTCInternServlet extends HttpServlet {
    private ObjectMapper mapper = new ObjectMapper();
    private final String FILE = "D:\\YandexDisk\\ARCHIVE\\ДОКУМЕНТЫ\\RTC\\students.csv";                            //берем данные отсюда
    //получение средней оценки по номеру группы
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        mapper = new ObjectMapper();
        //кодировка
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        //тип контента
        resp.setContentType("application/json; charset=UTF-8");
        //получаем поток вывода
        try (PrintWriter out = new PrintWriter(new OutputStreamWriter(resp.getOutputStream(), "UTF8"), true)) {           
            //получаем номер группы
            try{
                var group = Integer.parseInt(req.getParameter("group"));
                //загрузаем данные с сортировкой по группе
                Command <DataGroup> sbf = new UploadbyGroup();
                sbf.setDataLoader(new DataSupplierCSV(FILE));
                Person[] persons = sbf.execute().getPersons(group);
                //пишем
                if (persons.length!=0){
                    out.write(mapper.writeValueAsString(persons));
                }else{
                    resp.sendError(HttpServletResponse.SC_NO_CONTENT);
                }
            }catch (NumberFormatException ex){
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }finally{
                out.flush();
            }
        }
    }
    //изменение оценки по фамилии, номеру группы и предмету
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        mapper = new ObjectMapper();
        //кодировка
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        //тип контента
        resp.setContentType("application/json; charset=UTF-8");
        //запрос
        Request request;
        try (var input = req.getInputStream()) {
            //сюда пишем результат
            List<Person> output = new ArrayList<>();
            //строка JSON
            String s = new String(input.readAllBytes());
            //преврщаем в объект запроса
            request = mapper.readValue(s, Request.class);
            //загрузаем и сортируем по первой букве фамилии
            Command <DataGroup> ubfl = new UploadByFirstLetter();
            DataLoader dl = new DataSupplierCSV(FILE);
            ubfl.setDataLoader(dl);
            //получаем отсортированные по первой букве введенной фамилии
            Person[] sortByFirstLetter = ubfl.execute().getPersons(request.getFamily());
            //далее сортируем по номеру группы
            DataGroup dg = new DataGroup(p -> p.getGroup());
            dg.addPersons(sortByFirstLetter);
            //получаем по группе
            Person[] persons = dg.getPersons(request.getGroup());
            //получаем имена предметов
            String[] subjects = Arrays.copyOfRange(dl.getHeaders(), 4, dl.getHeaders().length);
            for (int i = 0; i<subjects.length; i++){
                //если имя предмета совпадает с введенным
                if (subjects[i].equals(request.getSubject())){
                    for (Person person : persons){
                        //если фамилия и имя совпадают
                        if (person.getFamily().equals(request.getFamily())&&person.getName().equals(request.getName())){
                            //изменяем оценку
                            int[] grades = person.getGrades();
                            grades[i]=request.getGrade();
                            output.add(new Person(person.getFamily(), person.getName(), person.getAge(), person.getGroup(), grades));
                        }
                    }
                }
            }
            //используем повторно
            persons=new Person[output.size()];
            output.toArray(persons);
            //выводим
            try (PrintWriter out = new PrintWriter(new OutputStreamWriter(resp.getOutputStream(), "UTF8"), true)) {
                if (persons.length!=0){
                    out.write(mapper.writeValueAsString(persons));
                }else{
                    resp.sendError(HttpServletResponse.SC_NO_CONTENT);
                }
                out.flush();
            }
            
        }
    }
}
