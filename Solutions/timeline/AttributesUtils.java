package com.ifs.core.view.timeline;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.TypedValue;

import com.ifs.core.R;

class AttributesUtils {

  static int windowBackground(Context context, int defaultValue) {
    return getColor(context, android.R.attr.windowBackground, defaultValue);
  }

  public static int colorPrimary(Context context, int defaultValue) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      return getColor(context, android.R.attr.colorPrimary, defaultValue);
    }
    return getColor(context, R.attr.colorPrimary, defaultValue);
  }

  public static int colorAccent(Context context, int defaultValue) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      return getColor(context, android.R.attr.colorAccent, defaultValue);
    }
    return getColor(context, R.attr.colorAccent, defaultValue);
  }

  @SuppressWarnings("deprecation")
  private static int getColor(Context context, int id, int defaultValue) {
    TypedValue value = new TypedValue();

    Resources.Theme theme = context.getTheme();
    if (theme != null && theme.resolveAttribute(id, value, true)) {
      if (value.type >= TypedValue.TYPE_FIRST_INT
          && value.type <= TypedValue.TYPE_LAST_INT) {
        return value.data;
      } else if (value.type == TypedValue.TYPE_STRING) {
        return context.getResources().getColor(value.resourceId);
      }
    }
    return defaultValue;
  }
}