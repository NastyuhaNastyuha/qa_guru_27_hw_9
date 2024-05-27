package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Student {

    @JsonProperty("FirstName")
    private String firstName;

    @JsonProperty("LastName")
    private String lastName;

    @JsonProperty("Score")
    private Integer score;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getScore() {
        return score;
    }
}
