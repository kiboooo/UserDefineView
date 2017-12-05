package com.example.kiboooo.userdefinedview.Bean;


import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Kiboooo on 2017/12/5.
 */

public class Life {
    public String date;

    @SerializedName("info")
    public INFO Info;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public INFO getInfo() {
        return Info;
    }

    public void setInfo(INFO info) {
        Info = info;
    }

    public class INFO{
        /*下列的温馨提示为数组的形式，下标【0】：表明情况；下标【1】：相对应的建议*/

        @SerializedName("kongtiao")
        public List<String> kongtiao;
        @SerializedName("yundong")
        public List<String>  yundong;
        @SerializedName("ziwaixian")
        public List<String>  ziwaixian;
        @SerializedName("ganmao")
        public List<String>  ganmao;
        @SerializedName("xiche")
        public List<String>  xiche;
        @SerializedName("chuanyi")
        public List<String>  chuanyi;

        public List<String> getKongtiao() {
            return kongtiao;
        }

        public void setKongtiao(List<String> kongtiao) {
            this.kongtiao = kongtiao;
        }

        public List<String> getYundong() {
            return yundong;
        }

        public void setYundong(List<String> yundong) {
            this.yundong = yundong;
        }

        public List<String> getZiwaixian() {
            return ziwaixian;
        }

        public void setZiwaixian(List<String> ziwaixian) {
            this.ziwaixian = ziwaixian;
        }

        public List<String> getGanmao() {
            return ganmao;
        }

        public void setGanmao(List<String> ganmao) {
            this.ganmao = ganmao;
        }

        public List<String> getXiche() {
            return xiche;
        }

        public void setXiche(List<String> xiche) {
            this.xiche = xiche;
        }

        public List<String> getChuanyi() {
            return chuanyi;
        }

        public void setChuanyi(List<String> chuanyi) {
            this.chuanyi = chuanyi;
        }
    }
}
