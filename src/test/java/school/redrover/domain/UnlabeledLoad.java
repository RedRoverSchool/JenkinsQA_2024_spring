package school.redrover.domain;

import com.google.gson.annotations.SerializedName;

public class UnlabeledLoad {

    @SerializedName("_class")
    private String clazz;

    @Override
    public String toString() {
        return "UnlabeledLoad{" +
                "clazz='" + clazz + '\'' +
                '}';
    }
}
