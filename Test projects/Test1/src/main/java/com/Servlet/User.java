package com.Servlet;

public class User {
    private String name;
    private Integer age;
    private String gender;
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public void setAge(int age) {
        this.age = age;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    @Override
    public String toString() {
        return "User [name=" + name + ", age=" + age + ", gender=" + gender + "]";
    }
}
