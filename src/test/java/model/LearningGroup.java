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
    private List<Students> students;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(Integer totalStudents) {
        this.totalStudents = totalStudents;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public boolean isHaveGraduated() {
        return haveGraduated;
    }

    public void setHaveGraduated(boolean haveGraduated) {
        this.haveGraduated = haveGraduated;
    }

    public String[] getSubjects() {
        return subjects;
    }

    public void setSubjects(String[] subjects) {
        this.subjects = subjects;
    }

    public List<Students> getStudents() {
        return students;
    }

    public void setStudents(List<Students> students) {
        this.students = students;
    }
}
