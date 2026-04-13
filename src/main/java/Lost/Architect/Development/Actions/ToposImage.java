package Lost.Architect.Development.Actions;

import Lost.Architect.Development.Annotation.Invariant;
import Lost.Architect.Development.Annotation.Parameters;
import Lost.Architect.Development.Annotation.TechDebt;
import Lost.Architect.Development.Data.DataPixel;
import Lost.Architect.Development.Soulvers.Engine.ElegantEngine;

import java.awt.image.BufferedImage;

@Invariant("Класс для работы с топосами изображений.")
public class ToposImage {

    @Invariant("Математический движок, через который проходят все расчеты.")
    private final ElegantEngine engine;

    @Invariant("Основной конструктор принимает только движок.")
    public ToposImage(ElegantEngine engine) {
        this.engine = engine;
    }

    @TechDebt("Разбить метод на подфункции.")
    @Invariant("Метод который рассчитывает toposImage исходного изображения.")
    @Parameters("image - исходное изображение в отношении которого рассчитывается topos.")
    public BufferedImage topos(BufferedImage image) {
        int sumRed = 0;
        int sumGreen = 0;
        int sumBlue = 0;
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                DataPixel data = new DataPixel(image.getRGB(x, y));
                sumRed = (int) engine.calculation(sumRed, data.copyRed(), 1);
                sumGreen = (int) engine.calculation(sumGreen, data.copyGreen(), 1);
                sumBlue = (int) engine.calculation(sumBlue, data.copyBlue(), 1);
            }
        }

        int sumSize = image.getHeight() * image.getWidth();

        DataPixel dataAveragePixel = new DataPixel(sumRed / sumSize, sumGreen / sumSize, sumBlue / sumSize);

        BufferedImage toposImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                toposImage.setRGB(x, y, dataAveragePixel.copyPixel());
            }
        }
        return toposImage;
    }

    @Invariant("Расчет разницы между картинками.")
    @Parameters({"minuend - исходное изображение, из которого вычитают",
                 "subtrahend - вычитаемое изображение",
                 "sizeWidth - ширина результирующего изображения",
                 "sizeHeight - высота результирующего изображения."})
    public BufferedImage deltaImage(BufferedImage minuend, BufferedImage subtrahend , int sizeWidth, int sizeHeight) {
        BufferedImage result = new BufferedImage(sizeWidth, sizeHeight, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < sizeHeight; y++) {
            for (int x = 0; x < sizeWidth; x++) {
                DataPixel dataPixel1 = new DataPixel(minuend.getRGB(x, y));
                DataPixel dataPixel2 = new DataPixel(subtrahend .getRGB(x, y));
                DataPixel dataPixelResult = new DataPixel(
                        (int) engine.delta(dataPixel1.copyRed(), dataPixel2.copyRed(), 1),
                        (int) engine.delta(dataPixel1.copyGreen(), dataPixel2.copyGreen(), 1),
                        (int) engine.delta(dataPixel1.copyBlue(), dataPixel2.copyBlue(), 1)
                );
                result.setRGB(x,y,dataPixelResult.copyPixel());
            }
        }
        return result;
    }
}
