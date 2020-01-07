
package com.allforcars.all4cars.Test.TestLargeAPI.Model;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONObject;

public class Result {

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("adx_keywords")
    @Expose
    private String adxKeywords;
    @SerializedName("column")
    @Expose
    private Object column;
    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("byline")
    @Expose
    private String byline;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("abstract")
    @Expose
    private String _abstract;
    @SerializedName("published_date")
    @Expose
    private String publishedDate;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("asset_id")
    @Expose
    private long assetId;
    @SerializedName("views")
    @Expose
    private Integer views;
    @SerializedName("des_facet")
    @Expose
    private Object desFacet = null;
    @SerializedName("org_facet")
    @Expose
    private Object orgFacet = null;
    @SerializedName("per_facet")
    @Expose
    private Object perFacet;
    @SerializedName("geo_facet")
    @Expose
    private Object geoFacet = null;
    @SerializedName("media")
    @Expose
    private List<Medium> media = null;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAdxKeywords() {
        return adxKeywords;
    }

    public void setAdxKeywords(String adxKeywords) {
        this.adxKeywords = adxKeywords;
    }

    public Object getColumn() {
        return column;
    }

    public void setColumn(Object column) {
        this.column = column;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getByline() {
        return byline;
    }

    public void setByline(String byline) {
        this.byline = byline;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbstract() {
        return _abstract;
    }

    public void setAbstract(String _abstract) {
        this._abstract = _abstract;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAssetId() {
        return assetId;
    }

    public void setAssetId(long assetId) {
        this.assetId = assetId;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public List<String> getDesFacet() {
        if(desFacet instanceof String)
            return null;
        else

            return (List<String>) desFacet;
    }



    public void setDesFacet(List<String> desFacet) {
        this.desFacet = desFacet;
    }

    public List<String> getOrgFacet() {
        if(orgFacet instanceof String)
            return null;
        else

            return (List<String>) orgFacet;
    }

    public void setOrgFacet(List<String> orgFacet) {
        this.orgFacet = orgFacet;
    }

    public List<String> getPerFacet() {
        if(perFacet instanceof String)
            return null;
        else

        return (List<String>) perFacet;
    }

    public void setPerFacet(List<String> perFacet) {
        this.perFacet = perFacet;
    }

    public List<String> getGeoFacet() {
         if(geoFacet instanceof String)
            return null;
        else

            return (List<String>) geoFacet;

    }

    public void setGeoFacet(List<String> geoFacet) {
        this.geoFacet = geoFacet;
    }

    public List<Medium> getMedia() {
        return media;
    }

    public void setMedia(List<Medium> media) {
        this.media = media;
    }
/*
    public static class ResultStateDeserializer implements JsonDeserializer<Result> {

        @Override
        public Result deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            Result result = new Gson().fromJson(json, Result.class);
            JsonObject jsonObject = json.getAsJsonObject();

            JsonObject my_result=jsonObject.getAsJsonObject("results");
            JsonArray jsonArray=my_result.getAsJsonArray();

            for (int i=0; i<jsonArray.size(); i++) {
               JsonObject jsonObject1 =jsonArray.get(i).getAsJsonObject().getAsJsonObject("per_facet");

                    if(jsonObject1.isJsonArray()){
                        //result.setMessage(elem.getAsString());
                        result.setPerFacet(jsonObject.getAsJsonObject().get("per_facet").getAsString());

                    }else{



                    }

            }
            return result ;
        }
    }
*/

}
