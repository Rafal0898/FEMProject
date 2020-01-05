import java.util.List;

import grid.GlobalData;
import grid.Grid;
import grid.Node;
import matrixes_vectors.Jacobian2D;
import matrixes_vectors.MatrixC;
import matrixes_vectors.MatrixH;
import matrixes_vectors.MatrixVectorOperations;
import matrixes_vectors.UniversalElement;
import matrixes_vectors.VectorP;

public class Main {
    public static void main(String[] args) {
        double h = 0.1, w = 0.1;//[m]
        int nH = 4, nW = 4;
        double initialTemperature = 100;//[°C]  //???????? temperatura poczatkowa
        double time = 500;//[s]
        double dT = 50;//[s]
        double ambientTemperature = 1200;//[°C] //?????? temperatura otoczenia
        double alfa = 300;//[W/m2K]
        double cw = 700;//[J/(kg°C)]
        double k = 25;//[W/(m°C)]
        double ro = 7800;//[kg/m3]

        GlobalData globalData = new GlobalData(h, w, nH, nW);
        Grid grid = new Grid(globalData);

        List<UniversalElement> universalElementList = UniversalElement.UniversalElementList();

        double[][] globalMatrixH = new double[16][16];
        double[][] globalMatrixC = new double[16][16];
        double[] globalVectorP = new double[16];
        for (int i = 0; i < grid.getElementListSize(); i++) {
            List<Node> actualElementsNodeList = grid.getElementList().get(i).getNodeList();
            Jacobian2D jacobian2D = new Jacobian2D(actualElementsNodeList);

            int whichSide = 0;
            boolean ifBC = false;
            for (int j = 0; j < 4; j++) {
                Node actualNode = actualElementsNodeList.get(j);
                if (actualNode.getbC()) {
                    ifBC = true;
                    if (actualNode.getX() == 0) {
                        whichSide = 4;
                    } else if (actualNode.getX() == h) {
                        whichSide = 2;
                    } else if (actualNode.getY() == 0) {
                        whichSide = 1;
                    } else if (actualNode.getY() == w) {
                        whichSide = 3;
                    }
                }
            }

            MatrixH matrixH = new MatrixH(k, jacobian2D, ifBC, whichSide, alfa, h, w);
            double[][] localMatrixH = matrixH.getH();
            //TODO
            //SKLADANIE globalMatrixH z localMatrixH

            MatrixC matrixC = new MatrixC(cw, ro, jacobian2D);
            double[][] localMatrixC = matrixC.getC();
            //TODO
            //SKLADANIE globalMatrixC z localMatrixC


            VectorP vectorP = new VectorP(alfa, ambientTemperature, jacobian2D, ifBC);
            double[] localVectorP = vectorP.getP();
            //TODO
            //SKLADANIE globalVectorP z localVectorP
        }

        //TODO
        //W kolejnych krokach czasowych
        globalMatrixH = MatrixVectorOperations.CalculateGlobalMatrixH(globalMatrixH, globalMatrixC, dT);

        double[] T0 = new double[16]; //?????????
        globalVectorP = MatrixVectorOperations.CalculateVectorP(globalVectorP, globalMatrixC, dT, T0);

        double[] t = GaussianElimination.lsolve(globalMatrixH, globalVectorP);

        double max = t[0], min = t[0];
        for (int jakasZmienna = 1; jakasZmienna < 16; jakasZmienna++) {
            if (t[jakasZmienna] < min) {
                min = t[jakasZmienna];
            }
            if (t[jakasZmienna] > max) {
                max = t[jakasZmienna];
            }
        }
        System.out.print("BreakPoint");
    }
}
