package com.project.movice.utils;

/**
 * Created by wy on 2018/2/5 19:43.
 */


public class IDcard {


    public boolean isIdcard(String s) {
        if (s == null || s.length() != 16)
            return false;
        // 校验年份
//        String year = s.length() == 15 ? "19" + s.substring(6, 8) : s.substring(6, 10);
//        final int iyear = Integer.parseInt(year);
//        if (iyear < 1900 || iyear > Calendar.getInstance().get(Calendar.YEAR))
//            return false;// 1900年的PASS，超过今年的PASS
        // 校验月份
        String month = s.substring(8, 10);
        final int imonth = Integer.parseInt(month);
        if (imonth < 1 || imonth > 12) {
            return false;
        }
        // 校验天数
        String day = s.substring(6, 8);
        final int iday = Integer.parseInt(day);
        if (!isday(iday))
            return false;
        return true;
    }

    static boolean isday(int iday) {
        if (iday > 40) {//大于40为女，需要减去40才是日
            iday = iday - 40;
        }
        if (iday < 1 || iday > 31)
            return false;
        return true;
    }


    /**
     * @param idcard
     * @return 1男 2女
     */
    public String getGender(String idcard) {
        String gender = "";
        if (isIdcard(idcard)) {
            // 获取性别
            String day = idcard.substring(6, 8);
            final int iday = Integer.parseInt(day);
            if (iday > 40) {//大于40为女，需要减去40才是日
                gender = "2";
            } else {
                gender = "1";
            }
        }
        return gender;
    }

}
