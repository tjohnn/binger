package ng.max.binger.utils;

import java.util.Date;
import java.util.Locale;

public class DateUtlils {

    public static String formatDateYear(Date date){
        return String.format(Locale.ENGLISH, "%1$tY", date);
    }

}
