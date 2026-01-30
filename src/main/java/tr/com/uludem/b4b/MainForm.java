package tr.com.uludem.b4b;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.awt.Dimension;

// Selenium
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainForm extends JPanel {
    private JPanel panel1;
    private JLabel ustLogo;
    private JPanel groupBox1;
    private JCheckBox checkBeniHatirla;
    private JButton btnGiris;
    private JComboBox<String> cbTarayici;
    private JLabel label4;
    private JPasswordField tbParola;
    private JLabel label3;
    private JTextField tbKullanici;
    private JLabel label2;
    private JTextField tbBayi;
    private JLabel label1;
    private JLabel altResim;

    private final Map<String, String> discoveredBrowserPaths = new LinkedHashMap<>();

    public MainForm() {
        setLayout(null);
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(400, 537));

        // panel1
        panel1 = new JPanel(null);
        panel1.setBackground(Color.WHITE);
        panel1.setBounds(0, 0, 400, 468);
        add(panel1);

        // ustLogo
        ustLogo = new JLabel();
        ImageIcon ustLogoIcon = loadIcon("/Resources/logo_ust.png");
        int baseX = 30, baseY = 13, baseW = 336, baseH = 132;
        double k = 0.92;
        int newW = (int) Math.round(baseW * k);
        int newH = (int) Math.round(baseH * k);
        int bottom = baseY + baseH;
        int newX = baseX;
        int newY = bottom - newH;

        ImageIcon scaled = loadIconScaled("/Resources/logo_ust.png", newW, newH);
        ustLogo.setIcon(scaled != null ? scaled : ustLogoIcon);
        ustLogo.setBounds(newX, newY, newW, newH);
        panel1.add(ustLogo);

        // groupBox1
        groupBox1 = new JPanel(null);
        groupBox1.setBackground(Color.WHITE);
        groupBox1.setBorder(new TitledBorder("KULLANICI GİRİŞİ"));
        groupBox1.setBounds(12, 185, 376, 272);
        panel1.add(groupBox1);

        // label1
        label1 = new JLabel("Bayi Kodu     ");
        label1.setFont(label1.getFont().deriveFont(Font.BOLD, 12f));
        label1.setBounds(14, 44, 141, 25);
        groupBox1.add(label1);

        // tbBayi
        tbBayi = new JTextField();
        tbBayi.setFont(tbBayi.getFont().deriveFont(12f));
        tbBayi.setBounds(141, 40, 213, 30);
        groupBox1.add(tbBayi);

        // label2
        label2 = new JLabel("Kullanıcı Adı");
        label2.setFont(label2.getFont().deriveFont(Font.BOLD, 12f));
        label2.setBounds(14, 78, 132, 25);
        groupBox1.add(label2);

        // tbKullanici
        tbKullanici = new JTextField();
        tbKullanici.setFont(tbKullanici.getFont().deriveFont(12f));
        tbKullanici.setBounds(141, 74, 213, 30);
        groupBox1.add(tbKullanici);

        // label3
        label3 = new JLabel("Şifre");
        label3.setFont(label3.getFont().deriveFont(Font.BOLD, 12f));
        label3.setBounds(14, 112, 57, 25);
        groupBox1.add(label3);

        // tbParola
        tbParola = new JPasswordField();
        tbParola.setFont(tbParola.getFont().deriveFont(12f));
        tbParola.setEchoChar('*');
        tbParola.setBounds(141, 108, 213, 30);
        groupBox1.add(tbParola);

        // label4
        label4 = new JLabel("Tarayıcı");
        label4.setFont(label4.getFont().deriveFont(Font.BOLD, 12f));
        label4.setBounds(14, 146, 89, 25);
        groupBox1.add(label4);

        // cbTarayici
        cbTarayici = new JComboBox<>();
        cbTarayici.setFont(cbTarayici.getFont().deriveFont(12f));
        cbTarayici.setBounds(141, 142, 213, 33);
        groupBox1.add(cbTarayici);

        populateBrowsersAuto();

        // checkBeniHatirla
        checkBeniHatirla = new JCheckBox("Beni Hatırla");
        checkBeniHatirla.setOpaque(false);
        checkBeniHatirla.setBounds(24, 186, 125, 23);
        groupBox1.add(checkBeniHatirla);

        // btnGiris
        btnGiris = new JButton("GİRİŞ YAP");
        btnGiris.setFont(btnGiris.getFont().deriveFont(Font.BOLD, 12f));
        btnGiris.setFocusPainted(false);
        btnGiris.setBounds(24, 221, 330, 33);
        groupBox1.add(btnGiris);
        btnGiris.addActionListener(e -> handleLogin());

        // altResim
        altResim = new JLabel();
        ImageIcon altIcon = loadIcon("/Resources/uludem.png");
        if (altIcon != null) altResim.setIcon(altIcon);
        altResim.setBounds(0, 468, 400, 69);
        add(altResim);

        altResim.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        altResim.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                String target = "https://uludem.com.tr";
                try {
                    String secilen = (cbTarayici != null && cbTarayici.getSelectedItem() != null)
                            ? (String) cbTarayici.getSelectedItem() : null;
                    if (secilen != null) {
                        BrowserLauncher.open(secilen, target);
                    } else if (Desktop.isDesktopSupported()) {
                        Desktop.getDesktop().browse(new java.net.URI(target));
                    }
                } catch (Exception ex) {
                    MessageWriteToFile.Write("MainForm.altLogoClick", ex);
                }
            }
        });

        loadRememberedUser();
        ThemeApplier.applyFromServer(this);
    }

    // === LOGIN ===
    private void handleLogin() {
        final String bayi = tbBayi.getText().trim();
        final String kul  = tbKullanici.getText().trim();
        final String sif  = new String(tbParola.getPassword());

        if (bayi.isEmpty()) { JOptionPane.showMessageDialog(this, "Bayi Kodu boş olamaz"); tbBayi.requestFocus(); return; }
        if (kul.isEmpty())  { JOptionPane.showMessageDialog(this, "Kullanıcı Adı boş olamaz"); tbKullanici.requestFocus(); return; }
        if (sif.isEmpty())  { JOptionPane.showMessageDialog(this, "Şifre boş olamaz"); tbParola.requestFocus(); return; }

        btnGiris.setEnabled(false);

        new Thread(() -> {
            WebDriver driver = null;
            try {
                String webBase = Config.get("web.url");
                if (webBase == null || webBase.isBlank()) {
                    String apiBase = Config.get("api.url");
                    webBase = (apiBase != null && !apiBase.isBlank())
                            ? apiBase.replace("b4bapi.", "b4b.")
                            : "https://b4b.uludem.com.tr";
                }
                if (!webBase.endsWith("/")) webBase += "/";
                String loginUrl = webBase + "auth/giris";

                String secilen = (String) cbTarayici.getSelectedItem();
                driver = createDriverFor(secilen);

                driver.manage().window().setSize(new org.openqa.selenium.Dimension(1100, 800));
                driver.get(loginUrl);

                // aday lokatör setleri
                java.util.List<By> SEL_BAYI = java.util.List.of(
                        By.name("bayiKod"), By.id("bayiKod"),
                        By.name("bayi_kodu"), By.id("bayi_kodu"), By.name("bayi")
                );
                java.util.List<By> SEL_USER = java.util.List.of(
                        By.name("kullaniciAdi"), By.id("kullaniciAdi"),
                        By.name("kullanici_adi"), By.id("kullanici_adi"), By.name("kullanici")
                );
                java.util.List<By> SEL_PASS = java.util.List.of(
                        By.name("sifre"), By.id("sifre"), By.cssSelector("input[type='password']")
                );
                java.util.List<By> SEL_SUBMIT = java.util.List.of(
                        By.cssSelector("button[type='submit']"),
                        By.cssSelector("input[type='submit']"),
                        By.xpath("//button[contains(.,'Giriş') or contains(.,'Giris')]")
                );

                WebElement bayiEl = waitFind(driver, SEL_BAYI, 20);
                WebElement kulEl  = waitFind(driver, SEL_USER, 20);
                WebElement sifEl  = waitFind(driver, SEL_PASS, 20);

                if (bayiEl == null || kulEl == null || sifEl == null) {
                    throw new RuntimeException("Giriş formu alanları bulunamadı (selectorları güncellemek gerekebilir).");
                }

                bayiEl.clear(); bayiEl.sendKeys(bayi);
                kulEl.clear();  kulEl.sendKeys(kul);
                sifEl.clear();  sifEl.sendKeys(sif);

                WebElement btn = waitFind(driver, SEL_SUBMIT, 5);
                if (btn != null) {
                    btn.click();
                } else {
                    try {
                        ((JavascriptExecutor)driver).executeScript("var f=document.querySelector('form'); if(f) f.submit();");
                    } catch (Exception ignored) {
                        sifEl.sendKeys(Keys.ENTER);
                    }
                }

                // yönlendirmeyi bekle
                try {
                    new WebDriverWait(driver, java.time.Duration.ofSeconds(20))
                            .until(drv -> {
                                String u = drv.getCurrentUrl();
                                return u != null && !u.contains("/auth/giris");
                            });
                } catch (Exception eWait) {
                    MessageWriteToFile.Write("Login.WaitRedirect", eWait);
                }

                // beni hatırla
                if (checkBeniHatirla.isSelected()) {
                    RememberMe.Data d = new RememberMe.Data();
                    d.bayiKodu = bayi; d.kullanici = kul; d.parola = sif;
                    d.varsayilanTarayici = (String) cbTarayici.getSelectedItem();
                    d.beniHatirla = true;
                    try { RememberMe.save(d); } catch (Exception ex) { MessageWriteToFile.Write("RememberMe.save", ex); }
                } else {
                    RememberMe.deleteIfExists();
                }

            } catch (Exception ex) {
                MessageWriteToFile.Write("MainForm.handleLogin.selenium", ex);
                SwingUtilities.invokeLater(() ->
                        JOptionPane.showMessageDialog(this, "Giriş otomasyonu başarısız: " + ex.getMessage()));
            } finally {
                SwingUtilities.invokeLater(() -> btnGiris.setEnabled(true));
            }
        }, "login-automation").start();
    }

    // --- Tek lokatör seti için bekleyerek bul (iframe taraması + stale tolere) ---
    private static WebElement waitFind(WebDriver driver, java.util.List<By> candidates, long timeoutSec) {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(timeoutSec));

        for (By by : candidates) {
            try {
                return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            } catch (Exception ignored) {}
        }

        // iframe denemeleri
        java.util.List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
        for (int i = 0; i < iframes.size(); i++) {
            try {
                driver.switchTo().defaultContent();
                driver.switchTo().frame(i);
                for (By by : candidates) {
                    try {
                        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
                    } catch (Exception ignored) {}
                }
            } catch (Exception ignored) {}
        }
        driver.switchTo().defaultContent();
        return null;
    }

    // ================== DRIVERS ==================

    // Edge'i sırayla: Selenium Manager -> yerel exe -> WebDriverManager
    private EdgeDriver startEdge() {
        // 1) Selenium Manager
        try {
            return new EdgeDriver(new EdgeOptions());
        } catch (Exception smFail) {
            MessageWriteToFile.Write("Edge.SeleniumManager", smFail);
        }

        // 2) Yerel msedgedriver.exe
        String[] guesses = new String[] {
                "C:\\\\WebDrivers\\\\msedgedriver.exe",
                System.getProperty("user.home") + "\\\\AppData\\\\Local\\\\msedgedriver\\\\msedgedriver.exe"
        };
        for (String g : guesses) {
            try {
                java.io.File f = new java.io.File(g);
                if (f.exists()) {
                    System.setProperty("webdriver.edge.driver", f.getAbsolutePath());
                    return new EdgeDriver(new EdgeOptions());
                }
            } catch (Exception localFail) {
                MessageWriteToFile.Write("Edge.LocalDriverTry:" + g, localFail);
            }
        }

        // 3) WebDriverManager (internet erişimi gerekir)
        try {
            WebDriverManager.edgedriver()
                    .clearDriverCache()
                    .clearResolutionCache()
                    .setup();
            return new EdgeDriver(new EdgeOptions());
        } catch (Exception wdmFail) {
            MessageWriteToFile.Write("Edge.WebDriverManager", wdmFail);
            throw new RuntimeException("EdgeDriver başlatılamadı (Selenium Manager + yerel + WDM başarısız).", wdmFail);
        }
    }

    private WebDriver createDriverFor(String secilen) {
        String pick = (secilen == null) ? "" : secilen.toLowerCase();

        try {
            // --- Microsoft Edge ---
            if (pick.contains("edge") || pick.contains("microsoft")) {
                try {
                    return startEdge();
                } catch (Exception e) {
                    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                            this,
                            "EdgeDriver başlatılamadı.\n" +
                                    "Kurumsal ağda indirme engelli olabilir.\n" +
                                    "İstersen C:\\WebDrivers\\msedgedriver.exe bırak, otomatik kullanayım.\n" +
                                    "Şimdilik Chrome ile devam ediyorum."
                    ));
                    return new ChromeDriver(new ChromeOptions());
                }
            }

            // --- Firefox ---
            if (pick.contains("firefox")) {
                try {
                    return new FirefoxDriver(new FirefoxOptions());
                } catch (Exception e) {
                    WebDriverManager.firefoxdriver().setup();
                    return new FirefoxDriver(new FirefoxOptions());
                }
            }

            // --- Opera / Brave / Vivaldi (Chromium) ---
            if (pick.contains("opera") || pick.contains("brave") || pick.contains("vivaldi")) {
                String exe = discoveredBrowserPaths.getOrDefault(secilen, null);
                if (exe == null || !new java.io.File(exe).exists()) {
                    // exe bulunamazsa Chrome dene
                    return new ChromeDriver(new ChromeOptions());
                }

                // Yüklü tarayıcının majör versiyonunu bul
                String majorStr = detectChromiumMajor(exe); // ör: "135"
                Integer major = null;
                try { if (majorStr != null) major = Integer.parseInt(majorStr); } catch (Exception ignore) {}

                // WDM ön hazırlık
                WebDriverManager.chromedriver()
                        .avoidBrowserDetection()
                        .clearDriverCache()
                        .clearResolutionCache();

                boolean setupOk = false;
                // tam majör
                if (major != null) {
                    try {
                        WebDriverManager.chromedriver().browserVersion(String.valueOf(major)).setup();
                        setupOk = true;
                    } catch (Exception ignore) {}
                    // ±1 denemeleri
                    if (!setupOk) {
                        try { WebDriverManager.chromedriver().browserVersion(String.valueOf(major + 1)).setup(); setupOk = true; } catch (Exception ignore) {}
                    }
                    if (!setupOk) {
                        try { WebDriverManager.chromedriver().browserVersion(String.valueOf(major - 1)).setup(); setupOk = true; } catch (Exception ignore) {}
                    }
                }
                if (!setupOk) {
                    // özel klasör fallback (paketleyeceksen)
                    String custom = "C:\\\\WebDrivers\\\\chromedriver-opera\\\\chromedriver.exe";
                    if (new java.io.File(custom).exists()) {
                        System.setProperty("webdriver.chrome.driver", custom);
                        setupOk = true;
                    }
                }
                if (!setupOk) {
                    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                            this,
                            "Seçilen tarayıcı (" + secilen + ") için uygun sürücü bulunamadı.\n" +
                                    "Chrome ile devam ediliyor."
                    ));
                    return new ChromeDriver(new ChromeOptions());
                }

                ChromeOptions opt = new ChromeOptions();
                opt.setBinary(exe);
                opt.addArguments("--no-default-browser-check", "--no-first-run");
                return new ChromeDriver(opt);
            }

            // --- Google Chrome (varsayılan) ---
            try {
                return new ChromeDriver(new ChromeOptions());
            } catch (Exception e) {
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver(new ChromeOptions());
            }

        } catch (Exception e) {
            throw new RuntimeException("WebDriver başlatılamadı (" + (secilen == null ? "Chrome" : secilen) + "): " + e.getMessage(), e);
        }
    }

    // Yüklü Chromium-tabanlı tarayıcıdan majör sürümü oku (örn: "Opera 135.0.7049.115" -> 135)
    private static String detectChromiumMajor(String browserExe) {
        try {
            Process p = new ProcessBuilder(browserExe, "--version")
                    .redirectErrorStream(true).start();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(p.getInputStream(), StandardCharsets.UTF_8))) {
                String out = br.readLine();
                if (out != null) {
                    java.util.regex.Matcher m =
                            java.util.regex.Pattern.compile("(\\d{2,3})\\.(\\d+)").matcher(out);
                    if (m.find()) return m.group(1);
                }
            }
        } catch (Exception ignored) {}
        return null;
    }

    // ================== UI Helpers ==================
    void setUstLogoFromUrl(String urlStr) {
        try {
            URL u = new URL(urlStr);
            URLConnection c = u.openConnection();
            c.setRequestProperty("ORIGIN", "EXE");
            Image img;
            try (InputStream in = c.getInputStream()) { img = ImageIO.read(in); }
            if (img != null) {
                int w = ustLogo.getWidth(), h = ustLogo.getHeight();
                if (w > 0 && h > 0) ustLogo.setIcon(new ImageIcon(img.getScaledInstance(w, h, Image.SCALE_SMOOTH)));
                else                 ustLogo.setIcon(new ImageIcon(img));
                ustLogo.revalidate(); ustLogo.repaint();
            }
        } catch (Exception ex) { MessageWriteToFile.Write("MainForm.setUstLogoFromUrl", ex); }
    }

    private static Image fetchImageFromUrl(String urlStr){
        try {
            URL u = new URL(urlStr);
            URLConnection c = u.openConnection();
            c.setRequestProperty("ORIGIN", "EXE");
            try (InputStream in = c.getInputStream()) { return ImageIO.read(in); }
        } catch (Exception e) { MessageWriteToFile.Write("MainForm.fetchImageFromUrl", e); }
        return null;
    }

    boolean setWindowIconFromUrl(String urlStr) {
        try {
            Window wnd = SwingUtilities.getWindowAncestor(this);
            if (!(wnd instanceof JFrame f)) return false;
            Image img = fetchImageFromUrl(urlStr);
            if (img == null) return false;
            f.setIconImage(img);
            return true;
        } catch (Exception ex) { MessageWriteToFile.Write("MainForm.setWindowIconFromUrl", ex); return false; }
    }

    void setWindowIconFromResources(String... paths) {
        try {
            Window wnd = SwingUtilities.getWindowAncestor(this);
            if (!(wnd instanceof JFrame f)) return;
            java.util.List<Image> list = new java.util.ArrayList<>();
            for (String p: paths) {
                URL u = getClass().getResource(p);
                if (u != null) list.add(new ImageIcon(u).getImage());
            }
            if (!list.isEmpty()) f.setIconImages(list);
        } catch (Exception ex) { MessageWriteToFile.Write("MainForm.setWindowIconFromResources", ex); }
    }

    private ImageIcon loadIcon(String path) {
        URL url = getClass().getResource(path);
        return (url != null) ? new ImageIcon(url) : null;
    }

    private ImageIcon loadIconScaled(String path, int w, int h) {
        URL url = getClass().getResource(path);
        if (url == null) return null;
        Image img = new ImageIcon(url).getImage();
        Image scaled = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    private void loadRememberedUser() {
        RememberMe.Data d = RememberMe.load();
        if (!d.beniHatirla) return;

        checkBeniHatirla.setSelected(true);
        tbBayi.setText(d.bayiKodu != null ? d.bayiKodu : "");
        tbKullanici.setText(d.kullanici != null ? d.kullanici : "");
        tbParola.setText(d.parola != null ? d.parola : "");

        if (d.varsayilanTarayici != null && !d.varsayilanTarayici.isEmpty())
            cbTarayici.setSelectedItem(d.varsayilanTarayici);
    }

    public void applyTheme(Color back, Color fore) {
        if (back != null) {
            setBackground(back);
            panel1.setBackground(back);
            groupBox1.setBackground(back);
        }
        if (fore != null) {
            label1.setForeground(fore);
            label2.setForeground(fore);
            label3.setForeground(fore);
            label4.setForeground(fore);
            tbBayi.setForeground(fore);
            tbKullanici.setForeground(fore);
            tbParola.setForeground(fore);
            checkBeniHatirla.setForeground(fore);
            btnGiris.setForeground(fore);
            if (groupBox1.getBorder() instanceof TitledBorder) {
                ((TitledBorder) groupBox1.getBorder()).setTitleColor(fore);
                groupBox1.repaint();
            }
        }
    }

    // ================== Browser discovery ==================
    private void populateBrowsersAuto() {
        cbTarayici.removeAllItems();
        discoveredBrowserPaths.clear();

        String[][] apps = {
                {"Google Chrome", "chrome.exe"},
                {"Microsoft Edge", "msedge.exe"},
                {"Mozilla Firefox", "firefox.exe"},
                {"Opera", "opera.exe"},
                {"Brave", "brave.exe"},
                {"Vivaldi", "vivaldi.exe"}
        };

        for (String[] row : apps) {
            String name = row[0], exe = row[1];

            String p1 = readAppPathFromRegistry("HKLM\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\App Paths\\" + exe);
            String p2 = readAppPathFromRegistry("HKLM\\SOFTWARE\\WOW6432Node\\Microsoft\\Windows\\CurrentVersion\\App Paths\\" + exe);
            String p3 = readAppPathFromRegistry("HKCU\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\App Paths\\" + exe);

            String pick = firstNonEmpty(p1, p2, p3);
            if (pick == null || !new java.io.File(pick).exists()) {
                String where = whereExe(exe);
                if (where != null && new java.io.File(where).exists()) pick = where;
            }
            if (pick != null && new java.io.File(pick).exists()) discoveredBrowserPaths.put(name, pick);
        }

        if (discoveredBrowserPaths.isEmpty()) {
            String[][] map = {
                    {"Google Chrome",
                            "C:\\\\Program Files\\\\Google\\\\Chrome\\\\Application\\\\chrome.exe",
                            "C:\\\\Program Files (x86)\\\\Google\\\\Chrome\\\\Application\\\\chrome.exe"},
                    {"Mozilla Firefox",
                            "C:\\\\Program Files\\\\Mozilla Firefox\\\\firefox.exe",
                            "C:\\\\Program Files (x86)\\\\Mozilla Firefox\\\\firefox.exe"},
                    {"Opera",
                            "C:\\\\Program Files\\\\Opera\\\\launcher.exe",
                            "C:\\\\Program Files (x86)\\\\Opera\\\\launcher.exe"},
                    {"Brave",
                            "C:\\\\Program Files\\\\BraveSoftware\\\\Brave-Browser\\\\Application\\\\brave.exe",
                            "C:\\\\Program Files (x86)\\\\BraveSoftware\\\\Brave-Browser\\\\Application\\\\brave.exe"},
                    {"Vivaldi",
                            "C:\\\\Program Files\\\\Vivaldi\\\\Application\\\\vivaldi.exe",
                            "C:\\\\Program Files (x86)\\\\Vivaldi\\\\Application\\\\vivaldi.exe"},
            };
            for (String[] row : map) {
                for (int i = 1; i < row.length; i++) {
                    if (new java.io.File(row[i]).exists()) {
                        discoveredBrowserPaths.put(row[0], row[i]);
                        break;
                    }
                }
            }
        }

        for (String name : discoveredBrowserPaths.keySet()) cbTarayici.addItem(name);
        if (cbTarayici.getItemCount() > 0 && cbTarayici.getSelectedIndex() == -1) cbTarayici.setSelectedIndex(0);
    }

    private static String firstNonEmpty(String... arr) {
        if (arr == null) return null;
        for (String s : arr) if (s != null && !s.isBlank()) return s.trim();
        return null;
    }

    private static String readAppPathFromRegistry(String key) {
        try {
            Process p = new ProcessBuilder("reg", "query", key, "/ve").redirectErrorStream(true).start();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(), Charset.forName("CP850")))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.contains("REG_SZ")) {
                        String path = line.substring(line.indexOf("REG_SZ") + 6).trim();
                        if ((path.startsWith("\"") && path.endsWith("\"")) || (path.startsWith("'") && path.endsWith("'")))
                            path = path.substring(1, path.length() - 1);
                        return path;
                    }
                }
            }
        } catch (Exception ignored) { }
        return null;
    }

    private static String whereExe(String exeName) {
        try {
            Process p = new ProcessBuilder("where", exeName).redirectErrorStream(true).start();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(), StandardCharsets.UTF_8))) {
                String line = br.readLine();
                if (line != null && !line.isBlank()) {
                    String path = line.trim();
                    if ((path.startsWith("\"") && path.endsWith("\"")) || (path.startsWith("'") && path.endsWith("'")))
                        path = path.substring(1, path.length() - 1);
                    return path;
                }
            }
        } catch (Exception ignored) { }
        return null;
    }
}
