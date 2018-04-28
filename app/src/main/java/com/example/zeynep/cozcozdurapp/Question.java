package com.example.zeynep.cozcozdurapp;
// Question sınıfı
/**
 * Created by Zeynep on 18.04.2018.
 */

public class Question {

    String soru_metni;
    String A;
    String B;
    String C;
    String D;
    String cevap;


    public String getSoru_metni() {
        return soru_metni;
    }

    public void setSoru_metni(String soru_metni) {
        this.soru_metni = soru_metni;
    }

    public String getA() {
        return A;
    }

    public void setA(String a) {
        A = a;
    }

    public String getB() {
        return B;
    }

    public void setB(String b) {
        B = b;
    }

    public String getC() {
        return C;
    }

    public void setC(String c) {
        C = c;
    }

    public String getD() {
        return D;
    }

    public void setD(String d) {
        D = d;
    }

    public String getCevap() {
        return cevap;
    }

    public void setCevap(String cevap) {
        this.cevap = cevap;
    }
}
