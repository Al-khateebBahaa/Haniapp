package com.ahmedMustafa.Hani.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllCountriresModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("countries")
    @Expose
    private List<Country> countries = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public class Country {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("flag_32")
        @Expose
        private String flag32;
        @SerializedName("flag_128")
        @Expose
        private String flag128;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("name_ar")
        @Expose
        private String nameAr;
        @SerializedName("phone_code")
        @Expose
        private Integer phoneCode;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getFlag32() {
            return flag32;
        }

        public void setFlag32(String flag32) {
            this.flag32 = flag32;
        }

        public String getFlag128() {
            return flag128;
        }

        public void setFlag128(String flag128) {
            this.flag128 = flag128;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNameAr() {
            return nameAr;
        }

        public void setNameAr(String nameAr) {
            this.nameAr = nameAr;
        }

        public Integer getPhoneCode() {
            return phoneCode;
        }

        public void setPhoneCode(Integer phoneCode) {
            this.phoneCode = phoneCode;
        }

    }
}
