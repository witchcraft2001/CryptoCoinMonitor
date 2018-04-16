package ru.dm_dev.cryptocurrencymonitor.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

//https://www.cryptocompare.com/api/
//https://min-api.cryptocompare.com/

public class CoinList {
    @SerializedName("Response")
    @Expose
    private String response;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("BaseImageUrl")
    @Expose
    private String baseImageUrl;
    @SerializedName("BaseLinkUrl")
    @Expose
    private String baseLinkUrl;
    @SerializedName("Data")
    @Expose
    private Map<String,Data> data;
    @SerializedName("Type")
    @Expose
    private Integer type;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBaseImageUrl() {
        return baseImageUrl;
    }

    public void setBaseImageUrl(String baseImageUrl) {
        this.baseImageUrl = baseImageUrl;
    }

    public String getBaseLinkUrl() {
        return baseLinkUrl;
    }

    public void setBaseLinkUrl(String baseLinkUrl) {
        this.baseLinkUrl = baseLinkUrl;
    }

    public Map<String,Data> getData() {
        return data;
    }

    public void setData(Map<String,Data> data) {
        this.data = data;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
