package matricesVectors;

import static java.lang.Math.sqrt;

public class VectorP {

    private double[] P = new double[4];

    public VectorP(double alfa, double ambientTemperature, int whichSide, double w, double h) {
        double[] N;
        double[] N2;
        switch (whichSide) {
            case 1: {
                double[] detJ = {w / 2, w / 2, w / 2, w / 2};
                double ksi = -1 / sqrt(3);
                double eta = -1;
                N = MatricesVectors.calculateMatrixN(ksi, eta);

                ksi *= (-1);
                N2 = MatricesVectors.calculateMatrixN(ksi, eta);
                for (int i = 0; i < 4; i++) {
                    P[i] += alfa * ambientTemperature * (N[i] + N2[i]) * detJ[i];
                }
                break;
            }
            case 2: {
                double[] detJ = {h / 2, h / 2, h / 2, h / 2};
                double ksi = 1;
                double eta = -1 / sqrt(3);
                N = MatricesVectors.calculateMatrixN(ksi, eta);
                eta *= (-1);
                N2 = MatricesVectors.calculateMatrixN(ksi, eta);
                for (int i = 0; i < 4; i++) {
                    P[i] += alfa * ambientTemperature * (N[i] + N2[i]) * detJ[i];
                }
                break;
            }
            case 3: {
                double[] detJ = {w / 2, w / 2, w / 2, w / 2};
                double ksi = 1 / sqrt(3);
                double eta = 1;
                N = MatricesVectors.calculateMatrixN(ksi, eta);
                ksi *= (-1);
                N2 = MatricesVectors.calculateMatrixN(ksi, eta);
                for (int i = 0; i < 4; i++) {
                    P[i] += alfa * ambientTemperature * (N[i] + N2[i]) * detJ[i];
                }
                break;
            }
            case 4: {
                double[] detJ = {h / 2, h / 2, h / 2, h / 2};
                double ksi = -1;
                double eta = 1 / sqrt(3);
                N = MatricesVectors.calculateMatrixN(ksi, eta);
                eta *= (-1);
                N2 = MatricesVectors.calculateMatrixN(ksi, eta);
                for (int i = 0; i < 4; i++) {
                    P[i] += alfa * ambientTemperature * (N[i] + N2[i]) * detJ[i];
                }
            }
        }
    }

    public double[] getP() {
        return P;
    }
}