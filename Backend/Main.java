import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Campus Navigator");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            // Create main panel with BorderLayout
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBackground(Color.WHITE);
            
            // Create header panel
            JPanel headerPanel = createHeaderPanel();
            mainPanel.add(headerPanel, BorderLayout.NORTH);
            
            // Create center panel for map and legend
            JPanel centerPanel = new JPanel(new BorderLayout());
            centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
            
            // Create and add the map panel
            MapPanel mapPanel = new MapPanel();
            centerPanel.add(mapPanel, BorderLayout.CENTER);
            
            // Create and add the legend panel
            JPanel legendPanel = createLegendPanel();
            centerPanel.add(legendPanel, BorderLayout.EAST);
            
            mainPanel.add(centerPanel, BorderLayout.CENTER);
            
            frame.add(mainPanel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
    
    private static JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(70, 130, 180));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Main title
        JLabel titleLabel = new JLabel("Campus Navigator - Dijkstra's Algorithm");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Subtitle
        JLabel subtitleLabel = new JLabel("Click on locations to find shortest path");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(230, 240, 255));
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Container for title and subtitle
        JPanel titleContainer = new JPanel();
        titleContainer.setLayout(new BoxLayout(titleContainer, BoxLayout.Y_AXIS));
        titleContainer.setBackground(new Color(70, 130, 180));
        
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        titleContainer.add(titleLabel);
        titleContainer.add(Box.createRigidArea(new Dimension(0, 8)));
        titleContainer.add(subtitleLabel);
        
        headerPanel.add(titleContainer, BorderLayout.CENTER);
        
        return headerPanel;
    }
    
    private static JPanel createLegendPanel() {
        JPanel legendPanel = new JPanel();
        legendPanel.setLayout(new BoxLayout(legendPanel, BoxLayout.Y_AXIS));
        legendPanel.setBackground(new Color(240, 248, 255));
        legendPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 2, 0, 0, new Color(100, 149, 237)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        legendPanel.setPreferredSize(new Dimension(250, 0));
        
        // Title
        JLabel titleLabel = new JLabel("Campus Locations");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        legendPanel.add(titleLabel);
        legendPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Add categories
        addCategory(legendPanel, "Academic Buildings", new String[]{
            "B1", "B2", "B3", "B4", "B5", "B6", "Central Library"
        });
        
        addCategory(legendPanel, "Hostels", new String[]{
            "Gargi Girl's Hostel", "Tagore Boy's Hostel"
        });
        
        addCategory(legendPanel, "Dining", new String[]{
            "Canteen", "Choti Canteen", "Hotspot"
        });
        
        addCategory(legendPanel, "Sports Facilities", new String[]{
            "Basketball Court", "Cricket Ground", 
            "Lawn Tennis Court", "Volleyball Court", "Sports Office"
        });
        
        addCategory(legendPanel, "Other Facilities", new String[]{
            "Admin Block", "ATM", "Health Centre", 
            "Main Gate", "Mansarowar", "Park", "Parking"
        });
        
        // Add instructions
        legendPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        JLabel instructionLabel = new JLabel("<html><i>Click on two locations<br>to find shortest path</i></html>");
        instructionLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        instructionLabel.setForeground(Color.GRAY);
        instructionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        legendPanel.add(instructionLabel);
        
        // Add glue to push everything to the top
        legendPanel.add(Box.createVerticalGlue());
        
        return legendPanel;
    }
    
    private static void addCategory(JPanel panel, String categoryName, String[] items) {
        // Category title
        JLabel categoryLabel = new JLabel(categoryName);
        categoryLabel.setFont(new Font("Arial", Font.BOLD, 13));
        categoryLabel.setForeground(new Color(25, 25, 112));
        categoryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(categoryLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        
        // Items
        for (String item : items) {
            JLabel itemLabel = new JLabel("  • " + item);
            itemLabel.setFont(new Font("Arial", Font.PLAIN, 11));
            itemLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(itemLabel);
        }
        
        panel.add(Box.createRigidArea(new Dimension(0, 12)));
    }
}

// Enhanced MapPanel with background image support
class MapPanelWithBackground extends JPanel {
    private BufferedImage backgroundImage;
    private String imagePath;
    
    public MapPanelWithBackground() {
      
        this.imagePath = "simple.png"; 
        loadBackgroundImage();
    }
    
    private void loadBackgroundImage() {
        try {
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                backgroundImage = ImageIO.read(imageFile);
                System.out.println("Background image loaded successfully!");
            } else {
                System.out.println("Image file not found: " + imagePath);
                System.out.println("Please place your campus map image in the project directory.");
            }
        } catch (Exception e) {
            System.err.println("Error loading background image: " + e.getMessage());
        }
    }
    
    public void setBackgroundImage(String path) {
        this.imagePath = path;
        loadBackgroundImage();
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Draw background image if loaded
        if (backgroundImage != null) {
            Graphics2D g2d = (Graphics2D) g;
            // Enable antialiasing for smoother image
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, 
                                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            
            // Draw the image scaled to fit the panel
            g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            // Draw a light background if no image is loaded
            g.setColor(new Color(230, 245, 230));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        
        // Your existing graph drawing code goes here
        // (nodes, edges, paths, etc.)
    }
}