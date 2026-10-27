package com.example.midexam;

public class StudentModel {
    static final int MIN_AGE = 17;
    static final int MAX_AGE = 50;

    private int id;
    private String name;
    private String studentId;
    private int age;

    public StudentModel(String name, String studentId, int age) {
        this.name = name;
        this.studentId = studentId;
        this.age = age;
    }

    public StudentModel(int id, String name, String studentId, int age) {
        this.id = id;
        this.name = name;
        this.studentId = studentId;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentId() {
        return studentId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
