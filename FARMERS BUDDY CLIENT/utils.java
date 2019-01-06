package com.example.farmersbuddyclient;
import android.content.Context;
import android.widget.Toast;

public class Utils {
public static void showTOast(Context context , String message){
Toast.makeText(context, message,
6000).show();

}
public static void d(Object message){
System.out.println(message);
}
}
