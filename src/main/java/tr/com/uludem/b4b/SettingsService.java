package tr.com.uludem.b4b;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

final class SettingsService {

    static JsonNode fetchSettings(String baseUrl) throws Exception{
        String endpoint = baseUrl.endsWith("/")
                ? baseUrl + "ayar/exe"
                : baseUrl + "/ayar/exe";
        HttpURLConnection conn =(HttpURLConnection) new URL(endpoint).openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("ORIGIN", "EXE");

        MessageWriteToFile.Write("SettingsService.fetchSettings -> " + endpoint);

        int http = conn.getResponseCode();
        try(InputStream is = (http >= 400 ? conn.getErrorStream() : conn.getInputStream())) {
            if (is == null) return null;
            String json = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            if (json.isEmpty()) return null;
            return new ObjectMapper().readTree(json);
        }
    }
}
