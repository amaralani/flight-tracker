package ir.mfava.modfava.pardazesh.util;

import com.ibm.icu.util.Calendar;
import org.joda.time.DateTime;

import java.util.Date;

/**
 * @author Drago
 */
public class DateUtils {

    /**
     * converts input julian date and return persian date
     *
     * @param date julian date
     * @return persian date
     */
    public static String convertJulianToPersian(Date date) {
        return convertJulianToPersian(date, "EEEE d MMMM y");
    }

    public static String convertJulianToPersian(Date date, String format) {
        if (date == null) {
            return "";
        }
        Calendar calendar = j2p(date);

        com.ibm.icu.util.ULocale uLocale = com.ibm.icu.util.ULocale.createCanonical("fa_IR");
        com.ibm.icu.text.SimpleDateFormat sdf = (com.ibm.icu.text.SimpleDateFormat) calendar.getDateTimeFormat(0, 0, uLocale);
        sdf.applyPattern(format);
        return sdf.format(calendar.getTime());
    }

    public static String convertJulianToPersianForUi(Date date) {
        if (date == null) {
            return "";
        }
        Calendar calendar = j2p(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return year + "-" + (month + 1) + "-" + day;

    }

    public static Integer convertJulianToPersianForBirthDay(Date date) {
        if (date == null) {
            return 0;
        }
        Calendar calendar = j2p(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String month1, day1;
        month1 = (month < 10) ? ("0" + month) : ("" + month);
        day1 = (day < 10) ? ("0" + day) : ("" + day);

        return Integer.parseInt(year + month1 + day1);

    }

    public static String convertJulianToPersianForUiWithSlash(Date date) {
        if (date == null) {
            return "";
        }
        Calendar calendar = j2p(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String m = (month < 10) ? ("0" + month) : ("" + month);
        String d = (day < 10) ? ("0" + day) : ("" + day);
        return year + "/" + m + "/" + d;
    }

    public static Calendar j2p(Date date) {
        if (date == null) {
            return null;
        }
        String timeZoneId = "Iran";
        String loc = "fa_IR";

        com.ibm.icu.util.TimeZone timeZone = com.ibm.icu.util.TimeZone.getTimeZone(timeZoneId);
        com.ibm.icu.util.ULocale uLocale = com.ibm.icu.util.ULocale.createCanonical(loc);
        Calendar calendar = new com.ghasemkiani.util.icu.PersianCalendar(timeZone, uLocale);
        calendar.setTimeInMillis(new DateTime(date).getMillis());
        return calendar;
    }

    public static String todayAsPersianDate(String format) {
        String timeZoneId = "Iran";
        String loc = "fa_IR";

        com.ibm.icu.util.TimeZone timeZone = com.ibm.icu.util.TimeZone.getTimeZone(timeZoneId);
        com.ibm.icu.util.ULocale uLocale = com.ibm.icu.util.ULocale.createCanonical(loc);
        Calendar calendar = new com.ghasemkiani.util.icu.PersianCalendar(timeZone, uLocale);
        calendar.setTimeInMillis(new DateTime(new Date()).getMillis());
        //change IRST to IRDT(+1)
        // calendar.add(Calendar.HOUR_OF_DAY, 1);

        com.ibm.icu.text.SimpleDateFormat sdf = (com.ibm.icu.text.SimpleDateFormat) calendar.getDateTimeFormat(0, 0, uLocale);
        sdf.applyPattern(format);
        return sdf.format(calendar.getTime());
    }

    public static String formatPersianDate(int year, int month, int day_of_month, String format) {
        String timeZoneId = "Iran";
        String loc = "fa_IR";

        com.ibm.icu.util.TimeZone timeZone = com.ibm.icu.util.TimeZone.getTimeZone(timeZoneId);
        com.ibm.icu.util.ULocale uLocale = com.ibm.icu.util.ULocale.createCanonical(loc);
        Calendar calendar = new com.ghasemkiani.util.icu.PersianCalendar(timeZone, uLocale);

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day_of_month);

        com.ibm.icu.text.SimpleDateFormat sdf = (com.ibm.icu.text.SimpleDateFormat) calendar.getDateTimeFormat(0, 0, uLocale);
        sdf.applyPattern(format);
        return sdf.format(calendar.getTime()).replace("\u200c", "");
    }

    public static String formatDateAsIntToString(int date) {
        String dateAsString = String.valueOf(date);
        if (dateAsString.length() != 8)
            return null;
        return dateAsString.substring(0, 4) + "/" + dateAsString.substring(4, 6) + "/" + dateAsString.substring(6, 8);

    }

    public static Calendar p2j(int year, int month, int day_of_month) {
        String timeZoneId = "Iran";
        String loc = "fa_IR";

        com.ibm.icu.util.TimeZone timeZone = com.ibm.icu.util.TimeZone.getTimeZone(timeZoneId);
        com.ibm.icu.util.ULocale uLocale = com.ibm.icu.util.ULocale.createCanonical(loc);
        Calendar calendar = new com.ghasemkiani.util.icu.PersianCalendar(timeZone, uLocale);

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day_of_month);

        return clearTime(calendar);
    }

    /**
     * @param year         year
     * @param month        0-11
     * @param day_of_month 1-31
     * @return
     */
    public static Date convertPersianToJulian(int year, int month, int day_of_month) {

        Calendar calendar = p2j(year, month, day_of_month);
        return new DateTime(calendar.getTimeInMillis()).toDate();
    }

    /**
     * @param persianDate 1390/01/01 1390-01-01
     * @return
     */
    public static Date convertPersianToJulian(String persianDate) {
        String[] parts = persianDate.split("[/-]");
        return convertPersianToJulian(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]) - 1, Integer.parseInt(parts[2]));
    }

    /**
     * @param persianDate 1390/11 1390-12
     * @return
     */
    public static Date convertPersianMountYerToJulian(String persianDate) {
        String[] parts = persianDate.split("[/-]");
        return convertPersianToJulian(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]) - 1, 1);
    }

    public static String addDaysForPersianDate(String PesrianDate, int days) {
        String[] parts = PesrianDate.split("[/-]");
        Calendar c = p2j(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]) - 1, Integer.parseInt(parts[2]));
        c.add(Calendar.DATE, days);
        return convertJulianToPersianForUiWithSlash(new DateTime(c.getTimeInMillis()).toDate());
    }

    public static int compareIgnoreTime(Date date1, Date date2) {
        return clearTime(date1).compareTo(clearTime(date2));
    }

    public static Date clearTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal = clearTime(cal);
        return cal.getTime();
    }

    public static Calendar clearTime(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    public static long differenceInDays(Date date1, Date date2) {
        return (clearTime(date2).getTime() - clearTime(date1).getTime()) / (24 * 60 * 60 * 1000);
    }
}
