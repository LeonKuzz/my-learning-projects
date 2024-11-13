import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;


public class PurchaseListKey implements Serializable {

    @Column(name = "student_name")
    private String studentName;
    @Column(name = "course_name")
    private String courseName;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
