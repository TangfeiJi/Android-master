package com.project.movice.utils;

import android.content.res.Resources;

import com.project.movice.R;
import com.project.movice.application.MoviceApp;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wy on 2018/1/11 10:39.
 */


public class ConstantParams {

    /**
     * @param isKey true为key查value  false为value查key
     * @param str   查询值
     * @return
     */
    public static String gender(boolean isKey, String str) {
        Resources r = MoviceApp.getContext().getApplicationContext().getResources();
        Map<String, String> params = new HashMap<>();
        params.put(r.getString(R.string.sex_man), "1");
        params.put(r.getString(R.string.sex_female), "2");

        if (!isKey) {
            String key = "";
            //Map,HashMap并没有实现Iteratable接口.不能用于增强for循环.
            for (String getKey : params.keySet()) {
                if (params.get(getKey).equals(str)) {
                    key = getKey;
                }
            }
            return key;
        }
        return params.get(str);
    }

    /**
     * 借款用途
     *
     * @param isKey true为key查value  false为value查key
     * @param str
     * @return
     */
    public static String borrowingPurposes(boolean isKey, String str) {
        Resources r = MoviceApp.getContext().getApplicationContext().getResources();
        Map<String, String> params = new HashMap<>();
        params.put(r.getString(R.string.borrowing_purposes1), "1");
        params.put(r.getString(R.string.borrowing_purposes2), "2");
        params.put(r.getString(R.string.borrowing_purposes3), "3");
        params.put(r.getString(R.string.borrowing_purposes4), "4");
        params.put(r.getString(R.string.borrowing_purposes5), "5");
        params.put(r.getString(R.string.borrowing_purposes6), "6");
        params.put(r.getString(R.string.borrowing_purposes7), "7");
        params.put(r.getString(R.string.borrowing_purposes8), "8");
        params.put(r.getString(R.string.borrowing_purposes9), "9");
        params.put(r.getString(R.string.borrowing_purposes10), "10");
        params.put(r.getString(R.string.borrowing_purposes11), "11");
        params.put(r.getString(R.string.borrowing_purposes12), "12");
        params.put(r.getString(R.string.borrowing_purposes13), "13");
        params.put(r.getString(R.string.borrowing_purposes14), "14");
        params.put(r.getString(R.string.borrowing_purposes15), "15");
        return getString(isKey, str, params);
    }

    /**
     * 借款天数
     *
     * @return
     */
    public static String getHari(String key) {
        Resources r = MoviceApp.getContext().getApplicationContext().getResources();
        Map<String, String> params = new HashMap<>();
//        params.put(r.getString(R.string.seven_hari), Constant.hari_seven + "");
//        params.put(r.getString(R.string.fourteen_hari), Constant.hari_fourteen + "");
//        params.put(r.getString(R.string.fourteen_hari), Constant.hari_fourteen1 + "");
        params.put(Constant.hari_seven + "", r.getString(R.string.seven_hari));
        params.put(Constant.hari_fourteen + "", r.getString(R.string.fourteen_hari));
        params.put(Constant.hari_fourteen1 + "", r.getString(R.string.fourteen_hari));
        return params.get(key);
    }

    /**
     * 学历
     *
     * @param isKey true为key查value  false为value查key
     * @param str
     * @return
     */
    public static String getEducation(boolean isKey, String str) {
        Resources r =MoviceApp.getContext().getApplicationContext().getResources();
        Map<String, String> params = new HashMap<>();
        params.put(r.getString(R.string.education1), "1");
        params.put(r.getString(R.string.education2), "2");
        params.put(r.getString(R.string.education3), "3");
        params.put(r.getString(R.string.education4), "4");
        params.put(r.getString(R.string.education5), "5");
        params.put(r.getString(R.string.education6), "6");
        params.put(r.getString(R.string.education7), "7");
        params.put(r.getString(R.string.education8), "8");
        params.put(r.getString(R.string.education9), "9");
        return getString(isKey, str, params);
    }



    /**
     * 可接审核电话时间
     *
     * @param isKey true为key查value  false为value查key
     * @param str
     * @return
     */
    public static String getPhoneTime(boolean isKey, String str) {
        Resources r =MoviceApp.getContext().getApplicationContext().getResources();
        Map<String, String> params = new HashMap<>();
        params.put(r.getString(R.string.time1), "1");
        params.put(r.getString(R.string.time2), "2");
        params.put(r.getString(R.string.time3), "3");
        params.put(r.getString(R.string.time4), "4");
        params.put(r.getString(R.string.time5), "5");
        return getString(isKey, str, params);
    }


    /**
     * 婚姻
     *
     * @param isKey true为key查value  false为value查key
     * @param str
     * @return
     */
    public static String getMarital(boolean isKey, String str) {
        Resources r = MoviceApp.getContext().getApplicationContext().getResources();
        Map<String, String> params = new HashMap<>();
        params.put(r.getString(R.string.unmarried), "1");
        params.put(r.getString(R.string.married), "2");
        params.put(r.getString(R.string.cerai), "3");
        params.put(r.getString(R.string.duda), "4");
        return getString(isKey, str, params);
    }

