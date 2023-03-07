package com.example.yj.bookapp3;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class BookWelActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private ArrayList<Fragment>fragments=new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_wel);
        ViewPager viewPager= (ViewPager)findViewById(R.id.view_paper);
        fragments.add(new BookFrag1());
        fragments.add(new BookFrag2());
        BookAdapter adapter = new BookAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(adapter);
    }
}
