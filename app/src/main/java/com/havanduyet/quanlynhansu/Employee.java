package com.havanduyet.quanlynhansu;
public class Employee {
 public String code, name, gender, hometown, education, photoUri, department;
 public int birthYear;
 public long salary;
 public Employee(String code,String name,int birthYear,String gender,String hometown,String education,String photoUri,long salary,String department){this.code=code;this.name=name;this.birthYear=birthYear;this.gender=gender;this.hometown=hometown;this.education=education;this.photoUri=photoUri;this.salary=salary;this.department=department;}
}
