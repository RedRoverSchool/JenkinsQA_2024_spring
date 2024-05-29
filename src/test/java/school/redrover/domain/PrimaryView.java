package school.redrover.domain;

import com.google.gson.annotations.SerializedName;

public class PrimaryView {

    @SerializedName("_class")
    private String clazz;
    private String name;
    private String url;

    @Override
    public String toString() {
        return "PrimaryView{" +
                "clazz='" + clazz + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
