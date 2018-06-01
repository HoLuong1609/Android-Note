package com.ifs.vpbscustomer.common;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Pair;
import android.widget.EditText;

import com.ifs.core.datetimeutils.DateTimeUtils;
import com.ifs.vpbscustomer.R;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TimeZone;
import java.util.TreeMap;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Utils {
    public static <T> Observable.Transformer<T, T> applySchedulers() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public static String getCurrency(Double amount) {
        String price;
        NumberFormat format =
                new DecimalFormat("#,##0.00");// #,##0.00 ¤ (¤:// NumberCurrency symbol)
        format.setCurrency(Currency.getInstance(Locale.US));//Or default locale


        price = format.format(amount);
        //price = price.replaceAll(",", "\\.");

        if (price.endsWith(".00")) {
            int centsIndex = price.lastIndexOf(".00");
            if (centsIndex != -1) {
                price = price.substring(0, centsIndex);
            }
        }
        //price = String.convertNumberToCurency("%s đ", price);
        return price;
    }

    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        return currentDateandTime;
    }

    public static synchronized String convertDate(String str_date) {
        synchronized (Utils.class) {
            if (str_date == null) {
                return "";
            }

            if (str_date.length() == 0) {
                return "";
            }
            DateTimeUtils.setTimeZone("GMT");
            Date date = DateTimeUtils.formatDate(str_date, Locale.getDefault());
            String x = DateTimeUtils.formatWithPattern(date, "dd/MM/yyyy");
            if (x.contains("01/01/0001")) {
                return "";
            } else {
                return x;
            }
        }
    }

    public static String convertToDate(String str_date) {
        if (str_date == null) {
            return "";
        }

        if (str_date.length() == 0) {
            return "";
        }

        DateTimeUtils.setTimeZone("GMT");
        Date date = DateTimeUtils.formatDate(str_date);
        return toGmtString(date);
    }

    private static String toGmtString(Date date) {
        //date formatter
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sssssssz");
        //getting default timeZone
        TimeZone timeZone = TimeZone.getDefault();
        //getting current time
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //adding / substracting curren't timezone's offset
        cal.add(Calendar.MILLISECOND, -1 * timeZone.getRawOffset());
        //formatting and returning string of date
        return sd.format(cal.getTime()).replace("GMT", "");
    }

    public static String convertTime(String str_date, String fomatDate) {
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = null;
        String time = "";
        try {
            date = dt.parse(str_date);
            SimpleDateFormat dt1 = new SimpleDateFormat(fomatDate);
            time = dt1.format(date);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }


    public static boolean validateEditext(EditText[] fields) {
        for (int i = 0; i < fields.length; i++) {
            EditText currentField = fields[i];
            if (currentField.getText().toString().length() <= 0) {
                return false;
            }
        }
        return true;
    }


    public static String formatCurrency(Double amount) {
        if (amount != null)
            return NumberFormat.getInstance().format(amount.longValue());

        return "";
    }

    public static String formatCurrency(int amount) {
        return NumberFormat.getInstance().format(amount);

    }

    public static String formatCurrency(Long amount) {
        if (amount != null)
            return NumberFormat.getInstance().format(amount);

        return "";
    }


    public static String formatCurrency(BigDecimal amount) {
        if (amount != null)
            return NumberFormat.getInstance().format(amount.toBigInteger());

        return "";
    }

    public static String formatCurrency(String price) {
        if (price != null)
            return convertNumberToCurency(Double.valueOf(price).longValue());

        return "";
    }

    public static String formatPercentage(double d) {
        return String.format("%.2f%s", d, "%");
    }

    public static String formatDoubleToString(double d) {
        return String.format(Locale.US, "%.2f", d);
    }


    public static String formatPercentage(BigDecimal n) {
        Double d = n.doubleValue();
        return String.format("%.2f%s", d, "%");
    }


    public static String formatDoubleToString(String price) {
        if (price.endsWith(".00")) {
            int centsIndex = price.lastIndexOf(".00");
            if (centsIndex != -1) {
                price = price.substring(0, centsIndex);
            }
        }

        if (price.endsWith(",00")) {
            int centsIndex = price.lastIndexOf(",00");
            if (centsIndex != -1) {
                price = price.substring(0, centsIndex);
            }
        }

        if (price.endsWith(",0")) {
            int centsIndex = price.lastIndexOf(",0");
            if (centsIndex != -1) {
                price = price.substring(0, centsIndex);
            }
        }

        if (price.endsWith(".0")) {
            int centsIndex = price.lastIndexOf(".0");
            if (centsIndex != -1) {
                price = price.substring(0, centsIndex);
            }
        }

        return price.replace(".", ",");
    }


    private static final NavigableMap<Long, String> suffixes = new TreeMap<>();

    static {
        suffixes.put(1_000L, " Nghìn");
        suffixes.put(1_000_000L, " Triệu");
        suffixes.put(1_000_000_000L, " Tỷ");
        suffixes.put(1_000_000_000_000L, " Nghìn Tỷ");
        suffixes.put(1_000_000_000_000_000L, " Triệu Tỷ");
        suffixes.put(1_000_000_000_000_000_000L, "Trăm Triệu Tỷ");
    }

    public static String convertNumberToCurency(long value) {
        //Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
        if (value == Long.MIN_VALUE) return convertNumberToCurency(Long.MIN_VALUE + 1);
        if (value < 0) return "-" + convertNumberToCurency(-value);
        if (value < 1000) return Long.toString(value); //deal with easy case

        Map.Entry<Long, String> e = suffixes.floorEntry(value);
        Long divideBy = e.getKey();
        String suffix = e.getValue();

        long truncated = value / (divideBy / 10); //the number part of the output times 10
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? ((truncated / 10d) + suffix).replace(".", ",") : ((truncated / 10) + suffix).replace(".", ",");
    }

    public static String numberToCurrency(Double d) {
        long value = d.longValue();
        //Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
        if (value == Long.MIN_VALUE) return convertNumberToCurency(Long.MIN_VALUE + 1);
        if (value < 0) return "-" + convertNumberToCurency(-value);
        if (value < 1000) return Long.toString(value); //deal with easy case

        Map.Entry<Long, String> e = suffixes.floorEntry(value);
        Long divideBy = e.getKey();
        String suffix = e.getValue();

        long truncated = value / (divideBy / 1000); //the number part of the output times 1000
        boolean hasDecimal = truncated < 1000000 && (truncated / 1000d) != (truncated / 1000);
        return hasDecimal ? ((truncated / 1000d) + "_" + suffix).replace(",", ".") : ((truncated / 1000) + "_" + suffix).replace(",", ".");
    }

    public static String convertNumberToCurency(Double d) {
        long value = d.longValue();
        //Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
        if (value == Long.MIN_VALUE) return convertNumberToCurency(Long.MIN_VALUE + 1);
        if (value < 0) return "-" + convertNumberToCurency(-value);
        if (value < 1000) return Long.toString(value); //deal with easy case

        Map.Entry<Long, String> e = suffixes.floorEntry(value);
        Long divideBy = e.getKey();
        String suffix = e.getValue();

        long truncated = value / (divideBy / 10); //the number part of the output times 10
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? ((truncated / 10d) + suffix).replace(".", ",") : ((truncated / 10) + suffix).replace(".", ",");
    }

    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            assert cursor != null;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static int getsScreenWidthDp(Context context) {
        Configuration configuration = context.getResources().getConfiguration();
        return configuration.screenWidthDp;
    }

    public static int getScreenHeightDp(Context context) {
        Configuration configuration = context.getResources().getConfiguration();
        return configuration.screenHeightDp;
    }

    public static Pair<String, String> getStatus(String status) {
        String stt = "";
        String color = "#E46C0A";
        if (Resource.ContractStatus[1].equals(status)) {
            stt = ContractUtils.ContractStatus.Active.getString();
            color = "#376092";
        } else if (Resource.ContractStatus[3].equals(status)) {
            stt = ContractUtils.ContractStatus.Closed.getString();
            color = "#974707";
        } else if (Resource.ContractStatus[4].equals(status)) {
            stt = ContractUtils.ContractStatus.Rejected.getString();
            color = "#EF363D";
        } else if (Resource.ContractStatus[0].equals(status)) {
            stt = ContractUtils.ContractStatus.NewAccountCreated.getString();
            color = "#376092";
        } else if (Resource.ContractStatus[2].equals(status)) {
            stt = ContractUtils.ContractStatus.Repo.getString();
            color = "#E46C0A";
        }

        return new Pair<>(color, stt);
    }

    public static Pair<String, String> getState(String state) {
        String message = "Đang kiểm tra TT HĐ";
        String color = "#14892F";
        if (state.contains("VALIDATOR")) {
            message = "Đang kiểm tra TT HĐ";
            color = "#14892F";
        }

        if (state.contains("ACTIVE")) {
            message = "HĐ đang chạy";
            color = "#376092";
        }

        if (state.contains("REJECT")) {
            message = "HĐ bị từ chối";
            color = "#EF363D";
        }

        if (state.contains("LOAN_PRINT")) {
            message = "In hợp đồng, chờ chữ ký KH";
            color = "#14892F";
        }

        if (state.contains("PAY_A_PART_PRINT")) {
            message = "Đã nhận lại HĐ, bản cứng";
            color = "#14892F";
        }

        Pair<String, String> states = new Pair<>(message, color);

        return states;
    }

    public static float dpFromPx(final Context context, final float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public static int getToolBarHeight(Context context) {
        int[] attrs = new int[]{R.attr.actionBarSize};
        TypedArray ta = context.obtainStyledAttributes(attrs);
        int toolBarHeight = ta.getDimensionPixelSize(0, -1);
        ta.recycle();
        return toolBarHeight;
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static String flattenToAscii(String string) {
        char[] out = new char[string.length()];
        string = Normalizer.normalize(string, Normalizer.Form.NFD);
        int j = 0;
        for (int i = 0, n = string.length(); i < n; ++i) {
            char c = string.charAt(i);
            if (c <= '\u007F') out[j++] = c;
        }
        return new String(out);
    }
}
