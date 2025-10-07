package org.example.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;


public class ResourceLoader {

    /**
     * Loads the application logo from the classpath.
     * <p>
     * The logo path is defined in {@link PathConstants#LOGO_RESOURCE}.
     * This method attempts to read the image as a {@link BufferedImage}.
     * </p>
     *
     * @return the loaded {@link BufferedImage}, or {@code null} if the file
     *         was not found or an error occurred during loading
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
