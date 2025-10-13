import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Gui {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GuiFrame();
        });
    }
}

class GuiFrame extends JFrame {

    private JTextField sourceField;
    private JTextField destField;
    private JButton findButton;
    private JLabel mapLabel;
    private JLabel heading;
    private JScrollBar scrollBar;

    public GuiFrame() {
        // Frame setup
        setTitle("Campus Navigator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 890);
        setLayout(null);
        setResizable(false);
        getContentPane().setBackground(new Color(30, 30, 30)); // Dark background

        // Heading
        heading = new JLabel("Campus Navigator", SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 24));
        heading.setForeground(Color.WHITE);
        heading.setBounds(0, 20, 900, 40);
        add(heading);

        // Main panel with border
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(30, 30, 30));
        mainPanel.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80), 2, true));
        mainPanel.setBounds(50, 80, 800, 770);
        add(mainPanel);

        // Source field
        JLabel srcLabel = new JLabel("Source");
        srcLabel.setForeground(Color.WHITE);
        srcLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        srcLabel.setBounds(30, 30, 100, 25);
        mainPanel.add(srcLabel);

        sourceField = new JTextField();
        sourceField.setBounds(200, 25, 520, 50);
        sourceField.setBackground(new Color(45, 45, 45));
        sourceField.setForeground(Color.WHITE);
        sourceField.setCaretColor(Color.WHITE);
        sourceField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(80, 80, 80), 1, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        sourceField.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(sourceField);

        // "To" label
        JLabel toLabel = new JLabel("To", SwingConstants.CENTER);
        toLabel.setForeground(Color.LIGHT_GRAY);
        toLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        toLabel.setBounds(200, 85, 520, 20);
        mainPanel.add(toLabel);

        // Destination field
        JLabel destLabel = new JLabel("Destination");
        destLabel.setForeground(Color.WHITE);
        destLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        destLabel.setBounds(30, 120, 150, 25);
        mainPanel.add(destLabel);

        destField = new JTextField();
        destField.setBounds(200, 115, 520, 50);
        destField.setBackground(new Color(45, 45, 45));
        destField.setForeground(Color.WHITE);
        destField.setCaretColor(Color.WHITE);
        destField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(80, 80, 80), 1, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        destField.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(destField);

        // Find Path Button
        findButton = new JButton("Find Path");
        findButton.setBounds(300, 180, 200, 40);
        findButton.setBackground(new Color(70, 130, 180));
        findButton.setForeground(Color.WHITE);
        findButton.setFont(new Font("Arial", Font.BOLD, 16));
        findButton.setFocusPainted(false);
        findButton.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80), 1, true));
        findButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        mainPanel.add(findButton);

        // Map panel with border and label
        JPanel mapPanel = new JPanel();
        mapPanel.setLayout(new BorderLayout());
        mapPanel.setBackground(new Color(30, 30, 30));
        mapPanel.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80), 1, true));
        mapPanel.setBounds(30, 235, 740, 515);
        mainPanel.add(mapPanel);

        // MAP label
        JLabel mapTitleLabel = new JLabel("MAP");
        mapTitleLabel.setForeground(Color.WHITE);
        mapTitleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        mapTitleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        mapPanel.add(mapTitleLabel, BorderLayout.NORTH);

        // Load and render map image with better quality
        mapLabel = new JLabel();
        mapLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mapLabel.setVerticalAlignment(SwingConstants.CENTER);

        File mapFile = new File("../assets/simple.png");
        if (mapFile.exists()) {
            try {
                // Read the original image
                BufferedImage originalImage = ImageIO.read(mapFile);
                
                // Calculate dimensions - use native size or minimal scaling
                int maxWidth = 720;
                int maxHeight = 500;
                int originalWidth = originalImage.getWidth();
                int originalHeight = originalImage.getHeight();
                
                // Only scale if image is larger than available space
                if (originalWidth <= maxWidth && originalHeight <= maxHeight) {
                    // Use original size - no scaling needed!
                    mapLabel.setIcon(new ImageIcon(originalImage));
                } else {
                    // Minimal scaling with highest quality
                    double widthRatio = (double) maxWidth / originalWidth;
                    double heightRatio = (double) maxHeight / originalHeight;
                    double ratio = Math.min(widthRatio, heightRatio);
                    
                    int newWidth = (int) (originalWidth * ratio);
                    int newHeight = (int) (originalHeight * ratio);
                    
                    // Use highest quality scaling
                    BufferedImage scaledImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g2d = scaledImage.createGraphics();
                    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                    g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
                    g2d.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
                    g2d.dispose();
                    
                    mapLabel.setIcon(new ImageIcon(scaledImage));
                }
            } catch (Exception e) {
                mapLabel.setText("Error loading map");
                mapLabel.setForeground(Color.LIGHT_GRAY);
                e.printStackTrace();
            }
        } else {
            mapLabel.setText("Map image not found");
            mapLabel.setForeground(Color.LIGHT_GRAY);
        }

        mapPanel.add(mapLabel, BorderLayout.CENTER);

        // Show frame
        setLocationRelativeTo(null); // Center on screen
        setVisible(true);
    }
}