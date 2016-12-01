package myvolley.myvolley_test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/*このクラスではサーバーから読み込んだデータをリストに表示するクラスです。*/

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    

    private Button readButton,editButton;
    private ListView listView;
    private ArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Viewの設定
        readButton=(Button)findViewById(R.id.readbtn);
        readButton.setOnClickListener(this);
        editButton=(Button)findViewById(R.id.editbtn);
        editButton.setOnClickListener(this);

        //リスト
        listView=(ListView)findViewById(R.id.listView);
        adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1);
    }


    @Override
    protected void onResume() {
        super.onResume();
        //サーバーからデータを読み込む
        rereadVolley();
    }


    @Override
    public void onClick(View view) {

        if (view.getId()==R.id.readbtn){
            //サーバに再度アクセス
            rereadVolley();

        }else if (view.getId()==R.id.editbtn){
            // 書き込みクラスに移動
            Intent intent=new Intent(this,WriteActivity.class);
            startActivity(intent);
        }
    }


    /*Volleyを起動データがあれば読み込みを開始*/
    private void rereadVolley() {

        //サーバーのアドレス
        String GET_URL="サーバーのURL.read.php";

        //Volleyによる通信開始　（GETかPOST、サーバーのURL、受信メゾット、エラーメゾット）
        RequestQueue getQueue=Volley.newRequestQueue(this);
        JsonObjectRequest mRequest = new JsonObjectRequest(Request.Method.GET,GET_URL,

                // 通信成功
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //リストを更新する
                        ChangeListView(response);
                    }
                },

                // 通信失敗
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,"通信に失敗しました。",Toast.LENGTH_SHORT).show();
                    }
                }
        );

        getQueue.add(mRequest);
    }



    private void ChangeListView(JSONObject response) {

        try {

            //Jsonデータを取得
            JSONArray count=  response.getJSONArray("SQL_TEST");
            adapter.clear();

            //Jsonデータからリストを作成
            for (int i=0;i<count.length();i++){
                JSONObject data=count.getJSONObject(i);
                adapter.add(data.getString("name")+"\n"+data.get("text"));
            }

            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        } catch (JSONException e) {
                e.printStackTrace();
        }
    }


    /*バックキーでホームに戻す。*/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode==KeyEvent.KEYCODE_BACK){
            moveTaskToBack(true);
        }
        return super.onKeyDown(keyCode, event);
    }
}
