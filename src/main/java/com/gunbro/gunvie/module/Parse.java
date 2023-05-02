package com.gunbro.gunvie.module;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Parse {


    //String 형태의 년 월 일 날짜를, LocalDate 형태의 날짜로 변환한다.
    //String : "20201231"  >>  LocalDate : 2020-12-31
    public static LocalDate StringToLocalDateParse(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate localDate = LocalDate.parse(date, formatter);    //일일 날짜
        return localDate;
    }
}
