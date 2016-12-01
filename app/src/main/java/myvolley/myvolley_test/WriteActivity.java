package myvolley.myvolley_test;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

/*このクラス書き込み専用アクティビティです。*/

public class WriteActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText name,text;
    private Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        //View設定
        name=(EditText)findViewById(R.id.nameEdit);
        text=(EditText)findViewById(R.id.textEdit);
        sendButton=(Button)findViewById(R.id.sendbtn);
        sendButton.setOnClickListener(this);
    }


    //ボタンリスナー
    @Override
    public void onClick(View view) {

        //もし文字が入力されていたらVolley起動
        if (name.getText().toString()!=null && text.getText().toString()!=null ){
            startVolley();
        }
    }

    /*無事送信が完了すればトーストを表示する。*/
    private void startVolley() {

        //queue
        RequestQueue postQueue = Volley.newRequestQueue(this);
        //URL
        String POST_URL="サーバーのURL.edit.php";

        StringRequest stringReq=new StringRequest(Request.Method.POST,POST_URL,

                //通信成功
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Toast.makeText(WriteActivity.this,"通信に成功しました。",Toast.LENGTH_SHORT).show();
                    }
                },

                //通信失敗
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Toast.makeText(WriteActivity.this,"通信に失敗しました。",Toast.LENGTH_SHORT).show();
                    }
                }){

            //送信するデータを設定
            @Override
            protected Map<String,String> getParams(){

                //今回は[名前]と[内容]を設定
                Map<String,String> params = new HashMap<String,String>();
                params.put("FastText",name.getText().toString());
                params.put("SecondText",text.getText().toString());
                return params;
            }
        };

       postQueue.add(stringReq);
    }
}
