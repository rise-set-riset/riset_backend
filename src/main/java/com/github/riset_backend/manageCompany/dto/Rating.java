package com.github.riset_backend.manageCompany.dto;

//ㅎㅎㅎ
public enum Rating {

    CHALLENGER(7000000),
    DIAMOND(6000000),
    PLATINUM(5000000),
    GOLD(4000000),
    SILVER(3000000),
    BRONZE(2000000);

    private final int salary;

    Rating(int salary) {
        this.salary = salary;
    }

    public int getSalary() {
        return salary;
    }
}