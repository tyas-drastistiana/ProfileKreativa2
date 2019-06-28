package com.ptkreativatechnologisolusindo.profilekreativa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.session.MediaSessionManager;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ptkreativatechnologisolusindo.profilekreativa.Data.Peserta;
import com.ptkreativatechnologisolusindo.profilekreativa.Data.Product;
import com.ptkreativatechnologisolusindo.profilekreativa.session.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    SessionManager sessionManager;
    TextView txt_judul, txt_lupapwd;
    Button bt_login, bt_register;
    TextInputEditText input_user, input_pwd;
    String username, password;
    LinkDatabase linkDatabase;
    private RequestQueue requestQueue;
    private JsonArrayRequest ArrayRequest;
    private List<Peserta> lstData;
    DataHelper dbHelper;

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
        dbHelper = new DataHelper(this);


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
                                Toast.makeText(getApplicationContext(), response.toLowerCase().toString(), Toast.LENGTH_LONG).show();
                                getPeserta();
//                                updatee();
                                selecttt();
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

    public void getPeserta(){
            String URL      =   linkDatabase.linkurl()+"peserta_event.php?operasi=view_peserta&username="+input_user.getText().toString()
                                +"&password="+input_pwd.getText().toString();
            ArrayRequest    =   new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {
                    JSONObject jsonObject = null;

//                    for (int i = 0; i < response.length(); i++) {
                        try {
                            jsonObject = response.getJSONObject(0);
                            Peserta model = new Peserta();
//                            Toast.makeText(getApplicationContext(),jsonObject.getString("EMAIL_PESERTA"), Toast.LENGTH_LONG ).show();
                            model.setID_PESERTA(jsonObject.getString("ID_PESERTA"));
                            model.setNAMA_PESERTA(jsonObject.getString("NAMA_PESERTA"));
                            model.setJENIS_KELAMIN(jsonObject.getString("JENIS_KELAMIN"));
                            model.setALAMAT_PESERTA(jsonObject.getString("ALAMAT_PESERTA"));
                            model.setEMAIL_PESERTA(jsonObject.getString("EMAIL_PESERTA"));
                            model.setNO_HP(jsonObject.getString("NO_HP"));
                            model.setPEND(jsonObject.getString("PENDIDIKAN_TERAKHIR"));

                            SQLiteDatabase db = dbHelper.getWritableDatabase();
                            db.execSQL("update user set id_peserta='"+jsonObject.getString("ID_PESERTA")+"'" +
                                    ",nama='"+jsonObject.getString("NAMA_PESERTA")+"'" +
                                    ", nohp='"+jsonObject.getString("NO_HP")+"'" +
                                    ", pend='"+jsonObject.getString("PENDIDIKAN_TERAKHIR")+"'" +
                                    ", jlk='"+jsonObject.getString("JENIS_KELAMIN")+"'" +
                                    ", alm='"+jsonObject.getString("ALAMAT_PESERTA")+"'" +
                                    ", email='"+jsonObject.getString("EMAIL_PESERTA")+"' where id='1'");

//
//                            SQLiteDatabase db = dbHelper.getWritableDatabase();
//                            db.execSQL("insert into Antrian(id, nama, nohp , pend , jlk , alm , email ) values('" +
//                                    jsonObject.getString("ID_PESERTA") + "','" +
//                                    jsonObject.getString("NAMA_PESERTA")+ "','" +
//                                    jsonObject.getString("NO_HP")+ "','" +
//                                    jsonObject.getString("PENDIDIKAN_TERAKHIR")+ "','" +
//                                    jsonObject.getString("JENIS_KELAMIN")+ "','" +
//                                    jsonObject.getString("ALAMAT_PESERTA")+ "','" +
//                                    jsonObject.getString("EMAIL_PESERTA")+"')");

//                            lstData.add(model);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                    }
//                    progressDialog.dismiss();
                    Intent intent = new Intent(LoginActivity.this ,HomeActivity.class);
                    startActivity(intent);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("error", error.toString());
//                    progressDialog.dismiss();
                }
            });
            requestQueue    =   Volley.newRequestQueue(LoginActivity.this);
            requestQueue.add(ArrayRequest);

    }

    void selecttt(){
        SQLiteDatabase db2 = dbHelper.getReadableDatabase();
        Cursor c = db2.rawQuery("SELECT * FROM user ", null);
        if (c.moveToFirst()){
            do {
                // Passing values
                String column1 = c.getString(0);
                String column2 = c.getString(1);
                String column3 = c.getString(2);
                String column4 = c.getString(3);
                String column5 = c.getString(4);
                String column6 = c.getString(5);
                String column7 = c.getString(6);
                // Do something Here with values
//                Toast.makeText(getApplicationContext(), column1+" " + column2+ "   "+column3+ "   "+column4+ "   "+column5+ "   "+column6+ "   "+column7, Toast.LENGTH_LONG).show();
            } while(c.moveToNext());
        }
        c.close();
        db2.close();
    }

//    void updatee(){
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        db.execSQL("update user set id_peserta='ID_PESERTA', nama='NAMA_PESERTA', nohp='NO_HP', pend='PENDIDIKAN_TERAKHIR',jlk='JENIS_KELAMIN', alm='ALAMAT_PESERTA', email='EMAIL_PESERTA' where id='1'");
//
//    }
}
