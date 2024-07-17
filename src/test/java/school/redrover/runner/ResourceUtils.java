package school.redrover.runner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public final class ResourceUtils {

    private ResourceUtils() {
    }

    private static String toStringAndClose(InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        }
    }

    public static String payloadFromResource(String resource) {
        try {
            InputStream inputStream = ResourceUtils.class.getResourceAsStream(resource);
            if (inputStream == null) {
                throw new IllegalArgumentException("Resource not found: " + resource);
            }
            return toStringAndClose(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read resource: " + resource, e);
        }
    }
}
