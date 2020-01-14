package matricesVectors;

import java.util.List;

import grid.Node;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class MatrixH {
    private double[][] H = new double[4][4];

    public MatrixH(double k, Jacobian2D jacobian2D) {
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

    public static double[][] calculateMatrixHbc(int whichSide, double alfa, Node node1, Node node2) {
        double[][] matrixH = new double[4][4];
        double[][] N;
        double[][] N2;
        double x1 = node1.getX();
        double y1 = node1.getY();
        double x2 = node2.getX();
        double y2 = node2.getY();
        double[] detJ = new double[4];
        //Function multiple1dMatrices requires double[] as last argument
        for (int i = 0; i < 4; i++) {
            detJ[i] = (sqrt(pow(x2 - x1, 2) + pow(y2 - y1, 2))) / 2;
        }

        switch (whichSide) {
            case 1: {
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
