package com.example.shaheed.andelachallengeproject;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    CardView myCard;
    private String TAG = MainActivity.class.getSimpleName();
    public ResponseModel[] report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new async_method().execute();
    }


    class async_method extends AsyncTask<Void, Void, String> {

        String jsonResponse;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this, "Connecting!",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            //TODO: Networking

            HTTPHandler httpHandler = new HTTPHandler();

            jsonResponse = null;
            // Making a request to url and getting response
            String url = "https://min-api.cryptocompare.com/data/pricemulti?fsyms=BTC,ETH&tsyms=USD,EUR";
            jsonResponse = httpHandler.makeServiceCall(url);

            if (jsonResponse != null) {
                try {
                    //JSONObject object = new JSONObject(jsonStr);

                    //getting the items node out of the whole response
                    //JSONArray jsonArray = object.getJSONArray("items");
                    JSONObject jsonObject = new JSONObject(jsonResponse);

                    report = new ResponseModel[jsonObject.length()];

                    for (int i = 0; i < jsonObject.length(); i++) {
                        JSONObject jobject = jsonObject.getJSONObject("BTC");
                        JSONObject jobject2 = jsonObject.getJSONObject("ETH");

                        //assigning value to variables in responseModel class
                        ResponseModel btcreport = new ResponseModel();
                        btcreport.setEUR((float) jobject.getDouble("EUR"));
                        btcreport.setUSD((float) jobject.getDouble("USD"));

                        ResponseModel ethreport = new ResponseModel();
                        ethreport.setEUR((float) jobject2.getDouble("EUR"));
                        ethreport.setUSD((float) jobject2.getDouble("USD"));

                        //adding each info from login and avatar_url to the devs array
                        report[i] = btcreport;
                        report[i] = ethreport;
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
                return null;
            } else {
                Toast.makeText(MainActivity.this,
                        "No response!", Toast.LENGTH_SHORT).show();
            }
            return url;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


        }
    }

}
