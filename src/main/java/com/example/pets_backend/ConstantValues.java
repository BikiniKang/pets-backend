package com.example.pets_backend;


import com.auth0.jwt.algorithms.Algorithm;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

public class ConstantValues {
    public static final String AUTHORIZATION_PREFIX = "Bearer ";
    public static final String SECRET = "secret";
    public static final Long EXPIRATION_TIME_MILLIS = 1000L * 60 * 60 * 24;
    public static final String LOGIN = "/login";
    public static final String REGISTER = "/register";
    public static final String VERIFY = "/verify";
    public static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET.getBytes(StandardCharsets.UTF_8));
    public static final String DEFAULT_IMAGE = "https://firebasestorage.googleapis.com/v0/b/pet-tracking-app-51857.appspot.com/o/4EL4hp_qRUYMzzal_G29f_userAvatar_1660437446659?alt=media&token=74ffbb41-8336-4770-a279-c60f5f320ac94";
    public static final String DEFAULT_IMAGE_PET = "https://firebasestorage.googleapis.com/v0/b/pet-tracking-app-51857.appspot.com/o/4EL4hp_qRUYMzzal_G29f_petAvatar_1666785259498?alt=media&token=e85467fc-13c1-4cd5-80a7-9dbb3efdeaa1";
    public static final String DEFAULT_ADDRESS = "";
    public static final String DEFAULT_PHONE = "";
    public static final String DEFAULT_EVENT_TYPE = "";
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm";
    public static final String ICS_TIME_PATTERN = "yyyyMMdd'T'HHmmssX";
    public static final List<String> RECORD_TYPES = Arrays.asList("Invoice", "Medication", "Vaccination");
    public static final String DELETED_PET_ID = "deleted_pet_id";
    public static final String TEAM_EMAIL = "pet_pocket@outlook.com";
    public static final String TIMEZONE = "Australia/Sydney";
    public static final String TEMPLATE_EVENT = "template-event.ftlh";
    public static final String TEMPLATE_UPCOMING_TASKS = "template-tasks.ftlh";
    public static final String TEMPLATE_OVERDUE_TASKS = "template-tasks-overdue.ftlh";
    public static final String TEMPLATE_BOOKING_INVITE = "template-booking-invite.ftlh";
    public static final String TEMPLATE_BOOKING_CONFIRM = "template-booking-confirm.ftlh";
    public static final String TEMPLATE_BOOKING_CANCEL = "template-booking-cancel.ftlh";
    public static final String OVERDUE_TASKS_NOTIFY_TIME = "09:00";
    public static final int DAYS_TO_ARCHIVE = 3;
    public static final String WEB_PREFIX = "https://pets-tracking.azurewebsites.net/";

    /**
     * Get all dates of the given month
     * @param month 'yyyy-MM'
     * @return a list of date strings, i.e., ["2022-10-01", "2022-10-02", ..., "2022-10-31"]
     */
    public static List<String> getDateSpanOfMonth(String month) {
        LocalDate beginDate = LocalDate.parse(month + "-01");
        LocalDate endDate = beginDate.with(lastDayOfMonth());
        if (beginDate.equals(endDate)) {
            return List.of(beginDate.toString());
        }
        return beginDate.datesUntil(endDate.plusDays(1)).map(LocalDate::toString).toList();
    }

    /**
     * Get all dates within the month such that from <= date <= to
     * Example: getDateSpan("2022-09-25", "2022-10-02", "2022-10") => ["2022-10-01", "2022-10-02"]
     * @param from 'yyyy-MM-dd'
     * @param to 'yyyy-MM-dd'
     * @param month 'yyyy-MM'
     * @return a list of date strings
     */
    public static List<String> getDateSpan(String from, String to, String month) {
        if (from.substring(0, 7).compareTo(month) <= 0 && to.substring(0, 7).compareTo(month) >= 0) {
            if (from.equals(to)) { // if the start and the end are in the same day, just return this day
                return List.of(from);
            }
            LocalDate dateFrom = LocalDate.parse(from);
            LocalDate dateTo = LocalDate.parse(to).plusDays(1); // plus 1 day to make sure that dateTo is included in the list
            return dateFrom.datesUntil(dateTo)
                    .map(LocalDate::toString)
                    .filter(s -> s.startsWith(month))
                    .toList();
        } else {
            return new ArrayList<>();
        }
    }
}
