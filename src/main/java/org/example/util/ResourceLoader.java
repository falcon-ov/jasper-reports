package org.example.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Утилита для загрузки ресурсов (изображений, файлов и т.д.)
 */
public class ResourceLoader {

    /**
     * Загружает логотип из resources или файловой системы.
     * @return BufferedImage или null если файл не найден
     */
    public static BufferedImage loadLogo() {
        String resourcePath = "/logo.png";
        String filePath = PathConstants.LOGO_PATH;

        try (InputStream is = ResourceLoader.class.getResourceAsStream(resourcePath)) {
            if (is != null) {
                BufferedImage img = ImageIO.read(is);
                System.out.println("Logo loaded successfully from resources: " + resourcePath);
                return img;
            }
        } catch (IOException e) {
            System.err.println("Error loading logo from resources: " + e.getMessage());
        }

        // Fallback: файловая система
        File logoFile = new File(filePath);
        if (logoFile.exists()) {
            try {
                BufferedImage img = ImageIO.read(logoFile);
                System.out.println("Logo loaded successfully from: " + logoFile.getAbsolutePath());
                return img;
            } catch (IOException e) {
                System.err.println("Error loading logo from file: " + e.getMessage());
            }
        } else {
            System.err.println("Logo file not found at: " + filePath);
        }
        return null;
    }

}