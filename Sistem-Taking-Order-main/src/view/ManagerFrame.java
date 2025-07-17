package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.Timer;
import java.util.stream.Collectors;

// Model untuk Item Pesanan
class OrderItem {
    private String nama;
    private int qty;
    private double harga;
    
    public OrderItem(String nama, int qty, double harga) {
        this.nama = nama;
        this.qty = qty;
        this.harga = harga;
    }
    
    // Getters
    public String getNama() { return nama; }
    public int getQty() { return qty; }
    public double getHarga() { return harga; }
    public double getSubtotal() { return qty * harga; }
}

// Model untuk Pesanan
class Order {
    private String id;
    private String tanggal;
    private String waktu;
    private String customer;
    private List<OrderItem> items;
    private double subtotal;
    private double pajak;
    private double total;
    private String status;
    
    public Order(String id, String tanggal, String waktu, String customer, 
                 List<OrderItem> items, double subtotal, double pajak, double total, String status) {
        this.id = id;
        this.tanggal = tanggal;
        this.waktu = waktu;
        this.customer = customer;
        this.items = items;
        this.subtotal = subtotal;
        this.pajak = pajak;
        this.total = total;
        this.status = status;
    }
    
    // Getters dan Setters
    public String getId() { return id; }
    public String getTanggal() { return tanggal; }
    public String getWaktu() { return waktu; }
    public String getCustomer() { return customer; }
    public List<OrderItem> getItems() { return items; }
    public double getSubtotal() { return subtotal; }
    public double getPajak() { return pajak; }
    public double getTotal() { return total; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

// Model untuk Item Menu Analytics
class MenuAnalytics {
    private String namaMenu;
    private int totalDipesan;
    private int totalDibatalkan;
    private double totalPendapatan;
    private String kategori;
    
    public MenuAnalytics(String namaMenu, int totalDipesan, int totalDibatalkan, double totalPendapatan, String kategori) {
        this.namaMenu = namaMenu;
        this.totalDipesan = totalDipesan;
        this.totalDibatalkan = totalDibatalkan;
        this.totalPendapatan = totalPendapatan;
        this.kategori = kategori;
    }
    
    // Getters
    public String getNamaMenu() { return namaMenu; }
    public int getTotalDipesan() { return totalDipesan; }
    public int getTotalDibatalkan() { return totalDibatalkan; }
    public double getTotalPendapatan() { return totalPendapatan; }
    public String getKategori() { return kategori; }
    public double getPersentaseBatal() { 
        return totalDipesan > 0 ? (double) totalDibatalkan / (totalDipesan + totalDibatalkan) * 100 : 0; 
    }
}

// Database Simulasi yang Diperluas
class OrderDatabase {
    private Map<String, Order> orders;
    private Map<String, MenuAnalytics> menuAnalytics;
    
    public OrderDatabase() {
        initializeData();
        initializeMenuAnalytics();
    }
    
    private void initializeData() {
        orders = new HashMap<>();
        
        // Order 1
        List<OrderItem> items1 = Arrays.asList(
            new OrderItem("Nasi Gudeg", 2, 15000),
            new OrderItem("Es Teh Manis", 2, 5000),
            new OrderItem("Kerupuk", 1, 3000)
        );
        orders.put("NT001", new Order("NT001", "2024-07-16", "10:30", "Ahmad Wijaya", 
                                     items1, 43000, 4300, 47300, "pending"));
        
        // Order 2
        List<OrderItem> items2 = Arrays.asList(
            new OrderItem("Gado-gado", 1, 12000),
            new OrderItem("Lontong", 1, 8000),
            new OrderItem("Es Jeruk", 1, 7000)
        );
        orders.put("NT002", new Order("NT002", "2024-07-16", "11:15", "Siti Nurhaliza", 
                                     items2, 27000, 2700, 29700, "pending"));
        
        // Order 3
        List<OrderItem> items3 = Arrays.asList(
            new OrderItem("Soto Ayam", 1, 18000),
            new OrderItem("Nasi Putih", 1, 5000),
            new OrderItem("Es Campur", 1, 10000)
        );
        orders.put("NT003", new Order("NT003", "2024-07-16", "12:45", "Budi Santoso", 
                                     items3, 33000, 3300, 36300, "paid"));
        
        // Order 4
        List<OrderItem> items4 = Arrays.asList(
            new OrderItem("Nasi Gudeg", 1, 15000),
            new OrderItem("Ayam Goreng", 1, 20000),
            new OrderItem("Es Teh Manis", 1, 5000)
        );
        orders.put("NT004", new Order("NT004", "2024-07-16", "13:20", "Dewi Sartika", 
                                     items4, 40000, 4000, 44000, "paid"));
        
        // Order 5
        List<OrderItem> items5 = Arrays.asList(
            new OrderItem("Soto Ayam", 2, 18000),
            new OrderItem("Nasi Putih", 2, 5000),
            new OrderItem("Es Jeruk", 2, 7000)
        );
        orders.put("NT005", new Order("NT005", "2024-07-16", "14:00", "Rudi Hartono", 
                                     items5, 60000, 6000, 66000, "paid"));
        
        // Order 6
        List<OrderItem> items6 = Arrays.asList(
            new OrderItem("Gado-gado", 1, 12000),
            new OrderItem("Kerupuk", 2, 3000),
            new OrderItem("Es Campur", 1, 10000)
        );
        orders.put("NT006", new Order("NT006", "2024-07-16", "15:30", "Maya Sari", 
                                     items6, 28000, 2800, 30800, "cancelled"));
    }
    
    private void initializeMenuAnalytics() {
        menuAnalytics = new HashMap<>();
        
        // Data analisis menu berdasarkan simulasi data yang lebih lengkap
        menuAnalytics.put("Nasi Gudeg", new MenuAnalytics("Nasi Gudeg", 45, 3, 675000, "Makanan Utama"));
        menuAnalytics.put("Soto Ayam", new MenuAnalytics("Soto Ayam", 38, 2, 684000, "Makanan Utama"));
        menuAnalytics.put("Gado-gado", new MenuAnalytics("Gado-gado", 32, 8, 384000, "Makanan Utama"));
        menuAnalytics.put("Ayam Goreng", new MenuAnalytics("Ayam Goreng", 28, 1, 560000, "Makanan Utama"));
        menuAnalytics.put("Nasi Putih", new MenuAnalytics("Nasi Putih", 42, 0, 210000, "Makanan Pendamping"));
        menuAnalytics.put("Lontong", new MenuAnalytics("Lontong", 15, 12, 120000, "Makanan Pendamping"));
        menuAnalytics.put("Es Teh Manis", new MenuAnalytics("Es Teh Manis", 55, 2, 275000, "Minuman"));
        menuAnalytics.put("Es Jeruk", new MenuAnalytics("Es Jeruk", 35, 5, 245000, "Minuman"));
        menuAnalytics.put("Es Campur", new MenuAnalytics("Es Campur", 22, 8, 220000, "Minuman"));
        menuAnalytics.put("Kerupuk", new MenuAnalytics("Kerupuk", 48, 1, 144000, "Snack"));
        menuAnalytics.put("Pecel Lele", new MenuAnalytics("Pecel Lele", 12, 15, 180000, "Makanan Utama"));
        menuAnalytics.put("Rendang", new MenuAnalytics("Rendang", 8, 18, 200000, "Makanan Utama"));
    }
    
    public Order getOrder(String id) {
        return orders.get(id);
    }
    
    public Collection<Order> getAllOrders() {
        return orders.values();
    }
    
    public Collection<MenuAnalytics> getAllMenuAnalytics() {
        return menuAnalytics.values();
    }
    
    public void updateOrderStatus(String id, String status) {
        Order order = orders.get(id);
        if (order != null) {
            order.setStatus(status);
        }
    }
}

// Main Application Class
public class ManagerFrame extends JFrame {
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private OrderDatabase database;
    private NumberFormat currencyFormat;
    
    // Components
    private JTextField idNotaField;
    private JTextArea orderDetailArea;
    private JButton payButton;
    private JLabel statusLabel;
    private Order selectedOrder;
    
    public ManagerFrame() {
        database = new OrderDatabase();
        currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        
        initializeUI();
        setupEventHandlers();
        setVisible(true);
    }
    
    private void initializeUI() {
        setTitle("Sistem Kasir - Aplikasi Pembayaran dan Laporan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        
        // Create main panel with CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        // Create panels
        JPanel dashboardPanel = createDashboardPanel();
        JPanel paymentPanel = createPaymentPanel();
        JPanel reportPanel = createReportPanel();
        JPanel managerPanel = createManagerPanel();
        
        // Add panels to main panel
        mainPanel.add(dashboardPanel, "DASHBOARD");
        mainPanel.add(paymentPanel, "PAYMENT");
        mainPanel.add(reportPanel, "REPORT");
        mainPanel.add(managerPanel, "MANAGER");
        
        // Create menu bar
        JMenuBar menuBar = createMenuBar();
        setJMenuBar(menuBar);
        
        add(mainPanel);
    }
    
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu mainMenu = new JMenu("Menu");
        
        JMenuItem dashboardItem = new JMenuItem("Dashboard");
        dashboardItem.addActionListener(e -> cardLayout.show(mainPanel, "DASHBOARD"));
        
        JMenuItem paymentItem = new JMenuItem("Pembayaran");
        paymentItem.addActionListener(e -> cardLayout.show(mainPanel, "PAYMENT"));
        
        JMenuItem reportItem = new JMenuItem("Laporan Harian");
        reportItem.addActionListener(e -> {
            cardLayout.show(mainPanel, "REPORT");
            updateReportPanel();
        });
        
        JMenuItem managerItem = new JMenuItem("Dashboard Manager");
        managerItem.addActionListener(e -> {
            cardLayout.show(mainPanel, "MANAGER");
            updateManagerPanel();
        });
        
        mainMenu.add(dashboardItem);
        mainMenu.add(paymentItem);
        mainMenu.add(reportItem);
        mainMenu.add(managerItem);
        
        menuBar.add(mainMenu);
        return menuBar;
    }
    
    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(59, 130, 246));
        headerPanel.setPreferredSize(new Dimension(0, 80));
        JLabel headerLabel = new JLabel("Selamat Datang di Sistem Kasir");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        
        // Center panel with buttons
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        
        // Payment button
        JButton paymentBtn = new JButton("Pembayaran");
        paymentBtn.setPreferredSize(new Dimension(180, 100));
        paymentBtn.setFont(new Font("Arial", Font.BOLD, 16));
        paymentBtn.setBackground(new Color(34, 197, 94));
        paymentBtn.setForeground(Color.WHITE);
        paymentBtn.setFocusPainted(false);
        paymentBtn.addActionListener(e -> cardLayout.show(mainPanel, "PAYMENT"));
        
        // Report button
        JButton reportBtn = new JButton("Laporan Harian");
        reportBtn.setPreferredSize(new Dimension(180, 100));
        reportBtn.setFont(new Font("Arial", Font.BOLD, 16));
        reportBtn.setBackground(new Color(59, 130, 246));
        reportBtn.setForeground(Color.WHITE);
        reportBtn.setFocusPainted(false);
        reportBtn.addActionListener(e -> {
            cardLayout.show(mainPanel, "REPORT");
            updateReportPanel();
        });
        
        // Manager button
        JButton managerBtn = new JButton("Dashboard Manager");
        managerBtn.setPreferredSize(new Dimension(180, 100));
        managerBtn.setFont(new Font("Arial", Font.BOLD, 16));
        managerBtn.setBackground(new Color(147, 51, 234));
        managerBtn.setForeground(Color.WHITE);
        managerBtn.setFocusPainted(false);
        managerBtn.addActionListener(e -> {
            cardLayout.show(mainPanel, "MANAGER");
            updateManagerPanel();
        });
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(paymentBtn, gbc);
        
        gbc.gridx = 1;
        centerPanel.add(reportBtn, gbc);
        
        gbc.gridx = 2;
        centerPanel.add(managerBtn, gbc);
        
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createPaymentPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        // Header with back button
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(34, 197, 94));
        headerPanel.setPreferredSize(new Dimension(0, 60));
        
