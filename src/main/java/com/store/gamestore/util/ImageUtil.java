package com.store.gamestore.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class ImageUtil {
    public static BufferedImage createImageFromBytes(byte[] imageData) {
        ByteArrayInputStream byteArray = new ByteArrayInputStream(imageData);
        BufferedImage image = null;
        try {
            image = ImageIO.read(byteArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
