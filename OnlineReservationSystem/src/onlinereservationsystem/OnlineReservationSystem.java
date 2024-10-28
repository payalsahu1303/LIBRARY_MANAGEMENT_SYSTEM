package onlinereservationsystem;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class OnlineReservationSystem {
    static Map<String, String> users = new HashMap<>();
    static Connection connection;

    public static void main(String[] args) {
        setupDatabase();
        SwingUtilities.invokeLater(() -> createLoginWindow());
    }

    public static void setupDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/online_reservation_system", "username", "password");

            String userQuery = "SELECT login_id, password FROM users";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(userQuery);
            while (rs.next()) {
                users.put(rs.getString("login_id"), rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createLoginWindow() {
        JFrame loginFrame = new JFrame("Login - Online Reservation System");
        loginFrame.setSize(400, 250);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLayout(new GridBagLayout());
        loginFrame.setLocationRelativeTo(null);

        JLabel userLabel = new JLabel("Login ID:");
        JTextField userField = new JTextField(15);

        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField(15);

        JButton loginButton = new JButton("Login");
        JLabel messageLabel = new JLabel();
        messageLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));

        loginButton.addActionListener(e -> {
            String loginId = userField.getText();
            String password = new String(passField.getPassword());

            if (users.containsKey(loginId) && users.get(loginId).equals(password)) {
                loginFrame.dispose();
                createMainMenu();
            } else {
                messageLabel.setText("Invalid credentials!");
                messageLabel.setForeground(Color.RED);
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0; gbc.gridy = 0; loginFrame.add(userLabel, gbc);
        gbc.gridx = 1; loginFrame.add(userField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; loginFrame.add(passLabel, gbc);
        gbc.gridx = 1; loginFrame.add(passField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; loginFrame.add(loginButton, gbc);
        gbc.gridx = 1; loginFrame.add(messageLabel, gbc);

        loginFrame.getContentPane().setBackground(new Color(240, 240, 240));
        loginFrame.setVisible(true);
    }

    public static void createMainMenu() {
        JFrame mainMenuFrame = new JFrame("Main Menu - Online Reservation System");
        mainMenuFrame.setSize(400, 250);
        mainMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainMenuFrame.setLayout(new GridBagLayout());
        mainMenuFrame.setLocationRelativeTo(null);

        JButton makeReservationButton = new JButton("Make a Reservation");
        JButton cancelReservationButton = new JButton("Cancel a Reservation");
        JButton exitButton = new JButton("Exit");

        styleButton(makeReservationButton);
        styleButton(cancelReservationButton);
        styleButton(exitButton);

        makeReservationButton.addActionListener(e -> createReservationForm());
        cancelReservationButton.addActionListener(e -> createCancellationForm());
        exitButton.addActionListener(e -> {
            try {
                connection.close(); 
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.exit(0);
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; mainMenuFrame.add(makeReservationButton, gbc);
        gbc.gridy = 1; mainMenuFrame.add(cancelReservationButton, gbc);
        gbc.gridy = 2; mainMenuFrame.add(exitButton, gbc);

        mainMenuFrame.getContentPane().setBackground(new Color(240, 240, 240));
        mainMenuFrame.setVisible(true);
    }

    public static void createReservationForm() {
        JFrame reservationFrame = new JFrame("Make a Reservation");
        reservationFrame.setSize(400, 500);
        reservationFrame.setLayout(new GridBagLayout());
        reservationFrame.setLocationRelativeTo(null);

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(15);

        JLabel trainNumberLabel = new JLabel("Train Number:");
        JTextField trainNumberField = new JTextField(15);

        JLabel trainNameLabel = new JLabel("Train Name:");
        JTextField trainNameField = new JTextField(15);

        JLabel classTypeLabel = new JLabel("Class Type:");
        JTextField classTypeField = new JTextField(15);

        JLabel dateOfJourneyLabel = new JLabel("Date of Journey:");
        JTextField dateOfJourneyField = new JTextField(15);

        JLabel fromLabel = new JLabel("From (Place):");
        JTextField fromField = new JTextField(15);

        JLabel toLabel = new JLabel("To (Destination):");
        JTextField toField = new JTextField(15);

        JButton submitButton = new JButton("Submit");
        JLabel messageLabel = new JLabel();
        messageLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));

        submitButton.addActionListener(e -> {
            String name = nameField.getText();
            String trainNumber = trainNumberField.getText();
            String trainName = trainNameField.getText();
            String classType = classTypeField.getText();
            String dateOfJourney = dateOfJourneyField.getText();
            String from = fromField.getText();
            String to = toField.getText();

            String pnr = String.valueOf(System.currentTimeMillis());

            try {
                String insertQuery = "INSERT INTO reservations (pnr, user_id, name, train_number, train_name, class_type, date_of_journey, from_location, to_location) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pstmt = connection.prepareStatement(insertQuery);
                pstmt.setString(1, pnr);
                pstmt.setInt(2, 1); // Assuming user_id is 1 for this example
                pstmt.setString(3, name);
                pstmt.setString(4, trainNumber);
                pstmt.setString(5, trainName);
                pstmt.setString(6, classType);
                pstmt.setString(7, dateOfJourney);
                pstmt.setString(8, from);
                pstmt.setString(9, to);
                pstmt.executeUpdate();

                messageLabel.setText("Reservation successful! PNR: " + pnr);
                messageLabel.setForeground(Color.GREEN);
            } catch (SQLException ex) {
                messageLabel.setText("Error: " + ex.getMessage());
                messageLabel.setForeground(Color.RED);
                ex.printStackTrace();
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0; gbc.gridy = 0; reservationFrame.add(nameLabel, gbc);
        gbc.gridx = 1; reservationFrame.add(nameField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; reservationFrame.add(trainNumberLabel, gbc);
        gbc.gridx = 1; reservationFrame.add(trainNumberField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; reservationFrame.add(trainNameLabel, gbc);
        gbc.gridx = 1; reservationFrame.add(trainNameField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; reservationFrame.add(classTypeLabel, gbc);
        gbc.gridx = 1; reservationFrame.add(classTypeField, gbc);
        gbc.gridx = 0; gbc.gridy = 4; reservationFrame.add(dateOfJourneyLabel, gbc);
        gbc.gridx = 1; reservationFrame.add(dateOfJourneyField, gbc);
        gbc.gridx = 0; gbc.gridy = 5; reservationFrame.add(fromLabel, gbc);
        gbc.gridx = 1; reservationFrame.add(fromField, gbc);
        gbc.gridx = 0; gbc.gridy = 6; reservationFrame.add(toLabel, gbc);
        gbc.gridx = 1; reservationFrame.add(toField, gbc);
        gbc.gridx = 0; gbc.gridy = 7; reservationFrame.add(submitButton, gbc);
        gbc.gridx = 1; reservationFrame.add(messageLabel, gbc);

        reservationFrame.getContentPane().setBackground(new Color(240, 240, 240));
        reservationFrame.setVisible(true);
    }

public static void createCancellationForm() {
        JFrame cancellationFrame = new JFrame("Cancel a Reservation");
        cancellationFrame.setSize(400, 300);
        cancellationFrame.setLayout(new GridBagLayout());
        cancellationFrame.setLocationRelativeTo(null);

        JLabel pnrLabel = new JLabel("PNR Number:");
        JTextField pnrField = new JTextField(15);

        JButton cancelButton = new JButton("Cancel");
        JLabel messageLabel = new JLabel();
        messageLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));

        cancelButton.addActionListener(e -> {
            String pnr = pnrField.getText();

            try {
                String deleteQuery = "DELETE FROM reservations WHERE pnr = ?";
                PreparedStatement pstmt = connection.prepareStatement(deleteQuery);
                pstmt.setString(1, pnr);
                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    messageLabel.setText("Reservation canceled successfully.");
                    messageLabel.setForeground(Color.GREEN);
                } else {
                    messageLabel.setText("No reservation found with this PNR.");
                    messageLabel.setForeground(Color.RED);
                }
            } catch (SQLException ex) {
                messageLabel.setText("Error: " + ex.getMessage());
                messageLabel.setForeground(Color.RED);
                ex.printStackTrace();
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0; gbc.gridy = 0; cancellationFrame.add(pnrLabel, gbc);
        gbc.gridx = 1; cancellationFrame.add(pnrField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; cancellationFrame.add(cancelButton, gbc);
        gbc.gridx = 1; cancellationFrame.add(messageLabel, gbc);

        cancellationFrame.getContentPane().setBackground(new Color(240, 240, 240));
        cancellationFrame.setVisible(true);
    }

    private static void styleButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
    }
}
