package school.redrover.domain;

import com.google.gson.annotations.SerializedName;

public abstract class BaseHudson {
    @SerializedName("_class")
    private String clazz;
    private String url;
    private boolean useCrumbs;
    private boolean useSecurity;

    public String getUrl() {
        return url;
    }

    public boolean isUseCrumbs() {
        return useCrumbs;
    }

    public boolean isUseSecurity() {
        return useSecurity;
    }
}
