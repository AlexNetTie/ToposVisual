package Lost.Architect.Development.Soulvers.Flows;

import Lost.Architect.Development.Annotation.ArchitectSolution;
import Lost.Architect.Development.Annotation.Invariant;
import Lost.Architect.Development.Annotation.Parameters;
import Lost.Architect.Development.Annotation.TechDebt;
import Lost.Architect.Development.Data.DataPixel;
import Lost.Architect.Development.Data.DataPixelGradient;

import java.awt.image.BufferedImage;

@TechDebt("Пересмотреть по итогу написания solver-а его архитектуру.")
@Invariant("Класс для расчета кривизны.")
public class MeanCurvatureMotionImage {

    @Invariant("Расчет вертикальной составляющей градиента")
    @ArchitectSolution("Градиенты вертикальный и горизонтальный специально разбиты на два отдельных метода")
    @Parameters({"image - исходное изображение",
            "DataPixelGradient[x][y] - результирующий массив объектов, что хранит вертикальный градиент."})
    public DataPixelGradient[][] solvedVerticalGradient(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        DataPixelGradient[][] verticalGradient = new DataPixelGradient[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (y == 0) {
                    verticalGradient[x][y] = new DataPixelGradient(0, 0, 0);
                } else if (y == height - 1) {
                    verticalGradient[x][y] = new DataPixelGradient(0, 0, 0);
                } else {
                    DataPixel top = new DataPixel(image.getRGB(x, y - 1));
                    DataPixel bottom = new DataPixel(image.getRGB(x, y + 1));

                    double dR = (double) (bottom.copyRed() - top.copyRed()) / 2;
                    double dG = (double) (bottom.copyGreen() - top.copyGreen()) / 2;
                    double dB = (double) (bottom.copyBlue() - top.copyBlue()) / 2;

                    verticalGradient[x][y] = new DataPixelGradient(dR, dG, dB);
                }
            }
        }
        return verticalGradient;
    }

    @Invariant("Расчет горизонтальной составляющей градиента.")
    @ArchitectSolution("Градиенты вертикальный и горизонтальный специально разбиты на два отдельных метода.")
    @Parameters({"image - исходное изображение",
            "DataPixelGradient[x][y] - результирующий массив объектов, что хранит горизонтальный градиент."})
    public DataPixelGradient[][] solvedHorizontalGradient(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        DataPixelGradient[][] horizontalGradient = new DataPixelGradient[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (x == 0) {
                    horizontalGradient[x][y] = new DataPixelGradient(0, 0, 0);
                } else if (x == (width - 1)) {
                    horizontalGradient[x][y] = new DataPixelGradient(0, 0, 0);
                } else {
                    DataPixel left = new DataPixel(image.getRGB(x - 1, y));
                    DataPixel right = new DataPixel(image.getRGB(x + 1, y));

                    double dR = (double) (right.copyRed() - left.copyRed()) / 2;
                    double dG = (double) (right.copyGreen() - left.copyGreen()) / 2;
                    double dB = (double) (right.copyBlue() - left.copyBlue()) / 2;
                    horizontalGradient[x][y] = new DataPixelGradient(dR, dG, dB);
                }
            }
        }
        return horizontalGradient;
    }

    @TechDebt("Нужен валидатор, что предварительно проверяет оба переданных массива на соответствие одинаковой размерности.")
    @Invariant("Метод для нестандартного (для каждого канала) расчета модуля градиентов.")
    public DataPixelGradient[][] solvedModuleGradient(DataPixelGradient[][] verticalGradient, DataPixelGradient[][] horizontalGradient) {
        int width = verticalGradient.length;
        int height = verticalGradient[0].length;
        DataPixelGradient[][] module = new DataPixelGradient[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double moduledR = Math.pow((verticalGradient[x][y].copyRed() * verticalGradient[x][y].copyRed() +
                        horizontalGradient[x][y].copyRed() * horizontalGradient[x][y].copyRed()), 0.5);
                double moduledG = Math.pow((verticalGradient[x][y].copyGreen() * verticalGradient[x][y].copyGreen() +
                        horizontalGradient[x][y].copyGreen() * horizontalGradient[x][y].copyGreen()), 0.5);
                double moduledB = Math.pow((verticalGradient[x][y].copyBlue() * verticalGradient[x][y].copyBlue() +
                        horizontalGradient[x][y].copyBlue() * horizontalGradient[x][y].copyBlue()), 0.5);
                module[x][y] = new DataPixelGradient(moduledR, moduledG, moduledB);
            }
        }
        return module;
    }

    @TechDebt("Нужен валидатор, что предварительно проверяет оба переданных массива на соответствие одинаковой размерности.")
    @Invariant("Метод стандартная (общий модуль на все каналы) реализации модуля градиента.")
    public double[][] solvedModuleGradientStandard(DataPixelGradient[][] verticalGradient, DataPixelGradient[][] horizontalGradient) {
        int width = verticalGradient.length;
        int height = verticalGradient[0].length;
        double[][] result = new double[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                result[x][y] = Math.sqrt(
                        verticalGradient[x][y].copyRed() * verticalGradient[x][y].copyRed() +
                                horizontalGradient[x][y].copyRed() * horizontalGradient[x][y].copyRed() +
                                verticalGradient[x][y].copyGreen() * verticalGradient[x][y].copyGreen() +
                                horizontalGradient[x][y].copyGreen() * horizontalGradient[x][y].copyGreen() +
                                verticalGradient[x][y].copyBlue() * verticalGradient[x][y].copyBlue() +
                                horizontalGradient[x][y].copyBlue() * horizontalGradient[x][y].copyBlue()
                );
            }
        }
        return result;
    }

    @Invariant("Метод рассчитывает вектор единичной нормали.")
    public double[][] solvedUnitNormalVector(DataPixelGradient[][] gradients, double[][] moduleGradient) {
        int width = gradients.length;
        int height = gradients[0].length;
        double[][] result = new double[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (moduleGradient[x][y] < 1e-9) { // если почти ноль
                    result[x][y] = 0.0;
                } else {
                    result[x][y] = gradients[x][y].summaryGradient() / moduleGradient[x][y];
                }
            }
        }
        return result;
    }


}