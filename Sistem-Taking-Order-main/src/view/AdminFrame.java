package view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class AdminFrame extends JFrame {
    private JPanel sidebar;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private JTable projectTable;

    public AdminFrame() {
        setTitle("Dash UI - Admin Dashboard");
        setSize(1024, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Sidebar
        sidebar = createSidebar();
        add(sidebar, BorderLayout.WEST);

        // Topbar
        JPanel topbar = createTopBar();
        add(topbar, BorderLayout.NORTH);

        // Main panel with cards and table
        mainPanel = createMainPanel();
        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createSidebar() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(34, 39, 54));
        panel.setPreferredSize(new Dimension(220, getHeight()));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(15, 10, 15, 10));

        // Title / Logo
        JLabel title = new JLabel("Dash UI");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Dashboard menu (expandable)
        JLabel dashboardLabel = new JLabel("Dashboard");
        dashboardLabel.setForeground(Color.WHITE);
        dashboardLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        dashboardLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(dashboardLabel);

        // Dashboard sub menus
        JLabel v1 = createSidebarMenuItem("Dashboard v1", true);
        JLabel v2 = createSidebarMenuItem("Dashboard v2", false);
        JLabel v3 = createSidebarMenuItem("Dashboard v3", false);

        panel.add(v1);
        panel.add(v2);
        panel.add(v3);

        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Other sidebar items
        String[] items = {
                "Widgets",
                "Layout Options",
                "Charts",
                "UI Elements",
                "Forms",
                "Tables",
                "EXAMPLES",
                "Calendar",
                "Gallery",
                "Docs",
                "Changelog"
        };

        for (String item : items) {
            JLabel label = createSidebarMenuItem(item, false);
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(label);
        }

        return panel;
    }

    private JLabel createSidebarMenuItem(String text, boolean selected) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(selected ? Color.WHITE : new Color(150, 150, 150));
        label.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 5));

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!selected) {
                    label.setForeground(Color.WHITE);
                    label.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!selected) {
                    label.setForeground(new Color(150, 150, 150));
                    label.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
        });

        return label;
    }

    private JPanel createTopBar() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 250));
        panel.setBorder(new EmptyBorder(5, 15, 5, 15));
        panel.setPreferredSize(new Dimension(getWidth(), 45));

        // Hamburger menu icon
        JLabel menuIcon = new JLabel("\u2630"); // simple hamburger icon
        menuIcon.setFont(new Font("Segoe UI", Font.BOLD, 22));
        menuIcon.setForeground(new Color(34, 39, 54));
        panel.add(menuIcon, BorderLayout.WEST);

        // Search field center
        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        panel.add(searchField, BorderLayout.CENTER);

        // Profile icon right
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 5));
        rightPanel.setOpaque(false);

        JLabel bellIcon = new JLabel("\uD83D\uDD14"); // Bell emoji as placeholder
        bellIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        rightPanel.add(bellIcon);

        JLabel profilePic = new JLabel();
        profilePic.setPreferredSize(new Dimension(32, 32));
        profilePic.setOpaque(true);
        profilePic.setBackground(Color.GRAY);
        profilePic.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
        rightPanel.add(profilePic);

        panel.add(rightPanel, BorderLayout.EAST);

        return panel;
    }

    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 250));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Card stats panel
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 0));
        cardPanel.setOpaque(false);

        cardPanel.add(createCard("Projects", "18", "2 Completed", new Color(96, 91, 234)));
        cardPanel.add(createCard("Active Task", "132", "28 Completed", new Color(175, 169, 242)));
        cardPanel.add(createCard("Teams", "12", "1 Completed", new Color(182, 174, 250)));
        cardPanel.add(createCard("Productivity", "76%", "5% Completed", new Color(200, 192, 255)));

        panel.add(cardPanel, BorderLayout.NORTH);

        // Table for active projects
        String[] headers = {"Project Name", "Hours", "Priority", "Members", "Progress"};
        Object[][] data = {
                {"Dropbox Design System", 34, "Medium", "Alice, Bob, +5", "15%"},
                {"Slack Team UI Design", 47, "High", "Carol, Dave, +5", "35%"},
                {"GitHub Redesign", 66, "Low", "Eve, Frank, +2", "22%"},
                {"Admin Panel", 12, "High", "Grace, Helen", "67%"}
        };

        projectTable = new JTable(new DefaultTableModel(data, headers));
        JScrollPane tableScrollPane = new JScrollPane(projectTable);
        tableScrollPane.setPreferredSize(new Dimension(panel.getWidth(), 300));
        tableScrollPane.setForeground(Color.BLACK);

        panel.add(tableScrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createCard(String title, String mainValue, String subValue, Color bgColor) {
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(180, 100));
        card.setBackground(Color.WHITE);
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        // Title top
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setForeground(new Color(80, 80, 80));
        card.add(titleLabel, BorderLayout.NORTH);

        // Main value center
        JLabel mainValueLabel = new JLabel(mainValue);
        mainValueLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        mainValueLabel.setForeground(bgColor.darker());
        mainValueLabel.setBorder(new EmptyBorder(10, 0, 0, 0));
        card.add(mainValueLabel, BorderLayout.CENTER);

        // Sub value bottom
        JLabel subValueLabel = new JLabel(subValue);
        subValueLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subValueLabel.setForeground(new Color(120, 120, 120));
        card.add(subValueLabel, BorderLayout.SOUTH);

        return card;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdminFrame frame = new AdminFrame();
            frame.setVisible(true);
        });
    }
}
