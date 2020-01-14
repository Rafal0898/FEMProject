package matricesVectors;


import grid.Node;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class VectorP {

    private double[] P = new double[4];

    public VectorP(int whichSide, Node node1, Node node2) {
        double[] N;
        double[] N2;
        double x1 = node1.getX();
        double y1 = node1.getY();
        double x2 = node2.getX();
        double y2 = node2.getY();
        double detJ = (sqrt(pow(x2 - x1, 2) + pow(y2 - y1, 2))) / 2;
        switch (whichSide) {
            case 1: {
                double ksi = -1 / sqrt(3);
                double eta = -1;
                N = MatricesVectors.calculateMatrixN(ksi, eta);

                ksi *= (-1);
                N2 = MatricesVectors.calculateMatrixN(ksi, eta);
                for (int i = 0; i < 4; i++) {
                    P[i] += (N[i] + N2[i]) * detJ;
                }
                break;
            }
            case 2: {
                double ksi = 1;
                double eta = -1 / sqrt(3);
                N = MatricesVectors.calculateMatrixN(ksi, eta);
                eta *= (-1);
                N2 = MatricesVectors.calculateMatrixN(ksi, eta);
                for (int i = 0; i < 4; i++) {
                    P[i] += (N[i] + N2[i]) * detJ;
                }
                break;
            }
            case 3: {
                double ksi = 1 / sqrt(3);
                double eta = 1;
                N = MatricesVectors.calculateMatrixN(ksi, eta);
                ksi *= (-1);
                N2 = MatricesVectors.calculateMatrixN(ksi, eta);
                for (int i = 0; i < 4; i++) {
                    P[i] += (N[i] + N2[i]) * detJ;
                }
                break;
            }
            case 4: {
                double ksi = -1;
                double eta = 1 / sqrt(3);
                N = MatricesVectors.calculateMatrixN(ksi, eta);
                eta *= (-1);
                N2 = MatricesVectors.calculateMatrixN(ksi, eta);
                for (int i = 0; i < 4; i++) {
                    P[i] += (N[i] + N2[i]) * detJ;
                }
            }
        }
    }

    public double[] getP() {
        return P;
    }
}