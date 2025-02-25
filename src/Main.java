import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import grid.GlobalData;
import grid.Grid;
import grid.Node;
import matricesVectors.Jacobian2D;
import matricesVectors.MatricesVectors;
import matricesVectors.MatrixC;
import matricesVectors.MatrixH;
import matricesVectors.VectorP;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String path = System.getProperty("user.dir").toString() + "\\src\\TestCase1.txt";
        Scanner scanner = new Scanner(new File(path));
        double initialTemperature = Double.valueOf(scanner.nextLine());//100;//[°C] temperatura poczatkowa
        double simulationTime = Double.valueOf(scanner.nextLine());// 500;//[s]
        double dT = Double.valueOf(scanner.nextLine());// 50;//[s]
        double ambientTemperature = Double.valueOf(scanner.nextLine());// 1200;//[°C] temperatura otoczenia
        double alfa = Double.valueOf(scanner.nextLine());// 300;//[W/m2K]
        double h = Double.valueOf(scanner.nextLine());// 0.1; //[m]
        double w = Double.valueOf(scanner.nextLine());// 0.1;//[m]
        int nH = Integer.valueOf(scanner.nextLine());//4;
        int nW = Integer.valueOf(scanner.nextLine());//4;
        double cw = Double.valueOf(scanner.nextLine());//700;//[J/(kg°C)]
        double k = Double.valueOf(scanner.nextLine());//25;//[W/(m°C)]
        double ro = Double.valueOf(scanner.nextLine());//7800;//[kg/m3]

        GlobalData globalData = new GlobalData(h, w, nH, nW);
        Grid grid = new Grid(globalData);

        double[][] globalMatrixH = new double[grid.getNodeListSize()][grid.getNodeListSize()];
        double[][] globalMatrixHCdT = new double[grid.getNodeListSize()][grid.getNodeListSize()];
        double[][] globalMatrixC = new double[grid.getNodeListSize()][grid.getNodeListSize()];
        double[] globalVectorP = new double[grid.getNodeListSize()];

        for (int i = 0; i < grid.getElementListSize(); i++) {
            List<Node> actualElementsNodeList = grid.getElementList().get(i).getNodeList();
            Jacobian2D jacobian2D = new Jacobian2D(actualElementsNodeList);

            double[][] localMatrixHbc = new double[4][4];
            double[] localVectorP = new double[4];

            int whichSide;
            if (actualElementsNodeList.get(0).getBc() && actualElementsNodeList.get(1).getBc()) {//jezeli 1 i 2 ma BC
                whichSide = 1;
                localMatrixHbc = MatricesVectors.sumMatrices(localMatrixHbc,
                        MatrixH.calculateMatrixHbc(whichSide, alfa, actualElementsNodeList.get(0), actualElementsNodeList.get(1)), 1);
                localVectorP = MatricesVectors.sumVectors(localVectorP,
                        new VectorP(whichSide, actualElementsNodeList.get(0), actualElementsNodeList.get(1)).getP(), 4);
            }
            if (actualElementsNodeList.get(1).getBc() && actualElementsNodeList.get(2).getBc()) {//jezeli 2 i 3 ma BC
                whichSide = 2;
                localMatrixHbc = MatricesVectors.sumMatrices(localMatrixHbc,
                        MatrixH.calculateMatrixHbc(whichSide, alfa, actualElementsNodeList.get(1), actualElementsNodeList.get(2)), 1);
                localVectorP = MatricesVectors.sumVectors(localVectorP,
                        new VectorP(whichSide, actualElementsNodeList.get(1), actualElementsNodeList.get(2)).getP(), 4);
            }
            if (actualElementsNodeList.get(2).getBc() && actualElementsNodeList.get(3).getBc()) {//jezeli 3 i 4 maja BC
                whichSide = 3;
                localMatrixHbc = MatricesVectors.sumMatrices(localMatrixHbc,
                        MatrixH.calculateMatrixHbc(whichSide, alfa, actualElementsNodeList.get(2), actualElementsNodeList.get(3)), 1);
                localVectorP = MatricesVectors.sumVectors(localVectorP,
                        new VectorP(whichSide, actualElementsNodeList.get(2), actualElementsNodeList.get(3)).getP(), 4);
            }
            if (actualElementsNodeList.get(0).getBc() && actualElementsNodeList.get(3).getBc()) {//jezeli 1 i 4 ma BC
                whichSide = 4;
                localMatrixHbc = MatricesVectors.sumMatrices(localMatrixHbc,
                        MatrixH.calculateMatrixHbc(whichSide, alfa, actualElementsNodeList.get(0), actualElementsNodeList.get(3)), 1);
                localVectorP = MatricesVectors.sumVectors(localVectorP,
                        new VectorP(whichSide, actualElementsNodeList.get(0), actualElementsNodeList.get(3)).getP(), 4);
            }

            MatrixH matrixH = new MatrixH(k, jacobian2D);
            double[][] localMatrixH = matrixH.getH();
            MatricesVectors.aggregateMatrix(globalMatrixH, localMatrixH, actualElementsNodeList);

            localMatrixH = MatricesVectors.sumMatrices(localMatrixH, localMatrixHbc, 1);
            MatricesVectors.aggregateMatrix(globalMatrixHCdT, localMatrixH, actualElementsNodeList);

            MatrixC matrixC = new MatrixC(cw, ro, jacobian2D);
            double[][] localMatrixC = matrixC.getC();
            MatricesVectors.aggregateMatrix(globalMatrixC, localMatrixC, actualElementsNodeList);

            MatricesVectors.aggregateVector(globalVectorP, localVectorP, actualElementsNodeList);
        }

        double[] T0 = new double[grid.getNodeListSize()];
        for (int i = 0; i < grid.getNodeListSize(); i++) {
            T0[i] = initialTemperature;
            globalVectorP[i] *= alfa * ambientTemperature;
        }
        if (grid.getNodeListSize() <= 25) { //DONT PRINT TOO BIG MATRICES
            System.out.println("________________ Interaction 0 ______________");
            System.out.println("________________ Matrix [C] ______________");
            MatricesVectors.showMatrix(globalMatrixC, grid.getNodeListSize());

            System.out.println("________________ Interaction 0 ______________");
            System.out.println("________________ Matrix [H] ______________");
            MatricesVectors.showMatrix(globalMatrixH, grid.getNodeListSize());
        }

        globalMatrixHCdT = MatricesVectors.calculateGlobalMatrixH(globalMatrixHCdT, globalMatrixC, dT, grid.getNodeListSize());
        double[][] copyOfGlobalMatrixHCdT = MatricesVectors.copyMatrix(globalMatrixHCdT, grid.getNodeListSize());

        double[] tMax = new double[(int) (simulationTime / dT)];
        double[] tMin = new double[(int) (simulationTime / dT)];
        int interaction = 0;

        for (int i = 0; i < (int) simulationTime / dT; i++) {
            double[] globalVectorPCdT = MatricesVectors.calculateGlobalVectorPCdT(globalVectorP, globalMatrixC, dT, T0, grid.getNodeListSize());

            if (grid.getNodeListSize() <= 25) { //DONT PRINT TOO BIG MATRICES
                System.out.println("________________ Interaction " + interaction + " ______________");
                System.out.println("________________ Matrix [H] + [C]/dT ______________");
                MatricesVectors.showMatrix(copyOfGlobalMatrixHCdT, grid.getNodeListSize());
                System.out.println("________________ Vector {P}+{[C]/dT}*{T0} ______________");
                MatricesVectors.showVector(globalVectorPCdT, grid.getNodeListSize());
            }

            double[] t = GaussianElimination.lsolve(globalMatrixHCdT, globalVectorPCdT);

            globalMatrixHCdT = MatricesVectors.copyMatrix(copyOfGlobalMatrixHCdT, grid.getNodeListSize());
            double max = t[0], min = t[0];
            for (int j = 1; j < grid.getNodeListSize(); j++) {
                if (t[j] < min) {
                    min = t[j];
                }
                if (t[j] > max) {
                    max = t[j];
                }
            }
            tMax[interaction] = max;
            tMin[interaction] = min;
            interaction++;
            T0 = t;
        }

        MatricesVectors.roundVector(tMin, (int) (simulationTime / dT));
        MatricesVectors.roundVector(tMax, (int) (simulationTime / dT));
        System.out.println("Time[s] \t\t MinTemp[°C] \t\t MaxTemp[°C]");
        for (int i = 0; i < (int) simulationTime / dT; i++) {
            System.out.println((i + 1) * dT + "\t\t\t\t\t" + tMin[i] + "\t\t\t\t" + tMax[i]);
        }
    }
}
