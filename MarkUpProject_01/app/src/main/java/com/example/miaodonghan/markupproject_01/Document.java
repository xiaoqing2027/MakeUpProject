package com.example.miaodonghan.markupproject_01;

import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by miaodonghan on 2/7/16.
 */

class Line {
    String text;
    public String getMd() {
        return "";
    }

}
public class Document {
    TextView textView;

    ArrayList<Line> lines;


    public Document(TextView textView) {
        this.textView = textView;
    }
    public void setHeader(int line) {
        // ...
        updateTextView();
    }

    public void setBold(int line) {

    }


    public void updateTextView() {
        textView.setText(this.getMd());
    }

    public String getMd() {
        String resultMD = "";
        for(Line line : lines) {
            resultMD += line.getMd() + "\n";
        }
        return resultMD;
    }
}
