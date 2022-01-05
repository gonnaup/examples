package org.gonnaup.examples.javase.time;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.TimeZone;

/**
 * jdk8开始的java.time API
 *
 * @author gonnaup
 * @version created at 2022/1/3 20:24
 */
@Slf4j
public class TimeApi {

    /**
     * 日期格式化
     */
    public void format() {
        //返回月份为2，日期为当前日期（Day of Month）的LocalDate
        LocalDate date = LocalDate.now().withMonth(2);
        log.info("LocalDate withMonth_2 is {}", date);

        LocalTime time = LocalTime.now().withNano(0);
        log.info("LocalTime withNano_0 is {}", time);

        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatedDateTime = dateTimeFormatter.format(dateTime);
        log.info("format the dateTime is {}", formatedDateTime);

        // 字符串转日期
        LocalDate futureDate = LocalDate.of(2048, 1, 1);
        LocalDate.parse("2048-01-01");
        log.info("futureDate is {}", futureDate);

        LocalTime futureTime = LocalTime.of(12, 12, 12);
        LocalTime.parse("12:12:12");
        log.info("futureTime is {}", futureTime);

        LocalDateTime futureDateTime = LocalDateTime.of(futureDate, futureTime);
        LocalDateTime.parse("2048-01-01T12:12:12");
        log.info("futureDateTime is {}", futureDateTime);
    }

    /**
     * 日期间的计算
     */
    public void calculate() {
        //计算一周后的日期
        LocalDate today = LocalDate.now();
        LocalDate weekAfter1 = today.plusWeeks(1); //way 1
        LocalDate weekAfter2 = today.plus(1, ChronoUnit.WEEKS); //way 2
        assert weekAfter1.equals(weekAfter2);
        log.info("一周后日期：{}", weekAfter1);

        //计算两个日期间隔多少天，间隔多少年，多少月
        LocalDate date1 = LocalDate.parse("2048-02-26");
        LocalDate date2 = LocalDate.parse("2050-12-23");
        Period period = Period.between(date1, date2);
        log.info("{} 到 {} 相隔 {}年 {}月 {}日", date1, date2, period.getYears(), period.getMonths(), period.getDays());
        //period.getDays()得到的是抛去年月以外的天数
        //要求总天数用下面方法
        long periodDays = date2.toEpochDay() - date1.toEpochDay();
        log.info("{} 和 {} 间隔 {}天", date1, date2, periodDays);
    }

    /**
     * 获取指定日期
     */
    public void obtainSpecifiedDate() {
        LocalDate today = LocalDate.now();
        //获取当月第一天, with()调整日期的特定的时间域
        LocalDate firstDayOfThisMonth = today.with(TemporalAdjusters.firstDayOfMonth());
        //today.with(temporal -> temporal.with(ChronoField.DAY_OF_MONTH, 1));
        log.info("firstDayOfThisMonth {}", firstDayOfThisMonth);

        //获取本月最后一天
        LocalDate lastDayOfThisMonth = today.with(TemporalAdjusters.lastDayOfMonth());
        log.info("lastDayOfThisMonth {}", lastDayOfThisMonth);

        //取下一天
        LocalDate tomorrow = today.plusDays(1);
        log.info("tomorrow {}", tomorrow);

        //当年最后一天
        LocalDate lastDayOfThisYear = today.with(TemporalAdjusters.lastDayOfYear());
        log.info("lastDayOfThisYear {}", lastDayOfThisYear);

        //2048年最后一个周日
        LocalDate lastSundayOf2048 = LocalDate.parse("2048-12-31").with(TemporalAdjusters.lastInMonth(DayOfWeek.SUNDAY));
        log.info("lastSundayOf2048 {}", lastSundayOf2048);

    }

    /**
     * 时区
     */
    public void timeZone() {
        // JDK1.8之前
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        String shanghai = format.format(date);
        format.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));
        String tokyo = format.format(date);
        log.info("上海时区时间 {}，东京时区时间 {}", shanghai, tokyo);

        // JDK1.8引入了ZonedDateTime
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        log.info("当前时区时间 {}", zonedDateTime);

        //东京时间
        ZoneId jst = ZoneId.of(ZoneId.SHORT_IDS.get("JST"));
        log.info("东京时区 {}", jst.getRules());
        ZonedDateTime tokyoDateTime = zonedDateTime.withZoneSameInstant(jst);
        log.info("东京时间 {}", tokyoDateTime);
        log.info("东京时间转当地时间 {}", tokyoDateTime.toLocalDateTime());
        log.info("本地时区时间 {}", tokyoDateTime.toLocalDateTime().atZone(ZoneId.systemDefault()));
    }

    /**
     * {@link Clock}
     * {@link Instant}
     * {@link Duration}
     */
    public void clock_instant_duration() {
        Clock clock = Clock.systemDefaultZone();
        log.info("clock to millis {}", clock.millis());
        log.info("clock to instant {}", clock.instant());
        log.info("instant to Date {}", Date.from(clock.instant()));

        //注意：此处的参数必须是具体的日期时间且带有ZoneID，否则会抛出异常
//        Instant instant = Instant.from(LocalDateTime.now()); 抛出 异常
        Instant instant1 = Instant.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()));
        log.info("instant atStartOfDay milliseconds {}", instant1.toEpochMilli());
        Instant instant2 = Instant.from(LocalTime.now().atDate(LocalDate.now()).atZone(ZoneId.systemDefault()));
        log.info("instant localTime atDateNow milliseconds {}", instant2.toEpochMilli());
        log.info("LocalDateTime to instant {}", LocalDateTime.now().toInstant(ZoneOffset.ofHours(8)).toEpochMilli());
        log.info("instant from localDateTime {}", Instant.from(LocalDateTime.now().atZone(ZoneId.systemDefault())).toEpochMilli());

        Duration duration = Duration.ofHours(1);
        log.info("duration ofHours_1 {} => {} seconds", duration, duration.getSeconds());
        log.info("duration ofHours_1 plus minutes_20 is {} Milliseconds", duration.plus(30, ChronoUnit.MINUTES).toMillis());
    }


    public static void main(String[] args) {
        TimeApi timeApi = new TimeApi();
        timeApi.format();
        timeApi.calculate();
        timeApi.obtainSpecifiedDate();
        timeApi.timeZone();
        timeApi.clock_instant_duration();
    }
}
