package com.example.springApi.controller.formatter;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

//날싸 시간은 브라우저에서 문자열로 전송되지만
//서버에서는 LocalDate,LocalDateTime 변환되어야 한다.
//스프링MVC 동작 과정에서 사용될 수 있도록 설정을 추가해 주어야 합니다.
//WebMvcConfigurer 인터페이스를 구현하고 addFormatters()를 오버라이딩하여
//FormatterRegistry에 LocalDateFormatter를 추가합니다.
public class LocalDateFormatter implements Formatter<LocalDate> {

    @Override
    public LocalDate parse(String text, Locale locale) throws ParseException {
        return LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @Override
    public String print(LocalDate object, Locale locale) {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(object);
    }
}
