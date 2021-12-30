package vn.edu.stu.webservice;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import vn.edu.stu.webservice.model.HangHoa;

public class HangHoaActivity extends AppCompatActivity {
    EditText edtTenHangHoa;
    ListView lvDSHangHoa;
    Button btnGetData;
    ArrayAdapter<HangHoa> adapter;
    final String server = "http://trannhatban.top/de1/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hang_hoa);
        addControls();
        addEvents();
    }
    private void addControls() {
        edtTenHangHoa = findViewById(R.id.edtHangHoa);
        lvDSHangHoa = findViewById(R.id.lvDSHangHoa);
        btnGetData = findViewById(R.id.btnLayHangHoa);
        adapter = new ArrayAdapter<>(HangHoaActivity.this, android.R.layout.simple_list_item_1);
        lvDSHangHoa.setAdapter(adapter);
    }
    private void addEvents() {
        btnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layHangHoa(server);
            }
        });
    }
    private void layHangHoa(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(HangHoaActivity.this);
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(HangHoaActivity.this,response,Toast.LENGTH_LONG).show();
                adapter.clear();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    int len = jsonArray.length();
                    for(int i =0; i<len;i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int ma = Integer.parseInt(jsonObject.getString("ma"));
                        String ten = jsonObject.getString("ten");
                        Double gia = Double.valueOf(jsonObject.getString("gia"));
                        adapter.add(new HangHoa(ma, ten, gia));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HangHoaActivity.this, "Lỗi\n",Toast.LENGTH_LONG).show();
                Log.d("AAA", "Lỗi\n" + error.toString());
            }
        };
        Uri.Builder builder = Uri.parse(server + "gethanghoa.php?").buildUpon();
        builder.appendQueryParameter("loaihanghoa", edtTenHangHoa.getText().toString().trim());
        url = builder.build().toString();
        StringRequest request = new StringRequest(Request.Method.GET, url,
                listener,
                errorListener
        );
        request.setRetryPolicy( new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }


}