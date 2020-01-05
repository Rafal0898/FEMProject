package matrixes_vectors;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.sqrt;

public class UniversalElement {
    private double[] N;
    private double[] dNdE;
    private double[] dNdN;

    public UniversalElement(double ksi, double eta) {
        N = new double[4];
        N[0] = 0.25 * (1 - ksi) * (1 - eta);
        N[1] = 0.25 * (1 + ksi) * (1 - eta);
        N[2] = 0.25 * (1 + ksi) * (1 + eta);
        N[3] = 0.25 * (1 - ksi) * (1 + eta);

        dNdE = new double[4];
        dNdE[0] = -0.25 * (1 - eta);
        dNdE[1] = 0.25 * (1 - eta);
        dNdE[2] = 0.25 * (1 + eta);
        dNdE[3] = -0.25 * (1 + eta);

        dNdN = new double[4];
        dNdN[0] = -0.25 * (1 - ksi);
        dNdN[1] = -0.25 * (1 + ksi);
        dNdN[2] = 0.25 * (1 + ksi);
        dNdN[3] = 0.25 * (1 - ksi);
    }

    public static List<UniversalElement> UniversalElementList() {
        double ksi = 1 / sqrt(3);
        double eta = 1 / sqrt(3);

        List<UniversalElement> universalElementList = new ArrayList<>();
        universalElementList.add(new UniversalElement(-ksi, -eta));
        universalElementList.add(new UniversalElement(ksi, -eta));
        universalElementList.add(new UniversalElement(ksi, eta));
        universalElementList.add(new UniversalElement(-ksi, eta));

        return universalElementList;
    }

    public double[] getN() {
        return N;
    }

    public double[] getdNdE() {
        return dNdE;
    }

    public double[] getdNdN() {
        return dNdN;
    }
}
