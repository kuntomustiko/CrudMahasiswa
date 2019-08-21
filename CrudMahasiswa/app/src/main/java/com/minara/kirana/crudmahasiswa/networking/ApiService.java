package com.minara.kirana.crudmahasiswa.networking;

import com.minara.kirana.crudmahasiswa.data.model.ResponseMahasiswa;
import com.minara.kirana.crudmahasiswa.data.model.ResponsePost;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    //harus sesuai dengan function yang ada di database kita
    @GET("getMahasiswa")
    Call<ResponseMahasiswa> setShowData();

    // untuk mengirimkan data yang sudah di insert di aplikasi ke server
    // "nim", "insetMahasiswa" harus sesuai dengan isi file untuk di server
    // kalau post harus di encode dan gak boleh publik
    @FormUrlEncoded
    @POST("insertMahasiswa")
    Call<ResponsePost> create(@Field("nim") String nim,
                              @Field("name") String name,
                              @Field("majors") String majors);

    @FormUrlEncoded
    @POST("updateMahasiswa")
    Call<ResponsePost> update(
            @Field("id") String id,
            @Field("nim") String nim,
            @Field("name") String name,
            @Field("majors") String majors);

    @FormUrlEncoded
    @POST("deleteMahasiswa")
    Call<ResponsePost> delete(
            @Field("id") String id);
}
