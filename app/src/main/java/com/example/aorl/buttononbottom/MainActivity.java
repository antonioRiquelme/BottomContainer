package com.example.aorl.buttononbottom;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

public class MainActivity extends AppCompatActivity {

  ConstraintLayout parentView;
  ViewGroup contentView;
  DynamicSpace dynamicSpace;
  ViewGroup buttonLayout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    parentView = findViewById(R.id.parent_view);
    contentView = findViewById(R.id.content_view);
    dynamicSpace = findViewById(R.id.dynamicSpace);
    buttonLayout = findViewById(R.id.button_layout);

    parentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
      @Override
      public void onGlobalLayout() {
        parentView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

        int height = dynamicSpace.getHeight();

        if (height == 0) {
          Log.e("DynamicView", "you need a scrollView!");
        }
      }
    });

  }


}
