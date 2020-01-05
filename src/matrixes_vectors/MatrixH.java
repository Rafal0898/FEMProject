package matrixes_vectors;

import java.util.List;

import static java.lang.Math.sqrt;

public class MatrixH {
    private double[][] H;

    public MatrixH(double k, Jacobian2D jacobian2D, boolean ifBC, int whichSide, double alfa, double h, double w) {
        H = new double[4][4];

        double[][] dNdx = new double[4][4];
        double[][] dNdy = new double[4][4];
        double[][] N;
        double[][] N2;

        List<UniversalElement> universalElementList = UniversalElement.UniversalElementList();
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
        switch (whichSide) {
            case 0: {
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        double[][] dNdxdNdxTdetJ = MatrixVectorOperations.Matrix1DMultiplication(dNdx[i], dNdx[i], detJ);
                        double[][] dNdydNdyTdetJ = MatrixVectorOperations.Matrix1DMultiplication(dNdy[i], dNdy[i], detJ);

                        for (int l = 0; l < 4; l++) {
                            H[j][l] += k * (dNdxdNdxTdetJ[j][l] + dNdydNdyTdetJ[j][l]);
                        }
                    }
                }
                break;
            }
            case 1: {
                double[] detJ2 = {w / 2, w / 2, w / 2, w / 2};
                double ksi = -1 / sqrt(3);
                double eta = -1;
                N = MatrixVectorOperations.Matrix1DMultiplication(MatrixVectorOperations.CalculateMatrixN(ksi, eta),
                        MatrixVectorOperations.CalculateMatrixN(ksi, eta), detJ2);
                ksi *= (-1);
                N2 = MatrixVectorOperations.Matrix1DMultiplication(MatrixVectorOperations.CalculateMatrixN(ksi, eta),
                        MatrixVectorOperations.CalculateMatrixN(ksi, eta), detJ2);
                N = MatrixVectorOperations.MatrixSum(N, N2, alfa);
                H = N;
                break;
            }
            case 2: {
                double[] detJ2 = {h / 2, h / 2, h / 2, h / 2};
                double ksi = 1;
                double eta = -1 / sqrt(3);
                N = MatrixVectorOperations.Matrix1DMultiplication(MatrixVectorOperations.CalculateMatrixN(ksi, eta),
                        MatrixVectorOperations.CalculateMatrixN(ksi, eta), detJ2);
                eta *= (-1);
                N2 = MatrixVectorOperations.Matrix1DMultiplication(MatrixVectorOperations.CalculateMatrixN(ksi, eta),
                        MatrixVectorOperations.CalculateMatrixN(ksi, eta), detJ2);
                N = MatrixVectorOperations.MatrixSum(N, N2, alfa);
                H = N;
                break;
            }
            case 3: {
                double[] detJ2 = {w / 2, w / 2, w / 2, w / 2};
                double ksi = 1 / sqrt(3);
                double eta = 1;
                N = MatrixVectorOperations.Matrix1DMultiplication(MatrixVectorOperations.CalculateMatrixN(ksi, eta),
                        MatrixVectorOperations.CalculateMatrixN(ksi, eta), detJ2);
                ksi *= (-1);
                N2 = MatrixVectorOperations.Matrix1DMultiplication(MatrixVectorOperations.CalculateMatrixN(ksi, eta),
                        MatrixVectorOperations.CalculateMatrixN(ksi, eta), detJ2);
                N = MatrixVectorOperations.MatrixSum(N, N2, alfa);
                H = N;
                break;
            }
            case 4: {
                double[] detJ2 = {h / 2, h / 2, h / 2, h / 2};
                double ksi = -1;
                double eta = 1 / sqrt(3);
                N = MatrixVectorOperations.Matrix1DMultiplication(MatrixVectorOperations.CalculateMatrixN(ksi, eta),
                        MatrixVectorOperations.CalculateMatrixN(ksi, eta), detJ2);
                eta *= (-1);
                N2 = MatrixVectorOperations.Matrix1DMultiplication(MatrixVectorOperations.CalculateMatrixN(ksi, eta),
                        MatrixVectorOperations.CalculateMatrixN(ksi, eta), detJ2);
                N = MatrixVectorOperations.MatrixSum(N, N2, alfa);
                H = N;
            }
        }
    }


    public double[][] getH() {
        return H;
    }
}
