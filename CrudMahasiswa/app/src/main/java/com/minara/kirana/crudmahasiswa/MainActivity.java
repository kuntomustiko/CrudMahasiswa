package com.minara.kirana.crudmahasiswa;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.minara.kirana.crudmahasiswa.data.MyAdapter;
import com.minara.kirana.crudmahasiswa.data.model.DatanyaItem;
import com.minara.kirana.crudmahasiswa.data.model.ResponseMahasiswa;
import com.minara.kirana.crudmahasiswa.data.model.ResponsePost;
import com.minara.kirana.crudmahasiswa.networking.ApiService;
import com.minara.kirana.crudmahasiswa.networking.ConfigRetrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    // TODO deklarasi
    RecyclerView recyclerView;
    public static final String STATE = "STATE";
    List<DatanyaItem> data = new ArrayList<>();

    EditText edtNim, edtNama, edtJurusan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.my_recyclerview);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertMahasiswa();
            }
        });

        initGetData();
    }

    private void initGetData() {

        //TODO 1.0 panggil retrofit
        ApiService service = ConfigRetrofit.getInstance();

        //TODO 1.1 siapin requestnya
        Call<ResponseMahasiswa> call = service.setShowData();

        //TODO 1.2 kirim requestnya
        call.enqueue(new Callback<ResponseMahasiswa>() {

            //TODO function / method ini di execeute klo response berhasil
            @Override
            public void onResponse(retrofit2.Call<ResponseMahasiswa> call, Response<ResponseMahasiswa> response) {

                if (response.isSuccessful()){
                    String msg = response.body().getPesan();
                    int status = response.body().getStatus();

                    List<DatanyaItem> listData = response.body().getDatanya();

                    //TODO klo status bernilai 1 tampilkan dalam list
                    if (status == 1){
                        initView(listData);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();

                    } else Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();

                } else response.errorBody();
            }

            //TODO function ketika gagal
            @Override
            public void onFailure(Call<ResponseMahasiswa> call, Throwable t) {

            }
        });
    }

    private void initView(List<DatanyaItem> listData) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyAdapter adapter = new MyAdapter(this, listData);
        recyclerView.setAdapter(adapter);

    }

    private void insertMahasiswa() {
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.insert_mahasiswa, null);

        edtNim = v.findViewById(R.id.edt_nim_inputan);
        edtNama = v.findViewById(R.id.edt_nama_inputan);
        edtJurusan = v.findViewById(R.id.edt_jurusan_inputan);

        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setView(v);
        dialog.setCancelable(false);
        dialog.setTitle("Masukan Data");

        dialog.setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {

                //Todo get ambil inputan dari user
                String nim = edtNim.getText().toString().trim();
                String name = edtNama.getText().toString().trim();
                String majors = edtJurusan.getText().toString().trim();

                //TODO merespon ke server
                ConfigRetrofit.getInstance()
                        .create(nim, name, majors)
                        .enqueue(new Callback<ResponsePost>() {
                            @Override
                            public void onResponse(Call<ResponsePost> call, Response<ResponsePost> response) {

                                if (response.isSuccessful()){
                                    String msg= response.body().getPesan();
                                    int status =  response.body().getStatus();

                                    if (status == 1){
                                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                        // untuk langsung mengapdate datanya di tampilan setelah sukses insert tanpa harus ngescroll activitynya
                                        recreate();
                                    } else
                                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();

                                } else response.errorBody();
                            }

                            @Override
                            public void onFailure(Call<ResponsePost> call, Throwable t) {
                                t.printStackTrace();
                                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
            }
        });
        dialog.show();
    }

    //TODO ketika back ke page utama dia ga balik lagi ke detailnya
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_HOME);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}