        // Back button
        JButton backButton = new JButton("← Kembali");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(new Color(220, 38, 127));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        backButton.addActionListener(e -> {
            // Clear form when going back
            idNotaField.setText("");
            orderDetailArea.setText("");
            statusLabel.setText("");
            payButton.setEnabled(false);
            selectedOrder = null;
            cardLayout.show(mainPanel, "DASHBOARD");
        });
        
        JLabel headerLabel = new JLabel("Pembayaran Pesanan");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        headerPanel.add(backButton, BorderLayout.WEST);
        headerPanel.add(headerLabel, BorderLayout.CENTER);
        
        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createTitledBorder("Cari Pesanan"));
        
        JLabel idLabel = new JLabel("ID Nota:");
        idNotaField = new JTextField(15);
        JButton searchButton = new JButton("Cari");
        searchButton.setBackground(new Color(59, 130, 246));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        
        searchPanel.add(idLabel);
        searchPanel.add(idNotaField);
        searchPanel.add(searchButton);
        
        // Order detail panel
        JPanel detailPanel = new JPanel(new BorderLayout());
        detailPanel.setBorder(BorderFactory.createTitledBorder("Detail Pesanan"));
        detailPanel.setBackground(Color.WHITE);
        
        orderDetailArea = new JTextArea(15, 50);
        orderDetailArea.setEditable(false);
        orderDetailArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        orderDetailArea.setBackground(new Color(248, 249, 250));
        JScrollPane scrollPane = new JScrollPane(orderDetailArea);
        
        detailPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Payment panel
        JPanel paymentPanel = new JPanel(new FlowLayout());
        paymentPanel.setBackground(Color.WHITE);
        
        payButton = new JButton("Konfirmasi Pembayaran");
        payButton.setPreferredSize(new Dimension(200, 40));
        payButton.setBackground(new Color(34, 197, 94));
        payButton.setForeground(Color.WHITE);
        payButton.setFont(new Font("Arial", Font.BOLD, 14));
        payButton.setFocusPainted(false);
        payButton.setEnabled(false);
        
        statusLabel = new JLabel("");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 12));
        
        paymentPanel.add(payButton);
        paymentPanel.add(statusLabel);
        
        // Combine panels
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(searchPanel, BorderLayout.NORTH);
        topPanel.add(detailPanel, BorderLayout.CENTER);
        topPanel.add(paymentPanel, BorderLayout.SOUTH);
        
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(topPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createReportPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        // Header with back button
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(59, 130, 246));
        headerPanel.setPreferredSize(new Dimension(0, 60));
        
        // Back button
        JButton backButton = new JButton("← Kembali");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(new Color(220, 38, 127));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "DASHBOARD"));
        
        JLabel headerLabel = new JLabel("Laporan Harian - " + getCurrentDate());
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        headerPanel.add(backButton, BorderLayout.WEST);
        headerPanel.add(headerLabel, BorderLayout.CENTER);
        
        // Statistics panel
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        statsPanel.setBackground(Color.WHITE);
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Create stat cards
        JPanel totalTransactionCard = createStatCard("Total Transaksi", "0", new Color(59, 130, 246));
        JPanel totalRevenueCard = createStatCard("Total Pendapatan", "Rp 0", new Color(34, 197, 94));
        JPanel pendingCard = createStatCard("Pending", "0", new Color(251, 191, 36));
        JPanel totalOrderCard = createStatCard("Total Pesanan", "0", new Color(147, 51, 234));
        
        statsPanel.add(totalTransactionCard);
        statsPanel.add(totalRevenueCard);
        statsPanel.add(pendingCard);
        statsPanel.add(totalOrderCard);
        
        // Table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createTitledBorder("Daftar Transaksi Hari Ini"));
        
        String[] columnNames = {"ID Nota", "Waktu", "Customer", "Total", "Status"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        table.setRowHeight(25);
        table.getTableHeader().setBackground(new Color(243, 244, 246));
        
        JScrollPane tableScrollPane = new JScrollPane(table);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);
        
        // Combine panels
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(statsPanel, BorderLayout.NORTH);
        centerPanel.add(tablePanel, BorderLayout.CENTER);
        
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);
        
        return panel;
    }
    


    private JPanel createManagerPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        // Header with back button
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(147, 51, 234));
        headerPanel.setPreferredSize(new Dimension(0, 60));
        
        // Back button
        JButton backButton = new JButton("← Kembali");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(new Color(220, 38, 127));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "DASHBOARD"));
        
        JLabel headerLabel = new JLabel("Dashboard Manager - Analisis Penjualan");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        headerPanel.add(backButton, BorderLayout.WEST);
        headerPanel.add(headerLabel, BorderLayout.CENTER);
        
        // Main content with tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Tab 1: Sales Summary
        JPanel salesSummaryPanel = createSalesSummaryPanel();
        tabbedPane.addTab("📊 Ringkasan Penjualan", salesSummaryPanel);
        
        // Tab 2: Top Menu
        JPanel topMenuPanel = createTopMenuPanel();
        tabbedPane.addTab("🏆 Menu Terpopuler", topMenuPanel);
        
        // Tab 3: Low Performance Menu
        JPanel lowPerformancePanel = createLowPerformancePanel();
        tabbedPane.addTab("📉 Menu Kurang Diminati", lowPerformancePanel);
        
        // Tab 4: Cancelled Orders
        JPanel cancelledPanel = createCancelledPanel();
        tabbedPane.addTab("❌ Menu Sering Dibatalkan", cancelledPanel);
        
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(tabbedPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createSalesSummaryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Summary cards
        JPanel summaryPanel = new JPanel(new GridLayout(2, 3, 15, 15));
        summaryPanel.setBackground(Color.WHITE);
        
        JPanel totalMenuCard = createStatCard("Total Menu", "12", new Color(59, 130, 246));
        JPanel totalOrdersCard = createStatCard("Total Orders", "335", new Color(34, 197, 94));
        JPanel totalRevenueCard = createStatCard("Total Revenue", "Rp 3,503,000", new Color(147, 51, 234));
        JPanel avgOrderCard = createStatCard("Rata-rata Order", "Rp 10,458", new Color(251, 191, 36));
        JPanel bestSellerCard = createStatCard("Best Seller", "Nasi Gudeg", new Color(239, 68, 68));
        JPanel cancelRateCard = createStatCard("Cancel Rate", "12.5%", new Color(107, 114, 128));
        
        summaryPanel.add(totalMenuCard);
        summaryPanel.add(totalOrdersCard);
        summaryPanel.add(totalRevenueCard);
        summaryPanel.add(avgOrderCard);
        summaryPanel.add(bestSellerCard);
        summaryPanel.add(cancelRateCard);
        
        // Chart area (placeholder)
        JPanel chartPanel = new JPanel(new BorderLayout());
        chartPanel.setBackground(Color.WHITE);
        chartPanel.setBorder(BorderFactory.createTitledBorder("Grafik Penjualan Bulanan"));
        
        JTextArea chartArea = new JTextArea(10, 50);
        chartArea.setEditable(false);
        chartArea.setBackground(new Color(248, 249, 250));
        chartArea.setText("📈 GRAFIK PENJUALAN BULANAN\n\n" +
                         "Juli 2024:\n" +
                         "Minggu 1: Rp 850,000\n" +
                         "Minggu 2: Rp 920,000\n" +
                         "Minggu 3: Rp 1,100,000\n" +
                         "Minggu 4: Rp 633,000 (current)\n\n" +
                         "Trend: ↗️ Peningkatan 15% dari bulan lalu\n" +
                         "Peak Hours: 12:00-14:00 dan 18:00-20:00");
        
        chartPanel.add(new JScrollPane(chartArea), BorderLayout.CENTER);
        
        panel.add(summaryPanel, BorderLayout.NORTH);
        panel.add(chartPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createTopMenuPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Table for top menu
        String[] columnNames = {"Rank", "Nama Menu", "Kategori", "Total Dipesan", "Pendapatan", "Persentase"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        table.setRowHeight(30);
        table.getTableHeader().setBackground(new Color(34, 197, 94));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        // Custom renderer for ranking
        table.getColumn("Rank").setCellRenderer(new RankCellRenderer());
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("🏆 Menu Terpopuler (Top 10)"));
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createLowPerformancePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Table for low performance menu
        String[] columnNames = {"Nama Menu", "Kategori", "Total Dipesan", "Pendapatan", "Status", "Rekomendasi"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        table.setRowHeight(35);
        table.getTableHeader().setBackground(new Color(239, 68, 68));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        // Custom renderer for status
        table.getColumn("Status").setCellRenderer(new StatusCellRenderer());
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("📉 Menu Kurang Diminati (Perlu Perhatian)"));
        
        // Action panel
        JPanel actionPanel = new JPanel(new FlowLayout());
        actionPanel.setBackground(Color.WHITE);
        
        JButton analyzeButton = new JButton("🔍 Analisis Mendalam");
        analyzeButton.setBackground(new Color(59, 130, 246));
        analyzeButton.setForeground(Color.WHITE);
        analyzeButton.setFont(new Font("Arial", Font.BOLD, 12));
        analyzeButton.setFocusPainted(false);
        
        JButton recommendationButton = new JButton("💡 Saran Perbaikan");
        recommendationButton.setBackground(new Color(147, 51, 234));
        recommendationButton.setForeground(Color.WHITE);
        recommendationButton.setFont(new Font("Arial", Font.BOLD, 12));
        recommendationButton.setFocusPainted(false);
        
        actionPanel.add(analyzeButton);
        actionPanel.add(recommendationButton);
        
        // Info panel
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createTitledBorder("📊 Analisis & Rekomendasi"));
        
        JTextArea infoArea = new JTextArea(8, 50);
        infoArea.setEditable(false);
        infoArea.setBackground(new Color(254, 242, 242));
        infoArea.setFont(new Font("Arial", Font.PLAIN, 12));
        infoArea.setText("🔍 ANALISIS MENU KURANG DIMINATI\n\n" +
                        "Kriteria menu kurang diminati:\n" +
                        "• Total pesanan < 20 per bulan\n" +
                        "• Pendapatan < Rp 200,000\n" +
                        "• Tingkat pembatalan > 30%\n\n" +
                        "💡 REKOMENDASI UMUM:\n" +
                        "• Review harga dan porsi\n" +
                        "• Perbaiki presentasi dan rasa\n" +
                        "• Pertimbangkan promosi khusus\n" +
                        "• Evaluasi waktu penyajian\n" +
                        "• Ganti menu jika perlu");
        
        infoPanel.add(new JScrollPane(infoArea), BorderLayout.CENTER);
        
        // Event handlers
        analyzeButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String menuName = (String) table.getValueAt(selectedRow, 0);
                showDetailedAnalysis(menuName);
            } else {
                JOptionPane.showMessageDialog(panel, "Pilih menu terlebih dahulu untuk analisis!");
            }
        });
        
        recommendationButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String menuName = (String) table.getValueAt(selectedRow, 0);
                showRecommendations(menuName);
            } else {
                JOptionPane.showMessageDialog(panel, "Pilih menu terlebih dahulu untuk rekomendasi!");
            }
        });
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(actionPanel, BorderLayout.SOUTH);
        panel.add(infoPanel, BorderLayout.EAST);
        
        return panel;
    }
    
    private JPanel createCancelledPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Table for cancelled orders
        String[] columnNames = {"Nama Menu", "Total Dipesan", "Total Dibatalkan", "% Pembatalan", "Kerugian", "Tingkat Bahaya"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        table.setRowHeight(35);
        table.getTableHeader().setBackground(new Color(220, 38, 127));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        // Custom renderer for danger level
        table.getColumn("Tingkat Bahaya").setCellRenderer(new DangerLevelCellRenderer());
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("❌ Menu dengan Tingkat Pembatalan Tinggi"));
        
        // Filter panel
        JPanel filterPanel = new JPanel(new FlowLayout());
        filterPanel.setBackground(Color.WHITE);
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filter & Sorting"));
        
        JLabel filterLabel = new JLabel("Filter berdasarkan:");
        JComboBox<String> filterCombo = new JComboBox<>(new String[]{"Semua", "Bahaya Tinggi (>50%)", "Bahaya Sedang (25-50%)", "Bahaya Rendah (<25%)"});
        
        JLabel sortLabel = new JLabel("Urutkan berdasarkan:");
        JComboBox<String> sortCombo = new JComboBox<>(new String[]{"% Pembatalan", "Total Dibatalkan", "Kerugian", "Nama Menu"});
        
        JButton applyFilterButton = new JButton("Terapkan");
        applyFilterButton.setBackground(new Color(59, 130, 246));
        applyFilterButton.setForeground(Color.WHITE);
        applyFilterButton.setFocusPainted(false);
        
        filterPanel.add(filterLabel);
        filterPanel.add(filterCombo);
        filterPanel.add(sortLabel);
        filterPanel.add(sortCombo);
        filterPanel.add(applyFilterButton);
        
        // Action panel
        JPanel actionPanel = new JPanel(new FlowLayout());
        actionPanel.setBackground(Color.WHITE);
        
        JButton investigateButton = new JButton("🔍 Investigasi");
        investigateButton.setBackground(new Color(239, 68, 68));
        investigateButton.setForeground(Color.WHITE);
        investigateButton.setFont(new Font("Arial", Font.BOLD, 12));
        investigateButton.setFocusPainted(false);
        
        JButton actionPlanButton = new JButton("📋 Rencana Tindakan");
        actionPlanButton.setBackground(new Color(147, 51, 234));
        actionPlanButton.setForeground(Color.WHITE);
        actionPlanButton.setFont(new Font("Arial", Font.BOLD, 12));
        actionPlanButton.setFocusPainted(false);
        
        JButton reportButton = new JButton("📊 Laporan Detail");
        reportButton.setBackground(new Color(34, 197, 94));
        reportButton.setForeground(Color.WHITE);
        reportButton.setFont(new Font("Arial", Font.BOLD, 12));
        reportButton.setFocusPainted(false);
        
        actionPanel.add(investigateButton);
        actionPanel.add(actionPlanButton);
        actionPanel.add(reportButton);
        
        // Statistics panel
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        statsPanel.setBackground(Color.WHITE);
        statsPanel.setBorder(BorderFactory.createTitledBorder("Statistik Pembatalan"));
        
        JPanel totalCancelCard = createStatCard("Total Dibatalkan", "74", new Color(239, 68, 68));
        JPanel avgCancelCard = createStatCard("Rata-rata %", "22.1%", new Color(251, 191, 36));
        JPanel lossCard = createStatCard("Total Kerugian", "Rp 1,234,000", new Color(220, 38, 127));
        JPanel criticalCard = createStatCard("Menu Kritis", "3", new Color(107, 114, 128));
        
        statsPanel.add(totalCancelCard);
        statsPanel.add(avgCancelCard);
        statsPanel.add(lossCard);
        statsPanel.add(criticalCard);
        
        // Event handlers
        investigateButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String menuName = (String) table.getValueAt(selectedRow, 0);
                showCancellationInvestigation(menuName);
            } else {
                JOptionPane.showMessageDialog(panel, "Pilih menu terlebih dahulu untuk investigasi!");
            }
        });
        
        actionPlanButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String menuName = (String) table.getValueAt(selectedRow, 0);
                showActionPlan(menuName);
            } else {
                JOptionPane.showMessageDialog(panel, "Pilih menu terlebih dahulu untuk rencana tindakan!");
            }
        });
        
        reportButton.addActionListener(e -> {
            showCancellationReport();
        });
        
        applyFilterButton.addActionListener(e -> {
            String filter = (String) filterCombo.getSelectedItem();
            String sort = (String) sortCombo.getSelectedItem();
            applyCancellationFilter(tableModel, filter, sort);
        });
        
        // Top panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(filterPanel, BorderLayout.NORTH);
        topPanel.add(statsPanel, BorderLayout.CENTER);
        
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(actionPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    // Helper methods for creating stat cards
    private JPanel createStatCard(String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(color);
        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 12));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setForeground(Color.WHITE);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 24));
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        
        return card;
    }
    
    // Custom cell renderers
    private class RankCellRenderer extends JLabel implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value.toString());
            setOpaque(true);
            setHorizontalAlignment(SwingConstants.CENTER);
            setFont(new Font("Arial", Font.BOLD, 14));
            
            int rank = Integer.parseInt(value.toString());
            if (rank == 1) {
                setBackground(new Color(255, 215, 0)); // Gold
                setForeground(Color.BLACK);
                setText("🥇 " + value);
            } else if (rank == 2) {
                setBackground(new Color(192, 192, 192)); // Silver
                setForeground(Color.BLACK);
                setText("🥈 " + value);
            } else if (rank == 3) {
                setBackground(new Color(205, 127, 50)); // Bronze
                setForeground(Color.WHITE);
                setText("🥉 " + value);
            } else {
                setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
                setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());
            }
            
            return this;
        }
    }
    
    private class StatusCellRenderer extends JLabel implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value.toString());
            setOpaque(true);
            setHorizontalAlignment(SwingConstants.CENTER);
            setFont(new Font("Arial", Font.BOLD, 12));
            
            String status = value.toString();
            if (status.contains("Kritis")) {
                setBackground(new Color(239, 68, 68));
                setForeground(Color.WHITE);
                setText("🚨 " + status);
            } else if (status.contains("Perlu Perhatian")) {
                setBackground(new Color(251, 191, 36));
                setForeground(Color.BLACK);
                setText("⚠️ " + status);
            } else {
                setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
                setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());
            }
            
            return this;
        }
    }
    
    private class DangerLevelCellRenderer extends JLabel implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value.toString());
            setOpaque(true);
            setHorizontalAlignment(SwingConstants.CENTER);
            setFont(new Font("Arial", Font.BOLD, 12));
            
            String level = value.toString();
            if (level.contains("Tinggi")) {
                setBackground(new Color(239, 68, 68));
                setForeground(Color.WHITE);
                setText("🔴 " + level);
            } else if (level.contains("Sedang")) {
                setBackground(new Color(251, 191, 36));
                setForeground(Color.BLACK);
                setText("🟡 " + level);
            } else if (level.contains("Rendah")) {
                setBackground(new Color(34, 197, 94));
                setForeground(Color.WHITE);
                setText("🟢 " + level);
            } else {
                setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
                setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());
            }
            
            return this;
        }
    }
    
    // Event handler methods
    private void setupEventHandlers() {
        // Payment panel search functionality
        JPanel paymentPanel = (JPanel) mainPanel.getComponent(1);
        JPanel topPanel = (JPanel) paymentPanel.getComponent(1);
        JPanel searchPanel = (JPanel) topPanel.getComponent(0);
        JButton searchButton = (JButton) searchPanel.getComponent(2);
        
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchOrder();
            }
        });
        
        // Enter key support for search
        idNotaField.addActionListener(e -> searchOrder());
        
        // Payment confirmation
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processPayment();
            }
        });
    }
    
    private void searchOrder() {
        String orderId = idNotaField.getText().trim();
        if (orderId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Masukkan ID Nota terlebih dahulu!");
            return;
        }
        
        Order order = database.getOrder(orderId);
        if (order == null) {
            orderDetailArea.setText("Pesanan tidak ditemukan!");
            payButton.setEnabled(false);
            selectedOrder = null;
            statusLabel.setText("");
            return;
        }
        
        selectedOrder = order;
        displayOrderDetails(order);
        
        if ("paid".equals(order.getStatus())) {
            statusLabel.setText("✅ Sudah Dibayar");
            statusLabel.setForeground(new Color(34, 197, 94));
            payButton.setEnabled(false);
        } else if ("cancelled".equals(order.getStatus())) {
            statusLabel.setText("❌ Dibatalkan");
            statusLabel.setForeground(new Color(239, 68, 68));
            payButton.setEnabled(false);
        } else {
            statusLabel.setText("⏳ Menunggu Pembayaran");
            statusLabel.setForeground(new Color(251, 191, 36));
            payButton.setEnabled(true);
        }
    }
    
    private void displayOrderDetails(Order order) {
        StringBuilder details = new StringBuilder();
        details.append("═══════════════════════════════════════════════════════════════\n");
        details.append("                        DETAIL PESANAN\n");
        details.append("═══════════════════════════════════════════════════════════════\n");
        details.append(String.format("ID Nota    : %s\n", order.getId()));
        details.append(String.format("Tanggal    : %s\n", order.getTanggal()));
        details.append(String.format("Waktu      : %s\n", order.getWaktu()));
        details.append(String.format("Customer   : %s\n", order.getCustomer()));
        details.append(String.format("Status     : %s\n", order.getStatus().toUpperCase()));
        details.append("═══════════════════════════════════════════════════════════════\n");
        details.append("                        ITEM PESANAN\n");
        details.append("═══════════════════════════════════════════════════════════════\n");
        
        for (OrderItem item : order.getItems()) {
            details.append(String.format("%-25s x%2d  %s  %s\n", 
                item.getNama(), 
                item.getQty(), 
                currencyFormat.format(item.getHarga()),
                currencyFormat.format(item.getSubtotal())));
        }
        
        details.append("───────────────────────────────────────────────────────────────\n");
        details.append(String.format("%-35s %s\n", "Subtotal:", currencyFormat.format(order.getSubtotal())));
        details.append(String.format("%-35s %s\n", "Pajak (10%):", currencyFormat.format(order.getPajak())));
        details.append("═══════════════════════════════════════════════════════════════\n");
        details.append(String.format("%-35s %s\n", "TOTAL:", currencyFormat.format(order.getTotal())));
        details.append("═══════════════════════════════════════════════════════════════\n");
        
        orderDetailArea.setText(details.toString());
    }
    
    private void processPayment() {
        if (selectedOrder == null) {
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Konfirmasi pembayaran sebesar " + currencyFormat.format(selectedOrder.getTotal()) + "?",
            "Konfirmasi Pembayaran", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            database.updateOrderStatus(selectedOrder.getId(), "paid");
            statusLabel.setText("✅ Pembayaran Berhasil!");
            statusLabel.setForeground(new Color(34, 197, 94));
            payButton.setEnabled(false);
            
            // Show success message
            JOptionPane.showMessageDialog(this, 
                "Pembayaran berhasil!\nID Nota: " + selectedOrder.getId() + 
                "\nTotal: " + currencyFormat.format(selectedOrder.getTotal()),
                "Pembayaran Berhasil", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private String getCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(new Date());
    }

    private void updateReportPanel() {
        // Update report panel with current data
        JPanel reportPanel = (JPanel) mainPanel.getComponent(2);
        JPanel centerPanel = (JPanel) reportPanel.getComponent(1);
        JPanel statsPanel = (JPanel) centerPanel.getComponent(0);
        JPanel tablePanel = (JPanel) centerPanel.getComponent(1);
        
        // Update statistics
        Collection<Order> orders = database.getAllOrders();
        int totalTransactions = 0;
        double totalRevenue = 0;
        int pendingCount = 0;
        
        for (Order order : orders) {
            if (!"cancelled".equals(order.getStatus())) {
                totalTransactions++;
                if ("paid".equals(order.getStatus())) {
                    totalRevenue += order.getTotal();
                }
            }
            if ("pending".equals(order.getStatus())) {
                pendingCount++;
            }
        }
        
        // Update stat cards
        updateStatCard((JPanel) statsPanel.getComponent(0), "Total Transaksi", String.valueOf(totalTransactions));
        updateStatCard((JPanel) statsPanel.getComponent(1), "Total Pendapatan", currencyFormat.format(totalRevenue));
        updateStatCard((JPanel) statsPanel.getComponent(2), "Pending", String.valueOf(pendingCount));
        updateStatCard((JPanel) statsPanel.getComponent(3), "Total Pesanan", String.valueOf(orders.size()));
        
        // Update table
        JScrollPane scrollPane = (JScrollPane) tablePanel.getComponent(0);
        JTable table = (JTable) scrollPane.getViewport().getView();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        
        for (Order order : orders) {
            model.addRow(new Object[]{
                order.getId(),
                order.getWaktu(),
                order.getCustomer(),
                currencyFormat.format(order.getTotal()),
                order.getStatus().toUpperCase()
            });
        }
    }
    
    private void updateManagerPanel() {
        // Update all manager panels with current data
        updateTopMenuTable();
        updateLowPerformanceTable();
        updateCancelledTable();
    }
    
    private void updateTopMenuTable() {
        JTabbedPane tabbedPane = (JTabbedPane) ((JPanel) mainPanel.getComponent(3)).getComponent(1);
        JPanel topMenuPanel = (JPanel) tabbedPane.getComponent(1);
        JScrollPane scrollPane = (JScrollPane) topMenuPanel.getComponent(0);
        JTable table = (JTable) scrollPane.getViewport().getView();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        
        // Get menu analytics and sort by orders
        List<MenuAnalytics> menuList = new ArrayList<>(database.getAllMenuAnalytics());
        menuList.sort((a, b) -> Integer.compare(b.getTotalDipesan(), a.getTotalDipesan()));
        
        int rank = 1;
        for (MenuAnalytics menu : menuList) {
            if (rank <= 10) { // Top 10 only
                double percentage = (double) menu.getTotalDipesan() / 335 * 100; // Total dari semua orders
                model.addRow(new Object[]{
                    rank,
                    menu.getNamaMenu(),
                    menu.getKategori(),
                    menu.getTotalDipesan(),
                    currencyFormat.format(menu.getTotalPendapatan()),
                    String.format("%.1f%%", percentage)
                });
                rank++;
            }
        }
    }
    
    private void updateLowPerformanceTable() {
        JTabbedPane tabbedPane = (JTabbedPane) ((JPanel) mainPanel.getComponent(3)).getComponent(1);
        JPanel lowPerformancePanel = (JPanel) tabbedPane.getComponent(2);
        JScrollPane scrollPane = (JScrollPane) lowPerformancePanel.getComponent(0);
        JTable table = (JTable) scrollPane.getViewport().getView();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        
        // Get low performance menus
        for (MenuAnalytics menu : database.getAllMenuAnalytics()) {
            if (menu.getTotalDipesan() < 20 || menu.getTotalPendapatan() < 200000) {
                String status = menu.getTotalDipesan() < 10 ? "Kritis" : "Perlu Perhatian";
                String recommendation = getRecommendation(menu);
                
                model.addRow(new Object[]{
                    menu.getNamaMenu(),
                    menu.getKategori(),
                    menu.getTotalDipesan(),
                    currencyFormat.format(menu.getTotalPendapatan()),
                    status,
                    recommendation
                });
            }
        }
    }
    
    private void updateCancelledTable() {
        JTabbedPane tabbedPane = (JTabbedPane) ((JPanel) mainPanel.getComponent(3)).getComponent(1);
        JPanel cancelledPanel = (JPanel) tabbedPane.getComponent(3);
        JScrollPane scrollPane = (JScrollPane) cancelledPanel.getComponent(1);
        JTable table = (JTable) scrollPane.getViewport().getView();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        
        // Get cancelled menu data
        for (MenuAnalytics menu : database.getAllMenuAnalytics()) {
            if (menu.getTotalDibatalkan() > 0) {
                double cancelPercentage = menu.getPersentaseBatal();
                double estimatedLoss = menu.getTotalDibatalkan() * (menu.getTotalPendapatan() / menu.getTotalDipesan());
                
                String dangerLevel;
                if (cancelPercentage > 50) {
                    dangerLevel = "Tinggi";
                } else if (cancelPercentage > 25) {
                    dangerLevel = "Sedang";
                } else {
                    dangerLevel = "Rendah";
                }
                
                model.addRow(new Object[]{
                    menu.getNamaMenu(),
                    menu.getTotalDipesan(),
                    menu.getTotalDibatalkan(),
                    String.format("%.1f%%", cancelPercentage),
                    currencyFormat.format(estimatedLoss),
                    dangerLevel
                });
            }
        }
    }
    
    private String getRecommendation(MenuAnalytics menu) {
        if (menu.getTotalDipesan() < 10) {
            return "Evaluasi total atau ganti menu";
        } else if (menu.getTotalPendapatan() < 150000) {
            return "Review harga atau promosi";
        } else {
            return "Tingkatkan kualitas dan marketing";
        }
    }
    
    private void updateStatCard(JPanel card, String title, String value) {
        JLabel valueLabel = (JLabel) card.getComponent(1);
        valueLabel.setText(value);
    }
    
    // Dialog methods
    private void showDetailedAnalysis(String menuName) {
        JDialog dialog = new JDialog(this, "Analisis Mendalam - " + menuName, true);
        dialog.setSize(600, 500);
        dialog.setLocationRelativeTo(this);
        
        JTextArea analysisArea = new JTextArea();
        analysisArea.setEditable(false);
        analysisArea.setFont(new Font("Arial", Font.PLAIN, 12));
        analysisArea.setText(generateDetailedAnalysis(menuName));
        
        dialog.add(new JScrollPane(analysisArea));
        dialog.setVisible(true);
    }
    
    private void showRecommendations(String menuName) {
        JDialog dialog = new JDialog(this, "Rekomendasi Perbaikan - " + menuName, true);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        
        JTextArea recommendArea = new JTextArea();
        recommendArea.setEditable(false);
        recommendArea.setFont(new Font("Arial", Font.PLAIN, 12));
        recommendArea.setText(generateRecommendations(menuName));
        
        dialog.add(new JScrollPane(recommendArea));
        dialog.setVisible(true);
    }
    
    private void showCancellationInvestigation(String menuName) {
        JDialog dialog = new JDialog(this, "Investigasi Pembatalan - " + menuName, true);
        dialog.setSize(650, 500);
        dialog.setLocationRelativeTo(this);
        
        JTextArea investigationArea = new JTextArea();
        investigationArea.setEditable(false);
        investigationArea.setFont(new Font("Arial", Font.PLAIN, 12));
        investigationArea.setText(generateCancellationInvestigation(menuName));
        
        dialog.add(new JScrollPane(investigationArea));
        dialog.setVisible(true);
    }
    
    // Lanjutan dari kode sebelumnya - melengkapi method yang terpotong

    private void showActionPlan(String menuName) {
        JDialog dialog = new JDialog(this, "Rencana Tindakan - " + menuName, true);
        dialog.setSize(700, 600);
        dialog.setLocationRelativeTo(this);
        
        JTextArea actionPlanArea = new JTextArea();
        actionPlanArea.setEditable(false);
        actionPlanArea.setFont(new Font("Arial", Font.PLAIN, 12));
        actionPlanArea.setText(generateActionPlan(menuName));
        
        dialog.add(new JScrollPane(actionPlanArea));
        dialog.setVisible(true);
    }
    
    private void showCancellationReport() {
        JDialog dialog = new JDialog(this, "Laporan Pembatalan Lengkap", true);
        dialog.setSize(800, 600);
        dialog.setLocationRelativeTo(this);
        
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Tab 1: Summary Report
        JTextArea summaryArea = new JTextArea();
        summaryArea.setEditable(false);
        summaryArea.setFont(new Font("Arial", Font.PLAIN, 12));
        summaryArea.setText(generateCancellationSummary());
        tabbedPane.addTab("📊 Ringkasan", new JScrollPane(summaryArea));
        
        // Tab 2: Chart Analysis
        JPanel chartPanel = createCancellationChart();
        tabbedPane.addTab("📈 Grafik", chartPanel);
        
        // Tab 3: Detailed Analysis
        JTextArea detailArea = new JTextArea();
        detailArea.setEditable(false);
        detailArea.setFont(new Font("Arial", Font.PLAIN, 12));
        detailArea.setText(generateDetailedCancellationAnalysis());
        tabbedPane.addTab("🔍 Analisis Detail", new JScrollPane(detailArea));
        
        dialog.add(tabbedPane);
        dialog.setVisible(true);
    }
    
    // Method untuk filter pembatalan
    private void applyCancellationFilter(DefaultTableModel model, String filter, String sort) {
        model.setRowCount(0);
        
        List<MenuAnalytics> menuList = new ArrayList<>(database.getAllMenuAnalytics());
        
        // Apply filter
        switch(filter) {
            case "Bahaya Tinggi (>50%)":
                menuList = menuList.stream()
                    .filter(m -> m.getPersentaseBatal() > 50)
                    .collect(Collectors.toList());
                break;
            case "Bahaya Sedang (25-50%)":
                menuList = menuList.stream()
                    .filter(m -> m.getPersentaseBatal() >= 25 && m.getPersentaseBatal() <= 50)
                    .collect(Collectors.toList());
                break;
            case "Bahaya Rendah (<25%)":
                menuList = menuList.stream()
                    .filter(m -> m.getPersentaseBatal() < 25)
                    .collect(Collectors.toList());
                break;
        }
        
        // Apply sort
        switch(sort) {
            case "% Pembatalan":
                menuList.sort((a, b) -> Double.compare(b.getPersentaseBatal(), a.getPersentaseBatal()));
                break;
            case "Total Dibatalkan":
                menuList.sort((a, b) -> Integer.compare(b.getTotalDibatalkan(), a.getTotalDibatalkan()));
                break;
            case "Kerugian":
                menuList.sort((a, b) -> Double.compare(
                    b.getTotalDibatalkan() * (b.getTotalPendapatan() / b.getTotalDipesan()),
                    a.getTotalDibatalkan() * (a.getTotalPendapatan() / a.getTotalDipesan())
                ));
                break;
            case "Nama Menu":
                menuList.sort((a, b) -> a.getNamaMenu().compareTo(b.getNamaMenu()));
                break;
        }
        
        // Populate table
        for (MenuAnalytics menu : menuList) {
            if (menu.getTotalDibatalkan() > 0) {
                double cancelPercentage = menu.getPersentaseBatal();
                double estimatedLoss = menu.getTotalDibatalkan() * (menu.getTotalPendapatan() / menu.getTotalDipesan());
                
                String dangerLevel;
                if (cancelPercentage > 50) {
                    dangerLevel = "Tinggi";
                } else if (cancelPercentage > 25) {
                    dangerLevel = "Sedang";
                } else {
                    dangerLevel = "Rendah";
                }
                
                model.addRow(new Object[]{
                    menu.getNamaMenu(),
                    menu.getTotalDipesan(),
                    menu.getTotalDibatalkan(),
                    String.format("%.1f%%", cancelPercentage),
                    currencyFormat.format(estimatedLoss),
                    dangerLevel
                });
            }
        }
    }
    
    // Method untuk membuat panel sales overview
    private JPanel createSalesOverviewPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Header
        JLabel headerLabel = new JLabel("📊 OVERVIEW PENJUALAN");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(new Color(37, 99, 235));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        // Stats cards panel
        JPanel statsPanel = new JPanel(new GridLayout(2, 4, 15, 15));
        statsPanel.setBackground(Color.WHITE);
        
        // Top row stats
        JPanel totalSalesCard = createStatCard("Total Penjualan", "Rp 12,850,000", new Color(34, 197, 94));
        JPanel totalOrdersCard = createStatCard("Total Pesanan", "847", new Color(59, 130, 246));
        JPanel avgOrderCard = createStatCard("Rata-rata Pesanan", "Rp 47,500", new Color(147, 51, 234));
        JPanel bestSellerCard = createStatCard("Best Seller", "Nasi Gudeg", new Color(245, 158, 11));
        
        // Bottom row stats
        JPanel dailyAvgCard = createStatCard("Rata-rata Harian", "Rp 428,333", new Color(16, 185, 129));
        JPanel cancelRateCard = createStatCard("Tingkat Pembatalan", "8.2%", new Color(239, 68, 68));
        JPanel profitMarginCard = createStatCard("Margin Keuntungan", "35%", new Color(99, 102, 241));
        JPanel peakHourCard = createStatCard("Jam Sibuk", "12:00-14:00", new Color(244, 63, 94));
        
        statsPanel.add(totalSalesCard);
        statsPanel.add(totalOrdersCard);
        statsPanel.add(avgOrderCard);
        statsPanel.add(bestSellerCard);
        statsPanel.add(dailyAvgCard);
        statsPanel.add(cancelRateCard);
        statsPanel.add(profitMarginCard);
        statsPanel.add(peakHourCard);
        
        // Sales trend chart panel
        JPanel chartPanel = new JPanel(new BorderLayout());
        chartPanel.setBorder(BorderFactory.createTitledBorder("Tren Penjualan 30 Hari Terakhir"));
        chartPanel.setBackground(Color.WHITE);
        
        // Simple chart representation
        JPanel chartArea = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawSalesChart(g);
            }
        };
        chartArea.setBackground(Color.WHITE);
        chartArea.setPreferredSize(new Dimension(600, 200));
        
        chartPanel.add(chartArea, BorderLayout.CENTER);
        
        // Quick actions panel
        JPanel actionsPanel = new JPanel(new FlowLayout());
        actionsPanel.setBackground(Color.WHITE);
        actionsPanel.setBorder(BorderFactory.createTitledBorder("Aksi Cepat"));
        
        JButton exportButton = new JButton("📤 Export Data");
        exportButton.setBackground(new Color(34, 197, 94));
        exportButton.setForeground(Color.WHITE);
        exportButton.setFont(new Font("Arial", Font.BOLD, 12));
        exportButton.setFocusPainted(false);
        
        JButton printButton = new JButton("🖨️ Print Laporan");
        printButton.setBackground(new Color(59, 130, 246));
        printButton.setForeground(Color.WHITE);
        printButton.setFont(new Font("Arial", Font.BOLD, 12));
        printButton.setFocusPainted(false);
        
        JButton refreshButton = new JButton("🔄 Refresh Data");
        refreshButton.setBackground(new Color(147, 51, 234));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFont(new Font("Arial", Font.BOLD, 12));
        refreshButton.setFocusPainted(false);
        
        actionsPanel.add(exportButton);
        actionsPanel.add(printButton);
        actionsPanel.add(refreshButton);
        
        // Event handlers
        exportButton.addActionListener(e -> exportSalesData());
        printButton.addActionListener(e -> printSalesReport());
        refreshButton.addActionListener(e -> refreshAllData());
        
        // Layout
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.add(headerLabel, BorderLayout.NORTH);
        topPanel.add(statsPanel, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(chartPanel, BorderLayout.CENTER);
        bottomPanel.add(actionsPanel, BorderLayout.SOUTH);
        
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(bottomPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    // Method untuk menggambar chart sederhana
    private void drawSalesChart(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int width = getWidth();
        int height = getHeight();
        int margin = 40;
        
        // Sample data (dalam implementasi nyata, ambil dari database)
        int[] salesData = {450, 520, 380, 610, 720, 590, 480, 680, 750, 820, 
                          640, 580, 720, 890, 760, 680, 590, 720, 850, 920,
                          780, 640, 580, 720, 890, 760, 680, 590, 720, 850};
        
        // Draw axes
        g2d.setColor(Color.GRAY);
        g2d.drawLine(margin, height - margin, width - margin, height - margin); // X-axis
        g2d.drawLine(margin, margin, margin, height - margin); // Y-axis
        
        // Draw chart line
        g2d.setColor(new Color(59, 130, 246));
        g2d.setStroke(new BasicStroke(2));
        
        int pointSpacing = (width - 2 * margin) / (salesData.length - 1);
        int maxValue = Arrays.stream(salesData).max().getAsInt();
        
        for (int i = 0; i < salesData.length - 1; i++) {
            int x1 = margin + i * pointSpacing;
            int y1 = height - margin - (salesData[i] * (height - 2 * margin) / maxValue);
            int x2 = margin + (i + 1) * pointSpacing;
            int y2 = height - margin - (salesData[i + 1] * (height - 2 * margin) / maxValue);
            
            g2d.drawLine(x1, y1, x2, y2);
            
            // Draw points
            g2d.fillOval(x1 - 3, y1 - 3, 6, 6);
        }
        
        // Draw last point
        int lastX = margin + (salesData.length - 1) * pointSpacing;
        int lastY = height - margin - (salesData[salesData.length - 1] * (height - 2 * margin) / maxValue);
        g2d.fillOval(lastX - 3, lastY - 3, 6, 6);
        
        // Add labels
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.PLAIN, 10));
        g2d.drawString("Hari", width - margin - 20, height - margin + 20);
        g2d.drawString("Penjualan", margin - 35, margin - 5);
    }
    
    // Method untuk membuat chart pembatalan
    private JPanel createCancellationChart() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        JPanel chartArea = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawCancellationChart(g);
            }
        };
        chartArea.setBackground(Color.WHITE);
        chartArea.setPreferredSize(new Dimension(600, 400));
        
        panel.add(chartArea, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void drawCancellationChart(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int width = getWidth();
        int height = getHeight();
        int centerX = width / 2;
        int centerY = height / 2;
        int radius = Math.min(width, height) / 3;
        
        // Sample data untuk pie chart
        String[] categories = {"Waktu Lama", "Rasa Kurang", "Salah Pesanan", "Lainnya"};
        double[] values = {35, 28, 22, 15};
        Color[] colors = {
            new Color(239, 68, 68),
            new Color(251, 191, 36),
            new Color(147, 51, 234),
            new Color(107, 114, 128)
        };
        
        // Draw pie chart
        int startAngle = 0;
        for (int i = 0; i < values.length; i++) {
            int arcAngle = (int) (values[i] * 360 / 100);
            g2d.setColor(colors[i]);
            g2d.fillArc(centerX - radius, centerY - radius, radius * 2, radius * 2, startAngle, arcAngle);
            
            // Draw labels
            double labelAngle = Math.toRadians(startAngle + arcAngle / 2);
            int labelX = centerX + (int) ((radius + 50) * Math.cos(labelAngle));
            int labelY = centerY - (int) ((radius + 50) * Math.sin(labelAngle));
            
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 12));
            g2d.drawString(categories[i] + " (" + values[i] + "%)", labelX, labelY);
            
            startAngle += arcAngle;
        }
        
        // Draw title
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString("Alasan Pembatalan Pesanan", centerX - 100, 30);
    }
    
    // Method untuk generate text analysis
    private String generateDetailedAnalysis(String menuName) {
        return "═══════════════════════════════════════════════════════════════\n" +
               "                    ANALISIS MENDALAM\n" +
               "                        " + menuName + "\n" +
               "═══════════════════════════════════════════════════════════════\n\n" +
               "📊 STATISTIK PERFORMANCE:\n" +
               "• Total Dipesan: 15 porsi (30 hari terakhir)\n" +
               "• Pendapatan: Rp 175,000\n" +
               "• Rata-rata per hari: 0.5 porsi\n" +
               "• Tingkat pembatalan: 20%\n" +
               "• Waktu penyajian rata-rata: 18 menit\n\n" +
               "📈 TREN PENJUALAN:\n" +
               "• Minggu 1: 5 porsi\n" +
               "• Minggu 2: 4 porsi\n" +
               "• Minggu 3: 3 porsi\n" +
               "• Minggu 4: 3 porsi\n" +
               "➡️ Tren: MENURUN (-40%)\n\n" +
               "🎯 ANALISIS KOMPETITOR:\n" +
               "• Menu serupa dengan performa lebih baik: Nasi Gudeg Special\n" +
               "• Perbedaan harga: Rp 3,000 lebih murah\n" +
               "• Keunggulan kompetitor: Porsi lebih besar, presentasi lebih menarik\n\n" +
               "⚠️ MASALAH TERIDENTIFIKASI:\n" +
               "• Harga terlalu tinggi untuk porsi yang diberikan\n" +
               "• Kurang promosi dan exposure\n" +
               "• Waktu penyajian relatif lama\n" +
               "• Presentasi kurang menarik\n\n" +
               "🔍 FEEDBACK PELANGGAN:\n" +
               "• 'Rasanya enak tapi harganya kemahalan'\n" +
               "• 'Porsinya kurang untuk harga segitu'\n" +
               "• 'Lama banget keluar makanannya'\n";
    }
    
    private String generateRecommendations(String menuName) {
        return "═══════════════════════════════════════════════════════════════\n" +
               "                    REKOMENDASI PERBAIKAN\n" +
               "                        " + menuName + "\n" +
               "═══════════════════════════════════════════════════════════════\n\n" +
               "🎯 PRIORITAS TINGGI (Segera):\n" +
               "1. PENYESUAIAN HARGA\n" +
               "   • Turunkan harga dari Rp 18,000 menjadi Rp 15,000\n" +
               "   • Atau tingkatkan porsi dengan harga yang sama\n" +
               "   • Target: Meningkatkan daya saing harga\n\n" +
               "2. PERBAIKAN PRESENTASI\n" +
               "   • Gunakan piring yang lebih menarik\n" +
               "   • Tambahkan garnish dan hiasan\n" +
               "   • Susun makanan dengan lebih rapi\n" +
               "   • Target: Meningkatkan visual appeal\n\n" +
               "🎯 PRIORITAS SEDANG (1-2 Minggu):\n" +
               "3. OPTIMASI PROSES MEMASAK\n" +
               "   • Siapkan bahan semi-jadi sebelumnya\n" +
               "   • Streamline proses memasak\n" +
               "   • Target: Kurangi waktu penyajian menjadi 12 menit\n\n" +
               "4. STRATEGI PROMOSI\n" +
               "   • Buat paket combo dengan minuman\n" +
               "   • Promosi 'Buy 2 Get 1 Free' untuk grup\n" +
               "   • Highlight di menu board\n" +
               "   • Target: Meningkatkan awareness dan trial\n\n" +
               "🎯 PRIORITAS RENDAH (Jangka Panjang):\n" +
               "5. PENGEMBANGAN VARIAN\n" +
               "   • Buat varian pedas dan tidak pedas\n" +
               "   • Tambahkan topping opsional\n" +
               "   • Target: Perluas target market\n\n" +
               "📊 TARGET IMPROVEMENT:\n" +
               "• Pesanan per hari: 0.5 → 1.5 porsi\n" +
               "• Tingkat pembatalan: 20% → 10%\n" +
               "• Waktu penyajian: 18 → 12 menit\n" +
               "• Rating pelanggan: 3.2 → 4.0\n\n" +
               "💰 ESTIMASI INVESTASI:\n" +
               "• Pelatihan staff: Rp 200,000\n" +
               "• Peralatan presentasi: Rp 300,000\n" +
               "• Biaya promosi: Rp 500,000\n" +
               "• Total: Rp 1,000,000\n\n" +
               "📈 PROYEKSI ROI:\n" +
               "• Break even: 2 bulan\n" +
               "• Profit tambahan/bulan: Rp 750,000\n" +
               "• ROI: 75% dalam 3 bulan";
    }
    
    private String generateCancellationInvestigation(String menuName) {
        return "═══════════════════════════════════════════════════════════════\n" +
               "                    INVESTIGASI PEMBATALAN\n" +
               "                        " + menuName + "\n" +
               "═══════════════════════════════════════════════════════════════\n\n" +
               "📊 DATA PEMBATALAN:\n" +
               "• Total pesanan: 45\n" +
               "• Total dibatalkan: 18\n" +
               "• Persentase pembatalan: 40%\n" +
               "• Kerugian estimasi: Rp 315,000\n\n" +
               "📅 POLA PEMBATALAN:\n" +
               "• Senin-Jumat: 35% (11 dari 31 pesanan)\n" +
               "• Sabtu-Minggu: 50% (7 dari 14 pesanan)\n" +
               "• Jam 11-13: 45% pembatalan\n" +
               "• Jam 18-20: 35% pembatalan\n\n" +
               "🔍 ANALISIS ALASAN PEMBATALAN:\n" +
               "1. WAKTU TUNGGU TERLALU LAMA (50%)\n" +
               "   • Rata-rata waktu tunggu: 25 menit\n" +
               "   • Pelanggan mengharapkan: 15 menit\n" +
               "   • Penyebab: Proses memasak kompleks\n\n" +
               "2. KEHABISAN BAHAN (28%)\n" +
               "   • Sering terjadi saat weekend\n" +
               "   • Stok tidak diupdate real-time\n" +
               "   • Tidak ada sistem early warning\n\n" +
               "3. PERUBAHAN PESANAN (22%)\n" +
               "   • Pelanggan mengubah pikiran\n" +
               "   • Melihat menu lain yang lebih menarik\n" +
               "   • Pertimbangan harga\n\n" +
               "⚠️ DAMPAK PEMBATALAN:\n" +
               "• Kerugian bahan yang sudah diproses\n" +
               "• Waktu staff terbuang\n" +
               "• Pelanggan tidak puas\n" +
               "• Reputasi menu menurun\n\n" +
               "🎯 REKOMENDASI SEGERA:\n" +
               "1. Implementasi sistem pre-order\n" +
               "2. Update stock real-time\n" +
               "3. Optimasi proses memasak\n" +
               "4. Training staff tentang time management\n" +
               "5. Buat sistem kompensasi untuk pembatalan\n\n" +
               "📈 TARGET PERBAIKAN:\n" +
               "• Kurangi tingkat pembatalan menjadi <15%\n" +
               "• Waktu penyajian maksimal 18 menit\n" +
               "• Zero stock-out untuk menu populer\n" +
               "• Tingkatkan customer satisfaction score";
    }
    
    private String generateActionPlan(String menuName) {
        return "═══════════════════════════════════════════════════════════════\n" +
               "                    RENCANA TINDAKAN\n" +
               "                        " + menuName + "\n" +
               "═══════════════════════════════════════════════════════════════\n\n" +
               "🎯 MINGGU 1: PERBAIKAN SEGERA\n" +
               "Hari 1-2:\n" +
               "• ✅ Analisis mendalam proses memasak\n" +
               "• ✅ Identifikasi bottleneck dalam preparation\n" +
               "• ✅ Meeting dengan kitchen staff\n\n" +
               "Hari 3-4:\n" +
               "• 🔄 Implementasi prep work untuk bahan\n" +
               "• 🔄 Revisi SOP memasak\n" +
               "• 🔄 Testing waktu penyajian baru\n\n" +
               "Hari 5-7:\n" +
               "• 📋 Training staff dengan SOP baru\n" +
               "• 📋 Monitoring performance harian\n" +
               "• 📋 Evaluasi feedback pelanggan\n\n" +
               "🎯 MINGGU 2: OPTIMASI OPERASIONAL\n" +
               "• Update sistem inventory management\n" +
               "• Implementasi early warning stock\n" +
               "• Perbaikan presentasi dan plating\n" +
               "• Launch promosi soft opening\n\n" +
               "🎯 MINGGU 3: PROMOSI & MARKETING\n" +
               "• Kampanye 'New & Improved' menu\n" +
               "• Social media content creation\n" +
               "• Customer feedback collection\n" +
               "• Monitoring competitor response\n\n" +
               "🎯 MINGGU 4: EVALUASI & FINE-TUNING\n" +
               "• Analisis performance improvement\n" +
               "• Customer satisfaction survey\n" +
               "• Staff performance evaluation\n" +
               "• Preparation untuk scaling up\n\n" +
               "📊 KPI TARGETS:\n" +
               "• Tingkat pembatalan: 40% → 15%\n" +
               "• Waktu penyajian: 25 → 18 menit\n" +
               "• Pesanan harian: 1.5 → 4 porsi\n" +
               "• Rating pelanggan: 3.0 → 4.2\n\n" +
               "💰 BUDGET ALLOCATION:\n" +
               "• Staff training: Rp 300,000\n" +
               "• Equipment upgrade: Rp 500,000\n" +
               "• Marketing campaign: Rp 400,000\n" +
               "• Contingency: Rp 200,000\n" +
               "• Total: Rp 1,400,000\n\n" +
               "👥 TEAM RESPONSIBILITIES:\n" +
               "• Kitchen Manager: SOP implementation\n" +
               "• Head Chef: Recipe optimization\n" +
               "• Service Staff: Customer feedback\n" +
               "• Manager: Overall coordination & monitoring\n\n" +
               "📈 SUCCESS METRICS:\n" +
               "• Daily sales increase ≥ 150%\n" +
               "• Customer complaints < 5%\n" +
               "• Break-even pada minggu ke-3\n" +
               "• Profit margin improvement ≥ 25%";
    }
    
    // Lanjutan method generateCancellationSummary() yang terputus
    private String generateCancellationSummary() {
        return "═══════════════════════════════════════════════════════════════\n" +
               "                    RINGKASAN PEMBATALAN\n" +
               "                      (30 Hari Terakhir)\n" +
               "═══════════════════════════════════════════════════════════════\n\n" +
               "📊 STATISTIK UMUM:\n" +
               "• Total pesanan: 847\n" +
               "• Total pembatalan: 74\n" +
               "• Tingkat pembatalan: 8.7%\n" +
               "• Kerugian total: Rp 1,234,000\n" +
               "• Rata-rata kerugian per hari: Rp 41,133\n\n" +
               "🔥 TOP 5 MENU DENGAN PEMBATALAN TERTINGGI:\n" +
               "1. Soto Betawi (18 kali - 45% tingkat pembatalan)\n" +
               "2. Rendang Daging (15 kali - 35% tingkat pembatalan)\n" +
               "3. Ayam Bakar (12 kali - 30% tingkat pembatalan)\n" +
               "4. Gado-Gado (10 kali - 25% tingkat pembatalan)\n" +
               "5. Mie Ayam (8 kali - 20% tingkat pembatalan)\n\n" +
               "📈 TREN PEMBATALAN:\n" +
               "• Minggu 1: 22 pembatalan (↑ 15%)\n" +
               "• Minggu 2: 18 pembatalan (↓ 18%)\n" +
               "• Minggu 3: 16 pembatalan (↓ 11%)\n" +
               "• Minggu 4: 18 pembatalan (↑ 13%)\n\n" +
               "⏰ WAKTU PEMBATALAN:\n" +
               "• 11:00-13:00: 45% dari total pembatalan\n" +
               "• 18:00-20:00: 35% dari total pembatalan\n" +
               "• 07:00-09:00: 20% dari total pembatalan\n\n" +
               "🔍 ALASAN PEMBATALAN:\n" +
               "• Waktu tunggu lama: 40%\n" +
               "• Kehabisan bahan: 30%\n" +
               "• Perubahan pesanan: 20%\n" +
               "• Keluhan rasa: 10%\n\n" +
               "⚠️ DAMPAK BISNIS:\n" +
               "• Kehilangan revenue: Rp 1,234,000\n" +
               "• Pemborosan bahan: Rp 456,000\n" +
               "• Waktu staff terbuang: 37 jam\n" +
               "• Potensi kehilangan pelanggan: 25 orang\n\n" +
               "🎯 REKOMENDASI PRIORITAS:\n" +
               "1. Fokus perbaikan pada Soto Betawi\n" +
               "2. Optimasi stock management\n" +
               "3. Peningkatan efisiensi dapur\n" +
               "4. Training customer service\n" +
               "5. Implementasi sistem pre-order";
    }
    
    private String generateDetailedCancellationAnalysis() {
        return "═══════════════════════════════════════════════════════════════\n" +
               "                    ANALISIS DETAIL PEMBATALAN\n" +
               "═══════════════════════════════════════════════════════════════\n\n" +
               "📊 ANALISIS PER KATEGORI MENU:\n\n" +
               "🍲 MAKANAN BERAT:\n" +
               "• Total pesanan: 456\n" +
               "• Pembatalan: 52 (11.4%)\n" +
               "• Menu bermasalah: Soto Betawi, Rendang\n" +
               "• Penyebab utama: Waktu masak lama\n\n" +
               "🍜 MAKANAN RINGAN:\n" +
               "• Total pesanan: 234\n" +
               "• Pembatalan: 15 (6.4%)\n" +
               "• Menu bermasalah: Gado-Gado\n" +
               "• Penyebab utama: Kehabisan sayuran\n\n" +
               "🥤 MINUMAN:\n" +
               "• Total pesanan: 157\n" +
               "• Pembatalan: 7 (4.5%)\n" +
               "• Menu bermasalah: Es Campur\n" +
               "• Penyebab utama: Kehabisan es\n\n" +
               "📅 ANALISIS TEMPORAL:\n\n" +
               "HARI KERJA (Senin-Jumat):\n" +
               "• Pembatalan: 8.2%\n" +
               "• Puncak: 12:00-13:00 (jam makan siang)\n" +
               "• Alasan utama: Waktu tunggu\n\n" +
               "WEEKEND (Sabtu-Minggu):\n" +
               "• Pembatalan: 9.5%\n" +
               "• Puncak: 19:00-20:00 (jam makan malam)\n" +
               "• Alasan utama: Kehabisan stok\n\n" +
               "🎯 PATTERN ANALYSIS:\n" +
               "• Pembatalan meningkat saat hujan (+25%)\n" +
               "• Menu pedas lebih sering dibatalkan\n" +
               "• Pelanggan baru tingkat pembatalan 15%\n" +
               "• Pelanggan reguler tingkat pembatalan 5%\n\n" +
               "💡 INSIGHT PENTING:\n" +
               "• Menu dengan harga >Rp 25,000 lebih sering dibatalkan\n" +
               "• Pembatalan berkorelasi dengan kompleksitas menu\n" +
               "• Staff baru meningkatkan tingkat pembatalan 20%\n" +
               "• Sistem POS down menyebabkan 8 pembatalan\n\n" +
               "🔧 SOLUSI TEKNIS:\n" +
               "1. Implementasi kitchen display system\n" +
               "2. Real-time inventory tracking\n" +
               "3. Automated customer notification\n" +
               "4. Predictive analytics untuk demand\n" +
               "5. Staff scheduling optimization\n\n" +
               "📈 PROYEKSI PERBAIKAN:\n" +
               "• Target pengurangan pembatalan: 50%\n" +
               "• Estimasi peningkatan revenue: Rp 617,000/bulan\n" +
               "• ROI improvement program: 280%\n" +
               "• Timeline implementasi: 6 minggu";
    }
    
    // Method untuk export sales data
    private void exportSalesData() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Export Sales Data");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            
            int result = fileChooser.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                
                // Add .csv extension if not present
                if (!file.getName().toLowerCase().endsWith(".csv")) {
                    file = new File(file.getAbsolutePath() + ".csv");
                }
                
                try (PrintWriter writer = new PrintWriter(file)) {
                    writer.println("Tanggal,Menu,Jumlah Terjual,Pendapatan,Kategori");
                    
                    // Sample data export
                    writer.println("2024-01-15,Nasi Gudeg,25,Rp 275000,Makanan Berat");
                    writer.println("2024-01-15,Soto Betawi,18,Rp 234000,Makanan Berat");
                    writer.println("2024-01-15,Es Teh,35,Rp 140000,Minuman");
                    
                    JOptionPane.showMessageDialog(this, 
                        "Data berhasil diekspor ke: " + file.getAbsolutePath(),
                        "Export Berhasil", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error saat export data: " + e.getMessage(),
                "Export Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Method untuk print sales report
    private void printSalesReport() {
        try {
            JTextArea reportArea = new JTextArea();
            reportArea.setFont(new Font("Monospaced", Font.PLAIN, 10));
            reportArea.setText(generatePrintableReport());
            
            boolean printed = reportArea.print();
            if (printed) {
                JOptionPane.showMessageDialog(this, 
                    "Laporan berhasil dicetak",
                    "Print Berhasil", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error saat print: " + e.getMessage(),
                "Print Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Method untuk generate printable report
    private String generatePrintableReport() {
        StringBuilder report = new StringBuilder();
        report.append("                    LAPORAN PENJUALAN HARIAN\n");
        report.append("                    Warung Makan Sederhana\n");
        report.append("                    Tanggal: ").append(new SimpleDateFormat("dd/MM/yyyy").format(new Date())).append("\n");
        report.append("================================================================\n\n");
        
        report.append("RINGKASAN PENJUALAN:\n");
        report.append("Total Penjualan Hari Ini    : Rp 1,250,000\n");
        report.append("Total Pesanan               : 85 pesanan\n");
        report.append("Rata-rata per Pesanan       : Rp 14,706\n");
        report.append("Tingkat Pembatalan          : 7.2%\n\n");
        
        report.append("TOP 5 MENU TERLARIS:\n");
        report.append("1. Nasi Gudeg          - 25 porsi - Rp 275,000\n");
        report.append("2. Soto Betawi         - 18 porsi - Rp 234,000\n");
        report.append("3. Ayam Bakar          - 15 porsi - Rp 225,000\n");
        report.append("4. Gado-Gado           - 12 porsi - Rp 144,000\n");
        report.append("5. Es Teh              - 35 porsi - Rp 140,000\n\n");
        
        report.append("ANALISIS WAKTU PENJUALAN:\n");
        report.append("07:00-09:00            : 15 pesanan (17.6%)\n");
        report.append("11:00-13:00            : 38 pesanan (44.7%)\n");
        report.append("18:00-20:00            : 25 pesanan (29.4%)\n");
        report.append("Lainnya                : 7 pesanan (8.2%)\n\n");
        
        report.append("================================================================\n");
        report.append("Dicetak pada: ").append(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date())).append("\n");
        
        return report.toString();
    }
    
    // Method untuk refresh all data
    private void refreshAllData() {
        // Simulate data refresh
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setString("Memuat data...");
        
        JDialog progressDialog = new JDialog(this, "Refresh Data", true);
        progressDialog.setSize(300, 100);
        progressDialog.setLocationRelativeTo(this);
        progressDialog.add(progressBar);
        
        SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (int i = 0; i <= 100; i += 10) {
                    Thread.sleep(100);
                    publish(i);
                }
                return null;
            }
            
            @Override
            protected void process(List<Integer> chunks) {
                int progress = chunks.get(chunks.size() - 1);
                progressBar.setValue(progress);
                progressBar.setString("Memuat data... " + progress + "%");
            }
            
            @Override
            protected void done() {
                progressDialog.dispose();
                JOptionPane.showInputDialog(this);
                
                // Refresh the current panel
                refreshCurrentPanel();
            }
        };
        
        worker.execute();
        progressDialog.setVisible(true);
    }
    
    // Method untuk refresh current panel
    private void refreshCurrentPanel() {
        JTabbedPane tabbedPane = null;
        int selectedIndex = tabbedPane.getSelectedIndex();
        
        // Recreate the current panel
        switch (selectedIndex) {
            case 0:
                tabbedPane.setComponentAt(0, createSalesOverviewPanel());
                break;
            case 1:
                tabbedPane.setComponentAt(1, createManagerPanel());
                break;
            case 2:
                tabbedPane.setComponentAt(2, createCancellationChart());
                break;
        }
        
        // Refresh the display
        tabbedPane.revalidate();
        tabbedPane.repaint();
    }
    
    // Method untuk membuat panel menu trending
    private JPanel createTrendingMenuPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Header
        JLabel headerLabel = new JLabel("📈 MENU TRENDING");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerLabel.setForeground(new Color(34, 197, 94));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        // Trending indicators
        JPanel trendingPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        trendingPanel.setBackground(Color.WHITE);
        
        // Rising star
        JPanel risingPanel = createTrendCard("🚀 RISING STAR", "Nasi Gudeg Special", 
            "+150% orders this week", new Color(34, 197, 94));
        
        // Hot trend
        JPanel hotPanel = createTrendCard("🔥 HOT TREND", "Soto Betawi", 
            "Consistent high demand", new Color(245, 158, 11));
        
        // Declining
        JPanel decliningPanel = createTrendCard("📉 NEED ATTENTION", "Rendang Daging", 
            "-25% orders this week", new Color(239, 68, 68));
        
        trendingPanel.add(risingPanel);
        trendingPanel.add(hotPanel);
        trendingPanel.add(decliningPanel);
        
        panel.add(headerLabel, BorderLayout.NORTH);
        panel.add(trendingPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    // Method untuk membuat trend card
    private JPanel createTrendCard(String title, String menuName, String description, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setForeground(color);
        
        JLabel menuLabel = new JLabel(menuName);
        menuLabel.setFont(new Font("Arial", Font.BOLD, 16));
        menuLabel.setForeground(Color.BLACK);
        
        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        descLabel.setForeground(Color.GRAY);
        
        JPanel textPanel = new JPanel(new GridLayout(3, 1, 0, 5));
        textPanel.setBackground(Color.WHITE);
        textPanel.add(titleLabel);
        textPanel.add(menuLabel);
        textPanel.add(descLabel);
        
        card.add(textPanel, BorderLayout.CENTER);
        
        return card;
    }
    
    // Main method untuk menjalankan aplikasi
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getLookAndFeel());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            new ManagerGUI().setVisible(true);
        });
    }
}