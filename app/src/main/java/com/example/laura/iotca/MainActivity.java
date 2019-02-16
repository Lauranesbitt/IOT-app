

package com.example.laura.iotca;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;


public class MainActivity extends AppCompatActivity implements Response.Listener,
        Response.ErrorListener {
    public static final String REQUEST_TAG = "MainActivity";
    private TextView mTextView;
    private Button mButton;
    private RequestQueue mQueue;
    private EditText e1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.textView);
        mButton = (Button) findViewById(R.id.button);

    }

    @Override
    protected void onStart() {
        super.onStart();
        // Instantiate the RequestQueue.
        mQueue = CustomVolleyRequestQueue.getInstance(this.getApplicationContext())
                .getRequestQueue();
        String url = "https://dweet.io/dweet/for/LauraPi?4";


        final CustomJSONObjectRequest jsonRequest = new CustomJSONObjectRequest(Request.Method
                .GET, url,
                new JSONObject(), this, this);
        jsonRequest.setTag(REQUEST_TAG);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQueue.add(jsonRequest);
            }
        });

    }
    @Override
    protected void onStop() {
        super.onStop();
        if (mQueue != null) {
            mQueue.cancelAll(REQUEST_TAG);
        }
    }

    @Override
    //print out error
    public void onErrorResponse(VolleyError error) {
        mTextView.setText(error.getMessage());
    }

    @Override
    //print out response
    public void onResponse(Object response) {
        mTextView.setText("Response is: " + response);
        try {
            mTextView.setText(mTextView.getText() + ((JSONObject) response).getString
                    ("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}

