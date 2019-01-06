package com.example.farmersbuddyclient;
import java.io.Serializable;
public class Formerdata implements Serializable {
String objectId;
String Name;
String Type;
String price;
String url;
int Quatity;
boolean isQualityCheck;
int consumerPrice;
int userqunatity;
public int getUserqunatity() {
return userqunatity;
}
public void setUserqunatity(int userqunatity) {
this.userqunatity = userqunatity;
}
public String getObjectId() {
return objectId;
}
public void setObjectId(String objectId) {
this.objectId = objectId;
}
public int getConsumerPrice() {
return consumerPrice;
}
public void setConsumerPrice(int price) {
consumerPrice = price;
}
public String getName() {
return Name;
}
public void setName(String name) {
Name = name;
}
public String getType() {
return Type;
}
public void setType(String type) {
Type = type;
}
public String getPrice() {
return price;
}

public void setPrice(String price) {
this.price = price;
}
public String getUrl() {
return url;
}
public void setUrl(String url) {
this.url = url;
}
public int getQuatity() {
return Quatity;
}
public void setQuatity(int quatity) {
Quatity = quatity;
}
public boolean isQualityCheck() {
return isQualityCheck;
}
public void setQualityCheck(boolean isQualityCheck) {
this.isQualityCheck = isQualityCheck;
}

}
