package tr.com.uludem.b4b;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public final class AuthService {

    public static AuthResponse loginJson(
            String baseUrl,
            String bayiKod,
            String kullaniciAdi,
            String sifre,
            boolean isPlasiyer,
            String plasiyerKod
    ) throws Exception {

        final String endpoint = baseUrl.endsWith("/")
                ? baseUrl + "auth/giris"
                : baseUrl + "/auth/giris";

        String body = "{"
                + "\"bayiKod\":\""      + jesc(bayiKod)      + "\","
                + "\"kullaniciAdi\":\"" + jesc(kullaniciAdi) + "\","
                + "\"sifre\":\""        + jesc(sifre)        + "\","
                + "\"isPlasiyer\":"     + (isPlasiyer ? "true" : "false") + ","
                + "\"plasiyerKod\":\""  + jesc(plasiyerKod)  + "\""
                + "}";

        HttpURLConnection conn = (HttpURLConnection) new URL(endpoint).openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setRequestProperty("Accept", "application/json");
        // İstersen tarayıcıya benzetmek için Origin gönderebilirsin:
        // conn.setRequestProperty("Origin", "https://b4b.uludem.com.tr");

        try (OutputStream os = conn.getOutputStream()) {
            os.write(body.getBytes(StandardCharsets.UTF_8));
        }

        int http = conn.getResponseCode();
        InputStream is = (http >= 400 ? conn.getErrorStream() : conn.getInputStream());
        String json = (is != null) ? new String(is.readAllBytes(), StandardCharsets.UTF_8) : "";

        // Log
        MessageWriteToFile.Write("AuthService.loginJson -> " + endpoint + " | HTTP=" + http
                + " | BODY=" + body.replaceAll("\"sifre\"\\s*:\\s*\".*?\"", "\"sifre\":\"***\""));
        MessageWriteToFile.Write("AuthService.loginJson JSON <- " + (json.length() > 600 ? json.substring(0, 600) + "..." : json));

        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        AuthResponse resp = json.isEmpty() ? new AuthResponse() : mapper.readValue(json, AuthResponse.class);
        resp.httpStatus = http;
        return resp;
    }

    private static String jesc(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    // ==== DTO'lar ====

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AuthResponse {
        public int code;

        @JsonProperty("status")
        @JsonAlias({"Status"})
        public Boolean status;

        @JsonAlias({"message","Message"})
        public String message;

        public Object data; // şimdilik kullanılmıyor
        public transient int httpStatus;
    }
}
