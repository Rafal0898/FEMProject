package grid;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class Grid {
    private List<Node> nodeList;
    private List<Element> elementList;

    public Grid(GlobalData gD) {
        List<Node> nodeList = new ArrayList<>();
        double dX = gD.getW() / (gD.getnW() - 1);
        double dY = gD.getH() / (gD.getnH() - 1);
        for (int i = 1; i <= gD.getnW(); i++) {
            for (int j = 1; j <= gD.getnH(); j++) {
                if (i == 1 || j == 1 || i == gD.getnW() || j == gD.getnH()) {
                    nodeList.add(new Node((i - 1) * dX, (j - 1) * dY, 0, TRUE));
                } else {
                    nodeList.add(new Node((i - 1) * dX, (j - 1) * dY, 0, FALSE));
                }
            }
        }
        this.nodeList = nodeList;

        List<Element> elementList = new ArrayList<>();
        int j = 0;
        for (int i = 0; i < gD.getnE(); i++) {
            if (i % (gD.getnH() - 1) == 0 && i != 0) {
                j++;
            }
            List<Node> tempNodeList = new ArrayList<>();
            tempNodeList.add(this.nodeList.get(i + j));
            tempNodeList.add(this.nodeList.get(i + j + gD.getnH()));
            tempNodeList.add(this.nodeList.get(i + j + gD.getnH() + 1));
            tempNodeList.add(this.nodeList.get(i + j + 1));

            elementList.add(new Element(tempNodeList));
        }
        this.elementList = elementList;
    }

    public int getElementListSize() {
        return elementList.size();
    }

    public int getNodeListSize() {
        return nodeList.size();
    }

    public List<Element> getElementList() {
        return elementList;
    }
}
