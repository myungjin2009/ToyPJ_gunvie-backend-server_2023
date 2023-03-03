package com.gunbro.gunvie.model.jpa;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //조회 기준 날짜(showRange)
    @Column(nullable = false)
    private LocalDate rangeDate;

    //영화 이름(movieNm)
    @Column(nullable = false)
    private String name;

    //박스오피스 순위
    @Column(nullable = false)
    private int rankMv;

    //영화의 대표코드
    @Column(nullable = false)
    private String movieCd;

    //누적매출액
    @Column(nullable = false)
    private String salesAcc;

    //1일 관람객수
    @Column(nullable = false)
    private String audiCnt;

    //누적 관람객수
    @Column(nullable = false)
    private String audiAcc;

    //영화 개봉일
    @Column(nullable = false)
    private String openDt;


    @Column(nullable = false)
    private LocalDate createdAt;
    @Column(nullable = false)
    private LocalDate updatedAt;
    private LocalDate deletedAt;


    public Movie() {
        //TODO : 이렇게 하면 객체 수정할 때에도 createdAt 값이 변경될 것 같은데..
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }

    public LocalDate getRangeDate() {
        return rangeDate;
    }

    public void setRangeDate(LocalDate rangeDate) {
        this.rangeDate = rangeDate;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRankMv() {
        return rankMv;
    }

    public void setRankMv(int rankMv) {
        this.rankMv = rankMv;
    }

    public String getMovieCd() {
        return movieCd;
    }

    public void setMovieCd(String movieCd) {
        this.movieCd = movieCd;
    }

    public String getSalesAcc() {
        return salesAcc;
    }

    public void setSalesAcc(String salesAcc) {
        this.salesAcc = salesAcc;
    }

    public String getAudiCnt() {
        return audiCnt;
    }

    public void setAudiCnt(String audiCnt) {
        this.audiCnt = audiCnt;
    }

    public String getAudiAcc() {
        return audiAcc;
    }

    public void setAudiAcc(String audiAcc) {
        this.audiAcc = audiAcc;
    }

    public String getOpenDt() {
        return openDt;
    }

    public void setOpenDt(String openDt) {
        this.openDt = openDt;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDate getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDate deletedAt) {
        this.deletedAt = deletedAt;
    }
}
