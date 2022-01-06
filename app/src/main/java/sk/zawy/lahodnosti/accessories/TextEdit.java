package sk.zawy.lahodnosti.accessories;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import sk.zawy.lahodnosti.R;

public class TextEdit {

    public static String DAY_MONTH_DAYNAME="dd.MM. (EEEE)";
    public static String DAY_MONTH="dd.MM.";
    public static String DAY_MONTH_YEAR="dd.MM.yyyy";

    public static String notNull(String value) {
        if(value==null){
            return "";
        }else {
            return value;
        }
    }

    public static String localDateFormat(String value,String PATTERN){
        String newDateFormat="";
        try {
            Date date=new SimpleDateFormat("yyyy-MM-dd").parse(value);
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat(PATTERN);
            newDateFormat=simpleDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return newDateFormat;
    }

    /** For String URL from resource for change char '&' */
    public static String changeAndSymbol(String url){
        String editedUrl=url.replaceAll("&amp;","&");
        return editedUrl;
    }


    public static int textToNumber(String textNumber){
        if(textNumber!=null){
            try{
                return Integer.parseInt(textNumber);
            }catch (NumberFormatException e){
                return 0;
            }
        }else{
            return 0;
        }
    }
}

