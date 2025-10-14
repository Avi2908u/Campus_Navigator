public class Node {
    private String name;
    private int x;
    private int y;
    
    public Node(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }
    
    public String getName() {
        return name;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public double distanceTo(Node other) {
        int dx = this.x - other.x;
        int dy = this.y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
    
    public boolean contains(int px, int py, int radius) {
        int dx = px - this.x;
        int dy = py - this.y;
        return Math.sqrt(dx * dx + dy * dy) <= radius;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Node node = (Node) obj;
        return name.equals(node.name);
    }
    
    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
