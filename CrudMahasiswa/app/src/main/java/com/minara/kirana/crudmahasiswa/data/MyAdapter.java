package com.minara.kirana.crudmahasiswa.data;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.minara.kirana.crudmahasiswa.R;
import com.minara.kirana.crudmahasiswa.data.model.DatanyaItem;
import com.minara.kirana.crudmahasiswa.ui.UpdateDeleteActivity;

import java.util.List;

import static com.minara.kirana.crudmahasiswa.ui.UpdateDeleteActivity.EXTRA_OBJECT;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    Context context; // menunjukan/menegaskan kita ini sedang ada dimana
    private List<DatanyaItem> list;

    public MyAdapter(Context context, List<DatanyaItem> list) {
        this.context = context;
        this.list = list;
    }

    // TODO 1 fuction ini untuk menyisipkan sebuah layout ke dalam recyclerview
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup viewType, int i) {
        View view = LayoutInflater.from(viewType.getContext()).inflate(R.layout.item_mahasiswa, viewType, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }
    //TODO 2 fuction ini yang mengolah data yang ingin ditampilkan
    //mengambil data dari list lalu dimasukkan kedalam view-view item
    @Override
    public void onBindViewHolder( ViewHolder viewHolder, int position) {
        final String id= list.get(position).getMahasiswaId();
        final String nim = list.get(position).getMahasiswaNim();
        final String name = list.get(position).getMahasiswaNama();
        final String jurusan = list.get(position).getMahasiswaJurusan();

        //TODO masukkan kedalam view
        viewHolder.tvNim.setText(nim);
        viewHolder.tvName.setText(name);
        viewHolder.tvJurusan.setText(jurusan);

        //ketika sebuah item di klik
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO set data nya yng mau di kirim
                DatanyaItem datanyaItem = new DatanyaItem();
                datanyaItem.setMahasiswaId(id);
                datanyaItem.setMahasiswaNim(nim);
                datanyaItem.setMahasiswaNama(name);
                datanyaItem.setMahasiswaJurusan(jurusan);

                Intent intent = new Intent(context, UpdateDeleteActivity.class);

                //Keynya key si penerima
                intent.putExtra(EXTRA_OBJECT, datanyaItem);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list == null)

            return 0;
        return list.size();
    }

    //TODO deklarasika yang ingin di gunakanan dari layout yang sudah di inflate(ditempelkan)
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNim, tvName, tvJurusan;
        //TODO casting view / hub dengan id nya
        public ViewHolder( View itemView) {
            super(itemView);
            tvNim = itemView.findViewById(R.id.tvNim);
            tvName = itemView.findViewById(R.id.tvName);
            tvJurusan = itemView.findViewById(R.id.tvJurusan);
        }
    }
}
