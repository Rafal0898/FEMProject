package matricesVectors;

import static java.lang.Math.sqrt;

public class VectorP {

    double[] P;

    public VectorP(double alfa, double ambientTemperature, Jacobian2D jacobian2D, double[][] matrixHbc, int whichSide, double w, double h) {
        P = new double[4];
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
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        // TODO: 07.01.2020  TAK JAK SWITCH W MATRIXH
                        P[j] += N[i][j];
                    }
                }
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
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        // TODO: 07.01.2020  TAK JAK SWITCH W MATRIXH
                        P[j] += N[i][j];
                    }
                }
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
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        // TODO: 07.01.2020  TAK JAK SWITCH W MATRIXH
                        P[j] += N[i][j];
                    }
                }
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
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        // TODO: 07.01.2020  TAK JAK SWITCH W MATRIXH
                        P[j] += N[i][j];
                    }
                }
            }

//            List<UniversalElement> universalElementList = UniversalElement.buildUniversalElementList();

//            for (int i = 0; i < 4; i++) {
////                UniversalElement tempUniversalElement = universalElementList.get(i);
////                double[] N = tempUniversalElement.getN();
//
//                double[] Ntest = new double[4];
//                for (int j = 0; j < 4; j++) {
//                    Ntest[j] = matrixHbc[i][j];
//                }
//
//                for (int j = 0; j < 4; j++) {
//                    // TODO: 07.01.2020  TAK JAK SWITCH W MATRIXH
//                    P[j] += N[i][j];
//                }
//            }

            for (int i = 0; i < 4; i++) {
                P[i] *= alfa * ambientTemperature * jacobian2D.getDetJ()[i];
            }
        }
    }

    public double[] getP() {
        return P;
    }
}
