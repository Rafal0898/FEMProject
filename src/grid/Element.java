package grid;

import java.util.List;

public class Element {
    private static int idCounter = 1;
    private int id;
    private List<Node> nodeList;

    public Element(List<Node> nodeList) {
        this.id = idCounter;
        this.nodeList = nodeList;
        idCounter++;
    }

    public List<Node> getNodeList() {
        return nodeList;
    }
}