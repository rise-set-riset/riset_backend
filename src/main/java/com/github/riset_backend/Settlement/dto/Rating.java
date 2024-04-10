package com.github.riset_backend.Settlement.dto;


//ㅎㅎㅎ
public enum Rating {
    Archbishop(7000000),     // 대주교
    Baroness(6000000),       // 남작부인
    Knight(5000000),         // 기사
    Cook(4000000),           // 요리사
    Shepherdess(3000000),    // 양치기
    Flush(2000000);          // 광부

    private final int salary;

    Rating(int salary) {
        this.salary = salary;
    }

    public int getSalary() {
        return salary;
    }
}

