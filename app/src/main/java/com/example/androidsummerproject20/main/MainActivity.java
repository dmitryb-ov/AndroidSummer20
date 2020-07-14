//package com.example.androidsummerproject20.main;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.androidsummerproject20.R;
//import com.example.androidsummerproject20.activityToDoList.ToDoListActivity;
//
//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        Button btodoList = findViewById(R.id.btodoList);
//        btodoList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switch (v.getId()) {
//                    case R.id.btodoList:
//                        Intent intent = new Intent(MainActivity.this, ToDoListActivity.class);
//                        startActivity(intent);
//                        finish();
//                }
//            }
//        });
//    }
//}
