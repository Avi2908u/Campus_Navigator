import java.util.*;

public class Dijkstra {
    
    public static class PathResult {
        private List<Node> path;
        private double totalDistance;
        private List<Edge> pathEdges;
        
        public PathResult(List<Node> path, double totalDistance, List<Edge> pathEdges) {
            this.path = path;
            this.totalDistance = totalDistance;
            this.pathEdges = pathEdges;
        }
        
        public List<Node> getPath() {
            return path;
        }
        
        public double getTotalDistance() {
            return totalDistance;
        }
        
        public List<Edge> getPathEdges() {
            return pathEdges;
        }
        
        public boolean hasPath() {
            return path != null && !path.isEmpty();
        }
    }
    
    public static PathResult findShortestPath(Graph graph, Node start, Node end) {
        if (start == null || end == null) {
            return new PathResult(new ArrayList<>(), 0, new ArrayList<>());
        }
        
        Map<Node, Double> distances = new HashMap<>();
        Map<Node, Node> previous = new HashMap<>();
        PriorityQueue<NodeDistance> pq = new PriorityQueue<>();
        Set<Node> visited = new HashSet<>();
        
        for (Node node : graph.getNodes()) {
            distances.put(node, Double.POSITIVE_INFINITY);
        }
        distances.put(start, 0.0);
        
        pq.offer(new NodeDistance(start, 0.0));
        
        while (!pq.isEmpty()) {
            NodeDistance current = pq.poll();
            Node currentNode = current.node;
            
            if (visited.contains(currentNode)) {
                continue;
            }
            
            visited.add(currentNode);
            
            if (currentNode.equals(end)) {
                break;
            }
            
            for (Edge edge : graph.getAdjacentEdges(currentNode)) {
                Node neighbor = edge.getOtherNode(currentNode);
                
                if (neighbor == null || visited.contains(neighbor)) {
                    continue;
                }
                
                double newDist = distances.get(currentNode) + edge.getDistance();
                
                if (newDist < distances.get(neighbor)) {
                    distances.put(neighbor, newDist);
                    previous.put(neighbor, currentNode);
                    pq.offer(new NodeDistance(neighbor, newDist));
                }
            }
        }
        
        List<Node> path = reconstructPath(previous, start, end);
        double totalDistance = distances.get(end);
        List<Edge> pathEdges = getPathEdges(graph, path);
        
        if (totalDistance == Double.POSITIVE_INFINITY) {
            return new PathResult(new ArrayList<>(), 0, new ArrayList<>());
        }
        
        return new PathResult(path, totalDistance, pathEdges);
    }
    
    private static List<Node> reconstructPath(Map<Node, Node> previous, Node start, Node end) {
        List<Node> path = new ArrayList<>();
        Node current = end;
        
        while (current != null) {
            path.add(0, current);
            current = previous.get(current);
        }
        
        if (path.isEmpty() || !path.get(0).equals(start)) {
            return new ArrayList<>();
        }
        
        return path;
    }
    
    private static List<Edge> getPathEdges(Graph graph, List<Node> path) {
        List<Edge> pathEdges = new ArrayList<>();
        
        for (int i = 0; i < path.size() - 1; i++) {
            Node current = path.get(i);
            Node next = path.get(i + 1);
            
            for (Edge edge : graph.getEdges()) {
                if (edge.connectsNodes(current, next)) {
                    pathEdges.add(edge);
                    break;
                }
            }
        }
        
        return pathEdges;
    }
    
    private static class NodeDistance implements Comparable<NodeDistance> {
        Node node;
        double distance;
        
        NodeDistance(Node node, double distance) {
            this.node = node;
            this.distance = distance;
        }
        
        @Override
        public int compareTo(NodeDistance other) {
            return Double.compare(this.distance, other.distance);
        }
    }
}
