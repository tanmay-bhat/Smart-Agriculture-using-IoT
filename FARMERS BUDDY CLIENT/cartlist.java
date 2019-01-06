package com.example.farmersbuddyclient;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
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
import com.parse.ParseObject;
import com.squareup.picasso.Picasso;
public class CartList extends Activity {
ListView mListview;
String type;
List&lt;Formerdata&gt; farmersData = new ArrayList&lt;Formerdata&gt;();
Button btnCheckout;
TextView tvTotalAmt;
@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.cartview);
mListview = (ListView) findViewById(R.id.listview);
btnCheckout = (Button) findViewById(R.id.checkout);
tvTotalAmt = (TextView) findViewById(R.id.total);
Utils.d(&quot;&quot;+ProductList.cartsList);

FilterData(ProductList.cartsList);
mListview.setOnItemClickListener(new OnItemClickListener() {
@Override
public void onItemClick(AdapterView&lt;?&gt; arg0, View arg1, int arg2,

long arg3) {
// TODO Auto-generated method stub
Formerdata fData = farmersData.get(arg2);
}
});
btnCheckout.setOnClickListener(new OnClickListener() {
@Override
public void onClick(View arg0) {
// TODO Auto-generated method stub

}
});
}

public void createAlert(Activity activity){
AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
alertDialog.setTitle(&quot;Address&quot;);
alertDialog.setMessage(&quot;Enter Address&quot;);
final EditText input = new EditText(activity);
LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
LinearLayout.LayoutParams.MATCH_PARENT,
LinearLayout.LayoutParams.MATCH_PARENT);
input.setLayoutParams(lp);
alertDialog.setView(input);

alertDialog.setPositiveButton(&quot;OK&quot;,
new DialogInterface.OnClickListener() {
public void onClick(DialogInterface dialog, int which) {
String address = input.getText().toString();
for(Formerdata fData : ProductList.cartsList){

ParseObject po = new ParseObject(&quot;Order&quot;);
po.put(&quot;OrderDataInfo&quot;, fData.getObjectId());
po.put(&quot;Address&quot;, address);
po.put(&quot;Quantity&quot;, fData.getUserqunatity());
po.put(&quot;Amount&quot;, amount);
po.saveInBackground();

}
ProductList.cartsList.clear();
Utils.showTOast(getApplicationContext(), &quot;successfully

placed order.&quot;);
}
});
alertDialog.show();
}
int amount = 0;
public void FilterData(List&lt;Formerdata&gt; cropsData) {
for(Formerdata po : cropsData){
amount+=(po.getConsumerPrice()*po.getUserqunatity());
}
tvTotalAmt.setText(&quot;Total amount: Rs &quot;+amount);
CustomAdapter customAdapter = new CustomAdapter(CartList.this,

cropsData);

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
inflater = (LayoutInflater)

activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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

}else{
holder = (ViewHolder) vi.getTag();
}
holder.btnAddtocart.setVisibility(View.GONE);

if (data.size() &gt; 0) {
Formerdata fData = data.get(position);
holder.text.setText(fData.getName()+&quot; \n &quot;+

fData.getConsumerPrice() + &quot; Rs/ KG&quot;);

Picasso.with(activity).load(fData.getUrl()).into(holder.ivCrop);
} else {
Toast.makeText(getApplicationContext(), &quot;No records found&quot;,

6000).show();

}
return vi;
}

}
}