    /**
     * 职业
     *
     * @param isKey true为key查value  false为value查key
     * @param str
     * @return
     */
    public static String getOccupation(boolean isKey, String str) {
        Resources r = MoviceApp.getContext().getApplicationContext().getResources();
        Map<String, String> params = new HashMap<>();
        params.put(r.getString(R.string.occupation1), "1");
        params.put(r.getString(R.string.occupation2), "2");
        params.put(r.getString(R.string.occupation3), "3");
        params.put(r.getString(R.string.occupation4), "4");
        params.put(r.getString(R.string.occupation5), "5");
        params.put(r.getString(R.string.occupation6), "6");
        params.put(r.getString(R.string.occupation7), "7");
        params.put(r.getString(R.string.occupation8), "8");
        params.put(r.getString(R.string.occupation9), "9");
        params.put(r.getString(R.string.occupation10), "10");
        params.put(r.getString(R.string.occupation11), "11");
        params.put(r.getString(R.string.occupation12), "12");
        params.put(r.getString(R.string.occupation13), "13");
        params.put(r.getString(R.string.occupation14), "14");
        params.put(r.getString(R.string.occupation15), "15");
        params.put(r.getString(R.string.occupation16), "16");
        params.put(r.getString(R.string.occupation17), "17");
        params.put(r.getString(R.string.occupation18), "18");
        params.put(r.getString(R.string.occupation19), "19");
        params.put(r.getString(R.string.occupation20), "20");
        params.put(r.getString(R.string.occupation21), "21");
        params.put(r.getString(R.string.occupation22), "22");
        params.put(r.getString(R.string.occupation23), "23");
        params.put(r.getString(R.string.occupation24), "24");
        params.put(r.getString(R.string.occupation25), "25");
        params.put(r.getString(R.string.occupation26), "26");
        params.put(r.getString(R.string.occupation27), "27");
        params.put(r.getString(R.string.occupation28), "28");
        params.put(r.getString(R.string.occupation29), "29");
        params.put(r.getString(R.string.occupation30), "30");
        params.put(r.getString(R.string.occupation31), "31");
        params.put(r.getString(R.string.occupation32), "32");
        params.put(r.getString(R.string.occupation33), "33");
        params.put(r.getString(R.string.occupation34), "34");
        params.put(r.getString(R.string.occupation35), "35");
        params.put(r.getString(R.string.occupation36), "36");
        params.put(r.getString(R.string.occupation37), "37");
        params.put(r.getString(R.string.occupation38), "38");
        params.put(r.getString(R.string.occupation39), "39");
        params.put(r.getString(R.string.occupation40), "40");
        return getString(isKey, str, params);
    }

    /**
     * 收入
     *
     * @param isKey true为key查value  false为value查key
     * @param str
     * @return
     */
    public static String getIncomeLevel(boolean isKey, String str) {
        Resources r = MoviceApp.getContext().getApplicationContext().getResources();
        Map<String, String> params = new HashMap<>();
        params.put(r.getString(R.string.income_level1), "1");
        params.put(r.getString(R.string.income_level5), "5");
        params.put(r.getString(R.string.income_level6), "6");
        params.put(r.getString(R.string.income_level7), "7");
        params.put(r.getString(R.string.income_level8), "8");
        params.put(r.getString(R.string.income_level9), "9");
        params.put(r.getString(R.string.income_level10), "10");
        params.put(r.getString(R.string.income_level11), "11");
        params.put(r.getString(R.string.income_level12), "12");
        params.put(r.getString(R.string.income_level13), "13");
        params.put(r.getString(R.string.income_level14), "14");
        params.put(r.getString(R.string.income_level15), "15");
        params.put(r.getString(R.string.income_level16), "16");
        params.put(r.getString(R.string.income_level4), "4");
        return getString(isKey, str, params);
    }

    /**
     * 联系人1关系
     *
     * @param isKey true为key查value  false为value查key
     * @param str
     * @return
     */
    public static String getContactRelationship(boolean isKey, String str) {
        Resources r = MoviceApp.getContext().getApplicationContext().getResources();
        Map<String, String> params = new HashMap<>();
        params.put(r.getString(R.string.contact_relationship1), "1");
        params.put(r.getString(R.string.contact_relationship2), "2");
        params.put(r.getString(R.string.contact_relationship3), "3");
//        params.put(r.getString(R.string.contact_relationship5), "5");11111111111
        params.put(r.getString(R.string.contact_relationship51), "5");
        params.put(r.getString(R.string.contact_relationship52), "5");
        params.put(r.getString(R.string.contact_relationship53), "5");
        params.put(r.getString(R.string.contact_relationship54), "5");
        return getString(isKey, str, params);
    }

