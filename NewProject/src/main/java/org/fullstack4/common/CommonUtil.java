package org.fullstack4.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CommonUtil {
    public static boolean loginCheck(HttpSession session) {
        return session.getAttribute("user_id") != null;
    }

    public static boolean isValue(String str) {
        return str!=null && !str.isEmpty() && !str.isBlank() ;
    }
    public static boolean SaveIdcheck(HttpServletRequest req) {
        return CookieUtil.getCookieValue(req, "user_id") != "";
    }

    public static List<LocalDate> TakeLocaldDate() {
        // 오늘 날짜를 가져옵니다.
        LocalDate currentDate = LocalDate.now();

        // 날짜 형식을 지정합니다.
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<LocalDate> localDateList = new ArrayList<>();
        // 7일 뒤까지의 날짜와 해당하는 요일을 출력합니다.
        for (int i = 0; i < 7; i++) {

            LocalDate formattedDate = currentDate.plusDays(i);
            localDateList.add(formattedDate);

        }
        return localDateList;
    }

    public static List<String> TakeDate(){

        // 오늘 날짜를 가져옵니다.
        LocalDate currentDate = LocalDate.now();

        List<String> dateList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {

            // 해당하는 요일을 가져옵니다.
            DayOfWeek dayOfWeek = currentDate.plusDays(i).getDayOfWeek();

            // 요일을 문자열로 변환하여 출력합니다.
            String dayOfWeekString = dayOfWeek.toString();

            // 첫 글자를 대문자로 변환합니다.
            dayOfWeekString = dayOfWeekString.substring(0, 1) + dayOfWeekString.substring(1).toLowerCase();

            dateList.add(dayOfWeekString);

        }
        return dateList;
    }
}
