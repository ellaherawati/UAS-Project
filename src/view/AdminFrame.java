package view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class AdminFrame extends JFrame {
    private JPanel mainPanel;
    private JPanel contentPanel;
    private JButton dashboardButton, menuButton, userButton, createUserButton, settingsButton, logoutButton;
    
    // Color scheme - Modern dashboard colors
    private Color primaryColor = new Color(30, 41, 59);      // slate-800
    private Color secondaryColor = new Color(248, 250, 252);  // slate-50
    private Color accentColor = new Color(99, 102, 241);      // indigo-500
    private Color successColor = new Color(34, 197, 94);      // green-500
    private Color warningColor = new Color(251, 191, 36);     // amber-400
    private Color dangerColor = new Color(239, 68, 68);       // red-500
    private Color cardColor = Color.WHITE;
    private Color textPrimary = new Color(15, 23, 42);        // slate-900
    private Color textSecondary = new Color(100, 116, 139);   // slate-500
    
    // Data untuk simulasi
    private List<MenuItem> menuItems;
    private List<User> users;
    private NumberFormat currencyFormat;
    
    // Inner classes untuk data
    static class MenuItem {
        int id;
        String name;
        String category;
        double price;
        int stock;
        boolean isActive;
        
        MenuItem(int id, String name, String category, double price, int stock, boolean isActive) {
            this.id = id;
            this.name = name;
            this.category = category;
            this.price = price;
            this.stock = stock;
            this.isActive = isActive;
        }
    }
    
    static class User {
        int id;
        String username;
        String fullName;
        String role;
        boolean isActive;
        Date createdAt;
        
        User(int id, String username, String fullName, String role, boolean isActive) {
            this.id = id;
            this.username = username;
            this.fullName = fullName;
            this.role = role;
            this.isActive = isActive;
            this.createdAt = new Date();
        }
    }
    
    public AdminFrame() {
        currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        initializeData();
        initializeComponents();
        showDashboard();
    }
    
    private void initializeData() {
        // Sample menu items
        menuItems = new ArrayList<>();
        menuItems.add(new MenuItem(1, "Nasi Goreng", "Makanan Utama", 25000, 50, true));
        menuItems.add(new MenuItem(2, "Rendang", "Makanan Utama", 35000, 30, true));
        menuItems.add(new MenuItem(3, "Soto Ayam", "Makanan Utama", 20000, 40, true));
        menuItems.add(new MenuItem(4, "Es Teh", "Minuman", 8000, 100, true));
        menuItems.add(new MenuItem(5, "Jus Jeruk", "Minuman", 15000, 25, true));
        menuItems.add(new MenuItem(6, "Kerupuk", "Snack", 5000, 100, false));
        
        // Sample users
        users = new ArrayList<>();
        users.add(new User(1, "admin", "Administrator", "admin", true));
        users.add(new User(2, "kasir1", "Kasir Satu", "kasir", true));
        users.add(new User(3, "kasir2", "Kasir Dua", "kasir", true));
        users.add(new User(4, "manager", "Manager", "manager", true));
        users.add(new User(5, "kasir3", "Kasir Tiga", "kasir", false));
    }
    
    private void initializeComponents() {
        setTitle("Admin Dashboard - Dapur Arunika");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 900);
        setLocationRelativeTo(null);
        setResizable(true);
        
        // Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Main layout
        setLayout(new BorderLayout());
        
        // Create sidebar
        JPanel sidebar = createSidebar();
        add(sidebar, BorderLayout.WEST);
        
        // Create main content area
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(secondaryColor);
        add(mainPanel, BorderLayout.CENTER);
        
        // Create header
        JPanel header = createHeader();
        mainPanel.add(header, BorderLayout.NORTH);
        
        // Create content panel
        contentPanel = new JPanel(new CardLayout());
        contentPanel.setBackground(secondaryColor);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
    }
    
    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(primaryColor);
        sidebar.setPreferredSize(new Dimension(280, 900));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new EmptyBorder(24, 0, 24, 0));
        
        // Logo/Title
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logoPanel.setBackground(primaryColor);
        
        JLabel logoLabel = new JLabel("🍽️");
        logoLabel.setFont(new Font("Arial", Font.PLAIN, 32));
        
        JLabel titleLabel = new JLabel("Admin Panel");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        
        JPanel titleContainer = new JPanel(new BorderLayout());
        titleContainer.setBackground(primaryColor);
        titleContainer.add(logoLabel, BorderLayout.WEST);
        titleContainer.add(titleLabel, BorderLayout.CENTER);
        titleContainer.setBorder(new EmptyBorder(0, 20, 0, 0));
        
        logoPanel.add(titleContainer);
        sidebar.add(logoPanel);
        sidebar.add(Box.createVerticalStrut(40));
        
        // Menu section
        JLabel menuLabel = new JLabel("MENU UTAMA");
        menuLabel.setForeground(new Color(148, 163, 184)); // slate-400
        menuLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        menuLabel.setBorder(new EmptyBorder(0, 24, 12, 0));
        sidebar.add(menuLabel);
        
        // Menu buttons
        dashboardButton = createSidebarButton("📊", "Dashboard", true);
        menuButton = createSidebarButton("🍽️", "Kelola Menu", false);
        userButton = createSidebarButton("👥", "Kelola User", false);
        createUserButton = createSidebarButton("➕", "Buat User Baru", false);
        
        sidebar.add(dashboardButton);
        sidebar.add(Box.createVerticalStrut(4));
        sidebar.add(menuButton);
        sidebar.add(Box.createVerticalStrut(4));
        sidebar.add(userButton);
        sidebar.add(Box.createVerticalStrut(4));
        sidebar.add(createUserButton);
        
        sidebar.add(Box.createVerticalStrut(32));
        
        // Settings section
        JLabel settingsLabel = new JLabel("PENGATURAN");
        settingsLabel.setForeground(new Color(148, 163, 184));
        settingsLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        settingsLabel.setBorder(new EmptyBorder(0, 24, 12, 0));
        sidebar.add(settingsLabel);
        
        settingsButton = createSidebarButton("⚙️", "Pengaturan", false);
        sidebar.add(settingsButton);
        
        // Spacer
        sidebar.add(Box.createVerticalGlue());
        
        // Logout button
        logoutButton = createSidebarButton("🚪", "Logout", false);
        logoutButton.setBackground(dangerColor);
        sidebar.add(logoutButton);
        
        // Event listeners
        dashboardButton.addActionListener(e -> {
            setActiveButton(dashboardButton);
            showDashboard();
        });
        
        menuButton.addActionListener(e -> {
            setActiveButton(menuButton);
            showMenuManagement();
        });
        
        userButton.addActionListener(e -> {
            setActiveButton(userButton);
            showUserManagement();
        });
        
        createUserButton.addActionListener(e -> {
            setActiveButton(createUserButton);
            showCreateUser();
        });
        
        settingsButton.addActionListener(e -> {
            setActiveButton(settingsButton);
            showSettings();
        });
        
        logoutButton.addActionListener(e -> logout());
        
        return sidebar;
    }
    
    private JButton createSidebarButton(String icon, String text, boolean isActive) {
        JButton button = new JButton();
        button.setLayout(new BorderLayout());
        button.setBackground(isActive ? accentColor : primaryColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(250, 44));
        button.setMaximumSize(new Dimension(250, 44));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Icon
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        iconLabel.setForeground(Color.WHITE);
        iconLabel.setBorder(new EmptyBorder(0, 24, 0, 12));
        
        // Text
        JLabel textLabel = new JLabel(text);
        textLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textLabel.setForeground(Color.WHITE);
        
        button.add(iconLabel, BorderLayout.WEST);
        button.add(textLabel, BorderLayout.CENTER);
        
        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (button.getBackground() != accentColor) {
                    button.setBackground(new Color(51, 65, 85)); // slate-700
                }
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                if (button.getBackground() != accentColor) {
                    button.setBackground(primaryColor);
                }
            }
        });
        
        return button;
    }
    
    private void setActiveButton(JButton activeButton) {
        // Reset all buttons
        dashboardButton.setBackground(primaryColor);
        menuButton.setBackground(primaryColor);
        userButton.setBackground(primaryColor);
        createUserButton.setBackground(primaryColor);
        settingsButton.setBackground(primaryColor);
        
        // Set active button
        activeButton.setBackground(accentColor);
    }
    
    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(cardColor);
        header.setBorder(new EmptyBorder(24, 32, 24, 32));
        
        // Title
        JLabel headerTitle = new JLabel("Dashboard Admin");
        headerTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        headerTitle.setForeground(textPrimary);
        
        // User info and time
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBackground(cardColor);
        
        JLabel timeLabel = new JLabel(new SimpleDateFormat("dd MMM yyyy, HH:mm").format(new Date()));
        timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        timeLabel.setForeground(textSecondary);
        
        JLabel userLabel = new JLabel("👤 Admin");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        userLabel.setForeground(textPrimary);
        userLabel.setBorder(new EmptyBorder(0, 0, 0, 16));
        
        rightPanel.add(userLabel);
        rightPanel.add(timeLabel);
        
        header.add(headerTitle, BorderLayout.WEST);
        header.add(rightPanel, BorderLayout.EAST);
        
        return header;
    }
    
    private void showDashboard() {
        JPanel dashboard = new JPanel(new BorderLayout());
        dashboard.setBackground(secondaryColor);
        dashboard.setBorder(new EmptyBorder(0, 32, 32, 32));
        
        // Statistics cards
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 24, 0));
        statsPanel.setBackground(secondaryColor);
        
        // Calculate statistics
        int totalMenuItems = menuItems.size();
        int activeMenuItems = (int) menuItems.stream().filter(m -> m.isActive).count();
        int totalUsers = users.size();
        int activeUsers = (int) users.stream().filter(u -> u.isActive).count();
        
        statsPanel.add(createStatCard("Total Menu", String.valueOf(totalMenuItems), "🍽️", accentColor));
        statsPanel.add(createStatCard("Menu Aktif", String.valueOf(activeMenuItems), "✅", successColor));
        statsPanel.add(createStatCard("Total User", String.valueOf(totalUsers), "👥", warningColor));
        statsPanel.add(createStatCard("User Aktif", String.valueOf(activeUsers), "🟢", successColor));
        
        // Recent activity
        JPanel activityPanel = new JPanel(new BorderLayout());
        activityPanel.setBackground(cardColor);
        activityPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
            new EmptyBorder(24, 24, 24, 24)
        ));
        
        JLabel activityTitle = new JLabel("Aktivitas Terbaru");
        activityTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        activityTitle.setForeground(textPrimary);
        
        JPanel activityContent = new JPanel(new GridLayout(0, 1, 0, 12));
        activityContent.setBackground(cardColor);
        
        // Sample activities
        activityContent.add(createActivityItem("User 'kasir1' login", "2 menit lalu", "🔐"));
        activityContent.add(createActivityItem("Menu 'Nasi Goreng' diupdate", "5 menit lalu", "✏️"));
        activityContent.add(createActivityItem("User baru 'kasir3' dibuat", "10 menit lalu", "👤"));
        activityContent.add(createActivityItem("Transaksi ORD001 dibayar", "15 menit lalu", "💳"));
        
        activityPanel.add(activityTitle, BorderLayout.NORTH);
        activityPanel.add(Box.createVerticalStrut(16));
        activityPanel.add(activityContent, BorderLayout.CENTER);
        
        dashboard.add(statsPanel, BorderLayout.NORTH);
        dashboard.add(Box.createVerticalStrut(32));
        dashboard.add(activityPanel, BorderLayout.CENTER);
        
        contentPanel.removeAll();
        contentPanel.add(dashboard);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private JPanel createStatCard(String title, String value, String icon, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(cardColor);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
            new EmptyBorder(24, 24, 24, 24)
        ));
        
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Arial", Font.PLAIN, 32));
        iconLabel.setForeground(color);
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setForeground(textSecondary);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setForeground(textPrimary);
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setBackground(cardColor);
        textPanel.add(titleLabel, BorderLayout.NORTH);
        textPanel.add(Box.createVerticalStrut(8));
        textPanel.add(valueLabel, BorderLayout.CENTER);
        
        card.add(iconLabel, BorderLayout.NORTH);
        card.add(Box.createVerticalStrut(16));
        card.add(textPanel, BorderLayout.CENTER);
        
        return card;
    }
    
    private JPanel createActivityItem(String activity, String time, String icon) {
        JPanel item = new JPanel(new BorderLayout());
        item.setBackground(cardColor);
        item.setBorder(new EmptyBorder(12, 16, 12, 16));
        
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        iconLabel.setBorder(new EmptyBorder(0, 0, 0, 12));
        
        JLabel activityLabel = new JLabel(activity);
        activityLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        activityLabel.setForeground(textPrimary);
        
        JLabel timeLabel = new JLabel(time);
        timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        timeLabel.setForeground(textSecondary);
        
        item.add(iconLabel, BorderLayout.WEST);
        item.add(activityLabel, BorderLayout.CENTER);
        item.add(timeLabel, BorderLayout.EAST);
        
        return item;
    }
    
    private void showMenuManagement() {
        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.setBackground(secondaryColor);
        menuPanel.setBorder(new EmptyBorder(0, 32, 32, 32));
        
        // Header with add button
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(cardColor);
        headerPanel.setBorder(new EmptyBorder(24, 24, 24, 24));
        
        JLabel title = new JLabel("Kelola Menu");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(textPrimary);
        
        JButton addButton = createModernButton("➕ Tambah Menu", accentColor);
        addButton.addActionListener(e -> showAddMenuDialog());
        
        headerPanel.add(title, BorderLayout.WEST);
        headerPanel.add(addButton, BorderLayout.EAST);
        
        // Table
        String[] columns = {"ID", "Nama", "Kategori", "Harga", "Stok", "Status", "Aksi"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        for (MenuItem item : menuItems) {
            tableModel.addRow(new Object[]{
                item.id,
                item.name,
                item.category,
                currencyFormat.format(item.price),
                item.stock,
                item.isActive ? "Aktif" : "Tidak Aktif",
                "Action"
            });
        }
        
        JTable table = new JTable(tableModel);
        table.setRowHeight(48);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setBackground(new Color(241, 245, 249));
        table.getTableHeader().setForeground(textPrimary);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.setBackground(cardColor);
        table.setSelectionBackground(new Color(239, 246, 255));
        
        // Add custom renderer for action column
        table.getColumn("Aksi").setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                panel.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
                
                JButton editBtn = new JButton("Edit");
                editBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                editBtn.setBackground(accentColor);
                editBtn.setForeground(Color.WHITE);
                editBtn.setBorderPainted(false);
                editBtn.setFocusPainted(false);
                
                JButton deleteBtn = new JButton("Hapus");
                deleteBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                deleteBtn.setBackground(dangerColor);
                deleteBtn.setForeground(Color.WHITE);
                deleteBtn.setBorderPainted(false);
                deleteBtn.setFocusPainted(false);
                
                panel.add(editBtn);
                panel.add(deleteBtn);
                
                return panel;
            }
        });
        
        // Add mouse listener for action buttons
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int column = table.columnAtPoint(e.getPoint());
                
                if (column == 6) { // Action column
                    int menuId = (int) table.getValueAt(row, 0);
                    MenuItem selectedItem = menuItems.stream()
                        .filter(item -> item.id == menuId)
                        .findFirst()
                        .orElse(null);
                    
                    if (selectedItem != null) {
                        // Check if clicked on edit or delete area
                        Rectangle cellRect = table.getCellRect(row, column, false);
                        int clickX = e.getX() - cellRect.x;
                        
                        if (clickX < cellRect.width / 2) {
                            // Edit button clicked
                            showEditMenuDialog(selectedItem);
                        } else {
                            // Delete button clicked
                            int result = JOptionPane.showConfirmDialog(
                                AdminFrame.this,
                                "Apakah Anda yakin ingin menghapus menu '" + selectedItem.name + "'?",
                                "Konfirmasi Hapus",
                                JOptionPane.YES_NO_OPTION
                            );
                            
                            if (result == JOptionPane.YES_OPTION) {
                                menuItems.remove(selectedItem);
                                showMenuManagement(); // Refresh table
                                JOptionPane.showMessageDialog(AdminFrame.this, "Menu berhasil dihapus!");
                            }
                        }
                    }
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 1));
        
        menuPanel.add(headerPanel, BorderLayout.NORTH);
        menuPanel.add(scrollPane, BorderLayout.CENTER);
        
        contentPanel.removeAll();
        contentPanel.add(menuPanel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private void showUserManagement() {
        JPanel userPanel = new JPanel(new BorderLayout());
        userPanel.setBackground(secondaryColor);
        userPanel.setBorder(new EmptyBorder(0, 32, 32, 32));
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(cardColor);
        headerPanel.setBorder(new EmptyBorder(24, 24, 24, 24));
        
        JLabel title = new JLabel("Kelola User");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(textPrimary);
        
        headerPanel.add(title, BorderLayout.WEST);
        
        // Table
        String[] columns = {"ID", "Username", "Nama Lengkap", "Role", "Status", "Dibuat", "Aksi"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        for (User user : users) {
            tableModel.addRow(new Object[]{
                user.id,
                user.username,
                user.fullName,
                user.role.toUpperCase(),
                user.isActive ? "Aktif" : "Tidak Aktif",
                dateFormat.format(user.createdAt),
                "Action"
            });
        }
        
        JTable table = new JTable(tableModel);
        table.setRowHeight(48);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setBackground(new Color(241, 245, 249));
        table.getTableHeader().setForeground(textPrimary);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.setBackground(cardColor);
        table.setSelectionBackground(new Color(239, 246, 255));
        
        // Add custom renderer for action column
        table.getColumn("Aksi").setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                panel.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
                
                JButton editBtn = new JButton("Edit");
                editBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                editBtn.setBackground(accentColor);
                editBtn.setForeground(Color.WHITE);
                editBtn.setBorderPainted(false);
                editBtn.setFocusPainted(false);
                
                JButton deleteBtn = new JButton("Hapus");
                deleteBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                deleteBtn.setBackground(dangerColor);
                deleteBtn.setForeground(Color.WHITE);
                deleteBtn.setBorderPainted(false);
                deleteBtn.setFocusPainted(false);
                
                panel.add(editBtn);
                panel.add(deleteBtn);
                
                return panel;
            }
        });
        
        // Add mouse listener for action buttons
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int column = table.columnAtPoint(e.getPoint());
                
                if (column == 6) { // Action column
                    int userId = (int) table.getValueAt(row, 0);
                    User selectedUser = users.stream()
                        .filter(user -> user.id == userId)
                        .findFirst()
                        .orElse(null);
                    
                    if (selectedUser != null) {
                        // Check if clicked on edit or delete area
                        Rectangle cellRect = table.getCellRect(row, column, false);
                        int clickX = e.getX() - cellRect.x;
                        
                        if (clickX < cellRect.width / 2) {
                            // Edit button clicked
                            showEditUserDialog(selectedUser);
                        } else {
                            // Delete button clicked
                            if (selectedUser.username.equals("admin")) {
                                JOptionPane.showMessageDialog(AdminFrame.this, "User admin tidak dapat dihapus!");
                                return;
                            }
                            
                            int result = JOptionPane.showConfirmDialog(
                                AdminFrame.this,
                                "Apakah Anda yakin ingin menghapus user '" + selectedUser.username + "'?",
                                "Konfirmasi Hapus",
                                JOptionPane.YES_NO_OPTION
                            );
                            
                            if (result == JOptionPane.YES_OPTION) {
                                users.remove(selectedUser);
                                showUserManagement(); // Refresh table
                                JOptionPane.showMessageDialog(AdminFrame.this, "User berhasil dihapus!");
                            }
                        }
                    }
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 1));
        
        userPanel.add(headerPanel, BorderLayout.NORTH);
        userPanel.add(scrollPane, BorderLayout.CENTER);
        
        contentPanel.removeAll();
        contentPanel.add(userPanel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private void showCreateUser() {
        JPanel createPanel = new JPanel(new BorderLayout());
        createPanel.setBackground(secondaryColor);
        createPanel.setBorder(new EmptyBorder(0, 32, 32, 32));
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(cardColor);
        headerPanel.setBorder(new EmptyBorder(24, 24, 24, 24));
        
        JLabel title = new JLabel("Buat User Baru");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(textPrimary);
        
        headerPanel.add(title, BorderLayout.WEST);
        
        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(cardColor);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
            new EmptyBorder(32, 32, 32, 32)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 16, 0);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Username field
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        usernameLabel.setForeground(textPrimary);
        formPanel.add(usernameLabel, gbc);
        
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        JTextField usernameField = new JTextField(20);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usernameField.setPreferredSize(new Dimension(300, 40));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(209, 213, 219), 1),
            new EmptyBorder(8, 12, 8, 12)
        ));
        formPanel.add(usernameField, gbc);
        
        // Full name field
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JLabel fullNameLabel = new JLabel("Nama Lengkap:");
        fullNameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        fullNameLabel.setForeground(textPrimary);
        formPanel.add(fullNameLabel, gbc);
        
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        JTextField fullNameField = new JTextField(20);
        fullNameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        fullNameField.setPreferredSize(new Dimension(300, 40));
        fullNameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(209, 213, 219), 1),
            new EmptyBorder(8, 12, 8, 12)
        ));
        formPanel.add(fullNameField, gbc);
        
        // Role field
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        roleLabel.setForeground(textPrimary);
        formPanel.add(roleLabel, gbc);
        
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        String[] roles = {"kasir", "manager", "admin"};
        JComboBox<String> roleCombo = new JComboBox<>(roles);
        roleCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        roleCombo.setPreferredSize(new Dimension(300, 40));
        roleCombo.setBackground(Color.WHITE);
        formPanel.add(roleCombo, gbc);
        
        // Password field
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        passwordLabel.setForeground(textPrimary);
        formPanel.add(passwordLabel, gbc);
        
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setPreferredSize(new Dimension(300, 40));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(209, 213, 219), 1),
            new EmptyBorder(8, 12, 8, 12)
        ));
        formPanel.add(passwordField, gbc);
        
        // Buttons
        gbc.gridy = 8;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 0));
        buttonPanel.setBackground(cardColor);
        
        JButton saveButton = createModernButton("💾 Simpan", successColor);
        JButton cancelButton = createModernButton("❌ Batal", new Color(107, 114, 128));
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        formPanel.add(buttonPanel, gbc);
        
        // Event listeners
        saveButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String fullName = fullNameField.getText().trim();
            String role = (String) roleCombo.getSelectedItem();
            String password = new String(passwordField.getPassword());
            
            if (username.isEmpty() || fullName.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(AdminFrame.this, "Semua field harus diisi!");
                return;
            }
            
            // Check if username already exists
            boolean usernameExists = users.stream().anyMatch(u -> u.username.equals(username));
            if (usernameExists) {
                JOptionPane.showMessageDialog(AdminFrame.this, "Username sudah digunakan!");
                return;
            }
            
            // Create new user
            int newId = users.stream().mapToInt(u -> u.id).max().orElse(0) + 1;
            User newUser = new User(newId, username, fullName, role, true);
            users.add(newUser);
            
            JOptionPane.showMessageDialog(AdminFrame.this, "User berhasil dibuat!");
            
            // Clear form
            usernameField.setText("");
            fullNameField.setText("");
            passwordField.setText("");
            roleCombo.setSelectedIndex(0);
        });
        
        cancelButton.addActionListener(e -> {
            usernameField.setText("");
            fullNameField.setText("");
            passwordField.setText("");
            roleCombo.setSelectedIndex(0);
        });
        
        createPanel.add(headerPanel, BorderLayout.NORTH);
        createPanel.add(formPanel, BorderLayout.CENTER);
        
        contentPanel.removeAll();
        contentPanel.add(createPanel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private void showSettings() {
        JPanel settingsPanel = new JPanel(new BorderLayout());
        settingsPanel.setBackground(secondaryColor);
        settingsPanel.setBorder(new EmptyBorder(0, 32, 32, 32));
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(cardColor);
        headerPanel.setBorder(new EmptyBorder(24, 24, 24, 24));
        
        JLabel title = new JLabel("Pengaturan Sistem");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(textPrimary);
        
        headerPanel.add(title, BorderLayout.WEST);
        
        // Settings content
        JPanel contentPanel = new JPanel(new GridLayout(0, 1, 0, 16));
        contentPanel.setBackground(cardColor);
        contentPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
            new EmptyBorder(32, 32, 32, 32)
        ));
        
        // Restaurant Info
        JPanel restaurantPanel = createSettingsSection("🏪 Informasi Restoran");
        restaurantPanel.add(createSettingsItem("Nama Restoran", "Dapur Arunika"));
        restaurantPanel.add(createSettingsItem("Alamat", "Jl. Merdeka No. 123, Jakarta"));
        restaurantPanel.add(createSettingsItem("Telepon", "(021) 12345678"));
        contentPanel.add(restaurantPanel);
        
        // System Settings
        JPanel systemPanel = createSettingsSection("⚙️ Pengaturan Sistem");
        systemPanel.add(createSettingsItem("Zona Waktu", "Asia/Jakarta"));
        systemPanel.add(createSettingsItem("Format Mata Uang", "IDR (Rupiah)"));
        systemPanel.add(createSettingsItem("Bahasa", "Indonesia"));
        contentPanel.add(systemPanel);
        
        // Backup Settings
        JPanel backupPanel = createSettingsSection("💾 Backup & Restore");
        JButton backupButton = createModernButton("🔄 Backup Data", accentColor);
        JButton restoreButton = createModernButton("📥 Restore Data", warningColor);
        
        JPanel backupButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backupButtonPanel.setBackground(cardColor);
        backupButtonPanel.add(backupButton);
        backupButtonPanel.add(restoreButton);
        
        backupPanel.add(backupButtonPanel);
        contentPanel.add(backupPanel);
        
        // Event listeners
        backupButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(AdminFrame.this, "Backup data berhasil disimpan!");
        });
        
        restoreButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(
                AdminFrame.this,
                "Apakah Anda yakin ingin restore data? Data saat ini akan ditimpa.",
                "Konfirmasi Restore",
                JOptionPane.YES_NO_OPTION
            );
            
            if (result == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(AdminFrame.this, "Data berhasil di-restore!");
            }
        });
        
        settingsPanel.add(headerPanel, BorderLayout.NORTH);
        settingsPanel.add(contentPanel, BorderLayout.CENTER);
        
        this.contentPanel.removeAll();
        this.contentPanel.add(settingsPanel);
        this.contentPanel.revalidate();
        this.contentPanel.repaint();
    }
    
    private JPanel createSettingsSection(String title) {
        JPanel section = new JPanel(new BorderLayout());
        section.setBackground(cardColor);
        section.setBorder(new EmptyBorder(16, 0, 16, 0));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(textPrimary);
        titleLabel.setBorder(new EmptyBorder(0, 0, 12, 0));
        
        JPanel contentPanel = new JPanel(new GridLayout(0, 1, 0, 8));
        contentPanel.setBackground(cardColor);
        
        section.add(titleLabel, BorderLayout.NORTH);
        section.add(contentPanel, BorderLayout.CENTER);
        
        return section;
    }
    
    private JPanel createSettingsItem(String label, String value) {
        JPanel item = new JPanel(new BorderLayout());
        item.setBackground(cardColor);
        item.setBorder(new EmptyBorder(8, 16, 8, 16));
        
        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        labelComponent.setForeground(textPrimary);
        
        JLabel valueComponent = new JLabel(value);
        valueComponent.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        valueComponent.setForeground(textSecondary);
        
        item.add(labelComponent, BorderLayout.WEST);
        item.add(valueComponent, BorderLayout.EAST);
        
        return item;
    }
    
    private void showAddMenuDialog() {
        JDialog dialog = new JDialog(this, "Tambah Menu Baru", true);
        dialog.setSize(450, 400);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(cardColor);
        panel.setBorder(new EmptyBorder(24, 24, 24, 24));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 16, 0);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Form fields
        JTextField nameField = createFormField("Nama Menu:", panel, gbc, 0);
        
        gbc.gridy = 2;
        JLabel categoryLabel = new JLabel("Kategori:");
        categoryLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panel.add(categoryLabel, gbc);
        
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        String[] categories = {"Makanan Utama", "Minuman", "Snack", "Dessert"};
        JComboBox<String> categoryCombo = new JComboBox<>(categories);
        categoryCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        categoryCombo.setPreferredSize(new Dimension(300, 40));
        panel.add(categoryCombo, gbc);
        
        JTextField priceField = createFormField("Harga:", panel, gbc, 4);
        JTextField stockField = createFormField("Stok:", panel, gbc, 6);
        
        // Buttons
        gbc.gridy = 8;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(cardColor);
        
        JButton saveButton = createModernButton("Simpan", successColor);
        JButton cancelButton = createModernButton("Batal", new Color(107, 114, 128));
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        panel.add(buttonPanel, gbc);
        
        // Event listeners
        saveButton.addActionListener(e -> {
            try {
                String name = nameField.getText().trim();
                String category = (String) categoryCombo.getSelectedItem();
                double price = Double.parseDouble(priceField.getText().trim());
                int stock = Integer.parseInt(stockField.getText().trim());
                
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Nama menu tidak boleh kosong!");
                    return;
                }
                
                int newId = menuItems.stream().mapToInt(m -> m.id).max().orElse(0) + 1;
                MenuItem newItem = new MenuItem(newId, name, category, price, stock, true);
                menuItems.add(newItem);
                
                JOptionPane.showMessageDialog(dialog, "Menu berhasil ditambahkan!");
                dialog.dispose();
                showMenuManagement(); // Refresh table
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Harga dan stok harus berupa angka!");
            }
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    private void showEditMenuDialog(MenuItem item) {
        JDialog dialog = new JDialog(this, "Edit Menu", true);
        dialog.setSize(450, 400);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(cardColor);
        panel.setBorder(new EmptyBorder(24, 24, 24, 24));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 16, 0);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Form fields with current values
        JTextField nameField = createFormField("Nama Menu:", panel, gbc, 0);
        nameField.setText(item.name);
        
        gbc.gridy = 2;
        JLabel categoryLabel = new JLabel("Kategori:");
        categoryLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panel.add(categoryLabel, gbc);
        
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        String[] categories = {"Makanan Utama", "Minuman", "Snack", "Dessert"};
        JComboBox<String> categoryCombo = new JComboBox<>(categories);
        categoryCombo.setSelectedItem(item.category);
        categoryCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        categoryCombo.setPreferredSize(new Dimension(300, 40));
        panel.add(categoryCombo, gbc);
        
        JTextField priceField = createFormField("Harga:", panel, gbc, 4);
        priceField.setText(String.valueOf((int)item.price));
        
        JTextField stockField = createFormField("Stok:", panel, gbc, 6);
        stockField.setText(String.valueOf(item.stock));
        
        // Status checkbox
        gbc.gridy = 8;
        gbc.fill = GridBagConstraints.NONE;
        JCheckBox statusCheck = new JCheckBox("Aktif");
        statusCheck.setSelected(item.isActive);
        statusCheck.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        statusCheck.setBackground(cardColor);
        panel.add(statusCheck, gbc);
        
        // Buttons
        gbc.gridy = 9;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(cardColor);
        
        JButton saveButton = createModernButton("Simpan", successColor);
        JButton cancelButton = createModernButton("Batal", new Color(107, 114, 128));
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        panel.add(buttonPanel, gbc);
        
        // Event listeners
        saveButton.addActionListener(e -> {
            try {
                item.name = nameField.getText().trim();
                item.category = (String) categoryCombo.getSelectedItem();
                item.price = Double.parseDouble(priceField.getText().trim());
                item.stock = Integer.parseInt(stockField.getText().trim());
                item.isActive = statusCheck.isSelected();
                
                if (item.name.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Nama menu tidak boleh kosong!");
                    return;
                }
                
                JOptionPane.showMessageDialog(dialog, "Menu berhasil diupdate!");
                dialog.dispose();
                showMenuManagement(); // Refresh table
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Harga dan stok harus berupa angka!");
            }
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    private void showEditUserDialog(User user) {
        JDialog dialog = new JDialog(this, "Edit User", true);
        dialog.setSize(450, 350);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(cardColor);
        panel.setBorder(new EmptyBorder(24, 24, 24, 24));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 16, 0);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Form fields with current values
        JTextField usernameField = createFormField("Username:", panel, gbc, 0);
        usernameField.setText(user.username);
        if (user.username.equals("admin")) {
            usernameField.setEditable(false);
        }
        
        JTextField fullNameField = createFormField("Nama Lengkap:", panel, gbc, 2);
        fullNameField.setText(user.fullName);
        
        gbc.gridy = 4;
        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panel.add(roleLabel, gbc);
        
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        String[] roles = {"kasir", "manager", "admin"};
        JComboBox<String> roleCombo = new JComboBox<>(roles);
        roleCombo.setSelectedItem(user.role);
        roleCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        roleCombo.setPreferredSize(new Dimension(300, 40));
        if (user.username.equals("admin")) {
            roleCombo.setEnabled(false);
        }
        panel.add(roleCombo, gbc);
        
        // Status checkbox
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.NONE;
        JCheckBox statusCheck = new JCheckBox("Aktif");
        statusCheck.setSelected(user.isActive);
        statusCheck.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        statusCheck.setBackground(cardColor);
        if (user.username.equals("admin")) {
            statusCheck.setEnabled(false);
        }
        panel.add(statusCheck, gbc);
        
        // Buttons
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(cardColor);
        
        JButton saveButton = createModernButton("Simpan", successColor);
        JButton cancelButton = createModernButton("Batal", new Color(107, 114, 128));
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        panel.add(buttonPanel, gbc);
        
        // Event listeners
        saveButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String fullName = fullNameField.getText().trim();
            String role = (String) roleCombo.getSelectedItem();
            
            if (username.isEmpty() || fullName.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Username dan nama lengkap tidak boleh kosong!");
                return;
            }
            
            // Check if username already exists (excluding current user)
            boolean usernameExists = users.stream()
                .anyMatch(u -> u.username.equals(username) && u.id != user.id);
            if (usernameExists) {
                JOptionPane.showMessageDialog(dialog, "Username sudah digunakan!");
                return;
            }
            
            user.username = username;
            user.fullName = fullName;
            user.role = role;
            user.isActive = statusCheck.isSelected();
            
            JOptionPane.showMessageDialog(dialog, "User berhasil diupdate!");
            dialog.dispose();
            showUserManagement(); // Refresh table
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    private JTextField createFormField(String labelText, JPanel panel, GridBagConstraints gbc, int row) {
        gbc.gridy = row;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(textPrimary);
        panel.add(label, gbc);
        
        gbc.gridy = row + 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        
        JTextField field = new JTextField(20);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(300, 40));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(209, 213, 219), 1),
            new EmptyBorder(8, 12, 8, 12)
        ));
        panel.add(field, gbc);
        
        return field;
    }
    
    private JButton createModernButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(120, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(backgroundColor.darker());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(backgroundColor);
            }
        });
        
        return button;
    }
    
    private void logout() {
        int result = JOptionPane.showConfirmDialog(
            this,
            "Apakah Anda yakin ingin logout?",
            "Konfirmasi Logout",
            JOptionPane.YES_NO_OPTION
        );
        
        if (result == JOptionPane.YES_OPTION) {
            // Close current window and return to login
            this.dispose();
            // Here you would typically show the login window again
            JOptionPane.showMessageDialog(null, "Logout berhasil. Silakan login kembali.");
            System.exit(0);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Set system look and feel
                UIManager.setLookAndFeel(UIManager.getLookAndFeel());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            new AdminFrame().setVisible(true);
        });
    }
}