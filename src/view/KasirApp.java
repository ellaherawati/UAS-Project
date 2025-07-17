package view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class KasirApp extends JFrame {
    private JPanel mainPanel;
    private JPanel contentPanel;
    private JButton homeButton, paymentButton, reportButton, logoutButton;
    private Color primaryColor = new Color(52, 73, 94);
    private Color secondaryColor = new Color(236, 240, 241);
    private Color accentColor = new Color(41, 128, 185);
    private Color successColor = new Color(39, 174, 96);
    private Color dangerColor = new Color(231, 76, 60);
    
    // Data untuk simulasi
    private List<Order> orders;
    private NumberFormat currencyFormat;
    
    // Inner classes untuk data
    static class Order {
        String id;
        String items;
        double subtotal;
        double discount;
        double tax;
        double total;
        Date date;
        boolean isPaid;
        
        Order(String id, String items, double subtotal, double discount, double tax, boolean isPaid) {
            this.id = id;
            this.items = items;
            this.subtotal = subtotal;
            this.discount = discount;
            this.tax = tax;
            this.total = subtotal - discount + tax;
            this.date = new Date();
            this.isPaid = isPaid;
        }
    }
    
    public KasirApp() {
        currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        initializeData();
        initializeComponents();
        showHomePanel();
    }
    
    private void initializeData() {
        orders = new ArrayList<>();
        // Sample data untuk testing
        orders.add(new Order("ORD001", "Nasi Goreng, Es Teh, Kerupuk", 88000, 10000, 7800, false));
        orders.add(new Order("ORD002", "Rendang, Nasi Putih, Jus Jeruk", 95000, 5000, 9000, false));
        orders.add(new Order("ORD003", "Soto Ayam, Es Kelapa", 60000, 0, 6000, true));
        orders.add(new Order("ORD004", "Ayam Penyet, Teh Tarik", 75000, 7500, 6750, true));
        orders.add(new Order("ORD005", "Bakso, Es Jeruk", 45000, 0, 4500, false));
    }
    
    private void initializeComponents() {
        setTitle("Dapur Arunika - Kasir System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setResizable(true);
        
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
        sidebar.setPreferredSize(new Dimension(250, 800));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new EmptyBorder(20, 0, 20, 0));
        
        // Logo/Title
        JLabel titleLabel = new JLabel("KASIR SYSTEM");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(titleLabel);
        
        sidebar.add(Box.createVerticalStrut(30));
        
        // Menu buttons
        homeButton = createMenuButton("🏠  Dashboard", true);
        paymentButton = createMenuButton("💳  Pembayaran", false);
        reportButton = createMenuButton("📊  Laporan Harian", false);
        
        sidebar.add(homeButton);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(paymentButton);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(reportButton);
        
        // Spacer
        sidebar.add(Box.createVerticalGlue());
        
        // Logout button
        logoutButton = createMenuButton("🚪  Logout", false);
        logoutButton.setBackground(dangerColor);
        sidebar.add(logoutButton);
        
        // Event listeners
        homeButton.addActionListener(e -> {
            setActiveButton(homeButton);
            showHomePanel();
        });
        
        paymentButton.addActionListener(e -> {
            setActiveButton(paymentButton);
            showPaymentPanel();
        });
        
        reportButton.addActionListener(e -> {
            setActiveButton(reportButton);
            showReportPanel();
        });
        
        logoutButton.addActionListener(e -> logout());
        
        return sidebar;
    }
    
    private JButton createMenuButton(String text, boolean isActive) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(isActive ? accentColor : primaryColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(200, 40));
        button.setPreferredSize(new Dimension(200, 40));
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (button.getBackground() != accentColor) {
                    button.setBackground(new Color(68, 89, 114));
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (button.getBackground() != accentColor) {
                    button.setBackground(primaryColor);
                }
            }
        });
        
        return button;
    }
    
    private void setActiveButton(JButton activeButton) {
        // Reset all buttons
        homeButton.setBackground(primaryColor);
        paymentButton.setBackground(primaryColor);
        reportButton.setBackground(primaryColor);
        
        // Set active button
        activeButton.setBackground(accentColor);
    }
    
    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(new EmptyBorder(20, 30, 20, 30));
        
        JLabel headerTitle = new JLabel("Dapur Arunika - Sistem Kasir");
        headerTitle.setFont(new Font("Arial", Font.BOLD, 24));
        headerTitle.setForeground(primaryColor);
        
        // Current time
        JLabel timeLabel = new JLabel(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        timeLabel.setForeground(Color.GRAY);
        
        header.add(headerTitle, BorderLayout.WEST);
        header.add(timeLabel, BorderLayout.EAST);
        
        return header;
    }
    
    private void showHomePanel() {
        JPanel homePanel = new JPanel(new BorderLayout());
        homePanel.setBackground(secondaryColor);
        homePanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        
        // Statistics cards
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 20, 0));
        statsPanel.setBackground(secondaryColor);
        
        // Calculate statistics
        int totalOrders = orders.size();
        int paidOrders = (int) orders.stream().filter(o -> o.isPaid).count();
        int unpaidOrders = totalOrders - paidOrders;
        double totalRevenue = orders.stream().filter(o -> o.isPaid).mapToDouble(o -> o.total).sum();
        
        statsPanel.add(createStatCard("Total Pesanan", String.valueOf(totalOrders), "📋", accentColor));
        statsPanel.add(createStatCard("Terbayar", String.valueOf(paidOrders), "✅", successColor));
        statsPanel.add(createStatCard("Belum Bayar", String.valueOf(unpaidOrders), "⏳", dangerColor));
        statsPanel.add(createStatCard("Total Pendapatan", currencyFormat.format(totalRevenue), "💰", new Color(155, 89, 182)));
        
        // Recent orders table
        JPanel recentPanel = new JPanel(new BorderLayout());
        recentPanel.setBackground(Color.WHITE);
        recentPanel.setBorder(BorderFactory.createTitledBorder("Pesanan Terbaru"));
        
        String[] columns = {"ID Nota", "Item", "Total", "Status"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        
        for (Order order : orders) {
            String status = order.isPaid ? "Terbayar" : "Belum Bayar";
            tableModel.addRow(new Object[]{
                order.id, 
                order.items, 
                currencyFormat.format(order.total), 
                status
            });
        }
        
        JTable table = new JTable(tableModel);
        table.setRowHeight(30);
        table.getTableHeader().setBackground(primaryColor);
        table.getTableHeader().setForeground(Color.WHITE);
        
        recentPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        
        homePanel.add(statsPanel, BorderLayout.NORTH);
        homePanel.add(Box.createVerticalStrut(30));
        homePanel.add(recentPanel, BorderLayout.CENTER);
        
        contentPanel.removeAll();
        contentPanel.add(homePanel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private JPanel createStatCard(String title, String value, String icon, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            new EmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        titleLabel.setForeground(Color.GRAY);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 18));
        valueLabel.setForeground(color);
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setBackground(Color.WHITE);
        textPanel.add(titleLabel, BorderLayout.NORTH);
        textPanel.add(valueLabel, BorderLayout.CENTER);
        
        card.add(iconLabel, BorderLayout.WEST);
        card.add(textPanel, BorderLayout.CENTER);
        
        return card;
    }
    
    private void showPaymentPanel() {
        JPanel paymentPanel = new JPanel(new BorderLayout());
        paymentPanel.setBackground(secondaryColor);
        paymentPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        
        // Payment form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder("Pembayaran"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        // ID Nota input
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("ID Nota:"), gbc);
        
        JTextField idNotaField = new JTextField(15);
        gbc.gridx = 1;
        formPanel.add(idNotaField, gbc);
        
        JButton searchButton = new JButton("Cari");
        searchButton.setBackground(accentColor);
        searchButton.setForeground(Color.WHITE);
        gbc.gridx = 2;
        formPanel.add(searchButton, gbc);
        
        // Order details panel
        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.setBackground(Color.WHITE);
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Detail Pesanan"));
        detailsPanel.setPreferredSize(new Dimension(600, 300));
        
        JTextArea detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        detailsArea.setBackground(new Color(248, 249, 250));
        detailsPanel.add(new JScrollPane(detailsArea), BorderLayout.CENTER);
        
        // Payment buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);
        
        JButton cashButton = new JButton("Bayar Cash");
        cashButton.setBackground(successColor);
        cashButton.setForeground(Color.WHITE);
        cashButton.setPreferredSize(new Dimension(120, 40));
        
        JButton cardButton = new JButton("Bayar Kartu");
        cardButton.setBackground(accentColor);
        cardButton.setForeground(Color.WHITE);
        cardButton.setPreferredSize(new Dimension(120, 40));
        
        JButton cancelButton = new JButton("Batal");
        cancelButton.setBackground(dangerColor);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setPreferredSize(new Dimension(120, 40));
        
        buttonPanel.add(cashButton);
        buttonPanel.add(cardButton);
        buttonPanel.add(cancelButton);
        
        detailsPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Search functionality
        searchButton.addActionListener(e -> {
            String idNota = idNotaField.getText().trim();
            Order order = findOrderById(idNota);
            
            if (order != null) {
                StringBuilder details = new StringBuilder();
                details.append("ID Nota: ").append(order.id).append("\n");
                details.append("Tanggal: ").append(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(order.date)).append("\n");
                details.append("Item: ").append(order.items).append("\n");
                details.append("Subtotal: ").append(currencyFormat.format(order.subtotal)).append("\n");
                details.append("Diskon: ").append(currencyFormat.format(order.discount)).append("\n");
                details.append("Pajak: ").append(currencyFormat.format(order.tax)).append("\n");
                details.append("Total: ").append(currencyFormat.format(order.total)).append("\n");
                details.append("Status: ").append(order.isPaid ? "TERBAYAR" : "BELUM BAYAR").append("\n");
                
                detailsArea.setText(details.toString());
                
                // Enable/disable payment buttons
                cashButton.setEnabled(!order.isPaid);
                cardButton.setEnabled(!order.isPaid);
            } else {
                detailsArea.setText("Nota tidak ditemukan!");
                cashButton.setEnabled(false);
                cardButton.setEnabled(false);
            }
        });
        
        // Payment functionality
        ActionListener paymentListener = e -> {
            String idNota = idNotaField.getText().trim();
            Order order = findOrderById(idNota);
            
            if (order != null && !order.isPaid) {
                order.isPaid = true;
                String paymentMethod = e.getSource() == cashButton ? "CASH" : "KARTU";
                JOptionPane.showMessageDialog(this, 
                    "Pembayaran berhasil!\nMetode: " + paymentMethod + 
                    "\nTotal: " + currencyFormat.format(order.total), 
                    "Pembayaran Berhasil", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Clear form
                idNotaField.setText("");
                detailsArea.setText("");
                cashButton.setEnabled(false);
                cardButton.setEnabled(false);
            }
        };
        
        cashButton.addActionListener(paymentListener);
        cardButton.addActionListener(paymentListener);
        
        cancelButton.addActionListener(e -> {
            idNotaField.setText("");
            detailsArea.setText("");
            cashButton.setEnabled(false);
            cardButton.setEnabled(false);
        });
        
        paymentPanel.add(formPanel, BorderLayout.NORTH);
        paymentPanel.add(detailsPanel, BorderLayout.CENTER);
        
        contentPanel.removeAll();
        contentPanel.add(paymentPanel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private void showReportPanel() {
        JPanel reportPanel = new JPanel(new BorderLayout());
        reportPanel.setBackground(secondaryColor);
        reportPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        
        // Report header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel reportTitle = new JLabel("Laporan Harian - " + new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        reportTitle.setFont(new Font("Arial", Font.BOLD, 18));
        reportTitle.setForeground(primaryColor);
        
        JButton printButton = new JButton("🖨️ Cetak Laporan");
        printButton.setBackground(accentColor);
        printButton.setForeground(Color.WHITE);
        
        headerPanel.add(reportTitle, BorderLayout.WEST);
        headerPanel.add(printButton, BorderLayout.EAST);
        
        // Summary cards
        JPanel summaryPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        summaryPanel.setBackground(secondaryColor);
        
        int totalTransactions = orders.size();
        int paidTransactions = (int) orders.stream().filter(o -> o.isPaid).count();
        double totalRevenue = orders.stream().filter(o -> o.isPaid).mapToDouble(o -> o.total).sum();
        
        summaryPanel.add(createStatCard("Total Transaksi", String.valueOf(totalTransactions), "📊", accentColor));
        summaryPanel.add(createStatCard("Transaksi Terbayar", String.valueOf(paidTransactions), "✅", successColor));
        summaryPanel.add(createStatCard("Total Pendapatan", currencyFormat.format(totalRevenue), "💰", new Color(155, 89, 182)));
        
        // Detailed report table
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createTitledBorder("Detail Transaksi"));
        
        String[] columns = {"ID Nota", "Waktu", "Item", "Subtotal", "Diskon", "Pajak", "Total", "Status"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        
        for (Order order : orders) {
            String status = order.isPaid ? "Terbayar" : "Belum Bayar";
            String time = new SimpleDateFormat("HH:mm").format(order.date);
            
            tableModel.addRow(new Object[]{
                order.id, 
                time,
                order.items, 
                currencyFormat.format(order.subtotal),
                currencyFormat.format(order.discount),
                currencyFormat.format(order.tax),
                currencyFormat.format(order.total), 
                status
            });
        }
        
        JTable table = new JTable(tableModel);
        table.setRowHeight(25);
        table.getTableHeader().setBackground(primaryColor);
        table.getTableHeader().setForeground(Color.WHITE);
        
        // Custom cell renderer for status column
        table.getColumnModel().getColumn(7).setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = new JLabel(value.toString());
                label.setOpaque(true);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                
                if (isSelected) {
                    label.setBackground(table.getSelectionBackground());
                } else {
                    label.setBackground(Color.WHITE);
                }
                
                if ("Terbayar".equals(value.toString())) {
                    label.setForeground(successColor);
                } else {
                    label.setForeground(dangerColor);
                }
                
                return label;
            }
        });
        
        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);
        
        // Print functionality
        printButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, 
                "Laporan berhasil dicetak!\n(Simulasi - fitur cetak tidak diimplementasikan)", 
                "Cetak Laporan", 
                JOptionPane.INFORMATION_MESSAGE);
        });
        
        reportPanel.add(headerPanel, BorderLayout.NORTH);
        reportPanel.add(Box.createVerticalStrut(20));
        reportPanel.add(summaryPanel, BorderLayout.CENTER);
        reportPanel.add(Box.createVerticalStrut(20));
        reportPanel.add(tablePanel, BorderLayout.SOUTH);
        
        contentPanel.removeAll();
        contentPanel.add(reportPanel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private Order findOrderById(String id) {
        return orders.stream()
            .filter(order -> order.id.equalsIgnoreCase(id))
            .findFirst()
            .orElse(null);
    }
    
    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Apakah Anda yakin ingin keluar?", 
            "Konfirmasi Logout", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
            // Kembali ke login atau tutup aplikasi
            System.exit(0);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getLookAndFeel());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            new KasirApp().setVisible(true);
        });
    }
}