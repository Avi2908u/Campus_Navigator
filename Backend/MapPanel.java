import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class MapPanel extends JPanel {
    private Graph graph;
    private Node selectedStart;
    private Node selectedEnd;
    private Node hoveredNode;
    private Dijkstra.PathResult shortestPath;
    
    public MapPanel() {
        graph = new Graph();
        graph.initializeCampusMap();
        
        setPreferredSize(new Dimension(800, 600));
        setBackground(new Color(240, 248, 255));
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClick(e.getX(), e.getY());
            }
        });
        
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                handleMouseMove(e.getX(), e.getY());
            }
        });
    }
    
    private void handleMouseClick(int x, int y) {
        Node clicked = graph.getNodeAt(x, y, 20);
        
        if (clicked != null) {
            if (selectedStart == null) {
                selectedStart = clicked;
                selectedEnd = null;
                shortestPath = null;
            } else if (selectedStart == clicked) {
                selectedStart = null;
                selectedEnd = null;
                shortestPath = null;
            } else {
                selectedEnd = clicked;
                // Calculate shortest path using Dijkstra's algorithm
                shortestPath = Dijkstra.findShortestPath(graph, selectedStart, selectedEnd);
            }
            repaint();
        }
    }
    
    private void handleMouseMove(int x, int y) {
        Node hovered = graph.getNodeAt(x, y, 20);
        if (hovered != hoveredNode) {
            hoveredNode = hovered;
            repaint();
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        drawEdges(g2d);
        drawShortestPath(g2d);
        drawNodes(g2d);
        drawInstructions(g2d);
    }
    
    private void drawEdges(Graphics2D g2d) {
        for (Edge edge : graph.getEdges()) {
            g2d.setColor(new Color(100, 149, 237, 150));
            g2d.setStroke(new BasicStroke(2));
            
            Node start = edge.getStart();
            Node end = edge.getEnd();
            g2d.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
            
            // Draw distance label
            int midX = (start.getX() + end.getX()) / 2;
            int midY = (start.getY() + end.getY()) / 2;
            
            String distText = String.format("%.0fm", edge.getDistance());
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(distText);
            
            g2d.setColor(new Color(255, 255, 255, 200));
            g2d.fillRect(midX - textWidth/2 - 3, midY - 8, textWidth + 6, 16);
            
            g2d.setColor(new Color(70, 130, 180));
            g2d.setFont(new Font("Arial", Font.BOLD, 11));
            g2d.drawString(distText, midX - textWidth/2, midY + 4);
        }
    }
    
    private void drawShortestPath(Graphics2D g2d) {
        if (shortestPath != null && shortestPath.hasPath()) {
            List<Edge> pathEdges = shortestPath.getPathEdges();
            
            // Draw highlighted path
            g2d.setColor(new Color(255, 69, 0));
            g2d.setStroke(new BasicStroke(5));
            
            for (Edge edge : pathEdges) {
                Node start = edge.getStart();
                Node end = edge.getEnd();
                g2d.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
            }
            
            // Draw path nodes with numbers
            List<Node> path = shortestPath.getPath();
            for (int i = 0; i < path.size(); i++) {
                Node node = path.get(i);
                
                // Draw step number
                if (i > 0 && i < path.size() - 1) {
                    g2d.setColor(new Color(255, 69, 0));
                    g2d.fillOval(node.getX() - 12, node.getY() - 12, 24, 24);
                    g2d.setColor(Color.WHITE);
                    g2d.setFont(new Font("Arial", Font.BOLD, 12));
                    String stepNum = String.valueOf(i + 1);
                    FontMetrics fm = g2d.getFontMetrics();
                    int w = fm.stringWidth(stepNum);
                    g2d.drawString(stepNum, node.getX() - w/2, node.getY() + 5);
                }
            }
        }
    }
    
    private void drawNodes(Graphics2D g2d) {
        for (Node node : graph.getNodes()) {
            boolean isSelected = (node == selectedStart || node == selectedEnd);
            boolean isHovered = (node == hoveredNode);
            
            int x = node.getX();
            int y = node.getY();
            
            // Draw selection/hover circle
            if (isSelected) {
                g2d.setColor(new Color(255, 69, 0));
                g2d.fillOval(x - 22, y - 22, 44, 44);
                g2d.setColor(Color.WHITE);
                g2d.fillOval(x - 18, y - 18, 36, 36);
            } else if (isHovered) {
                g2d.setColor(new Color(100, 149, 237));
                g2d.fillOval(x - 22, y - 22, 44, 44);
            }
            
            // Draw node circle
            g2d.setColor(new Color(70, 130, 180));
            g2d.fillOval(x - 15, y - 15, 30, 30);
            
            // Draw node name
            g2d.setFont(new Font("Arial", Font.BOLD, 12));
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(node.getName());
            
            g2d.setColor(new Color(255, 255, 255, 220));
            g2d.fillRect(x - textWidth/2 - 4, y + 20, textWidth + 8, 18);
            
            g2d.setColor(Color.BLACK);
            g2d.drawString(node.getName(), x - textWidth/2, y + 33);
        }
    }
    
    private void drawInstructions(Graphics2D g2d) {
        g2d.setColor(new Color(50, 50, 50));
        g2d.setFont(new Font("Arial", Font.PLAIN, 14));
        g2d.drawString("Click on locations to find shortest path using Dijkstra's Algorithm", 10, 20);
        
        if (selectedStart != null && selectedEnd == null) {
            g2d.drawString("Start: " + selectedStart.getName() + 
                    " (Click destination)", 10, 40);
        } else if (shortestPath != null && shortestPath.hasPath()) {
            double distance = shortestPath.getTotalDistance();
            g2d.setColor(new Color(255, 69, 0));
            g2d.setFont(new Font("Arial", Font.BOLD, 14));
            
            List<Node> path = shortestPath.getPath();
            String pathStr = path.get(0).getName();
            for (int i = 1; i < path.size(); i++) {
                pathStr += " → " + path.get(i).getName();
            }
            
            g2d.drawString(String.format("Shortest Path: %s", pathStr), 10, 40);
            g2d.drawString(String.format("Total Distance: %.0fm | Steps: %d", 
                    distance, path.size() - 1), 10, 60);
        } else if (selectedStart != null && selectedEnd != null) {
            g2d.setColor(Color.RED);
            g2d.drawString("No path found between selected locations!", 10, 40);
        }
    }
}