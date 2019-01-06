
 package com.example.farmersbuddyclient;

 import android.content.Context;
 import android.content.SharedPreferences;

 import com.parse.Parse;
 import com.parse.ParseObject;

 public class Application extends android.app.Application {
 // Debugging switch
 public static final boolean APPDEBUG = false;

 // Debugging tag for the application
 public static final String APPTAG = &quot;AnyWall&quot;;

 // Key for saving the search distance preference
 private static final String KEY_SEARCH_DISTANCE = &quot;searchDistance&quot;;

 private static SharedPreferences preferences;

 public Application() {
 }

 @Override
 public void onCreate() {
 super.onCreate();

 Parse.initialize(this, &quot;kCxzYBquZUojcFjhAr0YtWkxTlPDQyHo6ZowzUVT&quot;,
 &quot;IYkIFyXjBP75pAqWo2jbN0v41bkmLagTpisafK90&quot;);


 }

 public static float getSearchDistance() {
 return preferences.getFloat(KEY_SEARCH_DISTANCE, 250);
 }

 public static void setSearchDistance(float value) {
 preferences.edit().putFloat(KEY_SEARCH_DISTANCE, value).commit();
 }

 }
