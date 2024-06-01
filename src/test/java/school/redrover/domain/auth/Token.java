package school.redrover.domain.auth;

public class Token {
    private String status;
    private Data data;

    public Data getData() {
        return data;
    }

    @Override
    public String toString() {
        return String.format("Token{status='%s', %s}", status, data);
    }
}
