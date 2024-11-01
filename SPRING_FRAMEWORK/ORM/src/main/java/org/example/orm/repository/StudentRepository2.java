package org.example.orm.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.example.orm.entity.Student;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentRepository2 {
    @PersistenceContext
    private EntityManager entityManager;
    public List<Student> findById(int id) {
        String sql = "SELECT s.id, s.name FROM student s WHERE s.id = :id";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("id", id);

        List<Object[]> results = query.getResultList();
        List<Student> students = new ArrayList<>();

        for (Object[] row : results) {
            Student student = new Student();
            Field[] fields = Student.class.getDeclaredFields();

            for (int i = 0; i < row.length; i++) {
                try {
                    fields[i].setAccessible(true);
                    fields[i].set(student, row[i]);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            students.add(student);
        }
        return students;
    }
}
