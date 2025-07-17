package view;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

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

// Database Simulasi
class OrderDatabase {
    private Map<String, Order> orders;
    
    public OrderDatabase() {
        initializeData();
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
    }
    
    public Order getOrder(String id) {
        return orders.get(id);
    }
    
    public Collection<Order> getAllOrders() {
        return orders.values();
    }
    
    public void updateOrderStatus(String id, String status) {
        Order order = orders.get(id);
        if (order != null) {
            order.setStatus(status);
        }
    }
}

// Main Application Class
public class KasirApp extends JFrame {
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
    
    public KasirApp() {
        database = new OrderDatabase();
        currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        
        initializeUI();
        setupEventHandlers();
        setVisible(true);
    }
    
    private void initializeUI() {
        setTitle("Sistem Kasir - Aplikasi Pembayaran dan Laporan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        
        // Create main panel with CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        // Create panels
        JPanel dashboardPanel = createDashboardPanel();
        JPanel paymentPanel = createPaymentPanel();
        JPanel reportPanel = createReportPanel();
        
        // Add panels to main panel
        mainPanel.add(dashboardPanel, "DASHBOARD");
        mainPanel.add(paymentPanel, "PAYMENT");
        mainPanel.add(reportPanel, "REPORT");
        
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
        
        mainMenu.add(dashboardItem);
        mainMenu.add(paymentItem);
        mainMenu.add(reportItem);
        
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
        paymentBtn.setPreferredSize(new Dimension(200, 100));
        paymentBtn.setFont(new Font("Arial", Font.BOLD, 16));
        paymentBtn.setBackground(new Color(34, 197, 94));
        paymentBtn.setForeground(Color.WHITE);
        paymentBtn.setFocusPainted(false);
        paymentBtn.addActionListener(e -> cardLayout.show(mainPanel, "PAYMENT"));
        
        // Report button
        JButton reportBtn = new JButton("Laporan Harian");
        reportBtn.setPreferredSize(new Dimension(200, 100));
        reportBtn.setFont(new Font("Arial", Font.BOLD, 16));
        reportBtn.setBackground(new Color(59, 130, 246));
        reportBtn.setForeground(Color.WHITE);
        reportBtn.setFocusPainted(false);
        reportBtn.addActionListener(e -> {
            cardLayout.show(mainPanel, "REPORT");
            updateReportPanel();
        });
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(paymentBtn, gbc);
        
        gbc.gridx = 1;
        centerPanel.add(reportBtn, gbc);
        
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
    
    private JPanel createStatCard(String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(color);
        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setForeground(Color.WHITE);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        
        return card;
    }
    
    private void setupEventHandlers() {
        // Search button action
        Component[] components = ((JPanel) mainPanel.getComponent(1)).getComponents();
        JPanel topPanel = (JPanel) components[1];
        JPanel searchPanel = (JPanel) topPanel.getComponent(0);
        JButton searchButton = (JButton) searchPanel.getComponent(2);
        
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchOrder();
            }
        });
        
        // Enter key for search
        idNotaField.addActionListener(e -> searchOrder());
        
