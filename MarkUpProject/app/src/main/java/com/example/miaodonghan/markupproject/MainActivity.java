package com.example.miaodonghan.markupproject;

import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Layout;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import http_requests.GetRequestTask;
import http_requests.LogoutRequestTask;
import us.feras.mdv.MarkdownView;
import utils.Document;

public class MainActivity extends AppCompatActivity {

    EditText editor;
    Button headerbtn;
    Button boldbtn;
    Button quotebtn;
    Button listbtn;
    String msg;
    Document doc = new Document();
    Button savebtn;
    Button savebtn_newversion;
    Button previewbtn;
    int doc_id;
    int version_id;
    String ip;
    SharedPreferences sharedPreferences;
    public static String editor_content = "";


    private android.widget.RelativeLayout.LayoutParams layoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.markdowntext_edit);

        editor = (EditText) findViewById(R.id.editText);
        doc_id = getIntent().getIntExtra("doc_position", VersionListActivity.doc_id);
        Log.i("doc_iddddd:", doc_id + "");
        version_id = getIntent().getIntExtra("version_position", VersionListActivity.version_selected_id);


        GetRequestTask getRequestTask = new GetRequestTask(this, editor);
        //get selectedPosition from listview
        ip = getString(R.string.ip_address);
        getRequestTask.execute(ip + "/api/doc/" + doc_id + "/version/" + version_id);
        //getRequestTask.execute( "http://104.194.108.91:1337/api/doc/" + id);
        Log.i("_________________id :", "/api/doc/" + doc_id + "/version/" + version_id);
        Log.i("00000", editor_content);


//        MarkdownView markdownView = (MarkdownView) findViewById(R.id.markdownView);
//        markdownView.loadMarkdown(editor.getText().toString());

        //savebtn = (Button) findViewById(R.id.save_old);
        //savebtn.setOnClickListener(mySaveTtn);
        //savebtn_newversion = (Button) findViewById(R.id.save_new);
        //savebtn_newversion.setOnClickListener(mySaveTtn_newversion);
        previewbtn = (Button) findViewById(R.id.preview);
        previewbtn.setOnClickListener(previewbtn_handler);

        headerbtn = (Button) findViewById(R.id.HeaderBtn);
        headerbtn.setOnTouchListener(new headerbtnTouchListener());
        boldbtn = (Button) findViewById(R.id.BoldBtn);
        boldbtn.setOnTouchListener(new boldbtnTouchListener());
        quotebtn = (Button) findViewById(R.id.quotebtn);
        quotebtn.setOnTouchListener(new quotebtnTouchListener());
        listbtn = (Button) findViewById(R.id.listbtn);
        listbtn.setOnTouchListener(new listbtnTouchListener());


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
//                if (s.length() > 0) {
//
//                    MarkdownView markdownView = (MarkdownView) findViewById(R.id.markdownView);
//                    markdownView.loadMarkdown(editor.getText().toString());
////                   String str = editor.getText().toString().replace('\n', ' ');
//                }
                Log.i("11111", editor.getText().toString());
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

    private final class quotebtnTouchListener implements OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.VISIBLE);
                msg = "quotebtn";
                return true;
            } else {
                return false;
            }
        }
    }

    private final class listbtnTouchListener implements OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.VISIBLE);
                msg = "listbtn";
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
                    switch (msg) {
                        case "headerbtn":
                            handleHeaderbtn();
                            break;
                        case "boldbtn":
                            handleBoldbtn();
                            break;
                        case "quotebtn":
                            handleQuotebtn();
                            break;
                        case "listbtn":
                            handleListbtn();
                            break;
                    }

                    // Dropped, reassign View to ViewGroup
                    Toast.makeText(MainActivity.this, "drag drop", Toast.LENGTH_SHORT).show();
                    break;
                case DragEvent.ACTION_DRAG_ENDED:

                    break;
                default:
                    break;
            }
            return true;
        }
    }

    public void handleHeaderbtn() {
        MarkdownView markdownView = (MarkdownView) findViewById(R.id.user_markdownView);
        int lineNum = getCurrentCursorLine(editor);
        doc.setHeader(lineNum, markdownView, editor);
    }

    public void handleBoldbtn() {
        MarkdownView markdownView = (MarkdownView) findViewById(R.id.user_markdownView);
        int lineNum = getCurrentCursorLine(editor);
        doc.setBold(lineNum, editor, markdownView);
    }

    public void handleQuotebtn() {
        MarkdownView markdownView = (MarkdownView) findViewById(R.id.user_markdownView);
        int lineNum = getCurrentCursorLine(editor);
        doc.setQuote(lineNum, editor, markdownView);
    }

    public void handleListbtn() {
        MarkdownView markdownView = (MarkdownView) findViewById(R.id.user_markdownView);
        int lineNum = getCurrentCursorLine(editor);
        doc.setList(lineNum, editor, markdownView);
    }


    public int getCurrentCursorLine(EditText editText) {

        int selectionStart = Selection.getSelectionStart(editText.getText());
        Layout layout = editText.getLayout();

        if (!(selectionStart == -1)) {
            return layout.getLineForOffset(selectionStart);
        }

        return -1;
    }

