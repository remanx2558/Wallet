package com.yashwant.gahlot.Wallet.utilities;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UtilityMethods {

    public static String getCurrentTime() {
        return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
    }
}