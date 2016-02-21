package com.example.miaodonghan.markupproject_01;

import android.text.Layout;
import android.text.Selection;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import us.feras.mdv.MarkdownView;

/**
 * Created by maggie on 2/7/16.
 */


public class Document {
    String name;
    String body;

    public String getName() {
        return name;
    }

    public String getBody() {
        return body;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void document(){

    }

    public void setHeader(int line, MarkdownView markdownView, EditText editor) {

        //get current line text
        String text = editor.getText().toString();
        int lineStart = editor.getLayout().getLineStart(line);
        int end = editor.getLayout().getLineEnd(line);
        String lineText = text.substring(lineStart, end);
//        if (start != 0) {
//            frontText = text.substring(0, start);
//        } else {
//            frontText = "";
//        }
//        if (end < editor.length()){
//            behindText = text.substring(end, editor.length());
//        }else{
//            behindText ="";
//        }
        String frontText = notSelectedFrontTextHandler(editor,lineStart);
        String behindText = notSelectedBehindTextHandler(editor,end);

        //handle different heading
        if(lineText.charAt(0) != '#'){
            editor.setText(frontText + "# " + lineText + behindText);
        }else if(lineText.charAt(1) != '#' || lineText.charAt(2) != '#' ){
            editor.setText(frontText + "#" + lineText + behindText);
        }else if(lineText.charAt(2) == '#'){
            editor.setText(frontText + lineText.substring(4, lineText.length()) + behindText);
        }
        markdownView.loadMarkdown(editor.getText().toString());

    }

    public void setBold(int line, EditText editor, MarkdownView markdownView) {


        int start_selected = editor.getSelectionStart();
        int end_selected = editor.getSelectionEnd();

        if(start_selected != end_selected){
            String text = editor.getText().toString();

            int lineStart = editor.getLayout().getLineStart(line);
            int end = editor.getLayout().getLineEnd(line);
            String lineText = text.substring(lineStart, end);

            String frontText = notSelectedFrontTextHandler(editor,start_selected);
            String behindText = notSelectedBehindTextHandler(editor, end_selected);
            String selectedText = text.substring(start_selected, end_selected);
            //handle different heading
            if(lineText.charAt(0) == '#'){
                return;
                //Toast.makeText(context.this, "drag drop", Toast.LENGTH_SHORT).show();
            }
            if(selectedText.charAt(0) != '*'){
                selectedText.replace('*', ' ');
                editor.setText(frontText + "*" + selectedText + "*" + behindText);
            }else if(selectedText.charAt(1) != '*' ){
                selectedText.substring(1, selectedText.length() - 1).replace('*', ' ');
                editor.setText(frontText + "*" + selectedText + "*" + behindText);
            }else{
                String s =selectedText.substring(2, selectedText.length() - 2).replace('*', ' ');
                editor.setText(frontText + s + behindText);
            }

        }
        markdownView.loadMarkdown(editor.getText().toString());
    }



    public String notSelectedFrontTextHandler(EditText editor,int start){
        String text = editor.getText().toString();
        String frontText;
        if (start != 0) {
            frontText = text.substring(0, start);
        } else {
            frontText = "";
        }
       return frontText;
    }

    public String notSelectedBehindTextHandler(EditText editor, int end){
        String text = editor.getText().toString();
        String behindText;
        if (end < editor.length()){
            behindText = text.substring(end, editor.length());
        }else{
            behindText ="";
        }

        return behindText;
    }


//    public String getLineText(int line,EditText editor){
//        String text = editor.getText().toString();
//        int start = editor.getLayout().getLineStart(line);
//        int end = editor.getLayout().getLineEnd(line);
//        String lineText = text.substring(start, end);
//        return lineText;
//    }

}
