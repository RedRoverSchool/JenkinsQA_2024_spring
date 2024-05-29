package school.redrover.domain;

public class Job extends SlimJob {
    private String url;

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Job{" + super.toString() +
                "url='" + url + '\'' +
                '}';
    }
}