//    View.OnClickListener mySaveTtn = new View.OnClickListener() {
//
//        @Override
//        public void onClick(View v) {
//            PutRequestTask putRequestTask = new PutRequestTask(MainActivity.this, version_id, editor, ip, doc_id);
//            String text = editor.getText().toString();
//            int start = editor.getLayout().getLineStart(0);
//            int end = editor.getLayout().getLineStart(1);
//            int cstart = editor.getLayout().getLineStart(2);
//            String name = text.substring(start, end);
//            String content = text.substring(cstart, text.length());
//            putRequestTask.execute("" + version_id, name, content);
//            //String content = text;
//            //putRequestTask.execute(""+version_id,content);
//            Toast.makeText(MainActivity.this, "save successfully", Toast.LENGTH_LONG).show();
//        }
//    };
//    View.OnClickListener mySaveTtn_newversion = new View.OnClickListener() {
//
//        @Override
//        public void onClick(View v) {
//
//            User_PostRequestTask postRequestTask = new User_PostRequestTask(MainActivity.this, editor, ip, doc_id);
//
//            String text = editor.getText().toString();
//            int start = editor.getLayout().getLineStart(0);
//            int end = editor.getLayout().getLineStart(1);
//            int cstart = editor.getLayout().getLineStart(2);
//            String name = text.substring(start, end);
//            String content = text.substring(cstart, text.length());
//            //putRequestTask.execute(""+version_id,name,content);
//            //String content = text;
//            postRequestTask.execute(name, content);
//            Toast.makeText(MainActivity.this, "you created a new version successfully", Toast.LENGTH_LONG).show();
//        }
//    };

    View.OnClickListener previewbtn_handler = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            String text = editor.getText().toString();
            int start = editor.getLayout().getLineStart(0);
            int end = editor.getLayout().getLineStart(1);
            int cstart = editor.getLayout().getLineStart(2);
            String name = text.substring(start, end);
            String content = text.substring(cstart, text.length());
            sharedPreferences = getSharedPreferences(LoginActivity.Markup, MODE_PRIVATE);
            SharedPreferences.Editor e= sharedPreferences.edit();
            e.putString(LoginActivity.doc_name_s,name);
            Log.i("name/content","arerrrrrrr");
            e.putString(LoginActivity.doc_content_s,content);
            e.commit();
            editor_content = editor.getText().toString();
            Intent intent =new Intent(MainActivity.this, ShowPreviewActivity.class);
            intent.putExtra("doc_id_for_preview",doc_id);
            startActivity(intent);

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.home) {
            Toast.makeText(this, "GO to home page",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, HomePageHandler.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.login) {
            Toast.makeText(this, "I am login",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.logout) {
            Toast.makeText(this, "I am logout",
                    Toast.LENGTH_SHORT).show();
            String ip = getString(R.string.ip_address);
            LogoutRequestTask logoutRequestTask = new LogoutRequestTask(this,ip);
            Log.e("Loooggoutttt::", ip + "/api/auth/out");
            logoutRequestTask.execute();
            return true;
        }

        if (id == R.id.myprofile) {
            Toast.makeText(this, "I am myprofile",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, User_DocListActivity.class);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

}
