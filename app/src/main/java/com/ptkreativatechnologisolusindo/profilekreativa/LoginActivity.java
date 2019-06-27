package com.ptkreativatechnologisolusindo.profilekreativa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.session.MediaSessionManager;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ptkreativatechnologisolusindo.profilekreativa.session.SessionManager;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    SessionManager sessionManager;
    TextView txt_judul, txt_lupapwd;
    Button bt_login, bt_register;
    TextInputEditText input_user, input_pwd;
    String username, password;
    LinkDatabase linkDatabase;
    private RequestQueue requestQueue;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txt_judul = (TextView) findViewById(R.id.txt_judul);
        txt_lupapwd = (TextView) findViewById(R.id.txt_lupapwd);
        bt_login = (Button) findViewById(R.id.bt_login);
        bt_register = (Button) findViewById(R.id.bt_register);
        input_user = (TextInputEditText) findViewById(R.id.input_user);
        input_pwd = (TextInputEditText) findViewById(R.id.input_pwd);
        linkDatabase = new LinkDatabase();
        sessionManager = new SessionManager(this);


        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = input_user.getText().toString();
                password = input_pwd.getText().toString();

                if (username.equals("")||password.equals("")){
                    Toast.makeText(getBaseContext(), "Masukkan username dan password terlebih dahulu",Toast.LENGTH_LONG).show();
                }else {
                    String url = linkDatabase.linkurl()+"login_peserta.php";
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
//                            progressBar.setVisibility(View.INVISIBLE);
                            if(response.toLowerCase().toString().equals("login sukses")){
                                sessionManager.createSession(input_user.getText().toString(), input_pwd.getText().toString());
                                Intent intent = new Intent(LoginActivity.this ,HomeActivity.class);
                                startActivity(intent);
                            }else {
//                                login.setVisibility(View.VISIBLE);
                                Toast.makeText(getApplicationContext(), "Username atau Password anda salah !", Toast.LENGTH_LONG).show();}
                        }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Periksa koneksi internet anda", Toast.LENGTH_LONG).show();
//                            login.setVisibility(View.VISIBLE);
//                            progressBar.setVisibility(View.GONE);
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();

                            params.put("username", input_user.getText().toString());
                            params.put("password", input_pwd.getText().toString());

                            return params;
                        }
                    };
                    requestQueue    =   Volley.newRequestQueue(LoginActivity.this);
                    requestQueue.add(stringRequest);
                }
            }
//                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//                startActivity(intent);

        });

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistActivity.class);
                startActivity(intent);
            }
        });
    }
}
