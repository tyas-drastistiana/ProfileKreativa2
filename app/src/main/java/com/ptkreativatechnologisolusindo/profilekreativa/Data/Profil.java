package com.ptkreativatechnologisolusindo.profilekreativa.Data;

public class Profil {
    private String NAMA_PERUSAHAAN;
    private String ALAMAT;
    private String EMAIL;
    private String DESK_PERUSAHAAN;
    private String INSTAGRAM;
    private String TELP;
    private String FB;

    public String getFB() {
        return FB;
    }

    public void setFB(String FB) {
        this.FB = FB;
    }

    public void setALAMAT(String ALAMAT) {
        this.ALAMAT = ALAMAT;
    }

    public void setDESK_PERUSAHAAN(String DESK_PERUSAHAAN) {
        this.DESK_PERUSAHAAN = DESK_PERUSAHAAN;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public void setINSTAGRAM(String INSTAGRAM) {
        this.INSTAGRAM = INSTAGRAM;
    }

    public void setNAMA_PERUSAHAAN(String NAMA_PERUSAHAAN) {
        this.NAMA_PERUSAHAAN = NAMA_PERUSAHAAN;
    }

    public void setTELP(String TELP) {
        this.TELP = TELP;
    }

    public String getALAMAT() {
        return ALAMAT;
    }

    public String getDESK_PERUSAHAAN() {
        return DESK_PERUSAHAAN;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public String getINSTAGRAM() {
        return INSTAGRAM;
    }

    public String getNAMA_PERUSAHAAN() {
        return NAMA_PERUSAHAAN;
    }

    public String getTELP() {
        return TELP;
    }
}
