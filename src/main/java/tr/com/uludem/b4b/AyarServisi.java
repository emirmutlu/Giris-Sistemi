package tr.com.uludem.b4b;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

final class AyarServisi {

    static AyarYaniti AyarGetir(String baseUrl) throws Exception {
        String endpoint = baseUrl.endsWith("/") ? baseUrl + "ayar/exe" : baseUrl + "/ayar/exe";
        URL url = new URL(endpoint);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("ORIGIN", "EXE");

        int http = conn.getResponseCode();
        InputStream is = (http >= 400 ? conn.getErrorStream() : conn.getInputStream());
        String json = (is != null) ? new String(is.readAllBytes(), StandardCharsets.UTF_8) : "";

        MessageWriteToFile.Write("AyarServisi.AyarGetir -> " + endpoint + " | HTTP=" + http);
        MessageWriteToFile.Write("AyarServisi.AyarGetir JSON <- " + (json.length()>800 ? json.substring(0,800)+"..." : json));

        ObjectMapper m = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        AyarYaniti resp = json.isEmpty() ? new AyarYaniti() : m.readValue(json, AyarYaniti.class);
        resp.httpStatus = http;
        return resp;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AyarYaniti {
        public Boolean status;
        public int code;
        public String messages;

        @JsonProperty("data")
        public Ayarlar data;

        public transient int httpStatus;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Ayarlar {
        @JsonProperty("LOGO")
        public String LOGO;

        @JsonProperty("METINRENK")
        public String METINRENK;

        @JsonProperty("ARKAPLANRENK")
        public String ARKAPLANRENK;

        @JsonProperty("FAVICON")
        public String FAVICON;
    }
}
