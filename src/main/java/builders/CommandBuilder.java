/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package builders;

import services.StudentService;
import commands.CalcAverageGrade;
import commands.CalcHonorsPerson;
import commands.SearchByFamily;
import commands.UploadByFirstLetter;
import commands.UploadbyAge;
import commands.UploadbyGroup;
import data.groups.DataGroup;
import java.io.IOException;


/**
 *
 * @author Даниил
 */
public class CommandBuilder {
    private final StudentService SS;                                            //бизнес-логика
    //получаем объект
    public CommandBuilder(StudentService ss){
        SS = ss;
    }
    
    public Object calcAverageGrade() throws IOException{
        UploadbyGroup ubg = new UploadbyGroup(SS.getDataLoader());
        CalcAverageGrade cag = new CalcAverageGrade((DataGroup) ubg.execute());
        return cag.execute();//double
    }
    
    public Object calcHonorsPerson() throws IOException{
        UploadbyAge uba = new UploadbyAge(SS.getDataLoader());
        CalcHonorsPerson chp = new CalcHonorsPerson((DataGroup) uba.execute());
        return chp.execute();//Person[]
    }
    
    public Object searchByFamily(String family) throws IOException{
        UploadByFirstLetter ubfl = new UploadByFirstLetter(SS.getDataLoader());
        SearchByFamily sbf = new SearchByFamily((DataGroup) ubfl.execute(), family);
        return sbf.execute();//Person[]
    }
}
