import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    private List<Node> nodes;
    private List<Edge> edges;
    private Map<Node, List<Edge>> adjacencyList;
    
    public Graph() {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
        adjacencyList = new HashMap<>();
    }
    
    public void addNode(String name, int x, int y) {
        Node node = new Node(name, x, y);
        nodes.add(node);
        adjacencyList.put(node, new ArrayList<>());
    }
    
    public void addEdge(String startName, String endName) {
        Node start = findNode(startName);
        Node end = findNode(endName);
        
        if (start != null && end != null) {
            Edge edge = new Edge(start, end);
            edges.add(edge);
            adjacencyList.get(start).add(edge);
            adjacencyList.get(end).add(edge);
        }
    }
    
    public Node findNode(String name) {
        for (Node node : nodes) {
            if (node.getName().equals(name)) {
                return node;
            }
        }
        return null;
    }
    
    public Node getNodeAt(int x, int y, int radius) {
        for (Node node : nodes) {
            if (node.contains(x, y, radius)) {
                return node;
            }
        }
        return null;
    }
    
    public List<Node> getNodes() {
        return nodes;
    }
    
    public List<Edge> getEdges() {
        return edges;
    }
    
    public List<Edge> getAdjacentEdges(Node node) {
        return adjacencyList.getOrDefault(node, new ArrayList<>());
    }
    
    public void initializeCampusMap() {
        // Add campus locations
        addNode("Gargi Girl's Hostel", 150, 100);
        addNode("Canteen", 350, 150);
        addNode("Tagore Boy's Hostel", 550, 100);
        addNode("Mansarovar", 150, 300);
        addNode("B5 Workshop", 350, 350);
        addNode("Sports Office", 550, 300);
        addNode("Basketball Court", 150, 500);
        addNode("B2 IOT department", 350, 500);
        addNode("Central Library", 550, 500);
        addNode("B1", 650, 400);
        addNode("Health Centre", 750, 300);
        addNode("Volleyball Court", 250, 600);
        addNode("Admin Block", 450, 250);
        addNode("Parking", 650, 150);
        addNode("B3", 450, 450);
        addNode("B4", 550, 350);
        addNode("Choti Canteen", 250, 200);
        addNode("Lawn Tennis Court", 350, 600);
        addNode("Cricket Ground", 150, 650);
        addNode("Bank of India", 650, 250);
        addNode("ATM", 700, 250);
        
        // Create edges between locations
        addEdge("Gargi Girl's Hostel", "Tagore Boy's Hostel");
        addEdge("Cafeteria", "Admin Block");
        addEdge("Gargi Girl's Hostel", "Lab Building");
        addEdge("Cafeteria", "Auditorium");
        addEdge("Admin Block", "Sports Complex");
        addEdge("Lab Building", "Auditorium");
        addEdge("Auditorium", "Sports Complex");
        addEdge("Lab Building", "Hostel A");
        addEdge("Auditorium", "Hostel B");
        addEdge("Sports Complex", "Parking");
        addEdge("Hostel A", "Hostel B");
        addEdge("Hostel B", "Parking");
    }
}

