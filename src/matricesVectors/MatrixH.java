package matricesVectors;

import java.util.List;

import static java.lang.Math.sqrt;

public class MatrixH {
    private double[][] H;

    public MatrixH(double k, Jacobian2D jacobian2D) {
        H = new double[4][4];

        double[][] dNdx = new double[4][4];
        double[][] dNdy = new double[4][4];

        List<UniversalElement> universalElementList = UniversalElement.buildUniversalElementList();
        double[][] J = jacobian2D.getJ();
        double[] detJ = jacobian2D.getDetJ();

        for (int i = 0; i < 4; i++) {
            UniversalElement tempUniversalElement = universalElementList.get(i);
            double[] dNdE = tempUniversalElement.getdNdE();
            double[] dNdN = tempUniversalElement.getdNdN();

            for (int j = 0; j < 4; j++) {
                dNdx[i][j] = J[0][j] * dNdE[j] + J[1][j] * dNdN[j];
                dNdy[i][j] = J[2][j] * dNdE[j] + J[3][j] * dNdN[j];
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                double[][] dNdxdNdxTdetJ = MatricesVectors.multiply1dMatrices(dNdx[i], dNdx[i], detJ);
                double[][] dNdydNdyTdetJ = MatricesVectors.multiply1dMatrices(dNdy[i], dNdy[i], detJ);

                for (int l = 0; l < 4; l++) {
                    H[j][l] += k * (dNdxdNdxTdetJ[j][l] + dNdydNdyTdetJ[j][l]);
                }
            }
        }
    }

    public static double[][] calculateMatrixHbc(int whichSide, double alfa, double h, double w) {
        double[][] matrixH = new double[4][4];
        double[][] N;
        double[][] N2;

        switch (whichSide) {
            case 1: {
                double[] detJ = {w / 2, w / 2, w / 2, w / 2};
                double ksi = -1 / sqrt(3);
                double eta = -1;
                N = MatricesVectors.multiply1dMatrices(MatricesVectors.calculateMatrixN(ksi, eta),
                        MatricesVectors.calculateMatrixN(ksi, eta), detJ);
                ksi *= (-1);
                N2 = MatricesVectors.multiply1dMatrices(MatricesVectors.calculateMatrixN(ksi, eta),
                        MatricesVectors.calculateMatrixN(ksi, eta), detJ);
                N = MatricesVectors.sumMatrices(N, N2, alfa);
                matrixH = N;
                break;
            }
            case 2: {
                double[] detJ = {h / 2, h / 2, h / 2, h / 2};
                double ksi = 1;
                double eta = -1 / sqrt(3);
                N = MatricesVectors.multiply1dMatrices(MatricesVectors.calculateMatrixN(ksi, eta),
                        MatricesVectors.calculateMatrixN(ksi, eta), detJ);
                eta *= (-1);
                N2 = MatricesVectors.multiply1dMatrices(MatricesVectors.calculateMatrixN(ksi, eta),
                        MatricesVectors.calculateMatrixN(ksi, eta), detJ);
                N = MatricesVectors.sumMatrices(N, N2, alfa);
                matrixH = N;
                break;
            }
            case 3: {
                double[] detJ = {w / 2, w / 2, w / 2, w / 2};
                double ksi = 1 / sqrt(3);
                double eta = 1;
                N = MatricesVectors.multiply1dMatrices(MatricesVectors.calculateMatrixN(ksi, eta),
                        MatricesVectors.calculateMatrixN(ksi, eta), detJ);
                ksi *= (-1);
                N2 = MatricesVectors.multiply1dMatrices(MatricesVectors.calculateMatrixN(ksi, eta),
                        MatricesVectors.calculateMatrixN(ksi, eta), detJ);
                N = MatricesVectors.sumMatrices(N, N2, alfa);
                matrixH = N;
                break;
            }
            case 4: {
                double[] detJ = {h / 2, h / 2, h / 2, h / 2};
                double ksi = -1;
                double eta = 1 / sqrt(3);
                N = MatricesVectors.multiply1dMatrices(MatricesVectors.calculateMatrixN(ksi, eta),
                        MatricesVectors.calculateMatrixN(ksi, eta), detJ);
                eta *= (-1);
                N2 = MatricesVectors.multiply1dMatrices(MatricesVectors.calculateMatrixN(ksi, eta),
                        MatricesVectors.calculateMatrixN(ksi, eta), detJ);
                N = MatricesVectors.sumMatrices(N, N2, alfa);
                matrixH = N;
            }
        }
        return matrixH;
    }

    public double[][] getH() {
        return H;
    }
}
