package matricesVectors;

import java.util.List;

import grid.Node;

public class Jacobian2D {
    private double[][] J;
    private double[] detJ;
    private double[] xp;
    private double[] yp;

    public Jacobian2D(List<Node> nodeList) {
        List<UniversalElement> universalElementList = UniversalElement.buildUniversalElementList();
        double[] x = new double[4];
        double[] y = new double[4];
        J = new double[4][4];
        xp = new double[4];
        yp = new double[4];

        for(int i=0;i<4;i++){
            x[i] = nodeList.get(i).getX();
            y[i] = nodeList.get(i).getY();
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                UniversalElement tempUniversalElement = universalElementList.get(i);
                double[] N = tempUniversalElement.getN();
                xp[i] += x[j] * N[j];
                yp[i] += y[j] * N[j];
            }
        }

        for (int i = 0; i < 4; i++) {
            UniversalElement tempUniversalElement = universalElementList.get(i);
            double[] N = tempUniversalElement.getN();
            double[] dNdE = tempUniversalElement.getdNdE();
            double[] dNdN = tempUniversalElement.getdNdN();
            for (int j = 0; j < 4; j++) {
                J[0][i] += x[j] * dNdE[j];
                J[1][i] += y[j] * dNdE[j];
                J[2][i] += x[j] * dNdN[j];
                J[3][i] += y[j] * dNdN[j];
            }
        }
        detJ = new double[4];
        for (int i = 0; i < 4; i++) {
            detJ[i] = J[0][i] * J[3][i] - J[1][i] * J[2][i];
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                J[i][j] /= detJ[i];
            }
        }
    }
    public double[][] getJ() {
        return J;
    }
    public double[] getDetJ() {
        return detJ;
    }
}
