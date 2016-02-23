package com.example.miaodonghan.markupproject_01;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;


/**
 * Created by miaodonghan on 2/22/16.
 */
public class PutRequestTask  extends AsyncTask<String, Integer, String> {


    Context context;

    int selected_id;
    EditText editor;

    public PutRequestTask(Context context, int selected_id, EditText editor) {

        this.context = context;
        this.selected_id = selected_id;
        this.editor = editor;
    }

    @Override
    protected void onPreExecute() {
        // start a spinning sign
    }

    @Override
    protected String doInBackground(String... data) {
            String status ="";
            String url = " http://192.168.155.6:1337/api/doc/" + data[0];
        try {

            RequestParams params = new RequestParams();
            params.addHeader("name", data[1]);
            params.addQueryStringParameter("content", data[2]);

            // 只包含字符串参数时默认使用BodyParamsEntity，
            // 类似于UrlEncodedFormEntity（"application/x-www-form-urlencoded"）。
            //params.addBodyParameter("name3", "value3");
            Log.i("((((((((((((( ", ")");

            HttpUtils http = new HttpUtils();
            http.send(HttpRequest.HttpMethod.POST,
                    url,
                    params,
                    new RequestCallBack<String>() {

                        @Override
                        public void onStart() {
                            //testTextView.setText("conn...");
                        }

                        @Override
                        public void onLoading(long total, long current, boolean isUploading) {
                            if (isUploading) {
                                //testTextView.setText("upload: " + current + "/" + total);
                            } else {
                                //testTextView.setText("reply: " + current + "/" + total);
                            }
                        }

                        @Override
                        public void onSuccess(ResponseInfo<String> responseInfo) {
                           // testTextView.setText("reply: " + responseInfo.result);
                        }

                        @Override
                        public void onFailure(HttpException error, String msg) {
                            Log.i( "failure:", error.getExceptionCode() + ":" + msg);
                        }
                    });
//192.168.155.6
//            URL url = new URL("http://104.194.108.91:1337/api/doc/" + data[0]);
//            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
//            httpCon.setDoOutput(true);
//            httpCon.setRequestMethod("PUT");
//            OutputStreamWriter out = new OutputStreamWriter(
//                    httpCon.getOutputStream());
//
//            out.write("{"
//                    + " 'name' :  "+ data[1]
//                    + "'content' : " + data[2]
//                    + "}");
//
//            status = "successful";
//            out.close();
//            httpCon.getInputStream();


        } catch (Exception ex) {
            // Log.e("backgroud task", ex.getMessage());
            status = "failure";
        }

        return status;
    }

    //@Override
    protected void onPostExecute(String result) {

        //editor.setText(result);

    }

}