    /**
     * 联系人2关系
     *
     * @param isKey true为key查value  false为value查key
     * @param str
     * @return
     */
    public static String getColleague(boolean isKey, String str) {
        Resources r = MoviceApp.getContext().getApplicationContext().getResources();
        Map<String, String> params = new HashMap<>();
        params.put(r.getString(R.string.contact_relationship1), "1");
        params.put(r.getString(R.string.contact_relationship2), "2");
        params.put(r.getString(R.string.contact_relationship3), "3");
        params.put(r.getString(R.string.contact_relationship4), "4");
        params.put(r.getString(R.string.contact_relationship5), "5");

        params.put(r.getString(R.string.contact_relationship11), "11");
        params.put(r.getString(R.string.contact_relationship12), "12");
        params.put(r.getString(R.string.contact_relationship21), "21");
        params.put(r.getString(R.string.contact_relationship22), "22");

        params.put(r.getString(R.string.contact_relationship51), "51");
        params.put(r.getString(R.string.contact_relationship52), "52");
        params.put(r.getString(R.string.contact_relationship53), "53");
        params.put(r.getString(R.string.contact_relationship54), "54");
        params.put(r.getString(R.string.contact_relationship41), "41");
        params.put(r.getString(R.string.contact_relationship42), "42");
        return getString(isKey, str, params);
    }



    /**
     * 公司性质
     *
     * @param isKey true为key查value  false为value查key
     * @param str
     * @return
     */
    public static String getCorporationNature(boolean isKey, String str) {
        Resources r = MoviceApp.getContext().getApplicationContext().getResources();
        Map<String, String> params = new HashMap<>();
        params.put(r.getString(R.string.state_owned), "1");
        params.put(r.getString(R.string.foreign_capital), "2");
        params.put(r.getString(R.string.the_private), "3");
        params.put(r.getString(R.string.joint_venture), "4");
        return getString(isKey, str, params);
    }

    /**
     * 工作年限
     *
     * @param isKey true为key查value  false为value查key
     * @param str
     * @return
     */
    public static String getWorkingSeniority(boolean isKey, String str) {
        Resources r = MoviceApp.getContext().getApplicationContext().getResources();
        Map<String, String> params = new HashMap<>();
        params.put(r.getString(R.string.working_seniority1), "1");
        params.put(r.getString(R.string.working_seniority2), "2");
        params.put(r.getString(R.string.working_seniority3), "3");
        params.put(r.getString(R.string.working_seniority4), "4");
        params.put(r.getString(R.string.working_seniority5), "5");
        return getString(isKey, str, params);
    }


    /**
     * 居住类型
     *
     * @param isKey true为key查value  false为value查key
     * @param str
     * @return
     */
    public static String getHunianKetik(boolean isKey, String str) {
        Resources r = MoviceApp.getContext().getApplicationContext().getResources();
        Map<String, String> params = new HashMap<>();
        params.put(r.getString(R.string.hunian_ketik1), "1");
        params.put(r.getString(R.string.hunian_ketik2), "2");
        params.put(r.getString(R.string.hunian_ketik3), "3");
        params.put(r.getString(R.string.hunian_ketik4), "4");
        params.put(r.getString(R.string.hunian_ketik5), "5");
        params.put(r.getString(R.string.hunian_ketik6), "6");
        return getString(isKey, str, params);
    }

    /**
     * 居住时长
     *
     * @param isKey true为key查value  false为value查key
     * @param str
     * @return
     */
    public static String getDurasiHunian(boolean isKey, String str) {
        Resources r = MoviceApp.getContext().getApplicationContext().getResources();
        Map<String, String> params = new HashMap<>();
        params.put(r.getString(R.string.durasi_hunian1), "1");
        params.put(r.getString(R.string.durasi_hunian2), "2");
        params.put(r.getString(R.string.durasi_hunian3), "3");
        params.put(r.getString(R.string.durasi_hunian4), "4");
        params.put(r.getString(R.string.durasi_hunian5), "5");
        return getString(isKey, str, params);
    }

    /**
     * 孩子数量
     *
     * @param isKey true为key查value  false为value查key
     * @param str
     * @return
     */
    public static String getJumlahAnak(boolean isKey, String str) {
        Resources r = MoviceApp.getContext().getApplicationContext().getResources();
        Map<String, String> params = new HashMap<>();
        params.put(r.getString(R.string.jumlah_anak1), "1");
        params.put(r.getString(R.string.jumlah_anak2), "2");
        params.put(r.getString(R.string.jumlah_anak3), "3");
        params.put(r.getString(R.string.jumlah_anak4), "4");
        params.put(r.getString(R.string.jumlah_anak5), "5");
        params.put(r.getString(R.string.jumlah_anak6), "6");
        return getString(isKey, str, params);
    }

    private static String getString(boolean isKey, String str, Map<String, String> params) {
        if (!isKey) {
            String key = "";
            //Map,HashMap并没有实现Iteratable接口.不能用于增强for循环.
            for (String getKey : params.keySet()) {
                if (params.get(getKey).equals(str)) {
                    key = getKey;
                }
            }
            return key;
        }
        return params.get(str);
    }

}
