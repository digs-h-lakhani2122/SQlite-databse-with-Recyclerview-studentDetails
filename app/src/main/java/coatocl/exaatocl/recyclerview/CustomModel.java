package coatocl.exaatocl.recyclerview;

public class CustomModel
{
    int ID;
    String student_name;
    String student_department;
    String  student_semester;
    String  student_project_name;

    //    constructor
    public CustomModel(int ID,String student_name,String student_department,String student_semester,String  student_project_name)
    {
        this.ID=ID;
        this.student_name=student_name;
        this.student_department=student_department;
        this.student_semester=student_semester;
        this.student_project_name=student_project_name;
    }


//    done getter-setter for variable

    //    ID
    public int getID() { return ID; }

    public void setID(int ID) {
        this.ID = ID;
    }

    //    name
    public String getStudent_name()
    {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    //    department
    public String getStudent_department()
    {
        return student_department;
    }

    public void setStudent_department(String student_department) {
        this.student_department = student_department;
    }

    //    semester
    public String getStudent_semester()
    {
        return student_semester;
    }

    public void setStudent_semester(String student_semester) {
        this.student_semester = student_semester;
    }

    //    project_name
    public String getStudent_project_name()
    {
        return student_project_name;
    }

    public void setStudent_project_name(String student_project_name) {
        this.student_project_name = student_project_name;
    }
}
