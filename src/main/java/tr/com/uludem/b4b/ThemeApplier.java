package tr.com.uludem.b4b;

import java.awt.Color;

public final class ThemeApplier {

    static void applyFromServer(MainForm form) {
        try {
            String base = Config.get("api.url");
            if (base == null || base.isBlank()) {
                safeSetLocalFavicon(form);
                return;
            }

            AyarServisi.AyarYaniti yanit = AyarServisi.AyarGetir(base);
            if (yanit == null || yanit.data == null) {
                safeSetLocalFavicon(form);
                return;
            }

            var data = yanit.data;

            // Renkler
            Color fore = parseColor(data.METINRENK);
            Color back = parseColor(data.ARKAPLANRENK);
            form.applyTheme(back, fore);

            // LOGO
            if (notBlank(data.LOGO)) {
                try {
                    form.setUstLogoFromUrl(data.LOGO);
                } catch (Exception ex) {
                    MessageWriteToFile.Write("ThemeApplier.UstLogo", ex);
                }
            }

            // Favicon
            boolean faviconSet = false;
            if (notBlank(data.FAVICON) && isTrustedSameHost(data.FAVICON, base)) {
                try {
                    faviconSet = form.setWindowIconFromUrl(data.FAVICON);
                } catch (Exception ex) {
                    MessageWriteToFile.Write("ThemeApplier.FaviconUrl", ex);
                }
            }

            if (!faviconSet) {
                safeSetLocalFavicon(form);
            }

        } catch (Exception ex) {
            MessageWriteToFile.Write("ThemeApplier.applyFromServer", ex);
            try { safeSetLocalFavicon(form); } catch (Exception ignored) {}
        }
    }

    private static void safeSetLocalFavicon(MainForm form) {
        try {
            form.setWindowIconFromResources("/Resources/uludemfavicon.ico");
        } catch (Exception ex) {
            MessageWriteToFile.Write("ThemeApplier.safeSetLocalFavicon", ex);
        }
    }

    private static boolean notBlank(String s) {
        return s != null && !s.trim().isEmpty();
    }

    private static boolean isTrustedSameHost(String url, String base) {
        try {
            if (!notBlank(url) || !notBlank(base)) return false;
            var bu = new java.net.URL(base);
            var fu = new java.net.URL(url);
            return bu.getHost().equalsIgnoreCase(fu.getHost());
        } catch (Exception ignored) {
            return false;
        }
    }

    private static Color parseColor(String s) {
        if (!notBlank(s)) return null;
        try {
            s = s.trim();

            // RRGGBB / AARRGGBB
            if (s.startsWith("#")) {
                String hex = s.substring(1);
                if (hex.length() == 6) {
                    int rgb = Integer.parseInt(hex, 16);
                    return new Color((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF);
                } else if (hex.length() == 8) {
                    int argb = (int) Long.parseLong(hex, 16);
                    return new Color((argb >> 16) & 0xFF, (argb >> 8) & 0xFF, argb & 0xFF);
                }
            }

            // R,G,B
            if (s.contains(",")) {
                String[] p = s.split(",");
                if (p.length >= 3) {
                    int r = Integer.parseInt(p[0].trim());
                    int g = Integer.parseInt(p[1].trim());
                    int b = Integer.parseInt(p[2].trim());
                    return new Color(r, g, b);
                }
            }
        } catch (Exception ignored) { }
        return null;
    }
}
