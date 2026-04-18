package Lost.Architect.Development.Soulvers.Flows;

import Lost.Architect.Development.Annotation.ArchitectSolution;
import Lost.Architect.Development.Annotation.Invariant;
import Lost.Architect.Development.Annotation.Parameters;
import Lost.Architect.Development.Annotation.TechDebt;
import Lost.Architect.Development.Data.DataPixel;
import Lost.Architect.Development.Data.DataPixelGradient;

import java.awt.image.BufferedImage;

@TechDebt("Пересмотреть по итогу написание solver-а его архитектуру.")
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

    @Invariant("Вычисление средней кривизны по ВСЕМ пикселям изображения.")
    private double computeAverageCurvatureGlobal(double[][] kappa) {
        double sum = 0;
        int width = kappa.length;
        int height = kappa[0].length;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                sum += kappa[x][y];
            }
        }
        return sum / (width * height);
    }

    @Invariant("Расчет одного шага с сохранением объема и ограничением по вместимости.")
    public BufferedImage oneStepSolvedVolumePreserving(BufferedImage image, double dt) {
        // 1-4. Градиенты, модуль, нормали, кривизна (как раньше)
        DataPixelGradient[][] Ix = solvedHorizontalGradient(image);
        DataPixelGradient[][] Iy = solvedVerticalGradient(image);
        double[][] module = solvedModuleGradientStandard(Iy, Ix);
        double[][] Nx = solvedUnitNormalVector(Ix, module);
        double[][] Ny = solvedUnitNormalVector(Iy, module);
        double[][] kx = solvedKxComponent(Nx);
        double[][] ky = solvedKyComponent(Ny);
        double[][] kappa = solvedCurvature(kx, ky);

        // 5. Глобальная средняя кривизна
        double kappaAvg = computeAverageCurvatureGlobal(kappa);

        // 6. Эволюция с ограничением по вместимости
        return evolutionImageVolumePreservingCapacity(image, module, kappa, kappaAvg, dt);
    }

    @Invariant("Полноценный Volume-Preserving solver.")
    public BufferedImage solveMCMVolumePreserving(BufferedImage image, int iterations, double dt) {
        BufferedImage current = image;

        for (int i = 0; i < iterations; i++) {
            System.out.println("Итерация №" + i);
            current = oneStepSolvedVolumePreserving(current, dt);
        }

        return current;
    }

    @Invariant("Метод с ограничением потока по вместимости ячейки (без clamp, без переполнения).")
    public BufferedImage evolutionImageVolumePreservingCapacity(BufferedImage image,
                                                                double[][] moduleGradient,
                                                                double[][] curvature,
                                                                double kappaAvg,
                                                                double dt) {

        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage evolutionImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                DataPixel current = new DataPixel(image.getRGB(x, y));

                double adjustedKappa = curvature[x][y] - kappaAvg;
                double desiredFlow = dt * moduleGradient[x][y] * adjustedKappa;

                // Ограничение потока вместимостью ячейки
                int newRed = applyCapacityLimit(current.copyRed(), desiredFlow);
                int newGreen = applyCapacityLimit(current.copyGreen(), desiredFlow);
                int newBlue = applyCapacityLimit(current.copyBlue(), desiredFlow);

                evolutionImage.setRGB(x, y, new DataPixel(newRed, newGreen, newBlue).copyPixel());
            }
        }
        return evolutionImage;
    }

    @Invariant("Применяет поток с учётом вместимости ячейки [0, 255].")
    private int applyCapacityLimit(double currentValue, double desiredFlow) {
        double actualFlow;
        if (desiredFlow > 0) {
            actualFlow = Math.min(desiredFlow, 255 - currentValue);
        } else {
            actualFlow = Math.max(desiredFlow, -currentValue);
        }
        return (int) Math.round(currentValue + actualFlow);
    }
}