package Lost.Architect.Development;

import Lost.Architect.Development.Actions.ToposImage;
import Lost.Architect.Development.Data.DataPixel;
import Lost.Architect.Development.Soulvers.Engine.Engine;
import Lost.Architect.Development.Soulvers.Flows.MeanCurvatureMotionImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws IOException {
        //BufferedImage image = ImageIO.read(Objects.requireNonNull(Main.class.getResourceAsStream("/photo_2026-04-12_19-39-51.jpg")));
        BufferedImage image = ImageIO.read(Objects.requireNonNull(Main.class.getResourceAsStream("/four.png")));
        System.out.println("Ширина: " + image.getWidth());
        System.out.println("Высота: " + image.getHeight());

        ToposImage topos = new ToposImage(new Engine());

        DataPixel toposPixel = topos.topos(image);
        BufferedImage toposImage = topos.creationToposImage(toposPixel, image.getWidth(), image.getHeight());
        File output = new File("C:\\Users\\AlexPC\\Desktop\\image\\toposOriginal.jpg");
        ImageIO.write(toposImage,"jpg",output);
        System.out.println("Topos создан!");

        MeanCurvatureMotionImage solver = new MeanCurvatureMotionImage();
        BufferedImage smoothImage = solver.solveMeanCurvatureMotionImage(image,100,0.001);
        File output1 = new File("C:\\Users\\AlexPC\\Desktop\\image\\smoothImage.jpg");
        ImageIO.write(smoothImage,"jpg",output1);
        System.out.println("Smooth Image is created!!!");

        DataPixel smoothDataPixel = topos.topos(smoothImage);
        BufferedImage smoothTopos = topos.creationToposImage(smoothDataPixel,smoothImage.getWidth(),smoothImage.getHeight());
        File output3 = new File("C:\\Users\\AlexPC\\Desktop\\image\\smoothTopos.jpg");
        ImageIO.write(smoothTopos,"jpg",output3);
    }
}