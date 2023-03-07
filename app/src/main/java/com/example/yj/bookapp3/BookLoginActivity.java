package com.example.yj.bookapp3;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class BookLoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private EditText edtUser,edtPass;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0){
                JSONObject jsonObject = (JSONObject) (msg.obj);
                try {
                    String result = jsonObject.getString("result");
                    if(result.equals("ok")){
                        Toast.makeText(BookLoginActivity.this,"登录成功",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(BookLoginActivity.this,BookMainActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(BookLoginActivity.this,"登录失败",Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_login);

        btnLogin = (Button) findViewById(R.id.btn_login);
        edtUser = (EditText) findViewById(R.id.user);
        edtPass = (EditText) findViewById(R.id.password);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MediaType JSON = MediaType.parse("application/json;charset=utf-8");
                OkHttpClient client = new OkHttpClient();
                String json = "{\"username\":\""+edtUser.getText().toString()+"\",\"password\":\""+edtPass.getText().toString()+"\"}";
                RequestBody body = RequestBody.create(JSON,json);
                Request request = new Request.Builder().url("http://study.smartye.top/api/20230201/login").post(body).build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String strJson =  response.body().string();
                        try {
                            JSONObject jsonObject = new JSONObject(strJson);
                            handler.obtainMessage(0,jsonObject).sendToTarget();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}
