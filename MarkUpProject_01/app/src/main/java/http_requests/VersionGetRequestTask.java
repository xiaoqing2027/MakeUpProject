package http_requests;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by miaodonghan on 3/20/16.
 */
public class VersionGetRequestTask extends AsyncTask<String, Integer, String> {
    Context context;

    int selected_id;
    EditText editor;

    public VersionGetRequestTask(Context context, int selected_id, EditText editor) {

        this.context = context;
        this.selected_id = selected_id;
        this.editor =editor;
    }

    @Override
    protected void onPreExecute() {
        // start a spinning sign
    }

    @Override
    protected String doInBackground(String... uri) {
        String result ="";
        try {

            InputStream response = new URL(uri[0]).openStream();

            Scanner s = new Scanner(response).useDelimiter("\\A");
            String res = s.hasNext() ? s.next() : "";
            Log.e("++++++++++++++++", res.toString());
            JSONObject obj = new  JSONObject(res);


            result = "#"+obj.getString("name") + "\n\nupdate Time :  " + obj.getString("updatedAt")+"\n\n"+
                    obj.getString("content");


        } catch (Exception ex) {
            // Log.e("backgroud task", ex.getMessage());
        }

        return result;
    }

    //@Override
    protected void onPostExecute(String result) {

        editor.setText(result);

    }
}
