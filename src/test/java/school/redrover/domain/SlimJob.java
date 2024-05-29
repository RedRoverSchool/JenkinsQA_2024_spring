package school.redrover.domain;

import com.google.gson.annotations.SerializedName;

public class SlimJob {
    @SerializedName("_class")
    private String clazz;
    private String name;
    private String color;

    public String getClazz() {
        return clazz;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "SlimJob{" +
                "clazz='" + clazz + '\'' +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
