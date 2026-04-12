package Lost.Architect.Development.Actions;

import Lost.Architect.Development.Annotation.Invariant;
import Lost.Architect.Development.Data.DataPixel;
import Lost.Architect.Development.Soulvers.Engine.ElegantEngine;

import java.awt.image.BufferedImage;

public class ToposActions {

    @Invariant("Математический движок, через который проходят все расчеты")
    private final ElegantEngine engine;

    public ToposActions(ElegantEngine engine){
        this.engine = engine;
    }
    public BufferedImage topos(BufferedImage image) {
        int sumRed = 0;
        int sumGreen = 0;
        int sumBlue = 0;
        for(int y = 0; y < image.getHeight(); y++){
            for(int x = 0; x < image.getWidth(); x++){
                DataPixel data = new DataPixel(image.getRGB(x,y));
                sumRed = (int)engine.calculation(sumRed,data.copyRed(),1);
                sumGreen = (int)engine.calculation(sumGreen,data.copyGreen(),1);
                sumBlue = (int)engine.calculation(sumBlue,data.copyBlue(),1);
            }
        }

        int sumSize = image.getHeight() * image.getWidth();

        DataPixel dataAveragePixel = new DataPixel(sumRed/sumSize,sumGreen/sumSize,sumBlue/sumSize);

        BufferedImage toposImage = new BufferedImage(image.getWidth(),image.getHeight(),BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < image.getHeight(); y++){
            for (int x = 0; x < image.getWidth(); x++){
                toposImage.setRGB(x,y,dataAveragePixel.copyPixel());
            }
        }
        return toposImage;
    }
}
