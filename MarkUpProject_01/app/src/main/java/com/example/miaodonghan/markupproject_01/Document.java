package com.example.miaodonghan.markupproject_01;

import android.text.Layout;
import android.text.Selection;
import android.util.Log;
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
        String frontText;
        String behindText;
        //get current line text
        String text = eText.getText().toString();
        int start = eText.getLayout().getLineStart(line);
        int end = eText.getLayout().getLineEnd(line);
        String lineText = text.substring(start, end);
        if (start != 0) {
            frontText = text.substring(0, start);
        } else {
            frontText = "";
        }
        if (end < eText.length()){
            behindText = text.substring(end, eText.length());
        }else{
            behindText ="";
        }
        if(lineText.charAt(0) != '#'){
            eText.setText(frontText + "# " + lineText + behindText);
        }else if(lineText.charAt(1) != '#' || lineText.charAt(2) != '#' ){
            eText.setText(frontText +"#" + lineText + behindText);
        }else if(lineText.charAt(2) == '#'){
            eText.setText(frontText + lineText.substring(4, lineText.length()) + behindText);
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
