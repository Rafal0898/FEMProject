package grid;

public class Node {
    static int idCounter = 1;
    int id;
    private double x;
    private double y;
    private double t;
    private Boolean bC;

    public Node(double x, double y, double t, boolean bC) {
        this.id = idCounter;
        this.x = x;
        this.y = y;
        this.t = t;
        this.bC = bC;
        idCounter++;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Boolean getbC() {
        return bC;
    }
}
