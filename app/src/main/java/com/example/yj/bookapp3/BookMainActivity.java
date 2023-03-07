package com.example.yj.bookapp3;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class BookMainActivity extends AppCompatActivity {
    private ListView listView;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0){
                JSONArray jsonArray = (JSONArray) (msg.obj);
                MyAdapter adapter = new MyAdapter(BookMainActivity.this,jsonArray);
                listView.setAdapter(adapter);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_main);

        listView = (ListView) findViewById(R.id.list_view);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://study.smartye.top/20230201/book.json").get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String strArray =  response.body().string();
                try {
                    JSONArray jsonArray = new JSONArray(strArray);
                    handler.obtainMessage(0,jsonArray).sendToTarget();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static class MyAdapter extends BaseAdapter {
        private Context context;
        private JSONArray jsonArray;
        public MyAdapter(Context context,JSONArray jsonArray)
        {
            this.context = context;
            this.jsonArray =jsonArray;
        }
        @Override
        public int getCount() {
            return jsonArray.length();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            LayoutInflater inflater = LayoutInflater.from(this.context);
            convertView = inflater.inflate(R.layout.book_item,null);
            TextView tvName = (TextView) convertView.findViewById(R.id.name);
            TextView tvPrice = (TextView) convertView.findViewById(R.id.price);
            TextView tvWriter = (TextView) convertView.findViewById(R.id.writer);
            try {
                tvName.setText(jsonArray.getJSONObject(position).getString("book_name"));
                tvPrice.setText(jsonArray.getJSONObject(position).getString("book_price")+"元");
                tvWriter.setText(jsonArray.getJSONObject(position).getString("writer"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return convertView;
        }
    }
}
