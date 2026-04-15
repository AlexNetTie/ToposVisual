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

    @Invariant("Расчет горизонтальной компоненты кривизны или дивергенции нормали.")
    public double[][] solvedKxComponent(double[][] componentNormalVector) {
        int width = componentNormalVector.length;
        int height = componentNormalVector[0].length;
        double[][] result = new double[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (x == 0) {
                    result[x][y] = 0;
                } else if (x == (width - 1)) {
                    result[x][y] = 0;
                } else {
                    result[x][y] = (componentNormalVector[x + 1][y] - componentNormalVector[x - 1][y]) / 2;
                }
            }
        }
        return result;
    }

    @Invariant("Расчет вертикальной компоненты кривизны или дивергенции нормали.")
    public double[][] solvedKyComponent(double[][] componentNormalVector) {
        int width = componentNormalVector.length;
        int height = componentNormalVector[0].length;
        double[][] result = new double[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (y == 0) {
                    result[x][y] = 0;
                } else if (y == (height - 1)) {
                    result[x][y] = 0;
                } else {
                    result[x][y] = (componentNormalVector[x][y + 1] - componentNormalVector[x][y - 1]) / 2;
                }
            }
        }
        return result;
    }

    @Invariant("Расчет полной кривизны (дивергенции нормали)")
    public double[][] solvedCurvature(double[][] kx, double[][] ky) {
        int width = kx.length;
        int height = kx[0].length;
        double[][] kappa = new double[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                kappa[x][y] = kx[x][y] + ky[x][y];
            }
        }
        return kappa;
    }

    @Invariant("Метод по расчету новых значений для картинки. R_new = R + dt * module * kappa.")
    public BufferedImage evolutionImage(BufferedImage image, double[][] moduleGradient, double[][] curvature, double dt) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage evolutionImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                DataPixel current = new DataPixel(image.getRGB(x, y));

                // Поток: dt * |∇I| * κ
                double flow = dt * moduleGradient[x][y] * curvature[x][y];

                // Новые значения с ограничением 0-255
                int newRed = (int) Math.clamp(current.copyRed() + flow, 0, 255);
                int newGreen = (int) Math.clamp(current.copyGreen() + flow, 0, 255);
                int newBlue = (int) Math.clamp(current.copyBlue() + flow, 0, 255);

                DataPixel evolutionPixel = new DataPixel(newRed, newGreen, newBlue);
                evolutionImage.setRGB(x, y, evolutionPixel.copyPixel());
            }
        }
        return evolutionImage;
    }

    @Invariant("Расчет одного шага.")
    public BufferedImage oneStepSolved(BufferedImage image, double dt){
        // 1. Градиенты
        DataPixelGradient[][] Ix = solvedHorizontalGradient(image);
        DataPixelGradient[][] Iy = solvedVerticalGradient(image);

        // 2. Модуль
        double[][] module = solvedModuleGradientStandard(Iy, Ix);

        // 3. Нормали
        double[][] Nx = solvedUnitNormalVector(Ix, module);
        double[][] Ny = solvedUnitNormalVector(Iy, module);

        // 4. Кривизна
        double[][] kx = solvedKxComponent(Nx);
        double[][] ky = solvedKyComponent(Ny);
        double[][] kappa = solvedCurvature(kx, ky);

        // 5. Эволюция
        BufferedImage result = evolutionImage(image, module, kappa, dt);

        return result;
    }

    @Invariant("Полноценный solver для расчета.")
    public BufferedImage solveMeanCurvatureMotionImage(BufferedImage image, int iterations, double dt) {
        BufferedImage current = image;

        for (int i = 0; i < iterations; i++) {
            System.out.println("Итерация №" + i);
            current = oneStepSolved(current, dt);
        }

        return current;
    }
}