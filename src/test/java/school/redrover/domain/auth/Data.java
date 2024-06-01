package school.redrover.domain.auth;

public class Data {
    private String tokenName;
    private String tokenUuid;
    private String tokenValue;

    public String getTokenValue() {
        return tokenValue;
    }

    @Override
    public String toString() {
        return String.format(
                "Token{tokenName='%s', tokenUuid='%s', tokenValue='%s'}",
                tokenName, tokenUuid, tokenValue);
    }
}
