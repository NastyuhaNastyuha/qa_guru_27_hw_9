package model;

import java.util.List;
import java.util.UUID;

public class LearningGroup {

    private UUID id = new UUID( 4053239666997989821L, -7608541298835023258L);
    private String name;
    private Integer totalStudents;
    private Integer totalScore;
    private boolean haveGraduated;
    private String[] subjects;
    private List<Student> students;

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getTotalStudents() {
        return totalStudents;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public boolean isHaveGraduated() {
        return haveGraduated;
    }

    public String[] getSubjects() {
        return subjects;
    }

    public List<Student> getStudents() {
        return students;
    }
}
