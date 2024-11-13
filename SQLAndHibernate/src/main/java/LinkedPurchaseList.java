import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name =  "linked_purchase_list")
public class LinkedPurchaseList {
    @EmbeddedId
    private LinkedPurchaseListKey id;
//    @Column(name = "student_id", insertable = false, updatable = false)
//    private int studentId;
//
//    @Column(name = "course_id", insertable = false, updatable = false)
//    private int courseId;

//    public int getStudentId() {
//        return studentId;
//    }
//
//    public void setStudentId(int studentId) {
//        this.studentId = studentId;
//    }
//
//    public int getCourseId() {
//        return courseId;
//    }
//
//    public void setCourseId(int courseId) {
//        this.courseId = courseId;
//    }

    public LinkedPurchaseListKey getId() {
        return id;
    }

    public void setId(LinkedPurchaseListKey id) {
        this.id = id;
    }
}
