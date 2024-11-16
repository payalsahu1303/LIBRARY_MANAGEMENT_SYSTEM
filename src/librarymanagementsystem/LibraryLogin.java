package librarymanagementsystem;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class LibraryLogin {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/library";
    private static final String USER = "root"; 
    private static final String PASSWORD = "Payal@123"; 

    static Map<String, String> users = new HashMap<>(); 
    static Map<String, Book> books = new HashMap<>(); 

    public static void main(String[] args) {
        loadUsersFromDatabase();

        loadBooksFromDatabase();

        setGreenTheme();

        SwingUtilities.invokeLater(() -> createLoginWindow());
    }

    public static void setGreenTheme() {
        UIManager.put("Panel.background", new Color(204, 255, 204)); 
        UIManager.put("Button.background", new Color(102, 204, 102)); 
        UIManager.put("Button.foreground", Color.WHITE); 
        UIManager.put("Button.font", new Font("Arial", Font.BOLD, 14));
        UIManager.put("TextField.border", new LineBorder(new Color(34, 139, 34), 2)); 
        UIManager.put("Label.font", new Font("Arial", Font.PLAIN, 14)); 
        UIManager.put("TextField.font", new Font("Arial", Font.PLAIN, 14)); 
        UIManager.put("Table.font", new Font("Arial", Font.PLAIN, 14)); 
        UIManager.put("Table.background", new Color(204, 255, 204)); 
        UIManager.put("Table.foreground", Color.BLACK); 
        UIManager.put("Table.gridColor", new Color(102, 204, 102)); 
    }

    public static void createLoginWindow() {
        JFrame loginFrame = new JFrame("Login - Library Management System");
        loginFrame.setSize(400, 250); 
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField(20);

        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField(20);

        JButton loginButton = new JButton("Login");
        JLabel messageLabel = new JLabel();

        loginButton.addActionListener(e -> {
            String loginId = userField.getText();
            String password = new String(passField.getPassword());

            if (authenticateUser(loginId, password)) {
                loginFrame.dispose();
                createMainMenu();
            } else {
                messageLabel.setText("Invalid credentials!");
            }
        });

        gbc.anchor = GridBagConstraints.EAST;
        loginFrame.add(userLabel, gbc);

        gbc.gridx++;
        gbc.anchor = GridBagConstraints.WEST;
        loginFrame.add(userField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        loginFrame.add(passLabel, gbc);

        gbc.gridx++;
        gbc.anchor = GridBagConstraints.WEST;
        loginFrame.add(passField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginFrame.add(loginButton, gbc);

        gbc.gridy++;
        loginFrame.add(messageLabel, gbc);
        loginFrame.setLocationRelativeTo(null); // Center the window on the screen

        loginFrame.setVisible(true);
    }

    public static boolean authenticateUser(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void loadUsersFromDatabase() {
        String query = "SELECT username, password FROM users";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                users.put(rs.getString("username"), rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void loadBooksFromDatabase() {
        String query = "SELECT book_id, title, author, available FROM books";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                books.put(rs.getString("book_id"), new Book(
                        rs.getString("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getBoolean("available")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createMainMenu() {
        JFrame mainMenuFrame = new JFrame("Main Menu - Library Management System");
        mainMenuFrame.setSize(400, 300); 
        mainMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainMenuFrame.setLayout(new GridLayout(3, 1, 20, 20)); 

        JButton adminModuleButton = new JButton("Admin Module");
        JButton userModuleButton = new JButton("User Module");
        JButton exitButton = new JButton("Exit");

        adminModuleButton.addActionListener(e -> createAdminModule());
        userModuleButton.addActionListener(e -> createUserModule());
        exitButton.addActionListener(e -> System.exit(0));

        mainMenuFrame.add(adminModuleButton);
        mainMenuFrame.add(userModuleButton);
        mainMenuFrame.add(exitButton);
        mainMenuFrame.setLocationRelativeTo(null); 

        mainMenuFrame.setVisible(true);
    }

    public static void createAdminModule() {
        JFrame adminFrame = new JFrame("Admin Module");
        adminFrame.setSize(400, 300); 
        adminFrame.setLayout(new GridLayout(4, 1, 20, 20));

        JButton addBookButton = new JButton("Add Book");
        JButton removeBookButton = new JButton("Remove Book");
        JButton viewBooksButton = new JButton("View Books");
        JButton exitButton = new JButton("Exit Admin Module");

        addBookButton.addActionListener(e -> addBook());
        removeBookButton.addActionListener(e -> removeBook());
        viewBooksButton.addActionListener(e -> viewBooks());
        exitButton.addActionListener(e -> adminFrame.dispose());

        adminFrame.add(addBookButton);
        adminFrame.add(removeBookButton);
        adminFrame.add(viewBooksButton);
        adminFrame.add(exitButton);
        adminFrame.setLocationRelativeTo(null); 

        adminFrame.setVisible(true);
    }

    public static void createUserModule() {
        JFrame userFrame = new JFrame("User Module");
        userFrame.setSize(400, 300); 
        userFrame.setLayout(new GridLayout(4, 1, 20, 20));

        JButton issueBookButton = new JButton("Issue Book");
        JButton returnBookButton = new JButton("Return Book");
        JButton viewBooksButton = new JButton("View Books");
        JButton exitButton = new JButton("Exit User Module");

        issueBookButton.addActionListener(e -> issueBook());
        returnBookButton.addActionListener(e -> returnBook());
        viewBooksButton.addActionListener(e -> viewBooks());
        exitButton.addActionListener(e -> userFrame.dispose());

        userFrame.add(issueBookButton);
        userFrame.add(returnBookButton);
        userFrame.add(viewBooksButton);
        userFrame.add(exitButton);
        userFrame.setLocationRelativeTo(null); 

        userFrame.setVisible(true);
    }

    public static void addBook() {
        String title = JOptionPane.showInputDialog("Enter Book Title:");
        String author = JOptionPane.showInputDialog("Enter Author Name:");
        String category = JOptionPane.showInputDialog("Enter Category:");

        String query = "INSERT INTO books (title, author, category) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setString(3, category);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Book added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error adding book: " + e.getMessage());
        }
    }

    public static void removeBook() {
        String bookId = JOptionPane.showInputDialog("Enter Book ID to remove:");

        String query = "DELETE FROM books WHERE book_id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, Integer.parseInt(bookId));
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Book removed successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Book ID not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error removing book: " + e.getMessage());
        }
    }

    public static void viewBooks() {
        String[] columnNames = {"ID", "Title", "Author", "Available"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        String query = "SELECT book_id, title, author, available FROM books";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getBoolean("available") ? "Yes" : "No"
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JTable bookTable = new JTable(model);
        bookTable.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(bookTable);
        JFrame viewBooksFrame = new JFrame("View Books");
        viewBooksFrame.setSize(500, 300);
        viewBooksFrame.add(scrollPane);
        viewBooksFrame.setLocationRelativeTo(null); 
        viewBooksFrame.setVisible(true);
    }

    public static void issueBook() {
        String pnr = JOptionPane.showInputDialog("Enter PNR (to issue a book):");
        String bookId = JOptionPane.showInputDialog("Enter Book ID:");

        String query = "INSERT INTO transactions (user_id, book_id, issue_date, due_date) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, Integer.parseInt(pnr)); 
            stmt.setInt(2, Integer.parseInt(bookId));
            stmt.setDate(3, new java.sql.Date(System.currentTimeMillis())); 
            stmt.setDate(4, new java.sql.Date(System.currentTimeMillis() + 604800000L)); 
            stmt.executeUpdate();

            String updateQuery = "UPDATE books SET available = false WHERE book_id = ?";
            try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                updateStmt.setInt(1, Integer.parseInt(bookId));
                updateStmt.executeUpdate();
            }

            JOptionPane.showMessageDialog(null, "Book issued successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error issuing book: " + e.getMessage());
        }
    }

    public static void returnBook() {
        String pnr = JOptionPane.showInputDialog("Enter PNR (to return a book):");

        String query = "UPDATE transactions SET return_date = ?, fine = ? WHERE user_id = ? AND return_date IS NULL";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDate(1, new java.sql.Date(System.currentTimeMillis())); 
            stmt.setDouble(2, 0.0); 
            stmt.setInt(3, Integer.parseInt(pnr));
            int rowsAffected = stmt.executeUpdate();

            String updateQuery = "UPDATE books SET available = true WHERE book_id = (SELECT book_id FROM transactions WHERE user_id = ? AND return_date IS NOT NULL)";
            try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                updateStmt.setInt(1, Integer.parseInt(pnr));
                updateStmt.executeUpdate();
            }

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Book returned successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "PNR not found or no book issued!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error returning book: " + e.getMessage());
        }
    }
}

class Book {
    private String id;
    private String title;
    private String author;
    private boolean available;

    public Book(String id, String title, String author, boolean available) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.available = available;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
