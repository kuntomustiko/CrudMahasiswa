package com.minara.kirana.crudmahasiswa.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.minara.kirana.crudmahasiswa.MainActivity;
import com.minara.kirana.crudmahasiswa.R;
import com.minara.kirana.crudmahasiswa.data.model.DatanyaItem;
import com.minara.kirana.crudmahasiswa.data.model.ResponsePost;
import com.minara.kirana.crudmahasiswa.networking.ConfigRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateDeleteActivity extends AppCompatActivity implements View.OnClickListener {

    //TODO key / variable penampung untuk terima data yang dikirim
    public static final String EXTRA_OBJECT = "OBJECT";

    EditText edtNim, edtNama, edtMajors;
    String id, nim, name, majors;
    Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);

        edtNim = findViewById(R.id.edt_nim);
        edtNama = findViewById(R.id.edt_nama);
        edtMajors = findViewById(R.id.edt_jurusan);
        btnUpdate = findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(this);
        getDataMahasiswa();

    }

    private void getDataMahasiswa() {
        DatanyaItem data = getIntent().getParcelableExtra(EXTRA_OBJECT);
        id = data.getMahasiswaId();
        nim = data.getMahasiswaNim();
        name = data.getMahasiswaNama();
        majors = data.getMahasiswaJurusan();

        edtNim.setText(nim);
        edtNama.setText(name);
        edtMajors.setText(majors);

    }

    @Override
    public void onClick(View v) {

        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("Yakin Mau Update");
        dialog.setMessage("Are you sure want to Update?");

        dialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {

                nim = edtNim.getText().toString().trim();
                name = edtNama.getText().toString().trim();
                majors = edtMajors.getText().toString().trim();

                ConfigRetrofit.getInstance()
                        .update(id, nim, name, majors)
                        .enqueue(new Callback<ResponsePost>() {
                            @Override
                            public void onResponse(Call<ResponsePost> call, Response<ResponsePost> response) {

                                if (response.isSuccessful()){
                                    String msg= response.body().getPesan();
                                    int status =  response.body().getStatus();

                                    if (status == 1){
                                        Toast.makeText(UpdateDeleteActivity.this, msg, Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                        startActivity(new Intent(UpdateDeleteActivity.this, MainActivity.class));
                                        // untuk langsung mengapdate datanya di tampilan setelah sukses insert tanpa harus ngescroll activitynya
                                    } else
                                        Toast.makeText(UpdateDeleteActivity.this, msg, Toast.LENGTH_SHORT).show();

                                } else response.errorBody();
                            }

                            @Override
                            public void onFailure(Call<ResponsePost> call, Throwable t) {
                                t.printStackTrace();
                                Toast.makeText(UpdateDeleteActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });


            }
        });

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        int idMenu = item.getItemId();
        if (idMenu == R.id.action_delete){
            AlertDialog dialog = new AlertDialog.Builder(UpdateDeleteActivity.this).create();

            dialog.setTitle("Confirmation");
            dialog.setMessage("Are You Sure Delete?");
            dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            initDeleteData();
                        }
                    });

            dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "cancel",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            dialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void initDeleteData(){
        ConfigRetrofit.getInstance()
                .delete(id)
                .enqueue(new Callback<ResponsePost>() {
                    @Override
                    public void onResponse(Call<ResponsePost> call, Response<ResponsePost> response) {

                        if (response.isSuccessful()){
                            String msg = response.body().getPesan();
                            int status = response.body().getStatus();
                            if(status == 1){
                                Toast.makeText(UpdateDeleteActivity.this, msg, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(UpdateDeleteActivity.this, MainActivity.class));
                            } else
                                Toast.makeText(UpdateDeleteActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }else response.errorBody();
                    }

                    @Override
                    public void onFailure(Call<ResponsePost> call, Throwable t) {

                        t.printStackTrace();
                    }
                });
    }


}
