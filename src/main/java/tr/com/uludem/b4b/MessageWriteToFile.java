package tr.com.uludem.b4b;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class MessageWriteToFile {
    private static final File LOG_FILE = new File("C:\\UludemLogs\\UludemLog.txt");
    private static final DateTimeFormatter F = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void Write(String title, Exception ex) {
        String msg = ex == null ? "" : ex.toString();
        Write(title + " EX", msg);
    }

    public static void Write(String line) {
        try {
            File dir = LOG_FILE.getParentFile();
            if (dir != null && !dir.exists()) dir.mkdirs();
            String ts = "[" + LocalDateTime.now().format(F) + "] ";
            try (FileOutputStream fos = new FileOutputStream(LOG_FILE, true)) {
                fos.write((ts + line + System.lineSeparator()).getBytes(StandardCharsets.UTF_8));
            }
        } catch (Exception ignored) { }
    }


    public static void Write(String title, String message) {
        try {
            File dir = LOG_FILE.getParentFile();
            if (dir != null && !dir.exists()) dir.mkdirs();
            String line = "[" + LocalDateTime.now().format(F) + "] "
                    + (title != null ? title : "") + " "
                    + (message != null ? message : "") + System.lineSeparator();
            try (FileOutputStream fos = new FileOutputStream(LOG_FILE, true)) {
                fos.write(line.getBytes(StandardCharsets.UTF_8));
            }
        } catch (Exception ignored) { }
    }
}
