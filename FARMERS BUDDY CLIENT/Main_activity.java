package com.example.farmersbuddyclient;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
public class MainActivity extends Activity {
@Override
protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.activity_main);
}
@Override
public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
getMenuInflater().inflate(R.menu.main, menu);
return true;
}
}
Product list.java
package com.example.farmersbuddyclient;
import java.util.ArrayList;

import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;
public class ProductList extends Activity {
ListView mListview;
String type;
List&lt;Formerdata&gt; farmersData = new ArrayList&lt;Formerdata&gt;();
static List&lt;Formerdata&gt; cartsList = new ArrayList&lt;Formerdata&gt;();
Button btnCartList;
@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.productlist);
mListview = (ListView) findViewById(R.id.listview);
btnCartList = (Button) findViewById(R.id.cartlist);
type = getIntent().getStringExtra(&quot;type&quot;);
ParseQuery&lt;ParseObject&gt; query = ParseQuery.getQuery(&quot;Formerdata&quot;);
query.whereEqualTo(&quot;Type&quot;, type);

query.whereEqualTo(&quot;isQualityCheck&quot;, true);
query.findInBackground(new FindCallback&lt;ParseObject&gt;() {
public void done(List&lt;ParseObject&gt; scoreList, ParseException e) {
if (e == null) {
Toast.makeText(getApplicationContext(),
&quot;&quot; + scoreList.size(), 600000).show();

FilterData(scoreList);
} else {
Log.d(&quot;score&quot;, &quot;Error: &quot; + e.getMessage());
}
}
});
mListview.setOnItemClickListener(new OnItemClickListener() {
@Override
public void onItemClick(AdapterView&lt;?&gt; arg0, View arg1, int arg2,

long arg3) {
// TODO Auto-generated method stub
Formerdata fData = farmersData.get(arg2);
}
});
btnCartList.setOnClickListener(new OnClickListener() {
@Override
public void onClick(View v) {
// TODO Auto-generated method stub
Intent intent = new Intent(ProductList.this,CartList.class);
startActivity(intent);
}
});
}
public void FilterData(List&lt;ParseObject&gt; cropsData) {
for (ParseObject po : cropsData) {
Formerdata data = new Formerdata();
int qua = po.getInt(&quot;Quantity&quot;);
if(qua&lt;=0){
continue;
}
data.setName(po.getString(&quot;Name&quot;));
data.setType(po.getString(&quot;Type&quot;));
data.setPrice(po.getString(&quot;price&quot;));
data.setQuatity(po.getInt(&quot;Quantity&quot;));
data.setQualityCheck(po.getBoolean(&quot;isQualityCheck&quot;));
data.setUrl(po.getParseFile(&quot;Image&quot;).getUrl());
data.setObjectId(po.getObjectId());
data.setConsumerPrice(po.getInt(&quot;ConsumerPrice&quot;));

farmersData.add(data);
}
CustomAdapter customAdapter = new CustomAdapter(ProductList.this,

farmersData);
mListview.setAdapter(customAdapter);
}
public class CustomAdapter extends BaseAdapter {
private Activity activity;
private List&lt;Formerdata&gt; data;
private LayoutInflater inflater = null;
// public Resources res;
int i = 0;
public CustomAdapter(Activity a, List&lt;Formerdata&gt; cropsData) {
activity = a;
data = cropsData;
inflater = (LayoutInflater) activity

.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

}
public int getCount() {
if (data.size() &lt;= 0)
return 0;
return data.size();
}
public Object getItem(int position) {
return position;
}
public long getItemId(int position) {
return position;
}
public class ViewHolder {
public TextView text;
public ImageView ivCrop;
public Button btnAddtocart;
}
public View getView(final int position, View convertView,

ViewGroup parent) {

View vi = convertView;
ViewHolder holder;
if (convertView == null) {
vi = inflater.inflate(R.layout.productlistrow, null);
holder = new ViewHolder();
holder.text = (TextView) vi.findViewById(R.id.description);
holder.ivCrop = (ImageView) vi.findViewById(R.id.iv);
holder.btnAddtocart = (Button) vi.findViewById(R.id.addtocart);
vi.setTag(holder);
} else {
holder = (ViewHolder) vi.getTag();
}
holder.btnAddtocart.setOnClickListener(new OnClickListener() {
@Override
public void onClick(View arg0) {
// TODO Auto-generated method stub
createAlert(activity,data.get(position));

}
});
if (data.size() &gt; 0) {
Formerdata fData = data.get(position);
holder.text

.setText(fData.getName() + &quot; \n &quot; +

fData.getConsumerPrice() + &quot; Rs/ KG&quot;);

Picasso.with(activity).load(fData.getUrl()).into(holder.ivCrop);
} else {
Toast.makeText(getApplicationContext(), &quot;No records found&quot;,

6000).show();

}
return vi;
}
public void updateQuantityInfo(Formerdata data, int userQ){
ParseObject po = new ParseObject(&quot;Formerdata&quot;);
po.setObjectId(data.getObjectId());
int quantity = data.getQuatity() - userQ;
po.put(&quot;Quantity&quot;, quantity);
po.saveInBackground(new SaveCallback() {

@Override
public void done(ParseException arg0) {
// TODO Auto-generated method stub
System.out.println(&quot;parse save update: &quot;+arg0);
}
});
}
public void createAlert(Activity activity, final Formerdata data){
AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
alertDialog.setTitle(&quot;Quantity&quot;);
alertDialog.setMessage(&quot;Enter quantity&quot;);
final EditText input = new EditText(activity);
LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
LinearLayout.LayoutParams.MATCH_PARENT,
LinearLayout.LayoutParams.MATCH_PARENT);
input.setLayoutParams(lp);
input.setInputType(InputType.TYPE_CLASS_NUMBER);
alertDialog.setView(input);

alertDialog.setPositiveButton(&quot;OK&quot;,
new DialogInterface.OnClickListener() {
public void onClick(DialogInterface dialog, int which) {
String password = input.getText().toString();
data.setUserqunatity(Integer.parseInt(password));
cartsList.remove(data);
cartsList.add(data);
Utils.showTOast(getApplicationContext(),

&quot;Item added to cart list&quot;);
updateQuantityInfo(data, Integer.parseInt(password));
}
});
alertDialog.show();
}

}

}
