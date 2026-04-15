package Lost.Architect.Development;

import Lost.Architect.Development.Actions.ToposImage;
import Lost.Architect.Development.Data.DataPixel;
import Lost.Architect.Development.Data.DataPixelGradient;
import Lost.Architect.Development.Soulvers.Engine.Engine;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedImage image = ImageIO.read(Objects.requireNonNull(Main.class.getResourceAsStream("/photo_2026-04-12_19-39-51.jpg")));

        System.out.println("Ширина: " + image.getWidth());
        System.out.println("Высота: " + image.getHeight());

        ToposImage topos = new ToposImage(new Engine());

        DataPixel toposPixel = topos.topos(image);
        BufferedImage toposImage = topos.creationToposImage(toposPixel, image.getWidth(), image.getHeight());
        File output = new File("topos.jpg");
    }
}