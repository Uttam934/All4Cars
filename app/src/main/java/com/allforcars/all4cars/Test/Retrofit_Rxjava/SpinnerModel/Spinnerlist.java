
package com.allforcars.all4cars.Test.Retrofit_Rxjava.SpinnerModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Spinnerlist {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("result")
    @Expose
    private List<Result> result = null;
    @SerializedName("extra")
    @Expose
    private List<Object> extra = null;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public List<Object> getExtra() {
        return extra;
    }

    public void setExtra(List<Object> extra) {
        this.extra = extra;
    }

}
