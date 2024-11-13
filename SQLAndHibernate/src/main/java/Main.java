
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();

        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();

        CriteriaQuery<PurchaseList> queryP = builder.createQuery(PurchaseList.class);
        Root<PurchaseList> purchaseListRoot = queryP.from(PurchaseList.class);
        queryP.select(purchaseListRoot);
        List<PurchaseList> purchaseLists = session.createQuery(queryP).getResultList();

        for (PurchaseList purchaseList : purchaseLists) {
            Session session1 = sessionFactory.openSession();
            Transaction transaction = session1.beginTransaction();
            CriteriaQuery<Course> queryC = builder.createQuery(Course.class);
            Root<Course> courseRoot = queryC.from(Course.class);
            CriteriaQuery<Student> queryS = builder.createQuery(Student.class);
            Root<Student> studentRoot = queryS.from(Student.class);

            queryS.select(studentRoot).where(builder.equal(studentRoot.<String>get("name"), purchaseList.getStudentName()));
            Student student = session.createQuery(queryS).getSingleResult();

            queryC.select(courseRoot).where(builder.equal(courseRoot.<String>get("name"), purchaseList.getCourseName()));
            Course course = session.createQuery(queryC).getSingleResult();

            LinkedPurchaseList linkedPurchaseList = new LinkedPurchaseList();
            linkedPurchaseList.setId(new LinkedPurchaseListKey(student.getId(), course.getId()));
            session1.save(linkedPurchaseList);
            transaction.commit();
            session1.close();
        }
        sessionFactory.close();
    }
}
