package uz.pdp.appjparelationshipslesson7.payload;

import lombok.Data;
import uz.pdp.appjparelationshipslesson7.entity.Subject;

import java.util.List;

@Data
public class StudentDto {

    private String firstName;
    private String lastName;
    private Integer addressId;
    private List<Subject> subjectList;
    private Integer groupId;

}
