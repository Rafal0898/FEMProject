package matricesVectors;

import java.util.List;

public class MatrixC {
    private double[][] C;

    public MatrixC(double cw, double ro, Jacobian2D jacobian2D) {
        C = new double[4][4];

        double[][] NForAllPoints = new double[4][4];

        List<UniversalElement> universalElementList = UniversalElement.buildUniversalElementList();
        double[][] J = jacobian2D.getJ();
        double[] detJ = jacobian2D.getDetJ();

        for (int i = 0; i < 4; i++) {
            UniversalElement tempUniversalElement = universalElementList.get(i);
            double[] N = tempUniversalElement.getN();

            for (int j = 0; j < 4; j++) {
                NForAllPoints[i][j] = N[j];
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                double[][] NNT = MatricesVectors.multiply1dMatrices(NForAllPoints[i], NForAllPoints[i], detJ);

                for (int l = 0; l < 4; l++) {
                    C[j][l] += cw * ro * NNT[j][l];
                }
            }
        }
    }

    public double[][] getC() {
        return C;
    }
}
