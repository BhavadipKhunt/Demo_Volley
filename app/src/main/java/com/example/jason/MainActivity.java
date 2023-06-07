package com.example.jason;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String TAG="TTT";
    String url = "https://jsonplaceholder.typicode.com/users";
    ArrayList<DataModel> dataList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RequestQueue queue = Volley.newRequestQueue(this);
        if (!isOnline()) {
            finish();
        } else {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            //textView.setText("Response is: " + response.substring(0,500));
                            //Log.d(TAG, "onResponse: " + response);
                            DataModel model = null;
                            try {
                                JSONArray jsonArray=new JSONArray(response);
                                for(int i=0;i<jsonArray.length();i++)
                                {
                                    JSONObject mainObj=jsonArray.getJSONObject(i);
                                    //Log.d(TAG, "onResponse: "+mainObj.toString());
                                    int id=mainObj.getInt("id");
                                    String name=mainObj.getString("name");
                                    String username=mainObj.getString("username");
                                    String phone=mainObj.getString("phone");
                                    String email=mainObj.getString("email");
                                    model = new DataModel(id,name,username,phone,email);
                                    dataList.add(model);
                                    JSONObject address=mainObj.getJSONObject("address");
                                };

                                Log.d(TAG, "onResponse: "+dataList.get(0));
                            }
                            catch (JSONException e) {
                                throw new RuntimeException(e);
                            }



                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }


            });


            queue.add(stringRequest);
        }
    }
    private boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}