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
        // Add campus locations
        addNode("Gargi Girl's Hostel", 150, 100);
        addNode("Canteen", 350, 150);
        addNode("Tagore Boy's Hostel", 550, 100);
        addNode("B5", 350, 350);
        addNode("Football Ground", 350, 350);
        addNode("Mansarowar", 350, 350);
        addNode("Sports Office", 550, 300);
        addNode("Basketball Court", 150, 500);
        addNode("Hotspot", 350, 500);
        addNode("B2", 350, 500);
        addNode("Central Library", 550, 500);
        addNode("B1", 650, 400);
        addNode("Health Centre", 750, 300);
        addNode("Volleyball Court", 250, 600);
        addNode("Admin Block", 450, 250);
        addNode("Admin Ground", 450, 250);
        addNode("Parking", 650, 150);
        addNode("B3", 450, 450);
        addNode("B6", 450, 450);
        addNode("B4", 550, 350);
        addNode("Choti Canteen", 250, 200);
        addNode("Lawn Tennis Court", 350, 600);
        addNode("Cricket Ground", 150, 650);
        addNode("Main Gate", 650, 250);
        addNode("ATM", 700, 250);

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
