package net.artemkv.marvelserver.marvelconnector;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
class CreatorDataWrapper {
    private int code;
    private String status;
    private CreatorDataContainer data;

    @JsonProperty("code")
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("data")
    public CreatorDataContainer getData() {
        return data;
    }
    public void setData(CreatorDataContainer data) {
        this.data = data;
    }
}
