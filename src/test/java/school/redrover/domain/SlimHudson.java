package school.redrover.domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SlimHudson {

    @SerializedName("_class")
    private String clazz;
    private List<SlimJob> jobs;

    public List<SlimJob> getJob() {
        return jobs;
    }

    @Override
    public String toString() {
        return String.format("SlimHudson{%s}", jobs);
    }
}
