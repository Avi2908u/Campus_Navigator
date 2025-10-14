public class Edge {
    private Node start;
    private Node end;
    private double distance;
    
    public Edge(Node start, Node end) {
        this.start = start;
        this.end = end;
        this.distance = start.distanceTo(end);
    }
    
    public Edge(Node start, Node end, double customDistance) {
        this.start = start;
        this.end = end;
        this.distance = customDistance;
    }
    
    public Node getStart() {
        return start;
    }
    
    public Node getEnd() {
        return end;
    }
    
    public double getDistance() {
        return distance;
    }
    
    public Node getOtherNode(Node node) {
        if (start.equals(node)) return end;
        if (end.equals(node)) return start;
        return null;
    }
    
    public boolean connectsNodes(Node node1, Node node2) {
        return (start == node1 && end == node2) || (start == node2 && end == node1);
    }
}
