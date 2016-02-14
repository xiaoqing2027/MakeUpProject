package com.example.miaodonghan.markupproject_01;

import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import us.feras.mdv.MarkdownView;

/**
 * Created by maggie on 2/7/16.
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
    public void setHeader(int line, MarkdownView markdownView, EditText eText) {
        // ...
        updateTextView();

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
