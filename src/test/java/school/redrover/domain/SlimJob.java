package school.redrover.domain;

import com.google.gson.annotations.SerializedName;

public class SlimJob {
    @SerializedName("_class")
    private String clazz;
    private String name;
    private String color;

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return String.format("%n  Job{name:'%s', color:'%s'}", name, color);
    }
}
