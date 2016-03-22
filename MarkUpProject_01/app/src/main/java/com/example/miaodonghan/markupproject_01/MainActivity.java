package com.example.miaodonghan.markupproject_01;

import android.content.ClipData;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Layout;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import http_requests.PutRequestTask;
import us.feras.mdv.MarkdownView;

public class MainActivity extends AppCompatActivity {

    EditText editor;
    Button headerbtn;
    Button boldbtn;
    String msg;
    Document doc = new Document();
    Button savebtn;
    Button savebtn_newversion;
    int doc_id;
    int version_id;
    String ip;

    private android.widget.RelativeLayout.LayoutParams layoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editor = (EditText)findViewById(R.id.editText);
        doc_id =getIntent().getIntExtra("doc_position",VersionListActivity.doc_id);
        Log.i("doc_iddddd:", doc_id+"");
        version_id = getIntent().getIntExtra("version_position",VersionListActivity.version_selected_id);


        GetRequestTask getRequestTask = new GetRequestTask(this,version_id,editor);
        //get selectedPosition from listview
        ip = getString(R.string.ip_address);
        getRequestTask.execute(ip+"/api/doc/"+ doc_id+"/version/"+ version_id);
        //getRequestTask.execute( "http://104.194.108.91:1337/api/doc/" + id);
        Log.i("_________________id :", "http://104.194.111.39:1337/api/doc/" + doc_id + "/version/" + version_id);

        MarkdownView markdownView = (MarkdownView) findViewById(R.id.markdownView);
        markdownView.loadMarkdown(editor.getText().toString());

        savebtn =(Button)findViewById(R.id.save_old);
        savebtn.setOnClickListener(mySaveTtn);
        savebtn_newversion=(Button)findViewById(R.id.save_new);
        savebtn_newversion.setOnClickListener(mySaveTtn_newversion);
        headerbtn = (Button)findViewById(R.id.HeaderBtn);
        headerbtn.setOnTouchListener(new headerbtnTouchListener());

        boldbtn = (Button)findViewById(R.id.BoldBtn);
        boldbtn.setOnTouchListener(new boldbtnTouchListener());


        editor.setOnDragListener(new MyDragListener());
        editor.addTextChangedListener(new TextWatcher() {

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

                    MarkdownView markdownView = (MarkdownView) findViewById(R.id.markdownView);
                    markdownView.loadMarkdown(editor.getText().toString());
//                   String str = editor.getText().toString().replace('\n', ' ');
                }
            }
        });

    }



    private final class headerbtnTouchListener implements OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.VISIBLE);
                msg = "headerbtn";
                return true;
            } else {
                return false;
            }
        }
    }

    private final class boldbtnTouchListener implements OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.VISIBLE);
                msg = "boldbtn";
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
                    switch(msg){
                        case "headerbtn":
                            handleHeaderbtn();
                            break;
                        case "boldbtn":
                            handleBoldbtn();
                            break;
                    }
                    
                    // Dropped, reassign View to ViewGroup
                    Toast.makeText(MainActivity.this,"drag drop", Toast.LENGTH_SHORT).show();
                    break;
                case DragEvent.ACTION_DRAG_ENDED:

                    break;
                default:
                    break;
            }
            return true;
        }
    }

    public void handleHeaderbtn(){
        MarkdownView markdownView = (MarkdownView) findViewById(R.id.markdownView);
        int lineNum = getCurrentCursorLine(editor);
        doc.setHeader(lineNum,markdownView, editor);
    }

    public void handleBoldbtn(){
        MarkdownView markdownView = (MarkdownView) findViewById(R.id.markdownView);
        int lineNum = getCurrentCursorLine(editor);
        doc.setBold(lineNum,editor,markdownView);

    }


    public int getCurrentCursorLine(EditText editText) {

        int selectionStart = Selection.getSelectionStart(editText.getText());
        Layout layout = editText.getLayout();

        if (!(selectionStart == -1)) {
            return layout.getLineForOffset(selectionStart);
        }

        return -1;
    }

    View.OnClickListener mySaveTtn = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            PutRequestTask putRequestTask = new PutRequestTask(MainActivity.this,version_id,editor,ip,doc_id);
            String text = editor.getText().toString();
            int start = editor.getLayout().getLineStart(0);
            int end = editor.getLayout().getLineStart(1);
            int cstart =editor.getLayout().getLineStart(2);
            String name = text.substring(start, end);
            String content = text.substring(cstart,text.length());
            putRequestTask.execute(""+version_id,name,content);
            //String content = text;
            //putRequestTask.execute(""+version_id,content);
            Toast.makeText(MainActivity.this, "save successfully", Toast.LENGTH_LONG).show();
        }
    };
    View.OnClickListener mySaveTtn_newversion = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            PutRequestTask putRequestTask = new PutRequestTask(MainActivity.this,version_id,editor,ip,doc_id);
            String text = editor.getText().toString();
//            int start = editor.getLayout().getLineStart(0);
//            int end = editor.getLayout().getLineStart(1);
//            int cstart =editor.getLayout().getLineStart(2);
//            String name = text.substring(start, end);
//            String content = text.substring(cstart,text.length());
            //putRequestTask.execute(""+version_id,name,content);
            String content = text;
            putRequestTask.execute(""+version_id,content);
            Toast.makeText(MainActivity.this, "save successfully", Toast.LENGTH_LONG).show();
        }
    };

    public void onTouch(View v, MotionEvent e){
        Toast.makeText(MainActivity.this, "display text", Toast.LENGTH_LONG).show();

        editor.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
    /* When focus is lost check that the text field has valid values.*/
                if (!hasFocus) {
                    //validateInput(v);
                    MarkdownView markdownView = (MarkdownView) findViewById(R.id.markdownView);
                    markdownView.loadMarkdown(editor.getText().toString());
                }
            }
        });


        //get current line number
        int cursorPosition = editor.getSelectionStart();
        Log.i("cureent position: ", " " + cursorPosition);
        //get current line text
    }

}
