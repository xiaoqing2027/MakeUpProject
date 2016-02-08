package com.example.miaodonghan.markupproject_01;

import android.content.ClipData;
import android.content.ClipDescription;
import android.provider.DocumentsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.View.DragShadowBuilder;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import us.feras.mdv.MarkdownView;

public class MainActivity extends AppCompatActivity {
    TextView text;
    EditText eText;
    Document doc;
    Button headerbtn;
    String msg;
    String test;

    private android.widget.RelativeLayout.LayoutParams layoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eText = (EditText)findViewById(R.id.editText);
        test= "This is a test, how to edit text.";
        eText.setText(test);
        MarkdownView markdownView = (MarkdownView) findViewById(R.id.markdownView);
        markdownView.loadMarkdown(eText.getText().toString());
        headerbtn = (Button)findViewById(R.id.Headerbtn);
        headerbtn.setOnTouchListener(new MyTouchListener());
        eText.setOnDragListener(new MyDragListener());


        eText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //Your query to fetch Data
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {

                    //Your query to fetch Data

                    MarkdownView markdownView = (MarkdownView) findViewById(R.id.markdownView);
                    markdownView.loadMarkdown(eText.getText().toString());
                }
            }
        });

//        doc = new Document(text);
//
//        doc.setHeader(6);
//        doc.setBold(7);


    }



    private final class MyTouchListener implements OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "#");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.VISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }

    class MyDragListener implements OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing

                    break;
                case DragEvent.ACTION_DRAG_ENTERED:

                    break;
                case DragEvent.ACTION_DRAG_EXITED:

                    break;
                case DragEvent.ACTION_DROP:

                    // Dropped, reassign View to ViewGroup
                    Toast.makeText(MainActivity.this,"drag drop", Toast.LENGTH_SHORT).show();
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    displayAndCheckMarkDownFormat();
                    break;
                default:
                    break;
            }
            return true;
        }
    }

    public void displayAndCheckMarkDownFormat(){
        MarkdownView markdownView = (MarkdownView) findViewById(R.id.markdownView);
        String t = eText.getText().toString();
        if(t.charAt(0) != '#'){
            eText.setText("# " + t);
        }else if(t.charAt(1) != '#' || t.charAt(2) != '#' ){
            eText.setText("#" + t);
        }else if(t.charAt(2) == '#'){
            eText.setText(t.substring(4,t.length()));
        }
        markdownView.loadMarkdown(eText.getText().toString());
    }

    public void onTouch(View v, MotionEvent e){
        Toast.makeText(MainActivity.this, "display text", Toast.LENGTH_LONG).show();

        eText.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
    /* When focus is lost check that the text field has valid values.*/
                if (!hasFocus) {
                    //validateInput(v);
                    MarkdownView markdownView = (MarkdownView) findViewById(R.id.markdownView);
                    markdownView.loadMarkdown(eText.getText().toString());
                }
            }
        });
    }


}
