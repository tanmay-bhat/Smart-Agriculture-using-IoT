package com.example.farmersbuddyclient;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
public class SelectItems extends Activity{
Spinner spinItems;
String[] cropsItems = {&quot;Select crops Type&quot;,&quot;Vegetables&quot;,&quot;Fruits&quot;,&quot;Grains&quot;};
@Override
public void onCreate(Bundle savedInstanceState){
super.onCreate(savedInstanceState);
setContentView(R.layout.selectitems);
spinItems = (Spinner) findViewById(R.id.spin);
ArrayAdapter&lt;String&gt; aa = new ArrayAdapter&lt;String&gt;(this,

android.R.layout.simple_spinner_item,cropsItems);
aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
spinItems.setAdapter(aa);
spinItems.setOnItemSelectedListener(new OnItemSelectedListener() {
@Override
public void onItemSelected(AdapterView&lt;?&gt; arg0, View arg1,

int arg2, long arg3) {
// TODO Auto-generated method stub
if(arg2 == 0){
}else{
Intent intent = new

Intent(SelectItems.this,ProductList.class);

intent.putExtra(&quot;type&quot;, cropsItems[arg2]);
startActivity(intent);
}

}

@Override
public void onNothingSelected(AdapterView&lt;?&gt; arg0) {
// TODO Auto-generated method stub
}
});
}
}
