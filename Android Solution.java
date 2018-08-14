https://github.com/jaredrummler/MaterialSpinner


public static void changeAppIcon(Context context, String appIcon, String activityName) {
        // save to UserPreferences
        UserPreferences.getInstance().setAppIcon(appIcon);

        // change app icon
        PackageManager pm = context.getPackageManager();
        String packageName = context.getPackageName();
        for(String activity : Constants.SPLASH_ACTIVITY_LIST) {
            if (activity.equals(activityName)) {
                // enable this app icon
                pm.setComponentEnabledSetting(
                        new ComponentName(packageName, activityName),
                        PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                        PackageManager.DONT_KILL_APP);
                continue;
            }
            // disable others
            pm.setComponentEnabledSetting(
                    new ComponentName(packageName, activity),
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    PackageManager.DONT_KILL_APP);
        }
    }
	


Change recent app icon:		Bitmap recentIcon = BitmapFactory.decodeResource(getResources(), iconId); // Initialize this to whatever you want
							String title = null;  // You can either set the title to whatever you want or just use null and it will default to your app/activity name
							int color = ContextCompat.getColor(getContext(), R.color.default_app); // Set the color you want to set the title to, it's a good idea to use the colorPrimary attribute
							ActivityManager.TaskDescription description = new ActivityManager.TaskDescription(title, recentIcon, color);
							getActivity().setTaskDescription(description);
							
Multi touch:				
@Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(ev.getPointerCount() > 1){
            return true;
        } else return super.dispatchTouchEvent(ev);
    }

