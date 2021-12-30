package vn.edu.stu.webservice;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText edtUsername, edtPass;
    Button btnLogin;
    final String server = "http://trannhatban.top/de1/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }
    private void addControls() {
        edtUsername = findViewById(R.id.edtUsername);
        edtPass = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
    }

    private void addEvents() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dangNhap(server);
            }
        });
    }

    private void dangNhap(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean result = Boolean.parseBoolean(jsonObject.getString("RESULT"));
                    if(result == true){
                        Toast.makeText(MainActivity.this, "Đăng nhập thành công",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(MainActivity.this, HangHoaActivity.class));
                    }else {
                        Toast.makeText(MainActivity.this, "Đăng nhập không thành công",Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Lỗi\n",Toast.LENGTH_LONG).show();
                Log.d("AAA", "Lỗi\n" + error.toString());
            }
        };
        Uri.Builder builder = Uri.parse(server + "login.php").buildUpon();
        url = builder.build().toString();
        StringRequest request = new StringRequest(Request.Method.POST, url,
                listener,
                errorListener
                ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("username", edtUsername.getText().toString().trim());
                params.put("password", edtPass.getText().toString().trim());
                return params;
            }
        };
        request.setRetryPolicy( new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }


}