package school.redrover.domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SlimHudson {

    @SerializedName("_class")
    private String clazz;
    private List<SlimJob> jobs;

    public String getClazz() {
        return clazz;
    }

    public List<SlimJob> getJob() {
        return jobs;
    }

    @Override
    public String toString() {
        return "SlimHudson{" +
                "clazz='" + clazz + '\'' +
                ", jobs=" + jobs +
                '}';
    }
}
