package matrixes_vectors;

import java.util.List;

public class VectorP {

    double[] P;

    public VectorP(double alfa, double ambientTemperature, Jacobian2D jacobian2D, boolean ifBC) {
        P = new double[4];

        List<UniversalElement> universalElementList = UniversalElement.UniversalElementList();

        for (int i = 0; i < 4; i++) {
            UniversalElement tempUniversalElement = universalElementList.get(i);
            double[] N = tempUniversalElement.getN();

            for (int j = 0; j < 4; j++) {
                P[j] += N[j];
            }
        }

        for (int i = 0; i < 4; i++) {
            P[i] *= alfa * ambientTemperature * jacobian2D.getDetJ()[i];
        }
        if (ifBC) {
            for (int i = 0; i < 4; i++) {
                P[i] *= (-1);
            }
        }
    }

    public double[] getP() {
        return P;
    }
}
