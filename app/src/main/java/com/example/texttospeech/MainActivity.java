package com.example.texttospeech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    Button btnXmlParse, btnJsonParse;
    TextView txtXmlData, txtJsonData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BindUiData();

        btnXmlParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReadXmlData();
            }
        });

        btnJsonParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReadJsonData();
            }
        });
    }

    private void BindUiData() {
        btnXmlParse = (Button) findViewById(R.id.btn_xmlparse);
        btnJsonParse = (Button) findViewById(R.id.btn_jsonparse);
        txtXmlData = (TextView) findViewById(R.id.txt_xmldata);
        txtJsonData = (TextView) findViewById(R.id.txt_jsondata);
    }

    //Google Link to learn more about XmlPullParser
    //https://developer.android.com/training/basics/network-ops/xml#java

    private void ReadXmlData() {
        try {
            //To store all the Data in One Single String
            StringBuilder stringValue = new StringBuilder();
            //Read File
            InputStream inputStream = getAssets().open("citydetails.xml");
            //Initialize XML Parser
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(inputStream,null);
            //The method getEventType returns the type of event that happens.
            //E.g: Document start , tag start e.t.c.
            int eventType = parser.getEventType();
            //Check and Loop Unit the Document Ends
            while (eventType != XmlPullParser.END_DOCUMENT) {
                //Store TagName
                String tagName;
                //Used to do any operation when the Document Start
                if(eventType == XmlPullParser.START_DOCUMENT) { }
                //Used to do any operation when the Tag Start
                else if(eventType == XmlPullParser.START_TAG) {
                    tagName = parser.getName();
                    if(tagName.equalsIgnoreCase("Details")) {}
                    else {
                        //Move to next place
                        parser.next();
                        stringValue.append("\n"+tagName+" : "+ parser.getText());
                    }
                }
                //Used to do any operation when Tag Ends
                else if(eventType == XmlPullParser.END_TAG) { }

                //Move to next Place
                eventType = parser.next();
            }
            //Set String Builder Value to TextView
            txtXmlData.setText(stringValue);
        }
        catch (Exception ex) { }
    }

    private void ReadJsonData() {
        try {
            //To store all the Data in One Single String
            StringBuilder stringValue = new StringBuilder();

            //Read File from Asset
            InputStream inputStream = getAssets().open("citydetails.json");

            //To Check if the File is Empty or Not. Empty File the Size will be 0KB
            int size = inputStream.available();

            //Converting to Byte[] so can convert the File Content to string
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            //Convert the Byte[] to String
            String fileData = new String(buffer, "UTF-8");

            //Declare a Json Object
            JSONObject jsonObject = new JSONObject(fileData);

            String cityName = jsonObject.getJSONObject("Details").getString("City_Name");
            if(cityName.length() > 0) {
                stringValue.append("\n"+"City_Name :"+ cityName);
            }

            String latitude = jsonObject.getJSONObject("Details").getString("Latitude");
            if(latitude.length() > 0) {
                stringValue.append("\n"+"Latitude :"+ latitude);
            }

            String longitude = jsonObject.getJSONObject("Details").getString("Longitude");
            if(longitude.length() > 0) {
                stringValue.append("\n"+"Longitude :"+ longitude);
            }

            String temperature = jsonObject.getJSONObject("Details").getString("Temperature");
            if(temperature.length() > 0) {
                stringValue.append("\n"+"Temperature :"+ temperature);
            }

            String humidity = jsonObject.getJSONObject("Details").getString("Humidity");
            if(humidity.length() > 0) {
                stringValue.append("\n"+"Humidity :"+ humidity);
            }

            txtJsonData.setText(stringValue);
        }
        catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
/*

 */