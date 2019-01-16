package com.github.lookchunkuen.petlloassignment;

public class TrainService {

    private String CourseName;
    private String CourseFees;
    private String CourseDescription;
    private String FullDescription;

    public TrainService(String courseName, String courseFees, String courseDescription, String fullDescription) {
        CourseName = courseName;
        CourseFees = courseFees;
        CourseDescription = courseDescription;
        FullDescription = fullDescription;
    }

    public TrainService() {

    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public String getCourseFees() {
        return CourseFees;
    }

    public void setCourseFees(String courseFees) {
        CourseFees = courseFees;
    }

    public String getCourseDescription() {
        return CourseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        CourseDescription = courseDescription;
    }

    public String getFullDescription() {
        return FullDescription;
    }

    public void setFullDescription(String fullDescription) {
        FullDescription = fullDescription;
    }
}
