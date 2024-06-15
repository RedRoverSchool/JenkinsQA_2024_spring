package school.redrover.domain;

import java.util.List;

public class SlimHudson extends BaseHudson {

    private List<SlimJob> jobs;

    public List<SlimJob> getJobs() {
        return jobs;
    }

    @Override
    public String toString() {
        return String.format("SlimHudson{%s}", jobs);
    }
}
