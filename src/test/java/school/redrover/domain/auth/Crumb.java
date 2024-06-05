package school.redrover.domain.auth;

public class Crumb {
    private final String crumb;
    private final String crumbRequestField;

    public Crumb(String defaultCrumbIssuer) {
        crumb = defaultCrumbIssuer;
        crumbRequestField = "Jenkins-Crumb";
    }

    public String getCrumb() {
        return crumb;
    }

    public String getCrumbRequestField() {
        return crumbRequestField;
    }

    @Override
    public String toString() {
        return String.format("%s : '%s'}", crumbRequestField, crumb);
    }
}