        // Payment button action
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processPayment();
            }
        });
    }
    
    private void searchOrder() {
        String idNota = idNotaField.getText().trim().toUpperCase();
        
        if (idNota.isEmpty()) {
            showMessage("Silakan masukkan ID Nota", Color.RED);
            return;
        }
        
        selectedOrder = database.getOrder(idNota);
        
        if (selectedOrder == null) {
            showMessage("ID Nota tidak ditemukan", Color.RED);
            orderDetailArea.setText("");
            payButton.setEnabled(false);
            return;
        }
        
        displayOrderDetails(selectedOrder);
        payButton.setEnabled(selectedOrder.getStatus().equals("pending"));
        showMessage("Pesanan ditemukan", Color.GREEN);
    }
    
    private void displayOrderDetails(Order order) {
        StringBuilder details = new StringBuilder();
        details.append("=================== DETAIL PESANAN ===================\n\n");
        details.append("ID Nota      : ").append(order.getId()).append("\n");
        details.append("Tanggal      : ").append(order.getTanggal()).append("\n");
        details.append("Waktu        : ").append(order.getWaktu()).append("\n");
        details.append("Customer     : ").append(order.getCustomer()).append("\n");
        details.append("Status       : ").append(order.getStatus().equals("paid") ? "Lunas" : "Belum Dibayar").append("\n\n");
        
        details.append("================= ITEMS PESANAN =================\n");
        for (OrderItem item : order.getItems()) {
            details.append(String.format("%-20s x%d  %s\n", 
                item.getNama(), 
                item.getQty(), 
                currencyFormat.format(item.getSubtotal())));
        }
        
        details.append("\n================= RINGKASAN =================\n");
        details.append("Subtotal     : ").append(currencyFormat.format(order.getSubtotal())).append("\n");
        details.append("Pajak (10%)  : ").append(currencyFormat.format(order.getPajak())).append("\n");
        details.append("TOTAL        : ").append(currencyFormat.format(order.getTotal())).append("\n");
        
        orderDetailArea.setText(details.toString());
    }
    
    private void processPayment() {
        if (selectedOrder != null && selectedOrder.getStatus().equals("pending")) {
            database.updateOrderStatus(selectedOrder.getId(), "paid");
            selectedOrder.setStatus("paid");
            
            showMessage("Pembayaran berhasil diproses!", Color.GREEN);
            payButton.setEnabled(false);
            
            // Update display
            displayOrderDetails(selectedOrder);
            
            // Auto clear after 3 seconds
            javax.swing.Timer timer = new javax.swing.Timer(3000, e -> {
                idNotaField.setText("");
                orderDetailArea.setText("");
                statusLabel.setText("");
                selectedOrder = null;
            });
            timer.setRepeats(false);
            timer.start();
        }
    }
    
    private void showMessage(String message, Color color) {
        statusLabel.setText(message);
        statusLabel.setForeground(color);
        
        javax.swing.Timer timer = new javax.swing.Timer(3000, e -> statusLabel.setText(""));
        timer.setRepeats(false);
        timer.start();
    }
    
    private void updateReportPanel() {
        // Get report panel components
        JPanel reportPanel = (JPanel) mainPanel.getComponent(2);
        JPanel centerPanel = (JPanel) reportPanel.getComponent(1);
        JPanel statsPanel = (JPanel) centerPanel.getComponent(0);
        JPanel tablePanel = (JPanel) centerPanel.getComponent(1);
        
        // Calculate statistics
        Collection<Order> allOrders = database.getAllOrders();
        List<Order> todayOrders = new ArrayList<>();
        List<Order> paidOrders = new ArrayList<>();
        double totalRevenue = 0;
        
        for (Order order : allOrders) {
            if (order.getTanggal().equals("2024-07-16")) { // Today's date
                todayOrders.add(order);
                if (order.getStatus().equals("paid")) {
                    paidOrders.add(order);
                    totalRevenue += order.getTotal();
                }
            }
        }
        
        // Update stat cards
        updateStatCard(statsPanel, 0, String.valueOf(paidOrders.size()));
        updateStatCard(statsPanel, 1, currencyFormat.format(totalRevenue));
        updateStatCard(statsPanel, 2, String.valueOf(todayOrders.size() - paidOrders.size()));
        updateStatCard(statsPanel, 3, String.valueOf(todayOrders.size()));
        
        // Update table
        JScrollPane scrollPane = (JScrollPane) tablePanel.getComponent(0);
        JTable table = (JTable) scrollPane.getViewport().getView();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        
        model.setRowCount(0); // Clear existing rows
        
        for (Order order : todayOrders) {
            Object[] row = {
                order.getId(),
                order.getWaktu(),
                order.getCustomer(),
                currencyFormat.format(order.getTotal()),
                order.getStatus().equals("paid") ? "Lunas" : "Belum Dibayar"
            };
            model.addRow(row);
        }
    }
    
    private void updateStatCard(JPanel statsPanel, int index, String value) {
        JPanel card = (JPanel) statsPanel.getComponent(index);
        JLabel valueLabel = (JLabel) card.getComponent(1);
        valueLabel.setText(value);
    }
    
    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(new Date());
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getLookAndFeel());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new KasirApp();
        });
    }
}