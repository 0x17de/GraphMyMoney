package de.icetruck.graphmymoney;

/**
 * Created by hans on 13.04.14.
 */
public class Util {
    public static String shareToString(String share) {
        String[] s = share.split("/");

        long left = new Long(s[0]) * 100 / new Long(s[1]);

        return String.valueOf(left).replaceFirst("([0-9]{2})$", ".$1");
    }
}
