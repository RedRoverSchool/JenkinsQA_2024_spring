package school.redrover.domain;

import com.google.gson.annotations.SerializedName;

public class View {
    @SerializedName("_class")
    private String clazz;
    private String name;
    private String url;

    @Override
    public String toString() {
        return "View{" +
                "clazz='" + clazz + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
