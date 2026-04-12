package Lost.Architect.Development;

import Lost.Architect.Development.Data.DataPixel;

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

        // Тут среднее считаем по картинке, т.е. topos оригинального изображения
        int sumRed = 0;
        int sumGreen = 0;
        int sumBlue = 0;
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                DataPixel data = new DataPixel(image.getRGB(x, y));
                sumRed += data.copyRed();
                sumGreen += data.copyGreen();
                sumBlue += data.copyBlue();
            }
        }

        int sumSize = image.getHeight() * image.getWidth();
        DataPixel dataAveragePixel = new DataPixel(sumRed / sumSize, sumGreen / sumSize, sumBlue / sumSize);

        // Тут сохраняем в topos
        BufferedImage recopy = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                recopy.setRGB(x, y, dataAveragePixel.copyPixel());
            }
        }
        File output = new File("topos.jpg");
        ImageIO.write(recopy, "jpg", output);
        System.out.println("Копия сохранена: " + output.getAbsolutePath());

        BufferedImage detailClamp = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                DataPixel origin = new DataPixel(image.getRGB(x, y));
                DataPixel topos = new DataPixel(recopy.getRGB(x, y));
                DataPixel details = new DataPixel(Math.clamp(topos.copyRed() - origin.copyRed(), 0, 255),
                        Math.clamp(topos.copyGreen() - origin.copyGreen(), 0, 255),
                        Math.clamp(topos.copyBlue() - origin.copyBlue(), 0, 255));
                detailClamp.setRGB(x, y, details.copyPixel());
            }
        }

        File output1 = new File("detailsClamp.jpg");
        ImageIO.write(detailClamp, "jpg", output1);
        System.out.println("Картинка деталей widthClamp сохранена: " + output1.getAbsolutePath());

        BufferedImage detail = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                DataPixel origin = new DataPixel(image.getRGB(x, y));
                DataPixel topos = new DataPixel(recopy.getRGB(x, y));
                DataPixel details = new DataPixel(topos.copyRed() - origin.copyRed(),
                        topos.copyGreen() - origin.copyGreen(),
                        topos.copyBlue() - origin.copyBlue());
                detail.setRGB(x, y, details.copyPixel());
            }
        }

        File output2 = new File("details.jpg");
        ImageIO.write(detail, "jpg", output2);
        System.out.println("Картинка деталей сохранена: " + output2.getAbsolutePath());

        BufferedImage what = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < image.getHeight(); y++){
            for(int x = 0; x < image.getWidth(); x++){
                DataPixel origin = new DataPixel(recopy.getRGB(x,y));
                DataPixel details = new DataPixel(detail.getRGB(x,y));
                DataPixel gap = new DataPixel(origin.copyRed()-details.copyRed(),
                        origin.copyGreen() - details.copyGreen(),
                        origin.copyBlue()-details.copyBlue());
                what.setRGB(x,y,gap.copyPixel());
            }
        }

        File output3 = new File("gap.jpg");
        ImageIO.write(what, "jpg", output3);
        System.out.println("Картинка Gap сохранена: " + output3.getAbsolutePath());

    }
}