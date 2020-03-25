package com.itep.aidl;

/**
 * Created by Administrator on 2020/3/24 0024.
 */

public class StudentTest {
    private String name;
    private String age;
    public StudentTest(String name, String age) {
        super();
        this.name = name;
        this.age = age;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAge() {
        return age;
    }
    public void setAge(String age) {
        this.age = age;
    }

}

