package school.redrover.domain;

import com.google.gson.annotations.SerializedName;

public class Crumb {
    @SerializedName("_class")
    private String clazz;
    private String crumb;
    private String crumbRequestField;

    public String getCrumb() {
        return crumb;
    }

    public String getCrumbRequestField() {
        return crumbRequestField;
    }
}
