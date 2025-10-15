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

    public void addEdge(String startName, String endName, double distance) {
        Node start = findNode(startName);
        Node end = findNode(endName);

        if (start != null && end != null) {
            Edge edge = new Edge(start, end, distance);
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
        // Add campus locations with coordinates matching the map layout
        // Top area - Hostels (well separated)
        addNode("Gargi Girl's Hostel", 100, 80);
        addNode("Tagore Boy's Hostel", 350, 80);
        
        // Upper-middle area - B5 Workshop
        addNode("B5", 240, 180);
        
        // Left side - Canteen area (spread vertically)
        addNode("Mansarowar", 150, 300);
        addNode("Canteen", 180, 350);
        
        // Central area - Parking
        addNode("Parking", 280, 360);
        
        // Central-right area - Buildings (more horizontal spacing)
        addNode("Park", 360, 290);
        addNode("B3", 450, 280);
        addNode("Choti Canteen", 550, 290);
        
        // Middle row (spread out more)
        addNode("Central Library", 220, 420);
        addNode("B6", 300, 410);
        addNode("Basketball Court", 380, 420);
        addNode("B4", 470, 400);
        
        // Lower-middle area (better spacing)
        addNode("B2", 260, 470);
        addNode("Volleyball Court", 320, 490);
        addNode("Sports Office", 400, 500);
        addNode("Hotspot", 520, 480);
        
        // Right side facilities (pushed further right)
        addNode("Lawn Tennis Court", 620, 360);
        addNode("Cricket Ground", 680, 460);
        
        // Bottom area (more vertical spacing)
        addNode("Health Centre", 440, 560);
        addNode("B1", 510, 550);
        addNode("Admin Block", 480, 640);
        
        // Far right
        addNode("Main Gate", 600, 650);
        addNode("ATM", 650, 660);

        // Create edges between locations
        addEdge("Gargi Girl's Hostel", "Tagore Boy's Hostel", 80);
        addEdge("Gargi Girl's Hostel", "B5", 250);
        addEdge("Gargi Girl's Hostel", "Canteen", 300);
        addEdge("B5", "Mansarowar", 120);
        addEdge("Mansarowar", "Canteen", 25);
        addEdge("Mansarowar", "Football Ground", 40);
        addEdge("Basketball Court", "Football Ground", 10);
        addEdge("Canteen", "Parking", 30);
        addEdge("B2", "Canteen", 5);
        addEdge("Parking", "Basketball Court", 20);
        addEdge("Basketball Court", "B2", 70);
        addEdge("Basketball Court", "Hotspot", 52);
        addEdge("Hotspot", "B3", 60);
        addEdge("B3", "B6", 10);
        addEdge("Hotspot", "Choti Canteen", 50);
        addEdge("Hotspot", "B4", 40);
        addEdge("Hotspot", "Sports Office", 85);
        addEdge("Sports Office", "Health Centre", 5);
        addEdge("Sports Office", "Volleyball Court", 40);
        addEdge("Health Centre", "B1", 30);
        addEdge("Admin Ground", "B1", 30);
        addEdge("B4", "Lawn Tennis Court", 50);
        addEdge("Hotspot", "Cricket Ground", 80);
        addEdge("Volleyball Court", "Admin Block", 85);
        addEdge("Cricket Ground", "B1", 60);
        addEdge("B1", "Admin Block", 50);
        addEdge("Admin Block", "Main Gate", 85);
        addEdge("Main Gate", "ATM", 10);
    }
}