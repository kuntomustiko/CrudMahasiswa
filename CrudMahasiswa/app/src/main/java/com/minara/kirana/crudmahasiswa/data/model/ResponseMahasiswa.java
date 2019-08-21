package com.minara.kirana.crudmahasiswa.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseMahasiswa {

    @SerializedName("pesan")
    private String pesan;

    @SerializedName("datanya")
    private List<DatanyaItem> datanya;

    @SerializedName("status")
    private int status;

    public void setPesan(String pesan){
        this.pesan = pesan;
    }
    public String getPesan(){
        return pesan;
    }

    public void setDatanya(List<DatanyaItem> datanya){
        this.datanya = datanya;
    }
    public List<DatanyaItem> getDatanya(){
        return datanya;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public String toString(){
        return 	"ResponseMahasiswa{" +
                "pesan = '" + pesan + '\'' +
                ",datanya = '" + datanya + '\'' +
                ",status = '" + status + '\'' +
                "}";
    }
}
