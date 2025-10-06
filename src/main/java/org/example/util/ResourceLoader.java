package org.example.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * Утилита для загрузки ресурсов (изображений и т.д.)
 */
public class ResourceLoader {

    /**
     * Загружает логотип только из resources (classpath).
     * @return BufferedImage или null, если файл не найден или произошла ошибка
     */
    public static BufferedImage loadLogo() {
        String resourcePath = PathConstants.LOGO_RESOURCE;

        try (InputStream is = ResourceLoader.class.getResourceAsStream(resourcePath)) {
            if (is != null) {
                BufferedImage img = ImageIO.read(is);
                System.out.println("Logo loaded successfully from resources: " + resourcePath);
                return img;
            } else {
                System.err.println("Logo not found in resources: " + resourcePath);
            }
        } catch (IOException e) {
            System.err.println("Error loading logo from resources: " + e.getMessage());
        }

        return null;
    }
}
