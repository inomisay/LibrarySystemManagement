import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.util.*;

public class LibraryManagementSystem extends JFrame {

	// Attributes
	private static Scanner scanner = new Scanner(System.in);
    private static Admin adminMenu = new Admin();
    private static Staff staffMenu = new Staff();
    private static Member memberMenu = new Member();
    private static Library library = new Library();
    private static CSV_Writer csv_write = new CSV_Writer();

    private String USERNAME = "";
    private boolean loggedInAsAdmin = false;
    private boolean loggedInAsAdminStaff = true;

    private static final long serialVersionUID = 1L;
    
    private JPanel contentPane;
    private JTextField usernameField;
    private JPasswordField passwordField;
    
    /**
     * Launch the application.
     * @throws IOException 
     */ 
	public static void main(String[] args) throws IOException {

		/*
		//Console
		boolean exit = false;
        while (!exit) {
            int role = login();
            switch (role) {
                case 1:
                	adminMenu.adminMenu(); 
                    break;
                case 2:
                    staffMenu.staffMenu();
                    break;
                case 3:
                	memberMenu.memberMenu();
                    break;
                case 4:
                    exit = true;
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        */
        // GUI
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	LibraryManagementSystem frame = new LibraryManagementSystem();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
	
	// Console
	@SuppressWarnings("unused")
	private static int login() {
        int choice = 0;
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.println("\nLogin");
                System.out.println("1. Administrator");
                System.out.println("2. Staff");
                System.out.println("3. Member");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                if (choice >= 1 && choice <= 4) {
                    validInput = true;
                } else {
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input from the scanner
            }
        }
        scanner.nextLine(); // Consume newline character
        return choice;
    }

	// Constructor
    public LibraryManagementSystem() {
        // Call mainMenu method to display the main menu
    	signIn_UpMenu();
    }
    
	// GUI
	/**
     * mainMenu
     */
    private void signIn_UpMenu() {
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 950);
        setResizable(false);
        setLocationRelativeTo(null); // Center the frame on the screen

        contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(new EmptyBorder(2, 2, 2, 2));
        setContentPane(contentPane);

        // Background image label
        JLabel background = new JLabel();
        ImageIcon backgroundImage = new ImageIcon("SignInOut.JPG");
        Image scaledImage = backgroundImage.getImage().getScaledInstance(750, 1000, Image.SCALE_SMOOTH);
        background.setIcon(new ImageIcon(scaledImage));
        contentPane.add(background, BorderLayout.CENTER);

        // Welcome Label
        JLabel lblWelcome = new JLabel("Welcome to the Library Management System");
        lblWelcome.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 25));
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        lblWelcome.setBounds(75, 100, 600, 100);
        lblWelcome.setForeground(Color.BLACK);
        lblWelcome.setOpaque(false);
        lblWelcome.setBackground(new Color(255, 255, 255, 150)); // Set light transparent background
        background.add(lblWelcome, BorderLayout.NORTH);
        
        // Light transparent background for welcome label
        JLabel welcomeBackground = new JLabel();
        welcomeBackground.setOpaque(true);
        welcomeBackground.setBackground(new Color(255, 255, 255, 150));
        welcomeBackground.setBounds(70, 100, 610, 100);
        background.add(welcomeBackground, BorderLayout.CENTER);

        // Light transparent background for sign-in section
        JPanel signInPanel = new JPanel();
        signInPanel.setOpaque(true);
        signInPanel.setBackground(new Color(255, 255, 255, 150));
        signInPanel.setBounds(180, 300, 400, 250);
        signInPanel.setLayout(null);
        background.add(signInPanel);

        // Username Label
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("Monospaced", Font.PLAIN, 16));
        lblUsername.setBounds(50, 30, 100, 30);
        signInPanel.add(lblUsername);

        // Username TextField
        usernameField = new JTextField();
        usernameField.setBounds(160, 30, 200, 30);
        signInPanel.add(usernameField);

        // Password Label
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Monospaced", Font.PLAIN, 16));
        lblPassword.setBounds(50, 80, 100, 30);
        signInPanel.add(lblPassword);

        // Password PasswordField
        passwordField = new JPasswordField();
        passwordField.setBounds(160, 80, 200, 30);
        signInPanel.add(passwordField);

        // Login Buttons
        JButton btnAdmin = new JButton("Sign In as Admin");
        btnAdmin.setFont(new Font("Monospaced", Font.PLAIN, 14));
        btnAdmin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle admin login
                // Retrieve username and password entered by the user
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                
                // Call the authenticate method to check credentials
                boolean authenticated = adminMenu.authenticate(username, password);
                
                if (authenticated) {
                    // User authenticated successfully, proceed with login
                	loggedInAsAdmin = true;
                	loggedInAsAdminStaff = true;
                	USERNAME = username;
                	Admin_StaffMenu(USERNAME);
                } else {
                    // Display a popup message for incorrect credentials
                    JOptionPane.showMessageDialog(null, "Invalid username or password. Please try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                    
                    // Clear the username and password fields
                    usernameField.setText("");
                    passwordField.setText("");
                }
            }
        });
        
        btnAdmin.setBounds(40, 130, 155, 30);
        btnAdmin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signInPanel.add(btnAdmin);

        JButton btnStaff = new JButton("Sign In as Staff");
        btnStaff.setFont(new Font("Monospaced", Font.PLAIN, 14));
        btnStaff.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle staff login
                // Retrieve username and password entered by the user
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                
                // Call the authenticate method to check credentials
                boolean authenticated = staffMenu.authenticate(username, password);
                
                if (authenticated) {
                    // User authenticated successfully, proceed with login
                	loggedInAsAdmin = false;
                	loggedInAsAdminStaff = true;
                	USERNAME = username;
                	Admin_StaffMenu(USERNAME);
                } else {
                    // Display a popup message for incorrect credentials
                    JOptionPane.showMessageDialog(null, "Invalid username or password. Please try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                    
                    // Clear the username and password fields
                    usernameField.setText("");
                    passwordField.setText("");
                }
            }
        });
        btnStaff.setBounds(40, 170, 155, 30);
        btnStaff.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signInPanel.add(btnStaff);

        JButton btnMember = new JButton("Sign In as Member");
        btnMember.setFont(new Font("Monospaced", Font.PLAIN, 14));
        btnMember.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle member login
                // Retrieve username and password entered by the user
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                
                // Call the authenticate method to check credentials
                boolean authenticated = memberMenu.authenticate(username, password);
                
                if (authenticated) {
                    // User authenticated successfully, proceed with login
                	loggedInAsAdminStaff = false;
                	USERNAME = username;
                    MemberMenu(USERNAME);
                } else {
                    // Display a popup message for incorrect credentials
                    JOptionPane.showMessageDialog(null, "Invalid username or password. Please try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                    
                    // Clear the username and password fields
                    usernameField.setText("");
                    passwordField.setText("");
                }
            }
        });
        btnMember.setBounds(210, 130, 160, 30);
        btnMember.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Set cursor to hand icon
        signInPanel.add(btnMember);
        
        // Signup Button
        JButton btnSignup = new JButton("Sign Up");
        btnSignup.setFont(new Font("Monospaced", Font.PLAIN, 14));
        btnSignup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle signup action
                /// Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();

                signUp();
            }
        });
        btnSignup.setBounds(210, 170, 160, 30); // Position it below the "Login as Member" button
        btnSignup.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Set cursor to hand icon
        signInPanel.add(btnSignup);
        
        
        // Contact Admin Button
        JButton btnContactAdmin = new JButton("Trouble Sign In/Up? Click here to contact our Admins");
        btnContactAdmin.setFont(new Font("Monospaced", Font.PLAIN, 14));
        btnContactAdmin.setForeground(Color.BLUE);
        btnContactAdmin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnContactAdmin.setBorderPainted(true); // Remove border
        btnContactAdmin.setContentAreaFilled(false); // Make it transparent
        btnContactAdmin.setFocusPainted(false); // Remove focus border
        btnContactAdmin.setBounds(10, contentPane.getHeight() - 50, 250, 20); // Position on bottom-left corner
        btnContactAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call the method to show admin information page
            	showAdminInformationPage(contentPane);
            }
        });
        contentPane.add(btnContactAdmin, BorderLayout.SOUTH);

        // Make sure to call revalidate and repaint on the content pane
        contentPane.revalidate();
        contentPane.repaint();
    }

    // Admins Info
    private void showAdminInformationPage(Container contentPane) {
        // Clear the content pane
        contentPane.removeAll();
        contentPane.revalidate();
        contentPane.repaint();

        // Create a new background label
        JLabel background = new JLabel();
        ImageIcon backgroundImage = new ImageIcon("Admin_Info.JPG");
        Image scaledImage = backgroundImage.getImage().getScaledInstance(750, 1000, Image.SCALE_SMOOTH);
        background.setIcon(new ImageIcon(scaledImage));
        background.setBounds(0, 0, 750, 1000);
        contentPane.add(background);

        // Welcome Label for admin information
        JLabel lblWelcome = new JLabel("Admin Information");
        lblWelcome.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 25));
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        lblWelcome.setBounds(75, 100, 600, 100);
        lblWelcome.setForeground(Color.BLACK);
        lblWelcome.setOpaque(false);
        lblWelcome.setBackground(new Color(255, 255, 255, 150)); // Set light transparent background
        background.add(lblWelcome);

        // Light transparent background for welcome label
        JLabel welcomeBackground = new JLabel();
        welcomeBackground.setOpaque(true);
        welcomeBackground.setBackground(new Color(255, 255, 255, 150));
        welcomeBackground.setBounds(70, 100, 610, 100);
        background.add(welcomeBackground);

        // Panel for displaying admin information
        JPanel adminInfoPanel = new JPanel();
        adminInfoPanel.setOpaque(true);
        adminInfoPanel.setBackground(new Color(255, 255, 255, 150));
        adminInfoPanel.setLayout(new BorderLayout());
        adminInfoPanel.setBounds(170, 250, 420, 430);
        background.add(adminInfoPanel);

        // Get all admins information
        String adminsInfo = adminMenu.getAllAdminsInfo_GUI(adminMenu.getAdmins());

        // Text area to display admin information
        JTextArea textArea = new JTextArea(adminsInfo);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setEditable(false);
        textArea.setOpaque(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        adminInfoPanel.add(scrollPane, BorderLayout.CENTER);

        // Button to go back to sign-in/sign-up page
        JButton btnBack = new JButton("Back");
        btnBack.setFont(new Font("Monospaced", Font.PLAIN, 14));
        btnBack.setBounds(10, contentPane.getHeight() - 50, 250, 20); // Position on bottom-left corner
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Show the sign-in/sign-up menu again
                // Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();

                signIn_UpMenu();
            }
        });
        background.add(btnBack, BorderLayout.SOUTH);
        contentPane.add(btnBack, BorderLayout.SOUTH);

        // Make the content pane visible
        contentPane.setVisible(true);
    }

    // Signup Member
    private void signUp() {
        // Clear existing content pane
        contentPane.removeAll();
        contentPane.revalidate();
        contentPane.repaint();

        // Create a new background label
        JLabel signUpBackground = new JLabel();
        ImageIcon backgroundImage = new ImageIcon("SignUp.JPG");
        Image scaledImage = backgroundImage.getImage().getScaledInstance(750, 950, Image.SCALE_SMOOTH);
        signUpBackground.setIcon(new ImageIcon(scaledImage));
        signUpBackground.setBounds(0, 0, 750, 950);
        contentPane.add(signUpBackground);

        // Welcome Label for signup
        JLabel lblWelcome = new JLabel("Sign Up");
        lblWelcome.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 25));
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        lblWelcome.setBounds(75, 100, 600, 100);
        lblWelcome.setForeground(Color.BLACK);
        lblWelcome.setOpaque(false);
        lblWelcome.setBackground(new Color(255, 255, 255, 150)); // Set light transparent background
        signUpBackground.add(lblWelcome, BorderLayout.NORTH);

        // Light transparent background for welcome label
        JLabel welcomeBackground = new JLabel();
        welcomeBackground.setOpaque(true);
        welcomeBackground.setBackground(new Color(255, 255, 255, 150));
        welcomeBackground.setBounds(70, 100, 610, 100);
        signUpBackground.add(welcomeBackground, BorderLayout.CENTER);

        // Light transparent background for Signup Panel section
        JPanel signUpPanel = new JPanel();
        signUpPanel.setOpaque(true);
        signUpPanel.setBackground(new Color(255, 255, 255, 150));
        signUpPanel.setBounds(120, 250, 500, 500);
        signUpPanel.setLayout(null);
        signUpBackground.add(signUpPanel);

        // JLabels and JTextFields for user information
        JLabel lblName = new JLabel("Name:");
        lblName.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 18));
        lblName.setBounds(50, 50, 100, 30);
        lblName.setForeground(Color.BLACK);
        signUpPanel.add(lblName);

        JTextField nameField = new JTextField();
        nameField.setFont(new Font("Monospaced", Font.PLAIN, 16));
        nameField.setBounds(150, 50, 300, 30);
        signUpPanel.add(nameField);

        JLabel lblSurname = new JLabel("Surname:");
        lblSurname.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 18));
        lblSurname.setBounds(50, 100, 100, 30);
        lblSurname.setForeground(Color.BLACK);
        signUpPanel.add(lblSurname);

        JTextField surnameField = new JTextField();
        surnameField.setFont(new Font("Monospaced", Font.PLAIN, 16));
        surnameField.setBounds(150, 100, 300, 30);
        signUpPanel.add(surnameField);

        JLabel lblPhoneNumber = new JLabel("Phone Number:");
        lblPhoneNumber.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 18));
        lblPhoneNumber.setBounds(50, 150, 150, 30);
        lblPhoneNumber.setForeground(Color.BLACK);
        signUpPanel.add(lblPhoneNumber);

        JTextField phoneNumberField = new JTextField();
        phoneNumberField.setFont(new Font("Monospaced", Font.PLAIN, 16));
        phoneNumberField.setBounds(200, 150, 250, 30);
        signUpPanel.add(phoneNumberField);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 18));
        lblEmail.setBounds(50, 200, 100, 30);
        lblEmail.setForeground(Color.BLACK);
        signUpPanel.add(lblEmail);

        JTextField emailField = new JTextField();
        emailField.setFont(new Font("Monospaced", Font.PLAIN, 16));
        emailField.setBounds(150, 200, 300, 30);
        signUpPanel.add(emailField);

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 18));
        lblUsername.setBounds(50, 250, 150, 30);
        lblUsername.setForeground(Color.BLACK);
        signUpPanel.add(lblUsername);

        JTextField usernameField = new JTextField();
        usernameField.setFont(new Font("Monospaced", Font.PLAIN, 16));
        usernameField.setBounds(200, 250, 250, 30);
        signUpPanel.add(usernameField);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 18));
        lblPassword.setBounds(50, 300, 150, 30);
        lblPassword.setForeground(Color.BLACK);
        signUpPanel.add(lblPassword);

        JTextField passwordField = new JTextField();
        passwordField.setFont(new Font("Monospaced", Font.PLAIN, 16));
        passwordField.setBounds(200, 300, 250, 30);
        signUpPanel.add(passwordField);

        // JButton for signing up
        JButton btnSignUp = new JButton("Sign Up");
        btnSignUp.setFont(new Font("Monospaced", Font.PLAIN, 16));
        btnSignUp.setBounds(200, 400, 100, 30); // Adjusted position
        btnSignUp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSignUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                // Perform signup operation
                String name = nameField.getText();
                String surname = surnameField.getText();
                String phoneNumber = phoneNumberField.getText();
                String email = emailField.getText();
                String username = usernameField.getText();
                String password = passwordField.getText();
                
                boolean signUpSuccessful = library.addMember_GUI(name, surname, phoneNumber, email, username, password);

                // If signup is successful, you can redirect the user to a success page or perform other actions
                if (signUpSuccessful == true) {
                    // Clear text fields after successful signup
                    nameField.setText("");
                    surnameField.setText("");
                    phoneNumberField.setText("");
                    emailField.setText("");
                    usernameField.setText("");
                    passwordField.setText("");

                    // Inform the user about the confirmation email
                    JOptionPane.showMessageDialog(signUpPanel, "Confirmation will be sent to your email after signing up.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);

                    // Redirect the user to the sign-in/up menu
                    // Clear existing content pane
                    contentPane.removeAll();
                    contentPane.revalidate();
                    contentPane.repaint();

                    signIn_UpMenu();
                } else {
                    // If signup fails, you can inform the user and allow them to retry or take other actions
                    JOptionPane.showMessageDialog(signUpPanel, "Signup failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        signUpPanel.add(btnSignUp);

        // Back to Main Menu Button
        JButton btnBack = new JButton("Back to Main Menu");
        btnBack.setFont(new Font("Monospaced", Font.PLAIN, 16));
        btnBack.setBounds(150, 450, 200, 30); // Adjusted position
        btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();

                signIn_UpMenu();
            }
        });
        signUpPanel.add(btnBack);

        // Make sure to revalidate and repaint the content pane
        contentPane.revalidate();
        contentPane.repaint();
    }
  
    // Change Information Admin/Staff
    private void accountManagement(String username) {
        // Clear existing content pane
        contentPane.removeAll();
        contentPane.revalidate();
        contentPane.repaint();

        // Create a new background label
        JLabel accountManagementBackground = new JLabel();
        ImageIcon backgroundImage;

        // Check if logged in as admin or staff and set the background image accordingly
        if (loggedInAsAdmin == true) {
            backgroundImage = new ImageIcon("Admin.JPG");
        } else {
            backgroundImage = new ImageIcon("Staff.JPG");
        }

        // Scale and set the background image
        Image scaledImage = backgroundImage.getImage().getScaledInstance(750, 950, Image.SCALE_SMOOTH);
        accountManagementBackground.setIcon(new ImageIcon(scaledImage));
        accountManagementBackground.setBounds(0, 0, 750, 950);
        contentPane.add(accountManagementBackground);

        // Welcome Label for Account Management
        JLabel lblWelcome = new JLabel("Account Management");
        lblWelcome.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 25));
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        lblWelcome.setBounds(75, 100, 600, 100);
        lblWelcome.setForeground(Color.BLACK);
        lblWelcome.setOpaque(false);
        lblWelcome.setBackground(new Color(255, 255, 255, 150)); // Set light transparent background
        accountManagementBackground.add(lblWelcome, BorderLayout.NORTH);

        // Light transparent background for welcome label
        JLabel welcomeBackground = new JLabel();
        welcomeBackground.setOpaque(true);
        welcomeBackground.setBackground(new Color(255, 255, 255, 150));
        welcomeBackground.setBounds(70, 100, 610, 100);
        accountManagementBackground.add(welcomeBackground, BorderLayout.CENTER);

        // Light transparent background for Account Information Panel section
        JPanel accountInfoPanel = new JPanel();
        accountInfoPanel.setOpaque(true);
        accountInfoPanel.setBackground(new Color(255, 255, 255, 150));
        accountInfoPanel.setBounds(120, 250, 500, 600);
        accountInfoPanel.setLayout(null);
        accountManagementBackground.add(accountInfoPanel);
        
        // Retrieve account information from the Library 
        String name = "", surname = "", phoneNumber = "", email = "", address = "", password = "";
    	        
        if (loggedInAsAdmin == true) {
        	Admin adminInfo = adminMenu.searchAdminByUsername(USERNAME);
        	name = adminInfo.getName(); 
        	surname = adminInfo.getSurname();
        	phoneNumber = adminInfo.getPhoneNumber();
        	email = adminInfo.getEmail();
        	address = adminInfo.getAddress();
        	password = adminInfo.getPassword();
        	
        } else {
        	Staff staffInfo;
			try {
				staffInfo = library.searchStaffByUsername(USERNAME);
				name = staffInfo.getName();
                surname = staffInfo.getSurname();
                phoneNumber = staffInfo.getPhoneNumber();
                email = staffInfo.getEmail();
                address = staffInfo.getAddress();
                password = staffInfo.getPassword();
			} catch (IOException e) {
				e.printStackTrace();
			}    
        }
        
        // Labels and fields for account information
        JLabel lblName = new JLabel("Name:");
        lblName.setFont(new Font("Monospaced", Font.BOLD, 16));
        lblName.setBounds(50, 50, 100, 30);
        accountInfoPanel.add(lblName);
        JTextField txtName = new JTextField(name);
        txtName.setFont(new Font("Monospaced", Font.PLAIN, 16));
        txtName.setBounds(160, 50, 200, 30);
        accountInfoPanel.add(txtName);

        JLabel lblSurname = new JLabel("Surname:");
        lblSurname.setFont(new Font("Monospaced", Font.BOLD, 16));
        lblSurname.setBounds(50, 100, 100, 30);
        accountInfoPanel.add(lblSurname);
        JTextField txtSurname = new JTextField(surname);
        txtSurname.setFont(new Font("Monospaced", Font.PLAIN, 16));
        txtSurname.setBounds(160, 100, 200, 30);
        accountInfoPanel.add(txtSurname);

        JLabel lblPhoneNumber = new JLabel("Phone Number:");
        lblPhoneNumber.setFont(new Font("Monospaced", Font.BOLD, 16));
        lblPhoneNumber.setBounds(50, 150, 150, 30);
        accountInfoPanel.add(lblPhoneNumber);
        JTextField txtPhoneNumber = new JTextField(phoneNumber);
        txtPhoneNumber.setFont(new Font("Monospaced", Font.PLAIN, 16));
        txtPhoneNumber.setBounds(220, 150, 200, 30);
        accountInfoPanel.add(txtPhoneNumber);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Monospaced", Font.BOLD, 16));
        lblEmail.setBounds(50, 200, 100, 30);
        accountInfoPanel.add(lblEmail);
        JTextField txtEmail = new JTextField(email);
        txtEmail.setFont(new Font("Monospaced", Font.PLAIN, 16));
        txtEmail.setBounds(160, 200, 300, 30);
        accountInfoPanel.add(txtEmail);

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("Monospaced", Font.BOLD, 16));
        lblUsername.setBounds(50, 250, 100, 30);
        accountInfoPanel.add(lblUsername);
        JTextField txtUsername = new JTextField(username);
        txtUsername.setFont(new Font("Monospaced", Font.PLAIN, 16));
        txtUsername.setBounds(160, 250, 200, 30);
        txtUsername.setEditable(true);
        accountInfoPanel.add(txtUsername);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Monospaced", Font.BOLD, 16));
        lblPassword.setBounds(50, 300, 100, 30);
        accountInfoPanel.add(lblPassword);
        JPasswordField txtPassword = new JPasswordField(password);
        txtPassword.setFont(new Font("Monospaced", Font.PLAIN, 16));
        txtPassword.setBounds(160, 300, 200, 30);
        accountInfoPanel.add(txtPassword);
        
        JLabel lblAddress = new JLabel("Address:");
    	lblAddress.setFont(new Font("Monospaced", Font.BOLD, 16));
    	lblAddress.setBounds(50, 350, 100, 30); // Adjusted Y position
    	accountInfoPanel.add(lblAddress);
    	JTextField txtAddress = new JTextField(address);
    	txtAddress.setFont(new Font("Monospaced", Font.PLAIN, 16));
    	txtAddress.setBounds(160, 350, 300, 30); // Adjusted Y position
    	accountInfoPanel.add(txtAddress);

        // Check if logged in as admin or staff and set the background image accordingly
        if (loggedInAsAdmin == true) {
        	// Button for Save changes
        	JButton btnSave = new JButton("Save Changes");
        	btnSave.setFont(new Font("Monospaced", Font.PLAIN, 16));
        	btnSave.setBounds(150, 420, 200, 50); // Adjusted Y position
        	btnSave.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        	// Add action listener for Save button
        	btnSave.addActionListener(new ActionListener() {
        	    public void actionPerformed(ActionEvent e) {
        	    	// Save changes to the account information
                    String newName = txtName.getText();
                    String newSurname = txtSurname.getText();
                    String newPhoneNumber = txtPhoneNumber.getText();
                    String newEmail = txtEmail.getText();
                    String newAddress = txtAddress.getText();
                    String newUsername = txtUsername.getText();
                    String newPassword = new String(txtPassword.getPassword());
                    if (newName == null || newSurname == null || newPhoneNumber == null || newEmail == null || 
                    		newAddress == null || newUsername == null || newPassword == null) {
                    	JOptionPane.showMessageDialog(contentPane, "Please Fill The Blanks First!", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                    	// Check if username or password has been changed
                        boolean usernameChanged = !newUsername.equals(username);
                        boolean passwordChanged = !newPassword.equals("");
                        if (usernameChanged || passwordChanged) {
                        	 // Update information in arrays
                        	adminMenu.updateAdminInformation(username, newName, newSurname, newPhoneNumber, newEmail, newAddress, newPassword);
                            Boolean csvBoolean = adminMenu.updateAdminInformation_CSV(username, newName, newSurname, newPhoneNumber, newEmail, newAddress, newPassword);
                        	// Log out user if username or password has been changed
                            if ((usernameChanged || passwordChanged)&& csvBoolean == true) {
                                JOptionPane.showMessageDialog(contentPane, "Information changed successfully. You have been logged out.");
                                // Clear existing content pane
                    	        contentPane.removeAll();
                    	        contentPane.revalidate();
                    	        contentPane.repaint();
                    	        
                                signIn_UpMenu(); // Log out user
                                return;
                            }
                        } else {
                            // Update information in arrays
                        	adminMenu.updateAdminInformation(username, newName, newSurname, newPhoneNumber, newEmail, newAddress, newPassword);
                            // Show prompt for successful save
                            JOptionPane.showMessageDialog(contentPane, "Changes saved successfully.");
                        }
                    }
                }
            });
        	accountInfoPanel.add(btnSave);
        } else {
        	// Button for Save changes
        	JButton btnSave = new JButton("Save Changes");
        	btnSave.setFont(new Font("Monospaced", Font.PLAIN, 16));
        	btnSave.setBounds(150, 420, 200, 50); // Adjusted Y position
        	btnSave.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        	// Add action listener for Save button
        	btnSave.addActionListener(new ActionListener() {
        	    public void actionPerformed(ActionEvent e) {
        	    	// Save changes to the account information
                    String newName = txtName.getText();
                    String newSurname = txtSurname.getText();
                    String newPhoneNumber = txtPhoneNumber.getText();
                    String newEmail = txtEmail.getText();
                    String newAddress = txtAddress.getText();
                    String newUsername = txtUsername.getText();
                    String newPassword = new String(txtPassword.getPassword());
                    if (newName == null || newSurname == null || newPhoneNumber == null || newEmail == null || 
                    		newAddress == null || newUsername == null || newPassword == null) {
                    	JOptionPane.showMessageDialog(contentPane, "Please Fill The Blanks First!", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                    	// Check if username or password has been changed
                        boolean usernameChanged = !newUsername.equals(username);
                        boolean passwordChanged = !newPassword.equals("");
                        if (usernameChanged || passwordChanged) {
                            // Update information in CSV and arrays
                        	csv_write.updateStaff("Staff.csv", username, newName, newSurname, newPhoneNumber, newEmail, newAddress, newUsername, newPassword);
							try {
								library.addToArrayStaff(username, newName, newSurname, newPhoneNumber, newAddress, newEmail, newUsername, newPassword);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
                        	// Log out user if username or password has been changed
                            if (usernameChanged || passwordChanged) {
                                JOptionPane.showMessageDialog(contentPane, "Information changed successfully. You have been logged out.");
                                // Clear existing content pane
                    	        contentPane.removeAll();
                    	        contentPane.revalidate();
                    	        contentPane.repaint();
                    	        
                                signIn_UpMenu(); // Log out user
                                return;
                            }
                        } else {
                            // Update information in CSV and arrays
                        	csv_write.updateStaff("Staff.csv", username, newName, newSurname, newPhoneNumber, newEmail, newAddress, newUsername, newPassword);
                            try {
								library.addToArrayStaff(username, newName, newSurname, newPhoneNumber, newAddress, newEmail, newUsername, newPassword);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
                        	// Show prompt for successful save
                            JOptionPane.showMessageDialog(contentPane, "Changes saved successfully.");
                        }
                    }
                }
            });
        	accountInfoPanel.add(btnSave);
        }
                
        // Button for Back to Main Menu
    	JButton btnBack = new JButton("Back to Menu");
    	btnBack.setFont(new Font("Monospaced", Font.PLAIN, 16));
    	btnBack.setBounds(150, 480, 200, 50); // Adjusted Y position
    	btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    	// Add action listener for Back to Menu button
    	btnBack.addActionListener(new ActionListener() {
    	    public void actionPerformed(ActionEvent e) {
    	        // Clear existing content pane
    	        contentPane.removeAll();
    	        contentPane.revalidate();
    	        contentPane.repaint();
    	        // Check if logged in as admin or staff for back to menu
    	        if (loggedInAsAdmin) {
    	            Admin_StaffMenu(USERNAME);
    	        } else {
    	        	Admin_StaffMenu(USERNAME);
    	        }
    	    }
    	});
    	accountInfoPanel.add(btnBack);

    	// Button for Sign Out
    	JButton btnSignOut = new JButton("Sign Out");
    	btnSignOut.setFont(new Font("Monospaced", Font.PLAIN, 16));
    	btnSignOut.setBounds(150, 540, 200, 50); // Adjusted Y position
    	btnSignOut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    	btnSignOut.setBorderPainted(false); // Remove border
    	btnSignOut.setContentAreaFilled(false); // Make it transparent
    	btnSignOut.setFocusPainted(false); // Remove focus border
    	// Add action listener for Sign Out button
    	btnSignOut.addActionListener(new ActionListener() {
    	    public void actionPerformed(ActionEvent e) {
    	        // Clear existing content pane
    	        contentPane.removeAll();
    	        contentPane.revalidate();
    	        contentPane.repaint();

    	        signIn_UpMenu();
    	    }
    	});
    	accountInfoPanel.add(btnSignOut);
    	
        // Make sure to revalidate and repaint the content pane
        contentPane.revalidate();
        contentPane.repaint();
    }
    
    // Change Information Member
    private void accountManagementMember(String username) {
    	// Clear existing content pane
        contentPane.removeAll();
        contentPane.revalidate();
        contentPane.repaint();

        // Create a new background label
        JLabel accountManagementBackground = new JLabel();
        ImageIcon backgroundImage;
        backgroundImage = new ImageIcon("Member.JPG");

        // Scale and set the background image
        Image scaledImage = backgroundImage.getImage().getScaledInstance(750, 950, Image.SCALE_SMOOTH);
        accountManagementBackground.setIcon(new ImageIcon(scaledImage));
        accountManagementBackground.setBounds(0, 0, 750, 950);
        contentPane.add(accountManagementBackground);

        // Welcome Label for Account Management
        JLabel lblWelcome = new JLabel("Account Management");
        lblWelcome.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 25));
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        lblWelcome.setBounds(75, 100, 600, 100);
        lblWelcome.setForeground(Color.BLACK);
        lblWelcome.setOpaque(false);
        lblWelcome.setBackground(new Color(255, 255, 255, 150)); // Set light transparent background
        accountManagementBackground.add(lblWelcome, BorderLayout.NORTH);

        // Light transparent background for welcome label
        JLabel welcomeBackground = new JLabel();
        welcomeBackground.setOpaque(true);
        welcomeBackground.setBackground(new Color(255, 255, 255, 150));
        welcomeBackground.setBounds(70, 100, 610, 100);
        accountManagementBackground.add(welcomeBackground, BorderLayout.CENTER);

        // Light transparent background for Account Information Panel section
        JPanel accountInfoPanel = new JPanel();
        accountInfoPanel.setOpaque(true);
        accountInfoPanel.setBackground(new Color(255, 255, 255, 150));
        accountInfoPanel.setBounds(120, 250, 500, 600);
        accountInfoPanel.setLayout(null);
        accountManagementBackground.add(accountInfoPanel);
        
        // Retrieve account information from the Library 
        String name = "", surname = "", phoneNumber = "", email = "", password = "";
    	 
        Member memberInfo;
		try {
			memberInfo = library.searchMemberByUsername(USERNAME);
			name = memberInfo.getName();
            surname = memberInfo.getSurname();
            phoneNumber = memberInfo.getPhoneNumber();
            email = memberInfo.getEmail();
            password = memberInfo.getPassword();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Labels and fields for account information
        JLabel lblName = new JLabel("Name:");
        lblName.setFont(new Font("Monospaced", Font.BOLD, 16));
        lblName.setBounds(50, 50, 100, 30);
        accountInfoPanel.add(lblName);
        JTextField txtName = new JTextField(name);
        txtName.setFont(new Font("Monospaced", Font.PLAIN, 16));
        txtName.setBounds(160, 50, 200, 30);
        accountInfoPanel.add(txtName);

        JLabel lblSurname = new JLabel("Surname:");
        lblSurname.setFont(new Font("Monospaced", Font.BOLD, 16));
        lblSurname.setBounds(50, 100, 100, 30);
        accountInfoPanel.add(lblSurname);
        JTextField txtSurname = new JTextField(surname);
        txtSurname.setFont(new Font("Monospaced", Font.PLAIN, 16));
        txtSurname.setBounds(160, 100, 200, 30);
        accountInfoPanel.add(txtSurname);

        JLabel lblPhoneNumber = new JLabel("Phone Number:");
        lblPhoneNumber.setFont(new Font("Monospaced", Font.BOLD, 16));
        lblPhoneNumber.setBounds(50, 150, 150, 30);
        accountInfoPanel.add(lblPhoneNumber);
        JTextField txtPhoneNumber = new JTextField(phoneNumber);
        txtPhoneNumber.setFont(new Font("Monospaced", Font.PLAIN, 16));
        txtPhoneNumber.setBounds(220, 150, 200, 30);
        accountInfoPanel.add(txtPhoneNumber);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Monospaced", Font.BOLD, 16));
        lblEmail.setBounds(50, 200, 100, 30);
        accountInfoPanel.add(lblEmail);
        JTextField txtEmail = new JTextField(email);
        txtEmail.setFont(new Font("Monospaced", Font.PLAIN, 16));
        txtEmail.setBounds(160, 200, 300, 30);
        accountInfoPanel.add(txtEmail);

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("Monospaced", Font.BOLD, 16));
        lblUsername.setBounds(50, 250, 100, 30);
        accountInfoPanel.add(lblUsername);
        JTextField txtUsername = new JTextField(username);
        txtUsername.setFont(new Font("Monospaced", Font.PLAIN, 16));
        txtUsername.setBounds(160, 250, 200, 30);
        txtUsername.setEditable(true);
        accountInfoPanel.add(txtUsername);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Monospaced", Font.BOLD, 16));
        lblPassword.setBounds(50, 300, 100, 30);
        accountInfoPanel.add(lblPassword);
        JPasswordField txtPassword = new JPasswordField(password);
        txtPassword.setFont(new Font("Monospaced", Font.PLAIN, 16));
        txtPassword.setBounds(160, 300, 200, 30);
        accountInfoPanel.add(txtPassword);

        // Button for Save changes
        JButton btnSave = new JButton("Save Changes");
        btnSave.setFont(new Font("Monospaced", Font.PLAIN, 16));
        btnSave.setBounds(150, 370, 200, 50);
        btnSave.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        // Add action listener for Save button
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	// Save changes to the account information
                String newName = txtName.getText();
                String newSurname = txtSurname.getText();
                String newPhoneNumber = txtPhoneNumber.getText();
                String newEmail = txtEmail.getText();
                String newUsername = txtUsername.getText();
                String newPassword = new String(txtPassword.getPassword());
                if (newName == null || newSurname == null || newPhoneNumber == null || newEmail == null || 
                		newUsername == null || newPassword == null) {
                	JOptionPane.showMessageDialog(contentPane, "Please Fill The Blanks First!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                	// Check if username or password has been changed
                    boolean usernameChanged = !newUsername.equals(username);
                    boolean passwordChanged = !newPassword.equals("");
                    if (usernameChanged || passwordChanged) {
                        // Update information in CSV and arrays
                    	csv_write.updateMember("member.csv", username, newName, newSurname, newPhoneNumber, newEmail, newUsername, newPassword);
                    	try {
							library.addToArrayMember(username, newName, newSurname, newPhoneNumber, newEmail, newUsername, newPassword);
						} catch (IOException e1) {
							e1.printStackTrace();
						}

                    	// Log out user if username or password has been changed
                        if (usernameChanged || passwordChanged) {
                            JOptionPane.showMessageDialog(contentPane, "Information changed successfully. You have been logged out.");
                            // Clear existing content pane
                	        contentPane.removeAll();
                	        contentPane.revalidate();
                	        contentPane.repaint();
                	        
                            signIn_UpMenu(); // Log out user
                            return;
                        }
                    } else {
                        // Update information in CSV and arrays
                    	csv_write.updateMember("member.csv", username, newName, newSurname, newPhoneNumber, newEmail, newUsername, newPassword);
                    	try {
							library.addToArrayMember(username, newName, newSurname, newPhoneNumber, newEmail, newUsername, newPassword);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
                        // Show prompt for successful save
                        JOptionPane.showMessageDialog(contentPane, "Changes saved successfully.");
                    }
                }
            }
        });
        accountInfoPanel.add(btnSave);

        // Button for Back to Main Menu
        JButton btnBack = new JButton("Back to Menu");
        btnBack.setFont(new Font("Monospaced", Font.PLAIN, 16));
        btnBack.setBounds(150, 430, 200, 50);
        btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        // Add action listener for Back to Menu button
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();
                // Check if logged in as admin or staff for back to menu
                MemberMenu(USERNAME);
            }
        });
        accountInfoPanel.add(btnBack);

        // Button for Sign Out
        JButton btnSignOut = new JButton("Sign Out");
        btnSignOut.setFont(new Font("Monospaced", Font.PLAIN, 16));
        btnSignOut.setBounds(150, 490, 200, 50);
        btnSignOut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSignOut.setBorderPainted(false); // Remove border
        btnSignOut.setContentAreaFilled(false); // Make it transparent
        btnSignOut.setFocusPainted(false); // Remove focus border
        // Add action listener for Sign Out button
        btnSignOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();

                signIn_UpMenu();
            }
        });
        accountInfoPanel.add(btnSignOut);            
        
        // Make sure to revalidate and repaint the content pane
        contentPane.revalidate();
        contentPane.repaint(); 
    }
    
    // Admin/Staff Menu
    private void Admin_StaffMenu(String username) {
        // Clear existing content pane
        contentPane.removeAll();
        contentPane.revalidate();
        contentPane.repaint();

        // Create a new background label
        JLabel admin_staff_menuBackground = new JLabel();
        ImageIcon backgroundImage;

        // Check if logged in as admin or staff and set the background image accordingly
        if (loggedInAsAdmin == true) {
            backgroundImage = new ImageIcon("Admin.JPG");
        } else {
            backgroundImage = new ImageIcon("Staff.JPG");
        }

        // Scale and set the background image
        Image scaledImage = backgroundImage.getImage().getScaledInstance(750, 950, Image.SCALE_SMOOTH);
        admin_staff_menuBackground.setIcon(new ImageIcon(scaledImage));
        admin_staff_menuBackground.setBounds(0, 0, 750, 950);
        contentPane.add(admin_staff_menuBackground);

        // Welcome Label
        JLabel lblWelcome = new JLabel("Welcome, " + username + "!");
        lblWelcome.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 25));
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        lblWelcome.setBounds(75, 100, 600, 100);
        lblWelcome.setForeground(Color.BLACK);
        lblWelcome.setOpaque(false);
        lblWelcome.setBackground(new Color(255, 255, 255, 150)); // Set light transparent background
        admin_staff_menuBackground.add(lblWelcome, BorderLayout.NORTH);

        // Light transparent background for welcome label
        JLabel welcomeBackground = new JLabel();
        welcomeBackground.setOpaque(true);
        welcomeBackground.setBackground(new Color(255, 255, 255, 150));
        welcomeBackground.setBounds(70, 100, 610, 100);
        admin_staff_menuBackground.add(welcomeBackground, BorderLayout.CENTER);

        // Light transparent background for Admin and Staff Panel section
        JPanel admin_staff_Panel = new JPanel();
        admin_staff_Panel.setOpaque(true);
        admin_staff_Panel.setBackground(new Color(255, 255, 255, 150));
        admin_staff_Panel.setBounds(180, 300, 400, 400);
        admin_staff_Panel.setLayout(null);
        admin_staff_menuBackground.add(admin_staff_Panel);

        // Button for Book Management
        JButton btnUserManagement = new JButton("Book Management");
        btnUserManagement.setFont(new Font("Monospaced", Font.PLAIN, 16));
        // Add action listener for Book Management button
        btnUserManagement.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	// Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();
                
                bookManagement();
            }
        });
        btnUserManagement.setBounds(50, 50, 300, 50);
        btnUserManagement.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        admin_staff_Panel.add(btnUserManagement);

        // Button for Staff Management
        JButton btnInventoryManagement = new JButton("Staff Management");
        btnInventoryManagement.setFont(new Font("Monospaced", Font.PLAIN, 16));
        // Add action listener for Staff Management button
        btnInventoryManagement.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	// Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();
            	staffManagement();            
            }
        });
        btnInventoryManagement.setBounds(50, 110, 300, 50);
        btnInventoryManagement.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        admin_staff_Panel.add(btnInventoryManagement);

        // Button for Member Management
        JButton btnReportGeneration = new JButton("Member Management");
        btnReportGeneration.setFont(new Font("Monospaced", Font.PLAIN, 16));
        // Add action listener for Member Management button
        btnReportGeneration.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	// Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();
                
            	memberManagement();
            }
        });
        btnReportGeneration.setBounds(50, 170, 300, 50);
        btnReportGeneration.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        admin_staff_Panel.add(btnReportGeneration);
     
        // Button for Account Management
        JButton btnAccountManagement = new JButton("Account Management");
        btnAccountManagement.setFont(new Font("Monospaced", Font.PLAIN, 16));
        // Add action listener for Account Management button
        btnAccountManagement.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	// Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();
                
                accountManagement(USERNAME);
            }
        });
        btnAccountManagement.setBounds(50, 230, 300, 50);
        btnAccountManagement.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        admin_staff_Panel.add(btnAccountManagement);
        
        // Button for Sign Out
        JButton btnSignOut = new JButton("Sign Out");
        btnSignOut.setFont(new Font("Monospaced", Font.PLAIN, 16));
        btnSignOut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSignOut.setBorderPainted(false); // Remove border
        btnSignOut.setContentAreaFilled(false); // Make it transparent
        btnSignOut.setFocusPainted(false); // Remove focus border
        btnSignOut.setBounds(50, 290, 300, 50); // Adjusted position
        // Add action listener for Sign Out button
        btnSignOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();

                signIn_UpMenu();
            }
        });
        admin_staff_Panel.add(btnSignOut);

        // Make sure to revalidate and repaint the content pane
        contentPane.revalidate();
        contentPane.repaint();
    }
    
    // Member Menu
    private void MemberMenu(String username) {
        // Clear existing content pane
        contentPane.removeAll();
        contentPane.revalidate();
        contentPane.repaint();

        // Create a new background label
        JLabel memberBackground = new JLabel();
        ImageIcon backgroundImage = new ImageIcon("Member.JPG"); // Path to your image
        Image scaledImage = backgroundImage.getImage().getScaledInstance(750, 950, Image.SCALE_SMOOTH);
        memberBackground.setIcon(new ImageIcon(scaledImage));
        memberBackground.setBounds(0, 0, 750, 950);
        contentPane.add(memberBackground);

        // Welcome Label
        JLabel lblWelcome = new JLabel("Welcome, " + username + "!");
        lblWelcome.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 25));
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        lblWelcome.setBounds(75, 100, 600, 100);
        lblWelcome.setForeground(Color.BLACK);
        lblWelcome.setOpaque(false);
        lblWelcome.setBackground(new Color(255, 255, 255, 150)); // Set light transparent background
        memberBackground.add(lblWelcome, BorderLayout.NORTH);

        // Light transparent background for welcome label
        JLabel welcomeBackground = new JLabel();
        welcomeBackground.setOpaque(true);
        welcomeBackground.setBackground(new Color(255, 255, 255, 150));
        welcomeBackground.setBounds(70, 100, 610, 100);
        memberBackground.add(welcomeBackground, BorderLayout.CENTER);

        // Light transparent background for Member Panel section
        JPanel memberPanel = new JPanel();
        memberPanel.setOpaque(true);
        memberPanel.setBackground(new Color(255, 255, 255, 150));
        memberPanel.setBounds(180, 300, 400, 400);
        memberPanel.setLayout(null);
        memberBackground.add(memberPanel);

        // Button for Books
        JButton btnBooks = new JButton("Books");
        btnBooks.setFont(new Font("Monospaced", Font.PLAIN, 16)); // Increase font size
        // Add action listener for Books button
        btnBooks.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	// Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();
                
            	bookManagement();
            }
        });
        btnBooks.setBounds(50, 50, 300, 50); // Adjust position and size
        btnBooks.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Set hand cursor
        memberPanel.add(btnBooks);

        // Button for Check In/Out Books
        JButton btnCheckInOut = new JButton("Check In/Out Books");
        btnCheckInOut.setFont(new Font("Monospaced", Font.PLAIN, 16)); // Increase font size
        // Add action listener for Check In/Out Books button
        btnCheckInOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	// Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();
                
                checkInOutBooks();
            }
        });
        btnCheckInOut.setBounds(50, 110, 300, 50); // Adjust position and size
        btnCheckInOut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Set hand cursor
        memberPanel.add(btnCheckInOut);

        // Button for Loan Information
        JButton btnLoanInfo = new JButton("Loan Information");
        btnLoanInfo.setFont(new Font("Monospaced", Font.PLAIN, 16)); // Increase font size
        // Add action listener for Loan Information button
        btnLoanInfo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	// Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();
                
            	viewMemberLoans(USERNAME);
            }
        });
        btnLoanInfo.setBounds(50, 170, 300, 50); // Adjust position and size
        btnLoanInfo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Set hand cursor
        memberPanel.add(btnLoanInfo);

        // Button for Account Management
        JButton btnAccountManagement = new JButton("Account Management");
        btnAccountManagement.setFont(new Font("Monospaced", Font.PLAIN, 16)); // Increase font size
        // Add action listener for Account Management button
        btnAccountManagement.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	// Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();
                
                accountManagementMember(USERNAME);
            }
        });
        btnAccountManagement.setBounds(50, 230, 300, 50); // Adjust position and size
        btnAccountManagement.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Set hand cursor
        memberPanel.add(btnAccountManagement);

        // Button for Sign Out
        JButton btnSignOut = new JButton("Sign Out");
        btnSignOut.setFont(new Font("Monospaced", Font.PLAIN, 16)); // Increase font size
        btnSignOut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSignOut.setBorderPainted(false); // Remove border
        btnSignOut.setContentAreaFilled(false); // Make it transparent
        btnSignOut.setFocusPainted(false); // Remove focus border
        btnSignOut.setBounds(50, 300, 300, 50); // Adjust position and size
        // Add action listener for Sign Out button
        btnSignOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	// Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();
                
                signIn_UpMenu();
            }
        });
        memberPanel.add(btnSignOut);

        // Make sure to revalidate and repaint the content pane
        contentPane.revalidate();
        contentPane.repaint();
    }
  
    // Book Management Admin/Staff/Member
    private void bookManagement() {
        // Clear existing content pane
        contentPane.removeAll();
        contentPane.revalidate();
        contentPane.repaint();

        // Create a new background label
        JLabel bookManagementBackground = new JLabel();
        ImageIcon backgroundImage;

        // Check if logged in as admin or staff and set the background image accordingly
        if (loggedInAsAdmin == true) {
            backgroundImage = new ImageIcon("Admin.JPG");
        } else {
        	if (loggedInAsAdminStaff == true) {
                backgroundImage = new ImageIcon("Staff.JPG");
            } else {
                backgroundImage = new ImageIcon("Member.JPG");
            }
        }

        // Scale and set the background image
        Image scaledImage = backgroundImage.getImage().getScaledInstance(750, 950, Image.SCALE_SMOOTH);
        bookManagementBackground.setIcon(new ImageIcon(scaledImage));
        bookManagementBackground.setBounds(0, 0, 750, 950);
        contentPane.add(bookManagementBackground);

        // Welcome Label for Book Management
        JLabel lblWelcome = new JLabel("Book Management");
        lblWelcome.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 25));
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        lblWelcome.setBounds(75, 100, 600, 100);
        lblWelcome.setForeground(Color.BLACK);
        lblWelcome.setOpaque(false);
        lblWelcome.setBackground(new Color(255, 255, 255, 150)); // Set light transparent background
        bookManagementBackground.add(lblWelcome, BorderLayout.NORTH);

        // Light transparent background for welcome label
        JLabel welcomeBackground = new JLabel();
        welcomeBackground.setOpaque(true);
        welcomeBackground.setBackground(new Color(255, 255, 255, 150));
        welcomeBackground.setBounds(70, 100, 610, 100);
        bookManagementBackground.add(welcomeBackground, BorderLayout.CENTER);

        // Check if logged in as admin or staff and add the buttons accordingly
        if (loggedInAsAdminStaff == true) {
            // Light transparent background for Book Management Panel section
            JPanel bookManagementPanel = new JPanel();
            bookManagementPanel.setOpaque(true);
            bookManagementPanel.setBackground(new Color(255, 255, 255, 150));
            bookManagementPanel.setBounds(180, 300, 400, 550);
            bookManagementPanel.setLayout(null);
            bookManagementBackground.add(bookManagementPanel);

            // Button for Add Book
            JButton btnAddBook = new JButton("Add Book");
            btnAddBook.setFont(new Font("Monospaced", Font.PLAIN, 16));
            // Add action listener for Add Book button
            btnAddBook.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Clear existing content pane
                    contentPane.removeAll();
                    contentPane.revalidate();
                    contentPane.repaint();
                    
                    addBook();
                }
            });
            btnAddBook.setBounds(50, 50, 300, 50);
            btnAddBook.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            bookManagementPanel.add(btnAddBook);

            // Button for Remove Book
            JButton btnRemoveBook = new JButton("Remove Book");
            btnRemoveBook.setFont(new Font("Monospaced", Font.PLAIN, 16));
            // Add action listener for Remove Book button
            btnRemoveBook.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Clear existing content pane
                    contentPane.removeAll();
                    contentPane.revalidate();
                    contentPane.repaint();
                    
                    removeBook();
                }
            });
            btnRemoveBook.setBounds(50, 120, 300, 50);
            btnRemoveBook.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            bookManagementPanel.add(btnRemoveBook);

            // Button for Search Book
            JButton btnSearchBook = new JButton("Search Book");
            btnSearchBook.setFont(new Font("Monospaced", Font.PLAIN, 16));
            // Add action listener for Search Book button
            btnSearchBook.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Clear existing content pane
                    contentPane.removeAll();
                    contentPane.revalidate();
                    contentPane.repaint();
                    
                    searchBook();
                }
            });
            btnSearchBook.setBounds(50, 190, 300, 50);
            btnSearchBook.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            bookManagementPanel.add(btnSearchBook);

            // Button for View Books
            JButton btnViewBooks = new JButton("View Books");
            btnViewBooks.setFont(new Font("Monospaced", Font.PLAIN, 16));
            // Add action listener for View Books button
            btnViewBooks.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Clear existing content pane
                    contentPane.removeAll();
                    contentPane.revalidate();
                    contentPane.repaint();
                    
                    viewBooks();
                }
            });
            btnViewBooks.setBounds(50, 260, 300, 50);
            btnViewBooks.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            bookManagementPanel.add(btnViewBooks);

            // Button for Back to Main Menu
            JButton btnBack = new JButton("Back to Main Menu");
            btnBack.setFont(new Font("Monospaced", Font.PLAIN, 16));
            // Add action listener for Back to Main Menu button
            btnBack.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Clear existing content pane
                    contentPane.removeAll();
                    contentPane.revalidate();
                    contentPane.repaint();

                    Admin_StaffMenu(USERNAME);
                }
            });
            btnBack.setBounds(50, 330, 300, 50);
            btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            bookManagementPanel.add(btnBack);

            // Button for Sign Out
            JButton btnSignOut = new JButton("Sign Out");
            btnSignOut.setFont(new Font("Monospaced", Font.PLAIN, 16));
            btnSignOut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btnSignOut.setBorderPainted(false); // Remove border
            btnSignOut.setContentAreaFilled(false); // Make it transparent
            btnSignOut.setFocusPainted(false); // Remove focus border
            btnSignOut.setBounds(50, 400, 300, 50);
            // Add action listener for Sign Out button
            btnSignOut.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Clear existing content pane
                    contentPane.removeAll();
                    contentPane.revalidate();
                    contentPane.repaint();

                    signIn_UpMenu();
                }
            });
            bookManagementPanel.add(btnSignOut);
        } else {
            // Light transparent background for Book Management Panel section
            JPanel bookManagementPanel = new JPanel();
            bookManagementPanel.setOpaque(true);
            bookManagementPanel.setBackground(new Color(255, 255, 255, 150));
            bookManagementPanel.setBounds(180, 300, 400, 350);
            bookManagementPanel.setLayout(null);
            bookManagementBackground.add(bookManagementPanel);

            // Button for Search Book
            JButton btnSearchBook = new JButton("Search Book");
            btnSearchBook.setFont(new Font("Monospaced", Font.PLAIN, 16)); // Increase font size
            // Add action listener for Search Book button
            btnSearchBook.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Clear existing content pane
                    contentPane.removeAll();
                    contentPane.revalidate();
                    contentPane.repaint();
                    
                    searchBook();
                }
            });
            btnSearchBook.setBounds(50, 50, 300, 50); // Adjust position and size
            btnSearchBook.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Set hand cursor
            bookManagementPanel.add(btnSearchBook);

            // Button for View Books
            JButton btnViewBooks = new JButton("View Books");
            btnViewBooks.setFont(new Font("Monospaced", Font.PLAIN, 16)); // Increase font size
            // Add action listener for View Books button
            btnViewBooks.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Clear existing content pane
                    contentPane.removeAll();
                    contentPane.revalidate();
                    contentPane.repaint();
                    
                    viewBooks();
                }
            });
            btnViewBooks.setBounds(50, 120, 300, 50); // Adjust position and size
            btnViewBooks.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 
            bookManagementPanel.add(btnViewBooks);

            // Button for Back to Main Menu
            JButton btnBack = new JButton("Back to Main Menu");
            btnBack.setFont(new Font("Monospaced", Font.PLAIN, 16));
            // Add action listener for Back to Main Menu button
            btnBack.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Clear existing content pane
                    contentPane.removeAll();
                    contentPane.revalidate();
                    contentPane.repaint();
                    
                    MemberMenu(USERNAME);
                }
            });
            btnBack.setBounds(50, 190, 300, 50); 
            btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            bookManagementPanel.add(btnBack);

            // Button for Sign Out
            JButton btnSignOut = new JButton("Sign Out");
            btnSignOut.setFont(new Font("Monospaced", Font.PLAIN, 16)); 
            btnSignOut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btnSignOut.setBorderPainted(false); // Remove border
            btnSignOut.setContentAreaFilled(false); // Make it transparent
            btnSignOut.setFocusPainted(false); // Remove focus border
            btnSignOut.setBounds(50, 260, 300, 50);
            // Add action listener for Sign Out button
            btnSignOut.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Clear existing content pane
                    contentPane.removeAll();
                    contentPane.revalidate();
                    contentPane.repaint();
                    
                    signIn_UpMenu();
                }
            });
            bookManagementPanel.add(btnSignOut);
        }

        // Make sure to revalidate and repaint the content pane
        contentPane.revalidate();
        contentPane.repaint();
    }

    // Staff Management Admin/Staff
    private void staffManagement() {
        // Clear existing content pane
        contentPane.removeAll();
        contentPane.revalidate();
        contentPane.repaint();

        // Create a new background label
        JLabel staffManagementBackground = new JLabel();
        ImageIcon backgroundImage;

        // Check if logged in as admin or staff and set the background image accordingly
        if (loggedInAsAdmin == true) {
            backgroundImage = new ImageIcon("Admin.JPG");
        } else {
            backgroundImage = new ImageIcon("Staff.JPG");
        }

        // Scale and set the background image
        Image scaledImage = backgroundImage.getImage().getScaledInstance(750, 950, Image.SCALE_SMOOTH);
        staffManagementBackground.setIcon(new ImageIcon(scaledImage));
        staffManagementBackground.setBounds(0, 0, 750, 950);
        contentPane.add(staffManagementBackground);

        // Welcome Label
        JLabel lblWelcome = new JLabel("Staff Management");
        lblWelcome.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 25));
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        lblWelcome.setBounds(75, 100, 600, 100);
        lblWelcome.setForeground(Color.BLACK);
        lblWelcome.setOpaque(false);
        lblWelcome.setBackground(new Color(255, 255, 255, 150)); // Set light transparent background
        staffManagementBackground.add(lblWelcome, BorderLayout.NORTH);

        // Light transparent background for welcome label
        JLabel welcomeBackground = new JLabel();
        welcomeBackground.setOpaque(true);
        welcomeBackground.setBackground(new Color(255, 255, 255, 150));
        welcomeBackground.setBounds(70, 100, 610, 100);
        staffManagementBackground.add(welcomeBackground, BorderLayout.CENTER);

        // Check if logged in as admin or staff and add the buttons accordingly
        if (loggedInAsAdmin == true) {
        	// Light transparent background for Staff Management Panel section
            JPanel staffManagementPanel = new JPanel();
            staffManagementPanel.setOpaque(true);
            staffManagementPanel.setBackground(new Color(255, 255, 255, 150));
            staffManagementPanel.setBounds(180, 300, 400, 550);
            staffManagementPanel.setLayout(null);
            staffManagementBackground.add(staffManagementPanel);

            // Button for Add Staff
            JButton btnAddStaff = new JButton("Add Staff");
            btnAddStaff.setFont(new Font("Monospaced", Font.PLAIN, 16));
            // Add action listener for Add Staff button
            btnAddStaff.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	// Clear existing content pane
                    contentPane.removeAll();
                    contentPane.revalidate();
                    contentPane.repaint();
                    
                    addStaff();
                }
            });
            btnAddStaff.setBounds(50, 50, 300, 50);
            btnAddStaff.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            staffManagementPanel.add(btnAddStaff);

            // Button for Remove Staff
            JButton btnRemoveStaff = new JButton("Remove Staff");
            btnRemoveStaff.setFont(new Font("Monospaced", Font.PLAIN, 16));
            // Add action listener for Remove Staff button
            btnRemoveStaff.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	// Clear existing content pane
                    contentPane.removeAll();
                    contentPane.revalidate();
                    contentPane.repaint();
                    
                    removeStaff();
                }
            });
            btnRemoveStaff.setBounds(50, 120, 300, 50);
            btnRemoveStaff.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            staffManagementPanel.add(btnRemoveStaff);

            // Button for Search Staff
            JButton btnSearchStaff = new JButton("Search Staff");
            btnSearchStaff.setFont(new Font("Monospaced", Font.PLAIN, 16));
            // Add action listener for Search Staff button
            btnSearchStaff.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	// Clear existing content pane
                    contentPane.removeAll();
                    contentPane.revalidate();
                    contentPane.repaint();
                    
                    searchStaff();
                }
            });
            btnSearchStaff.setBounds(50, 190, 300, 50);
            btnSearchStaff.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            staffManagementPanel.add(btnSearchStaff);

            // Button for View Staff
            JButton btnViewStaff = new JButton("View Staff");
            btnViewStaff.setFont(new Font("Monospaced", Font.PLAIN, 16));
            // Add action listener for View Staff button
            btnViewStaff.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	// Clear existing content pane
                    contentPane.removeAll();
                    contentPane.revalidate();
                    contentPane.repaint();
                    
                    viewStaff();
                }
            });
            btnViewStaff.setBounds(50, 260, 300, 50);
            btnViewStaff.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            staffManagementPanel.add(btnViewStaff);

            // Button for Salary Calculation
            JButton btnSalaryCalculation = new JButton("Salary Calculation");
            btnSalaryCalculation.setFont(new Font("Monospaced", Font.PLAIN, 16));
            // Add action listener for Salary Calculation button
            btnSalaryCalculation.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	// Clear existing content pane
                    contentPane.removeAll();
                    contentPane.revalidate();
                    contentPane.repaint();
                    salaryCalculationStaff();
                }
            });
            btnSalaryCalculation.setBounds(50, 330, 300, 50);
            btnSalaryCalculation.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            staffManagementPanel.add(btnSalaryCalculation);

            // Button for Back to Main Menu
            JButton btnBack = new JButton("Back to Main Menu");
            btnBack.setFont(new Font("Monospaced", Font.PLAIN, 16));
            // Add action listener for Back to Main Menu button
            btnBack.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Clear existing content pane
                    contentPane.removeAll();
                    contentPane.revalidate();
                    contentPane.repaint();

                    Admin_StaffMenu(USERNAME);
                }
            });
            btnBack.setBounds(50, 400, 300, 50);
            btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            staffManagementPanel.add(btnBack);

            // Button for Sign Out
            JButton btnSignOut = new JButton("Sign Out");
            btnSignOut.setFont(new Font("Monospaced", Font.PLAIN, 16));
            btnSignOut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btnSignOut.setBorderPainted(false); // Remove border
            btnSignOut.setContentAreaFilled(false); // Make it transparent
            btnSignOut.setFocusPainted(false); // Remove focus border
            btnSignOut.setBounds(50, 470, 300, 50);
            // Add action listener for Sign Out button
            btnSignOut.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Clear existing content pane
                    contentPane.removeAll();
                    contentPane.revalidate();
                    contentPane.repaint();

                    signIn_UpMenu();
                }
            });
            staffManagementPanel.add(btnSignOut);
        } else {
        	// Light transparent background for Staff Management Panel section
            JPanel staffManagementPanel = new JPanel();
            staffManagementPanel.setOpaque(true);
            staffManagementPanel.setBackground(new Color(255, 255, 255, 150));
            staffManagementPanel.setBounds(180, 300, 400, 350);
            staffManagementPanel.setLayout(null);
            staffManagementBackground.add(staffManagementPanel);

            // Button for Search Staff
            JButton btnSearchStaff = new JButton("Search Staff");
            btnSearchStaff.setFont(new Font("Monospaced", Font.PLAIN, 16)); // Increase font size
            // Add action listener for Search Staff button
            btnSearchStaff.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	// Clear existing content pane
                    contentPane.removeAll();
                    contentPane.revalidate();
                    contentPane.repaint();
                    
                    searchStaff();
                }
            });
            btnSearchStaff.setBounds(50, 50, 300, 50); // Adjust position and size
            btnSearchStaff.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Set hand cursor
            staffManagementPanel.add(btnSearchStaff);

            // Button for View Staff
            JButton btnViewStaff = new JButton("View Staff");
            btnViewStaff.setFont(new Font("Monospaced", Font.PLAIN, 16)); // Increase font size
            // Add action listener for View Staff button
            btnViewStaff.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	 // Clear existing content pane
                    contentPane.removeAll();
                    contentPane.revalidate();
                    contentPane.repaint();
                    
                    viewStaff();
                }
            });
            btnViewStaff.setBounds(50, 120, 300, 50); // Adjust position and size
            btnViewStaff.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 
            staffManagementPanel.add(btnViewStaff);

            // Button for Back to Main Menu
            JButton btnBack = new JButton("Back to Main Menu");
            btnBack.setFont(new Font("Monospaced", Font.PLAIN, 16));
            // Add action listener for Back to Main Menu button
            btnBack.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	// Clear existing content pane
                    contentPane.removeAll();
                    contentPane.revalidate();
                    contentPane.repaint();
                    
                    Admin_StaffMenu(USERNAME);
                }
            });
            btnBack.setBounds(50, 190, 300, 50); 
            btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            staffManagementPanel.add(btnBack);

            // Button for Sign Out
            JButton btnSignOut = new JButton("Sign Out");
            btnSignOut.setFont(new Font("Monospaced", Font.PLAIN, 16)); 
            btnSignOut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btnSignOut.setBorderPainted(false); // Remove border
            btnSignOut.setContentAreaFilled(false); // Make it transparent
            btnSignOut.setFocusPainted(false); // Remove focus border
            btnSignOut.setBounds(50, 260, 300, 50);
            // Add action listener for Sign Out button
            btnSignOut.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	// Clear existing content pane
                    contentPane.removeAll();
                    contentPane.revalidate();
                    contentPane.repaint();
                    
                    signIn_UpMenu();
                }
            });
            staffManagementPanel.add(btnSignOut);
        }

        // Make sure to revalidate and repaint the content pane
        contentPane.revalidate();
        contentPane.repaint();
    }

    // Member Management Admin/Staff
    private void memberManagement() {
        // Clear existing content pane
        contentPane.removeAll();
        contentPane.revalidate();
        contentPane.repaint();

        // Create a new background label
        JLabel memberManagementBackground = new JLabel();
        ImageIcon backgroundImage;

        // Check if logged in as admin or staff and set the background image accordingly
        if (loggedInAsAdmin == true) {
            backgroundImage = new ImageIcon("Admin.JPG");
        } else {
            backgroundImage = new ImageIcon("Staff.JPG");
        }

        // Scale and set the background image
        Image scaledImage = backgroundImage.getImage().getScaledInstance(750, 950, Image.SCALE_SMOOTH);
        memberManagementBackground.setIcon(new ImageIcon(scaledImage));
        memberManagementBackground.setBounds(0, 0, 750, 950);
        contentPane.add(memberManagementBackground);

        // Welcome Label
        JLabel lblWelcome = new JLabel("Member Management");
        lblWelcome.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 25));
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        lblWelcome.setBounds(75, 100, 600, 100);
        lblWelcome.setForeground(Color.BLACK);
        lblWelcome.setOpaque(false);
        lblWelcome.setBackground(new Color(255, 255, 255, 150)); // Set light transparent background
        memberManagementBackground.add(lblWelcome, BorderLayout.NORTH);

        // Light transparent background for welcome label
        JLabel welcomeBackground = new JLabel();
        welcomeBackground.setOpaque(true);
        welcomeBackground.setBackground(new Color(255, 255, 255, 150));
        welcomeBackground.setBounds(70, 100, 610, 100);
        memberManagementBackground.add(welcomeBackground, BorderLayout.CENTER);

        // Light transparent background for Member Management Panel section
        JPanel memberManagementPanel = new JPanel();
        memberManagementPanel.setOpaque(true);
        memberManagementPanel.setBackground(new Color(255, 255, 255, 150));
        memberManagementPanel.setBounds(180, 300, 400, 550);
        memberManagementPanel.setLayout(null);
        memberManagementBackground.add(memberManagementPanel);

        // Button for Add Member
        JButton btnAddMember = new JButton("Add Member");
        btnAddMember.setFont(new Font("Monospaced", Font.PLAIN, 16));
        // Add action listener for Add Member button
        btnAddMember.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	// Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();
                
                addMember();
            }
        });
        btnAddMember.setBounds(50, 50, 300, 50);
        btnAddMember.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        memberManagementPanel.add(btnAddMember);

        // Button for Remove Member
        JButton btnRemoveMember = new JButton("Remove Member");
        btnRemoveMember.setFont(new Font("Monospaced", Font.PLAIN, 16));
        // Add action listener for Remove Member button
        btnRemoveMember.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	// Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();
                
                removeMember();
            }
        });
        btnRemoveMember.setBounds(50, 120, 300, 50);
        btnRemoveMember.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        memberManagementPanel.add(btnRemoveMember);

        // Button for Search Member
        JButton btnSearchMember = new JButton("Search Member");
        btnSearchMember.setFont(new Font("Monospaced", Font.PLAIN, 16));
        // Add action listener for Search Member button
        btnSearchMember.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                /// Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();
                
                searchMember();
            }
        });
        btnSearchMember.setBounds(50, 190, 300, 50);
        btnSearchMember.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        memberManagementPanel.add(btnSearchMember);

        // Button for View Member
        JButton btnViewMember = new JButton("View Member");
        btnViewMember.setFont(new Font("Monospaced", Font.PLAIN, 16));
        // Add action listener for View Member button
        btnViewMember.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	// Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();
                
                viewMembers();
                
            }
        });
        btnViewMember.setBounds(50, 260, 300, 50);
        btnViewMember.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        memberManagementPanel.add(btnViewMember);

        // Button for Loan Information
        JButton btnLoanInfo = new JButton("Loan Information");
        btnLoanInfo.setFont(new Font("Monospaced", Font.PLAIN, 16));
        // Add action listener for Loan Information button
        btnLoanInfo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	// Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();
                
                loanManagement();
            }
        });
        btnLoanInfo.setBounds(50, 330, 300, 50);
        btnLoanInfo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        memberManagementPanel.add(btnLoanInfo);

        // Button for Back to Main Menu
        JButton btnBack = new JButton("Back to Main Menu");
        btnBack.setFont(new Font("Monospaced", Font.PLAIN, 16));
        // Add action listener for Back to Main Menu button
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();

                Admin_StaffMenu(USERNAME);
            }
        });
        btnBack.setBounds(50, 400, 300, 50);
        btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        memberManagementPanel.add(btnBack);

        // Button for Sign Out
        JButton btnSignOut = new JButton("Sign Out");
        btnSignOut.setFont(new Font("Monospaced", Font.PLAIN, 16));
        btnSignOut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSignOut.setBorderPainted(false); // Remove border
        btnSignOut.setContentAreaFilled(false); // Make it transparent
        btnSignOut.setFocusPainted(false); // Remove focus border
        btnSignOut.setBounds(50, 470, 300, 50);
        // Add action listener for Sign Out button
        btnSignOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();

                signIn_UpMenu();
            }
        });
        memberManagementPanel.add(btnSignOut);

        // Make sure to revalidate and repaint the content pane
        contentPane.revalidate();
        contentPane.repaint();
    }
    
    // View Books Admin/Staff/Member (test)
    private void viewBooks() {
        // Clear existing content pane
        contentPane.removeAll();
        contentPane.revalidate();
        contentPane.repaint();

        // Create a new background label
        JLabel viewBooksBackground = new JLabel();
        ImageIcon backgroundImage = new ImageIcon("view.JPG"); // Path to your image
        Image scaledImage = backgroundImage.getImage().getScaledInstance(750, 950, Image.SCALE_SMOOTH);
        viewBooksBackground.setIcon(new ImageIcon(scaledImage));
        viewBooksBackground.setBounds(0, 0, 750, 950);
        contentPane.add(viewBooksBackground);

        // Welcome Label for viewing books
        JLabel lblWelcome = new JLabel("View Books");
        lblWelcome.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 25));
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        lblWelcome.setBounds(75, 100, 600, 100);
        lblWelcome.setForeground(Color.BLACK);
        lblWelcome.setOpaque(false);
        lblWelcome.setBackground(new Color(255, 255, 255, 150)); // Set light transparent background
        viewBooksBackground.add(lblWelcome, BorderLayout.NORTH);

        // Light transparent background for welcome label
        JLabel welcomeBackground = new JLabel();
        welcomeBackground.setOpaque(true);
        welcomeBackground.setBackground(new Color(255, 255, 255, 150));
        welcomeBackground.setBounds(70, 100, 610, 100);
        viewBooksBackground.add(welcomeBackground, BorderLayout.CENTER);

        // Light transparent background for Book Information Panel section
        JPanel bookInfoPanel = new JPanel();
        bookInfoPanel.setOpaque(true);
        bookInfoPanel.setBackground(new Color(255, 255, 255, 150));
        bookInfoPanel.setBounds(120, 250, 500, 600);
        bookInfoPanel.setLayout(null);
        viewBooksBackground.add(bookInfoPanel);

        // Retrieve book information and display it
        String bookInfo = library.printBooks_GUI();
        JTextArea textArea = new JTextArea(bookInfo);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setEditable(false);
        textArea.setOpaque(false); 
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(40, 20, 420, 400);
        bookInfoPanel.add(scrollPane);

        // Button for Back to Main Menu
        JButton btnBack = new JButton("Back to Book Management");
        btnBack.setFont(new Font("Monospaced", Font.PLAIN, 16));
        // Add action listener for Back to Main Menu button
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();
                
                bookManagement();
            }
        });
        btnBack.setBounds(100, 480, 300, 50);
        btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        bookInfoPanel.add(btnBack);

        // Button for Sign Out
        JButton btnSignOut = new JButton("Sign Out");
        btnSignOut.setFont(new Font("Monospaced", Font.PLAIN, 16));
        btnSignOut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSignOut.setBorderPainted(false); // Remove border
        btnSignOut.setContentAreaFilled(false); // Make it transparent
        btnSignOut.setFocusPainted(false); // Remove focus border
        btnSignOut.setBounds(100, 540, 300, 50);
        // Add action listener for Sign Out button
        btnSignOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();

                signIn_UpMenu();
            }
        });
        bookInfoPanel.add(btnSignOut);

        // Make sure to revalidate and repaint the content pane
        contentPane.revalidate();
        contentPane.repaint();
    }
    
    // View Staff Admin/Staff (test)
    private void viewStaff() {
        // Clear existing content pane
        contentPane.removeAll();
        contentPane.revalidate();
        contentPane.repaint();

        // Create a new background label
        JLabel viewStaffBackground = new JLabel();
        ImageIcon backgroundImage = new ImageIcon("view.JPG"); // Path to your image
        Image scaledImage = backgroundImage.getImage().getScaledInstance(750, 950, Image.SCALE_SMOOTH);
        viewStaffBackground.setIcon(new ImageIcon(scaledImage));
        viewStaffBackground.setBounds(0, 0, 750, 950);
        contentPane.add(viewStaffBackground);

        // Welcome Label for viewing staff
        JLabel lblWelcome = new JLabel("View Staff");
        lblWelcome.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 25));
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        lblWelcome.setBounds(75, 100, 600, 100);
        lblWelcome.setForeground(Color.BLACK);
        lblWelcome.setOpaque(false);
        lblWelcome.setBackground(new Color(255, 255, 255, 150)); // Set light transparent background
        viewStaffBackground.add(lblWelcome, BorderLayout.NORTH);

        // Light transparent background for welcome label
        JLabel welcomeBackground = new JLabel();
        welcomeBackground.setOpaque(true);
        welcomeBackground.setBackground(new Color(255, 255, 255, 150));
        welcomeBackground.setBounds(70, 100, 610, 100);
        viewStaffBackground.add(welcomeBackground, BorderLayout.CENTER);

        // Light transparent background for Staff Information Panel section
        JPanel staffInfoPanel = new JPanel();
        staffInfoPanel.setOpaque(true);
        staffInfoPanel.setBackground(new Color(255, 255, 255, 150));
        staffInfoPanel.setBounds(120, 250, 500, 600);
        staffInfoPanel.setLayout(null);
        viewStaffBackground.add(staffInfoPanel);

        // Retrieve staff information from the Library class and display it
        String staffInfo = library.printStaff_GUI();
        JTextArea textArea = new JTextArea(staffInfo);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setEditable(false);
        textArea.setOpaque(false); 
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(40, 20, 420, 400);
        staffInfoPanel.add(scrollPane);

        // Button for Back to Main Menu
        JButton btnBack = new JButton("Back to Staff Management");
        btnBack.setFont(new Font("Monospaced", Font.PLAIN, 16));
        // Add action listener for Back to Main Menu button
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();
                
                staffManagement();
            }
        });
        btnBack.setBounds(100, 480, 300, 50);
        btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        staffInfoPanel.add(btnBack);

        // Button for Sign Out
        JButton btnSignOut = new JButton("Sign Out");
        btnSignOut.setFont(new Font("Monospaced", Font.PLAIN, 16));
        btnSignOut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSignOut.setBorderPainted(false); // Remove border
        btnSignOut.setContentAreaFilled(false); // Make it transparent
        btnSignOut.setFocusPainted(false); // Remove focus border
        btnSignOut.setBounds(100, 540, 300, 50);
        // Add action listener for Sign Out button
        btnSignOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();

                signIn_UpMenu();
            }
        });
        staffInfoPanel.add(btnSignOut);

        // Make sure to revalidate and repaint the content pane
        contentPane.revalidate();
        contentPane.repaint();
    }

    // View Members Admin/Staff (test)
    private void viewMembers() {
        // Clear existing content pane
        contentPane.removeAll();
        contentPane.revalidate();
        contentPane.repaint();

        // Create a new background label
        JLabel viewMemberBackground = new JLabel();
        ImageIcon backgroundImage = new ImageIcon("view.JPG");
        Image scaledImage = backgroundImage.getImage().getScaledInstance(750, 950, Image.SCALE_SMOOTH);
        viewMemberBackground.setIcon(new ImageIcon(scaledImage));
        viewMemberBackground.setBounds(0, 0, 750, 950);
        contentPane.add(viewMemberBackground);

        // Welcome Label for viewing members
        JLabel lblWelcome = new JLabel("View Members");
        lblWelcome.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 25));
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        lblWelcome.setBounds(75, 100, 600, 100);
        lblWelcome.setForeground(Color.BLACK);
        lblWelcome.setOpaque(false);
        lblWelcome.setBackground(new Color(255, 255, 255, 150)); // Set light transparent background
        viewMemberBackground.add(lblWelcome, BorderLayout.NORTH);

        // Light transparent background for welcome label
        JLabel welcomeBackground = new JLabel();
        welcomeBackground.setOpaque(true);
        welcomeBackground.setBackground(new Color(255, 255, 255, 150));
        welcomeBackground.setBounds(70, 100, 610, 100);
        viewMemberBackground.add(welcomeBackground, BorderLayout.CENTER);

        // Light transparent background for Member Information Panel section
        JPanel memberInfoPanel = new JPanel();
        memberInfoPanel.setOpaque(true);
        memberInfoPanel.setBackground(new Color(255, 255, 255, 150));
        memberInfoPanel.setBounds(120, 250, 500, 600);
        memberInfoPanel.setLayout(null);
        viewMemberBackground.add(memberInfoPanel);

        // Retrieve member information from the Library class and display it
        String memberInfo = library.printMembers_GUI();
        JTextArea textArea = new JTextArea(memberInfo);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setEditable(false);
        textArea.setOpaque(false); 
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(40, 20, 420, 400);
        memberInfoPanel.add(scrollPane);

        // Button for Back to Main Menu
        JButton btnBack = new JButton("Back to Member Management");
        btnBack.setFont(new Font("Monospaced", Font.PLAIN, 16));
        // Add action listener for Back to Main Menu button
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();
                
                memberManagement();
            }
        });
        btnBack.setBounds(100, 480, 300, 50);
        btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        memberInfoPanel.add(btnBack);

        // Button for Sign Out
        JButton btnSignOut = new JButton("Sign Out");
        btnSignOut.setFont(new Font("Monospaced", Font.PLAIN, 16));
        btnSignOut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSignOut.setBorderPainted(false); // Remove border
        btnSignOut.setContentAreaFilled(false); // Make it transparent
        btnSignOut.setFocusPainted(false); // Remove focus border
        btnSignOut.setBounds(100, 540, 300, 50);
        // Add action listener for Sign Out button
        btnSignOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();

                signIn_UpMenu();
            }
        });
        memberInfoPanel.add(btnSignOut);

        // Make sure to revalidate and repaint the content pane
        contentPane.revalidate();
        contentPane.repaint();
    }
    
    // View Member's Loan
    private void viewMemberLoans(String username) {
        // Clear existing content pane
        contentPane.removeAll();
        contentPane.revalidate();
        contentPane.repaint();

        // Create a new background label
        JLabel viewLoansBackground = new JLabel();
        ImageIcon backgroundImage = new ImageIcon("Member.JPG");
        Image scaledImage = backgroundImage.getImage().getScaledInstance(750, 950, Image.SCALE_SMOOTH);
        viewLoansBackground.setIcon(new ImageIcon(scaledImage));
        viewLoansBackground.setBounds(0, 0, 750, 950);
        contentPane.add(viewLoansBackground);

        // Welcome Label for viewing loans
        JLabel lblWelcome = new JLabel("Your Loan Records");
        lblWelcome.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 25));
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        lblWelcome.setBounds(75, 100, 600, 100);
        lblWelcome.setForeground(Color.BLACK);
        lblWelcome.setOpaque(false);
        lblWelcome.setBackground(new Color(255, 255, 255, 150)); // Set light transparent background
        viewLoansBackground.add(lblWelcome, BorderLayout.NORTH);

        // Light transparent background for welcome label
        JLabel welcomeBackground = new JLabel();
        welcomeBackground.setOpaque(true);
        welcomeBackground.setBackground(new Color(255, 255, 255, 150));
        welcomeBackground.setBounds(70, 100, 610, 100);
        viewLoansBackground.add(welcomeBackground, BorderLayout.CENTER);

        // Light transparent background for Loan Information Panel section
        JPanel loanInfoPanel = new JPanel();
        loanInfoPanel.setOpaque(true);
        loanInfoPanel.setBackground(new Color(255, 255, 255, 150));
        loanInfoPanel.setBounds(120, 250, 500, 600);
        loanInfoPanel.setLayout(null);
        viewLoansBackground.add(loanInfoPanel);

        // Create and configure a text area to display loan records
        String loanInfo = library.viewMemberLoans_GUI(username);
        JTextArea textArea = new JTextArea(loanInfo);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setEditable(false);
        textArea.setOpaque(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(40, 20, 420, 400);
        loanInfoPanel.add(scrollPane);

        // Button for Back to Member Management
        JButton btnBack = new JButton("Back to Member Menu");
        btnBack.setFont(new Font("Monospaced", Font.PLAIN, 16));
        // Add action listener for Back to Member Management button
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();
                MemberMenu(username);
            }
        });
        btnBack.setBounds(100, 480, 300, 50);
        btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loanInfoPanel.add(btnBack);

        // Button for Sign Out
        JButton btnSignOut = new JButton("Sign Out");
        btnSignOut.setFont(new Font("Monospaced", Font.PLAIN, 16));
        btnSignOut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSignOut.setBorderPainted(false); // Remove border
        btnSignOut.setContentAreaFilled(false); // Make it transparent
        btnSignOut.setFocusPainted(false); // Remove focus border
        btnSignOut.setBounds(100, 540, 300, 50);
        // Add action listener for Sign Out button
        btnSignOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();
                
                signIn_UpMenu();
            }
        });
        loanInfoPanel.add(btnSignOut);

        // Make sure to revalidate and repaint the content pane
        contentPane.revalidate();
        contentPane.repaint();
    }

    // Add Book Admin/Staff
    private void addBook() {
        // Clear existing content pane
        contentPane.removeAll();
        contentPane.revalidate();
        contentPane.repaint();

        // Create a new background label
        JLabel bookAddBackground = new JLabel();
        ImageIcon backgroundImage = new ImageIcon("Add.JPG");
        Image scaledImage = backgroundImage.getImage().getScaledInstance(750, 950, Image.SCALE_SMOOTH);
        bookAddBackground.setIcon(new ImageIcon(scaledImage));
        bookAddBackground.setBounds(0, 0, 750, 950);
        contentPane.add(bookAddBackground);

        // Welcome Label for book addition
        JLabel lblWelcome = new JLabel("Add Book");
        lblWelcome.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 25));
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        lblWelcome.setBounds(75, 100, 600, 100);
        lblWelcome.setForeground(Color.BLACK);
        lblWelcome.setOpaque(false);
        lblWelcome.setBackground(new Color(255, 255, 255, 150)); // Set light transparent background
        bookAddBackground.add(lblWelcome, BorderLayout.NORTH);

        // Light transparent background for welcome label
        JLabel welcomeBackground = new JLabel();
        welcomeBackground.setOpaque(true);
        welcomeBackground.setBackground(new Color(255, 255, 255, 150));
        welcomeBackground.setBounds(70, 100, 610, 100);
        bookAddBackground.add(welcomeBackground, BorderLayout.CENTER);

        // Light transparent background for Add Panel section
        JPanel addPanel = new JPanel();
        addPanel.setOpaque(true);
        addPanel.setBackground(new Color(255, 255, 255, 150));
        addPanel.setBounds(120, 250, 500, 500);
        addPanel.setLayout(null);
        bookAddBackground.add(addPanel);

        // JLabels and JTextFields for book information
        JLabel lblTitle = new JLabel("Title:");
        lblTitle.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 18));
        lblTitle.setBounds(50, 50, 100, 30);
        lblTitle.setForeground(Color.BLACK);
        addPanel.add(lblTitle);

        JTextField titleField = new JTextField();
        titleField.setFont(new Font("Monospaced", Font.PLAIN, 16));
        titleField.setBounds(150, 50, 300, 30);
        addPanel.add(titleField);

        JLabel lblAuthor = new JLabel("Author:");
        lblAuthor.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 18));
        lblAuthor.setBounds(50, 100, 100, 30);
        lblAuthor.setForeground(Color.BLACK);
        addPanel.add(lblAuthor);

        JTextField authorField = new JTextField();
        authorField.setFont(new Font("Monospaced", Font.PLAIN, 16));
        authorField.setBounds(150, 100, 300, 30);
        addPanel.add(authorField);

        JLabel lblISBN = new JLabel("ISBN:");
        lblISBN.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 18));
        lblISBN.setBounds(50, 150, 100, 30);
        lblISBN.setForeground(Color.BLACK);
        addPanel.add(lblISBN);

        JTextField isbnField = new JTextField();
        isbnField.setFont(new Font("Monospaced", Font.PLAIN, 16));
        isbnField.setBounds(150, 150, 300, 30);
        addPanel.add(isbnField);

        JLabel lblPublishYear = new JLabel("Publish Year:");
        lblPublishYear.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 18));
        lblPublishYear.setBounds(50, 200, 150, 30);
        lblPublishYear.setForeground(Color.BLACK);
        addPanel.add(lblPublishYear);

        JTextField publishYearField = new JTextField();
        publishYearField.setFont(new Font("Monospaced", Font.PLAIN, 16));
        publishYearField.setBounds(200, 200, 250, 30);
        addPanel.add(publishYearField);

        JLabel lblImageRef = new JLabel("Image Reference:");
        lblImageRef.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 18));
        lblImageRef.setBounds(50, 250, 200, 30);
        lblImageRef.setForeground(Color.BLACK);
        addPanel.add(lblImageRef);

        JTextField imageRefField = new JTextField();
        imageRefField.setFont(new Font("Monospaced", Font.PLAIN, 16));
        imageRefField.setBounds(250, 250, 200, 30);
        addPanel.add(imageRefField);

        // JButton for adding the book
        JButton btnSave = new JButton("Add");
        btnSave.setFont(new Font("Monospaced", Font.PLAIN, 16));
        btnSave.setBounds(200, 300, 100, 30); // Adjusted position
        btnSave.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Perform addition operation
                String title = titleField.getText();
                String author = authorField.getText();
                String isbn = isbnField.getText();
                String publishYear = publishYearField.getText();
                String imageRef = imageRefField.getText();
                boolean added = library.addBook_GUI(title, author, isbn, publishYear, imageRef);
                if (added) {
                    JOptionPane.showMessageDialog(contentPane, "Book Successfully Added!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    // Clear text fields after successful addition
                    titleField.setText("");
                    authorField.setText("");
                    isbnField.setText("");
                    publishYearField.setText("");
                    imageRefField.setText("");
                } else {
                    JOptionPane.showMessageDialog(contentPane, "Failed to Add Book. Please Try Again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        addPanel.add(btnSave);

        // Back to Main Menu Button
        JButton btnBack = new JButton("Back to Book Management");
        btnBack.setFont(new Font("Monospaced", Font.PLAIN, 16));
        btnBack.setBounds(100, 350, 300, 50); // Adjusted position
        btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();

                bookManagement();
            }
        });
        addPanel.add(btnBack);

        // Sign Out Button
        JButton btnSignOut = new JButton("Sign Out");
        btnSignOut.setFont(new Font("Monospaced", Font.PLAIN, 16));
        btnSignOut.setBounds(100, 420, 300, 50); // Adjusted position
        btnSignOut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSignOut.setBorderPainted(false); // Remove border
        btnSignOut.setContentAreaFilled(false); // Make it transparent
        btnSignOut.setFocusPainted(false); // Remove focus border
        btnSignOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();
                
                signIn_UpMenu();
            }
        });
        addPanel.add(btnSignOut);

        // Make sure to revalidate and repaint the content pane
        contentPane.revalidate();
        contentPane.repaint();
    }

    // Add Staff Admin
    private void addStaff() {
        // Clear existing content pane
        contentPane.removeAll();
        contentPane.revalidate();
        contentPane.repaint();

        // Create a new background label
        JLabel staffAddBackground = new JLabel();
        ImageIcon backgroundImage = new ImageIcon("Add.JPG");
        Image scaledImage = backgroundImage.getImage().getScaledInstance(750, 950, Image.SCALE_SMOOTH);
        staffAddBackground.setIcon(new ImageIcon(scaledImage));
        staffAddBackground.setBounds(0, 0, 750, 950);
        contentPane.add(staffAddBackground);

        // Welcome Label for staff addition
        JLabel lblWelcome = new JLabel("Add Staff");
        lblWelcome.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 25));
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        lblWelcome.setBounds(75, 100, 600, 100);
        lblWelcome.setForeground(Color.BLACK);
        lblWelcome.setOpaque(false);
        lblWelcome.setBackground(new Color(255, 255, 255, 150)); // Set light transparent background
        staffAddBackground.add(lblWelcome, BorderLayout.NORTH);

        // Light transparent background for welcome label
        JLabel welcomeBackground = new JLabel();
        welcomeBackground.setOpaque(true);
        welcomeBackground.setBackground(new Color(255, 255, 255, 150));
        welcomeBackground.setBounds(70, 100, 610, 100);
        staffAddBackground.add(welcomeBackground, BorderLayout.CENTER);

        // Light transparent background for Add Panel section
        JPanel addPanel = new JPanel();
        addPanel.setOpaque(true);
        addPanel.setBackground(new Color(255, 255, 255, 150));
        addPanel.setBounds(120, 250, 500, 620);
        addPanel.setLayout(null);
        staffAddBackground.add(addPanel);

        // JLabels and JTextFields for staff information
        JLabel lblName = new JLabel("Name:");
        lblName.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 18));
        lblName.setBounds(50, 50, 100, 30);
        lblName.setForeground(Color.BLACK);
        addPanel.add(lblName);

        JTextField nameField = new JTextField();
        nameField.setFont(new Font("Monospaced", Font.PLAIN, 16));
        nameField.setBounds(150, 50, 300, 30);
        addPanel.add(nameField);

        JLabel lblSurname = new JLabel("Surname:");
        lblSurname.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 18));
        lblSurname.setBounds(50, 100, 100, 30);
        lblSurname.setForeground(Color.BLACK);
        addPanel.add(lblSurname);

        JTextField surnameField = new JTextField();
        surnameField.setFont(new Font("Monospaced", Font.PLAIN, 16));
        surnameField.setBounds(150, 100, 300, 30);
        addPanel.add(surnameField);

        JLabel lblPhoneNumber = new JLabel("Phone Number:");
        lblPhoneNumber.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 18));
        lblPhoneNumber.setBounds(50, 150, 150, 30);
        lblPhoneNumber.setForeground(Color.BLACK);
        addPanel.add(lblPhoneNumber);
        
        // JLabel for the fixed part of the phone number
        JLabel lblPhonePrefix = new JLabel("+(90)");
        lblPhonePrefix.setFont(new Font("Monospaced", Font.PLAIN, 16));
        lblPhonePrefix.setBounds(200, 150, 60, 30);
        addPanel.add(lblPhonePrefix);

        // JTextField for the user-entered part of the phone number
        JTextField phoneNumberField = new JTextField();
        phoneNumberField.setFont(new Font("Monospaced", Font.PLAIN, 16));
        phoneNumberField.setBounds(250, 150, 200, 30);
        addPanel.add(phoneNumberField);


        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 18));
        lblEmail.setBounds(50, 200, 100, 30);
        lblEmail.setForeground(Color.BLACK);
        addPanel.add(lblEmail);

        JTextField emailField = new JTextField();
        emailField.setFont(new Font("Monospaced", Font.PLAIN, 16));
        emailField.setBounds(150, 200, 300, 30);
        addPanel.add(emailField);

        JLabel lblAddress = new JLabel("Address:");
        lblAddress.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 18));
        lblAddress.setBounds(50, 250, 100, 30);
        lblAddress.setForeground(Color.BLACK);
        addPanel.add(lblAddress);

        JTextField addressField = new JTextField();
        addressField.setFont(new Font("Monospaced", Font.PLAIN, 16));
        addressField.setBounds(150, 250, 300, 30);
        addPanel.add(addressField);

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 18));
        lblUsername.setBounds(50, 300, 150, 30);
        lblUsername.setForeground(Color.BLACK);
        addPanel.add(lblUsername);

        JTextField usernameField = new JTextField();
        usernameField.setFont(new Font("Monospaced", Font.PLAIN, 16));
        usernameField.setBounds(200, 300, 250, 30);
        addPanel.add(usernameField);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 18));
        lblPassword.setBounds(50, 350, 150, 30);
        lblPassword.setForeground(Color.BLACK);
        addPanel.add(lblPassword);

        JTextField passwordField = new JTextField();
        passwordField.setFont(new Font("Monospaced", Font.PLAIN, 16));
        passwordField.setBounds(200, 350, 250, 30);
        addPanel.add(passwordField);

        JLabel lblSalary = new JLabel("Salary:");
        lblSalary.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 18));
        lblSalary.setBounds(50, 400, 100, 30);
        lblSalary.setForeground(Color.BLACK);
        addPanel.add(lblSalary);

        JTextField salaryField = new JTextField();
        salaryField.setFont(new Font("Monospaced", Font.PLAIN, 16));
        salaryField.setBounds(150, 400, 300, 30);
        addPanel.add(salaryField);

        // JButton for adding the staff
        JButton btnSave = new JButton("Add");
        btnSave.setFont(new Font("Monospaced", Font.PLAIN, 16));
        btnSave.setBounds(200, 450, 100, 30); // Adjusted position
        btnSave.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Perform addition operation
                String name = nameField.getText();
                String surname = surnameField.getText();
                String phoneNumber = phoneNumberField.getText();
                String email = emailField.getText();
                String address = addressField.getText();
                String username = usernameField.getText();
                String password = passwordField.getText();
                double salary = Double.parseDouble(salaryField.getText()); // Parse salary to double
                
                // Inform the staff about the password via email
                JOptionPane.showMessageDialog(contentPane, "Password will be sent to the staff by email for security reasons.", "Information", JOptionPane.INFORMATION_MESSAGE);

                boolean added = library.addStaff_GUI(name, surname, phoneNumber, address, email, username, password, salary);
                if (added) {
                    JOptionPane.showMessageDialog(contentPane, "Staff Successfully Added!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    // Clear text fields after successful addition
                    nameField.setText("");
                    surnameField.setText("");
                    phoneNumberField.setText("");
                    emailField.setText("");
                    usernameField.setText("");
                    passwordField.setText("");
                    salaryField.setText("");
                    addressField.setText("");
                } else {
                    JOptionPane.showMessageDialog(contentPane, "Failed to Add Staff. Please Try Again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        addPanel.add(btnSave);

        // Back to Main Menu Button
        JButton btnBack = new JButton("Back to Staff Management");
        btnBack.setFont(new Font("Monospaced", Font.PLAIN, 16));
        btnBack.setBounds(100, 500, 300, 50); // Adjusted position
        btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();

                staffManagement();
            }
        });
        addPanel.add(btnBack);

        // Sign Out Button
        JButton btnSignOut = new JButton("Sign Out");
        btnSignOut.setFont(new Font("Monospaced", Font.PLAIN, 16));
        btnSignOut.setBounds(100, 560, 300, 50); // Adjusted position
        btnSignOut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSignOut.setBorderPainted(false); // Remove border
        btnSignOut.setContentAreaFilled(false); // Make it transparent
        btnSignOut.setFocusPainted(false); // Remove focus border
        btnSignOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();
                
                signIn_UpMenu();
            }
        });
        addPanel.add(btnSignOut);

        // Make sure to revalidate and repaint the content pane
        contentPane.revalidate();
        contentPane.repaint();
    }

    // Add Member Admin/Staff
    private void addMember() {
        // Clear existing content pane
        contentPane.removeAll();
        contentPane.revalidate();
        contentPane.repaint();

        // Create a new background label
        JLabel memberAddBackground = new JLabel();
        ImageIcon backgroundImage = new ImageIcon("Add.JPG");
        Image scaledImage = backgroundImage.getImage().getScaledInstance(750, 950, Image.SCALE_SMOOTH);
        memberAddBackground.setIcon(new ImageIcon(scaledImage));
        memberAddBackground.setBounds(0, 0, 750, 950);
        contentPane.add(memberAddBackground);

        // Welcome Label for member addition
        JLabel lblWelcome = new JLabel("Add Member");
        lblWelcome.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 25));
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        lblWelcome.setBounds(75, 100, 600, 100);
        lblWelcome.setForeground(Color.BLACK);
        lblWelcome.setOpaque(false);
        lblWelcome.setBackground(new Color(255, 255, 255, 150)); // Set light transparent background
        memberAddBackground.add(lblWelcome, BorderLayout.NORTH);

        // Light transparent background for welcome label
        JLabel welcomeBackground = new JLabel();
        welcomeBackground.setOpaque(true);
        welcomeBackground.setBackground(new Color(255, 255, 255, 150));
        welcomeBackground.setBounds(70, 100, 610, 100);
        memberAddBackground.add(welcomeBackground, BorderLayout.CENTER);

        // Light transparent background for Add Panel section
        JPanel addPanel = new JPanel();
        addPanel.setOpaque(true);
        addPanel.setBackground(new Color(255, 255, 255, 150));
        addPanel.setBounds(120, 250, 500, 520); // Adjusted size
        addPanel.setLayout(null);
        memberAddBackground.add(addPanel);

        // JLabels and JTextFields for member information
        JLabel lblName = new JLabel("Name:");
        lblName.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 18));
        lblName.setBounds(50, 50, 100, 30);
        lblName.setForeground(Color.BLACK);
        addPanel.add(lblName);

        JTextField nameField = new JTextField();
        nameField.setFont(new Font("Monospaced", Font.PLAIN, 16));
        nameField.setBounds(150, 50, 300, 30);
        addPanel.add(nameField);

        JLabel lblSurname = new JLabel("Surname:");
        lblSurname.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 18));
        lblSurname.setBounds(50, 100, 100, 30);
        lblSurname.setForeground(Color.BLACK);
        addPanel.add(lblSurname);

        JTextField surnameField = new JTextField();
        surnameField.setFont(new Font("Monospaced", Font.PLAIN, 16));
        surnameField.setBounds(150, 100, 300, 30);
        addPanel.add(surnameField);

        JLabel lblPhoneNumber = new JLabel("Phone Number:");
        lblPhoneNumber.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 18));
        lblPhoneNumber.setBounds(50, 150, 150, 30);
        lblPhoneNumber.setForeground(Color.BLACK);
        addPanel.add(lblPhoneNumber);
        
        // JLabel for the fixed part of the phone number
        JLabel lblPhonePrefix = new JLabel("+(90)");
        lblPhonePrefix.setFont(new Font("Monospaced", Font.PLAIN, 16));
        lblPhonePrefix.setBounds(200, 150, 60, 30);
        addPanel.add(lblPhonePrefix);

        // JTextField for the user-entered part of the phone number
        JTextField phoneNumberField = new JTextField();
        phoneNumberField.setFont(new Font("Monospaced", Font.PLAIN, 16));
        phoneNumberField.setBounds(250, 150, 200, 30);
        addPanel.add(phoneNumberField);


        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 18));
        lblEmail.setBounds(50, 200, 100, 30);
        lblEmail.setForeground(Color.BLACK);
        addPanel.add(lblEmail);

        JTextField emailField = new JTextField();
        emailField.setFont(new Font("Monospaced", Font.PLAIN, 16));
        emailField.setBounds(150, 200, 300, 30);
        addPanel.add(emailField);

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 18));
        lblUsername.setBounds(50, 250, 150, 30);
        lblUsername.setForeground(Color.BLACK);
        addPanel.add(lblUsername);

        JTextField usernameField = new JTextField();
        usernameField.setFont(new Font("Monospaced", Font.PLAIN, 16));
        usernameField.setBounds(200, 250, 250, 30);
        addPanel.add(usernameField);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 18));
        lblPassword.setBounds(50, 300, 150, 30);
        lblPassword.setForeground(Color.BLACK);
        addPanel.add(lblPassword);

        JTextField passwordField = new JTextField();
        passwordField.setFont(new Font("Monospaced", Font.PLAIN, 16));
        passwordField.setBounds(200, 300, 250, 30);
        addPanel.add(passwordField);

        // JButton for adding the member
        JButton btnSave = new JButton("Add");
        btnSave.setFont(new Font("Monospaced", Font.PLAIN, 16));
        btnSave.setBounds(200, 350, 100, 30); // Adjusted position
        btnSave.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Perform addition operation
                String name = nameField.getText();
                String surname = surnameField.getText();
                String phoneNumber = phoneNumberField.getText();
                String email = emailField.getText();
                String username = usernameField.getText();
                String password = passwordField.getText();
                
                // Inform the member about the password via email
                JOptionPane.showMessageDialog(contentPane, "Password will be sent to the member by email for security reasons.", "Information", JOptionPane.INFORMATION_MESSAGE);

                boolean added = library.addMember_GUI(name, surname, phoneNumber, email, username, password);
                if (added) {
                    JOptionPane.showMessageDialog(contentPane, "Member Successfully Added!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    // Clear text fields after successful addition
                    nameField.setText("");
                    surnameField.setText("");
                    phoneNumberField.setText("");
                    emailField.setText("");
                    usernameField.setText("");
                    passwordField.setText("");
                } else {
                    JOptionPane.showMessageDialog(contentPane, "Failed to Add Member. Please Try Again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        addPanel.add(btnSave);

        // Back to Main Menu Button
        JButton btnBack = new JButton("Back to Member Management");
        btnBack.setFont(new Font("Monospaced", Font.PLAIN, 16));
        btnBack.setBounds(100, 400, 300, 50); // Adjusted position
        btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();

                memberManagement();
            }
        });
        addPanel.add(btnBack);

        // Sign Out Button
        JButton btnSignOut = new JButton("Sign Out");
        btnSignOut.setFont(new Font("Monospaced", Font.PLAIN, 16));
        btnSignOut.setBounds(100, 460, 300, 50); // Adjusted position
        btnSignOut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSignOut.setBorderPainted(false); // Remove border
        btnSignOut.setContentAreaFilled(false); // Make it transparent
        btnSignOut.setFocusPainted(false); // Remove focus border
        btnSignOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();
                
                signIn_UpMenu();
            }
        });
        addPanel.add(btnSignOut);

        // Make sure to revalidate and repaint the content pane
        contentPane.revalidate();
        contentPane.repaint();
    }
    
    // Salary Calculation Admin
    private void salaryCalculationStaff() {
        // Clear existing content pane
        contentPane.removeAll();
        contentPane.revalidate();
        contentPane.repaint();

        // Create a new background label
        JLabel salaryCalcBackground = new JLabel();
        ImageIcon backgroundImage = new ImageIcon("Admin.JPG");
        Image scaledImage = backgroundImage.getImage().getScaledInstance(750, 950, Image.SCALE_SMOOTH);
        salaryCalcBackground.setIcon(new ImageIcon(scaledImage));
        salaryCalcBackground.setBounds(0, 0, 750, 950);
        contentPane.add(salaryCalcBackground);

        // Welcome Label for salary calculation
        JLabel lblWelcome = new JLabel("Staff Salary Calculation");
        lblWelcome.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 25));
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        lblWelcome.setBounds(75, 100, 600, 100);
        lblWelcome.setForeground(Color.BLACK);
        lblWelcome.setOpaque(false);
        lblWelcome.setBackground(new Color(255, 255, 255, 150)); // Set light transparent background
        salaryCalcBackground.add(lblWelcome, BorderLayout.NORTH);

        // Light transparent background for welcome label
        JLabel welcomeBackground = new JLabel();
        welcomeBackground.setOpaque(true);
        welcomeBackground.setBackground(new Color(255, 255, 255, 150));
        welcomeBackground.setBounds(70, 100, 610, 100);
        salaryCalcBackground.add(welcomeBackground, BorderLayout.CENTER);

        // Light transparent background for calculation Panel section
        JPanel calculationPanel = new JPanel();
        calculationPanel.setOpaque(true);
        calculationPanel.setBackground(new Color(255, 255, 255, 150));
        calculationPanel.setBounds(120, 250, 500, 550);
        calculationPanel.setLayout(null);
        salaryCalcBackground.add(calculationPanel);

        // JLabels for salary calculation
        //-----------------------------------------------------------------------------------------
        int y = 50;
        JLabel lblStaffname = new JLabel("Staff Username:");
        lblStaffname.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 18));
        lblStaffname.setBounds(100, y, 200, 30); // Increased x-coordinate
        lblStaffname.setForeground(Color.BLACK);
        calculationPanel.add(lblStaffname);

        JTextField txtstaffnameValue = new JTextField();
        txtstaffnameValue.setFont(new Font("Monospaced", Font.PLAIN, 16));
        txtstaffnameValue.setBounds(300, y, 100, 30); // Increased x-coordinate
        calculationPanel.add(txtstaffnameValue);
        //-----------------------------------------------------------------------------------------
        y = y+50;
        JLabel lblMorningHours = new JLabel("Morning Hours:");
        lblMorningHours.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 18));
        lblMorningHours.setBounds(100, y, 200, 30); // Increased x-coordinate
        lblMorningHours.setForeground(Color.BLACK);
        calculationPanel.add(lblMorningHours);

        JTextField txtMorningHoursValue = new JTextField();
        txtMorningHoursValue.setFont(new Font("Monospaced", Font.PLAIN, 16));
        txtMorningHoursValue.setBounds(300, y, 100, 30); // Increased x-coordinate
        calculationPanel.add(txtMorningHoursValue);

        y = y+50;
        JLabel lblNoonHours = new JLabel("Noon Hours:");
        lblNoonHours.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 18));
        lblNoonHours.setBounds(100, y, 150, 30); // Increased x-coordinate
        lblNoonHours.setForeground(Color.BLACK);
        calculationPanel.add(lblNoonHours);

        JTextField txtNoonHoursValue = new JTextField();
        txtNoonHoursValue.setFont(new Font("Monospaced", Font.PLAIN, 16));
        txtNoonHoursValue.setBounds(300, y, 100, 30); // Increased x-coordinate
        calculationPanel.add(txtNoonHoursValue);

        y = y+50;
        JLabel lblNightHours = new JLabel("Night Hours:");
        lblNightHours.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 18));
        lblNightHours.setBounds(100, y, 150, 30); // Increased x-coordinate
        lblNightHours.setForeground(Color.BLACK);
        calculationPanel.add(lblNightHours);

        JTextField txtNightHoursValue = new JTextField();
        txtNightHoursValue.setFont(new Font("Monospaced", Font.PLAIN, 16));
        txtNightHoursValue.setBounds(300, y, 100, 30); // Increased x-coordinate
        calculationPanel.add(txtNightHoursValue);

        // JLabel for Bonus
        y = y+50;
        JLabel lblBonus = new JLabel("Bonus:");
        lblBonus.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 18));
        lblBonus.setBounds(100, y, 100, 30); // Increased x-coordinate
        lblBonus.setForeground(Color.BLACK);
        calculationPanel.add(lblBonus);

        // JTextField for Bonus Amount
        JTextField bonusAmountField = new JTextField();
        bonusAmountField.setFont(new Font("Monospaced", Font.PLAIN, 16));
        bonusAmountField.setBounds(300, y, 60, 30); // Increased x-coordinate
        calculationPanel.add(bonusAmountField);

        // JTextField for Bonus Value
        JTextField txtBonusValue = new JTextField("$");
        txtBonusValue.setFont(new Font("Monospaced", Font.PLAIN, 16));
        txtBonusValue.setBounds(360, y, 40, 30); // Increased x-coordinate
        txtBonusValue.setEditable(false); // Set editable to false
        calculationPanel.add(txtBonusValue);

        // JLabel for Deduction
        y = y+50;
        JLabel lblDeduction = new JLabel("Deduction:");
        lblDeduction.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 18));
        lblDeduction.setBounds(100, y, 200, 30); // Increased x-coordinate
        lblDeduction.setForeground(Color.BLACK);
        calculationPanel.add(lblDeduction);

        // JTextField for Deduction Amount
        JTextField deductionAmountField = new JTextField();
        deductionAmountField.setFont(new Font("Monospaced", Font.PLAIN, 16));
        deductionAmountField.setBounds(300, y, 60, 30); // Increased x-coordinate
        calculationPanel.add(deductionAmountField);

        // JTextField for Deduction Value
        JTextField txtDeductionValue = new JTextField("$");
        txtDeductionValue.setFont(new Font("Monospaced", Font.PLAIN, 16));
        txtDeductionValue.setBounds(360, y, 40, 30); // Increased x-coordinate
        txtDeductionValue.setEditable(false); // Set editable to false
        calculationPanel.add(txtDeductionValue);
        
        // JButton for saving the calculation
        y = y+60;
        JButton btnSave = new JButton("Save");
        btnSave.setFont(new Font("Monospaced", Font.PLAIN, 16));
        btnSave.setBounds(200, y, 100, 30); // Adjusted position
        btnSave.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
                // Perform saving operation
            	String staffusername = txtstaffnameValue.getText();
                int morning = Integer.parseInt(txtMorningHoursValue.getText());
                int noon= Integer.parseInt(txtMorningHoursValue.getText());
                int night= Integer.parseInt(txtNightHoursValue.getText());
                int bonus= Integer.parseInt(bonusAmountField.getText());
                int dedication= Integer.parseInt(deductionAmountField.getText());
                // Display pop-up message based on the result of the saving operation
            	boolean saveISsuccessful =library.StaffCalculateSalary(/*USERNAME*/staffusername, morning, noon, night, bonus, dedication);
            	if (saveISsuccessful == true) {
                    JOptionPane.showMessageDialog(contentPane, "Calculation saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    // Clear text fields after successful addition
                    txtMorningHoursValue.setText("");
                    txtNoonHoursValue.setText("");
                    txtNightHoursValue.setText("");
                    bonusAmountField.setText("");
                    deductionAmountField.setText("");
                    
                } else {
                    JOptionPane.showMessageDialog(contentPane, "Failed to save calculation. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        calculationPanel.add(btnSave);

        // Back to Main Menu Button
        y = y+40;
        JButton btnBack = new JButton("Back to Admin Menu");
        btnBack.setFont(new Font("Monospaced", Font.PLAIN, 16));
        btnBack.setBounds(100, y, 300, 50);
        btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();

                staffManagement();
            }
        });
        calculationPanel.add(btnBack);
        
        // Sign Out Button
        y = y+60;
        JButton btnSignOut = new JButton("Sign Out");
        btnSignOut.setFont(new Font("Monospaced", Font.PLAIN, 16));
        btnSignOut.setBounds(100, y, 300, 50);
        btnSignOut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSignOut.setBorderPainted(false); // Remove border
        btnSignOut.setContentAreaFilled(false); // Make it transparent
        btnSignOut.setFocusPainted(false); // Remove focus border
        btnSignOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();
                
                signIn_UpMenu();
            }
        });
        calculationPanel.add(btnSignOut);

        // Make sure to revalidate and repaint the content pane
        contentPane.revalidate();
        contentPane.repaint();
    } 

    // Remove Book Admin/Staff
    private void removeBook() {
        // Clear existing content pane
        contentPane.removeAll();
        contentPane.revalidate();
        contentPane.repaint();

        // Create a new background label
        JLabel bookRemoveBackground = new JLabel();
        ImageIcon backgroundImage = new ImageIcon("Remove.JPG");
        Image scaledImage = backgroundImage.getImage().getScaledInstance(750, 950, Image.SCALE_SMOOTH);
        bookRemoveBackground.setIcon(new ImageIcon(scaledImage));
        bookRemoveBackground.setBounds(0, 0, 750, 950);
        contentPane.add(bookRemoveBackground);

        // Welcome Label for book removal
        JLabel lblWelcome = new JLabel("Remove Book");
        lblWelcome.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 25));
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        lblWelcome.setBounds(75, 100, 600, 100);
        lblWelcome.setForeground(Color.BLACK);
        lblWelcome.setOpaque(false);
        lblWelcome.setBackground(new Color(255, 255, 255, 150)); // Set light transparent background
        bookRemoveBackground.add(lblWelcome, BorderLayout.NORTH);

        // Light transparent background for welcome label
        JLabel welcomeBackground = new JLabel();
        welcomeBackground.setOpaque(true);
        welcomeBackground.setBackground(new Color(255, 255, 255, 150));
        welcomeBackground.setBounds(70, 100, 610, 100);
        bookRemoveBackground.add(welcomeBackground, BorderLayout.CENTER);

        // Light transparent background for Remove Panel section
        JPanel removePanel = new JPanel();
        removePanel.setOpaque(true);
        removePanel.setBackground(new Color(255, 255, 255, 150));
        removePanel.setBounds(120, 250, 500, 400); 
        removePanel.setLayout(null);
        bookRemoveBackground.add(removePanel);

        // JLabel for displaying instructions
        JLabel lblInstructions = new JLabel("Enter Book ISBN to Remove:");
        lblInstructions.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 18));
        lblInstructions.setHorizontalAlignment(SwingConstants.CENTER);
        lblInstructions.setBounds(100, 50, 300, 30);
        lblInstructions.setForeground(Color.BLACK);
        removePanel.add(lblInstructions);

        // JTextField for entering book ISBN
        JTextField isbnField = new JTextField();
        isbnField.setFont(new Font("Monospaced", Font.PLAIN, 16));
        isbnField.setBounds(100, 100, 300, 30);
        removePanel.add(isbnField);

        // JButton for removing the book
        JButton btnSave = new JButton("Remove");
        btnSave.setFont(new Font("Monospaced", Font.PLAIN, 16));
        btnSave.setBounds(200, 150, 100, 30); // Adjusted position
        btnSave.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Perform removal operation
                String isbn = isbnField.getText();
                boolean removed = library.removeBookByISBN_GUI(isbn);
                if (removed) {
                    JOptionPane.showMessageDialog(contentPane, "Book Successfully Removed!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(contentPane, "Book Not Found or Removal Failed. Please Try Again.", "Error", JOptionPane.ERROR_MESSAGE);
                    // Clear text fields after successful addition
                    isbnField.setText("");
                }
            }
        });
        removePanel.add(btnSave);

        // Back to Main Menu Button
        JButton btnBack = new JButton("Back to Book Managament ");
        btnBack.setFont(new Font("Monospaced", Font.PLAIN, 16));
        btnBack.setBounds(100, 250, 300, 50); // Adjusted position
        btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();
                
                bookManagement();
            }
        });
        removePanel.add(btnBack);

        // Sign Out Button
        JButton btnSignOut = new JButton("Sign Out");
        btnSignOut.setFont(new Font("Monospaced", Font.PLAIN, 16));
        btnSignOut.setBounds(100, 320, 300, 50); // Adjusted position
        btnSignOut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSignOut.setBorderPainted(false); // Remove border
        btnSignOut.setContentAreaFilled(false); // Make it transparent
        btnSignOut.setFocusPainted(false); // Remove focus border
        btnSignOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();
                
                signIn_UpMenu();
            }
        });
        removePanel.add(btnSignOut);

        // Make sure to revalidate and repaint the content pane
        contentPane.revalidate();
        contentPane.repaint();
    }

    // Remove Staff Admin
    private void removeStaff() {
        // Clear existing content pane
        contentPane.removeAll();
        contentPane.revalidate();
        contentPane.repaint();

        // Create a new background label
        JLabel staffRemoveBackground = new JLabel();
        ImageIcon backgroundImage = new ImageIcon("Remove.JPG");
        Image scaledImage = backgroundImage.getImage().getScaledInstance(750, 950, Image.SCALE_SMOOTH);
        staffRemoveBackground.setIcon(new ImageIcon(scaledImage));
        staffRemoveBackground.setBounds(0, 0, 750, 950);
        contentPane.add(staffRemoveBackground);

        // Welcome Label for staff removal
        JLabel lblWelcome = new JLabel("Remove Staff");
        lblWelcome.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 25));
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        lblWelcome.setBounds(75, 100, 600, 100);
        lblWelcome.setForeground(Color.BLACK);
        lblWelcome.setOpaque(false);
        lblWelcome.setBackground(new Color(255, 255, 255, 150)); // Set light transparent background
        staffRemoveBackground.add(lblWelcome, BorderLayout.NORTH);

        // Light transparent background for welcome label
        JLabel welcomeBackground = new JLabel();
        welcomeBackground.setOpaque(true);
        welcomeBackground.setBackground(new Color(255, 255, 255, 150));
        welcomeBackground.setBounds(70, 100, 610, 100);
        staffRemoveBackground.add(welcomeBackground, BorderLayout.CENTER);

        // Light transparent background for Remove Panel section
        JPanel removePanel = new JPanel();
        removePanel.setOpaque(true);
        removePanel.setBackground(new Color(255, 255, 255, 150));
        removePanel.setBounds(120, 250, 500, 400); 
        removePanel.setLayout(null);
        staffRemoveBackground.add(removePanel);

        // JLabel for displaying instructions
        JLabel lblInstructions = new JLabel("Enter Staff Username to Remove:");
        lblInstructions.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 18));
        lblInstructions.setHorizontalAlignment(SwingConstants.CENTER);
        lblInstructions.setBounds(100, 50, 300, 30);
        lblInstructions.setForeground(Color.BLACK);
        removePanel.add(lblInstructions);

        // JTextField for entering staff username
        JTextField usernameField = new JTextField();
        usernameField.setFont(new Font("Monospaced", Font.PLAIN, 16));
        usernameField.setBounds(100, 100, 300, 30);
        removePanel.add(usernameField);

        // JButton for removing the staff
        JButton btnSave = new JButton("Remove");
        btnSave.setFont(new Font("Monospaced", Font.PLAIN, 16));
        btnSave.setBounds(200, 150, 100, 30); // Adjusted position
        btnSave.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Perform removal operation
                String username = usernameField.getText();
                boolean removed = false;
				removed = library.removeStaffByUsername_GUI(username);
                if (removed) {
                    JOptionPane.showMessageDialog(contentPane, "Staff Successfully Removed!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(contentPane, "Staff Not Found or Removal Failed. Please Try Again.", "Error", JOptionPane.ERROR_MESSAGE);
                    
                    // Clear text fields after successful addition
                    usernameField.setText("");

                }
            }
        });
        removePanel.add(btnSave);

        // Back to Main Menu Button
        JButton btnBack = new JButton("Back to Staff Management");
        btnBack.setFont(new Font("Monospaced", Font.PLAIN, 16));
        btnBack.setBounds(100, 250, 300, 50); // Adjusted position
        btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();
                
                staffManagement();
            }
        });
        removePanel.add(btnBack);

        // Sign Out Button
        JButton btnSignOut = new JButton("Sign Out");
        btnSignOut.setFont(new Font("Monospaced", Font.PLAIN, 16));
        btnSignOut.setBounds(100, 320, 300, 50); // Adjusted position
        btnSignOut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSignOut.setBorderPainted(false); // Remove border
        btnSignOut.setContentAreaFilled(false); // Make it transparent
        btnSignOut.setFocusPainted(false); // Remove focus border
        btnSignOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();
                
                signIn_UpMenu();
            }
        });
        removePanel.add(btnSignOut);

        // Make sure to revalidate and repaint the content pane
        contentPane.revalidate();
        contentPane.repaint();
    }

    // Remove Member Admin/Staff
    private void removeMember() {
        // Clear existing content pane
        contentPane.removeAll();
        contentPane.revalidate();
        contentPane.repaint();

        // Create a new background label
        JLabel memberRemoveBackground = new JLabel();
        ImageIcon backgroundImage = new ImageIcon("Remove.JPG");
        Image scaledImage = backgroundImage.getImage().getScaledInstance(750, 950, Image.SCALE_SMOOTH);
        memberRemoveBackground.setIcon(new ImageIcon(scaledImage));
        memberRemoveBackground.setBounds(0, 0, 750, 950);
        contentPane.add(memberRemoveBackground);

        // Welcome Label for member removal
        JLabel lblWelcome = new JLabel("Remove Member");
        lblWelcome.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 25));
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        lblWelcome.setBounds(75, 100, 600, 100);
        lblWelcome.setForeground(Color.BLACK);
        lblWelcome.setOpaque(false);
        lblWelcome.setBackground(new Color(255, 255, 255, 150)); // Set light transparent background
        memberRemoveBackground.add(lblWelcome, BorderLayout.NORTH);

        // Light transparent background for welcome label
        JLabel welcomeBackground = new JLabel();
        welcomeBackground.setOpaque(true);
        welcomeBackground.setBackground(new Color(255, 255, 255, 150));
        welcomeBackground.setBounds(70, 100, 610, 100);
        memberRemoveBackground.add(welcomeBackground, BorderLayout.CENTER);

        // Light transparent background for Remove Panel section
        JPanel removePanel = new JPanel();
        removePanel.setOpaque(true);
        removePanel.setBackground(new Color(255, 255, 255, 150));
        removePanel.setBounds(120, 250, 500, 400); 
        removePanel.setLayout(null);
        memberRemoveBackground.add(removePanel);

        // JLabel for displaying instructions
        JLabel lblInstructions = new JLabel("Enter Member Username to Remove:");
        lblInstructions.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 18));
        lblInstructions.setHorizontalAlignment(SwingConstants.CENTER);
        lblInstructions.setBounds(100, 50, 300, 30);
        lblInstructions.setForeground(Color.BLACK);
        removePanel.add(lblInstructions);

        // JTextField for entering member username
        JTextField usernameField = new JTextField();
        usernameField.setFont(new Font("Monospaced", Font.PLAIN, 16));
        usernameField.setBounds(100, 100, 300, 30);
        removePanel.add(usernameField);

        // JButton for removing the member
        JButton btnSave = new JButton("Remove");
        btnSave.setFont(new Font("Monospaced", Font.PLAIN, 16));
        btnSave.setBounds(200, 150, 100, 30); // Adjusted position
        btnSave.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Perform removal operation
                String username = usernameField.getText();
                boolean removed = false;
                removed = library.removeMemberByUsername_GUI(username);
                if (removed) {
                    JOptionPane.showMessageDialog(contentPane, "Member Successfully Removed!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(contentPane, "Member Not Found or Removal Failed. Please Try Again.", "Error", JOptionPane.ERROR_MESSAGE);
                    
                    // Clear text fields after successful addition
                    usernameField.setText("");
                }
            }
        });
        removePanel.add(btnSave);

        // Back to Main Menu Button
        JButton btnBack = new JButton("Back to Member Management");
        btnBack.setFont(new Font("Monospaced", Font.PLAIN, 16));
        btnBack.setBounds(100, 250, 300, 50); // Adjusted position
        btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();
                
                memberManagement();
            }
        });
        removePanel.add(btnBack);

        // Sign Out Button
        JButton btnSignOut = new JButton("Sign Out");
        btnSignOut.setFont(new Font("Monospaced", Font.PLAIN, 16));
        btnSignOut.setBounds(100, 320, 300, 50); // Adjusted position
        btnSignOut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSignOut.setBorderPainted(false); // Remove border
        btnSignOut.setContentAreaFilled(false); // Make it transparent
        btnSignOut.setFocusPainted(false); // Remove focus border
        btnSignOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();
                
                signIn_UpMenu();
            }
        });
        removePanel.add(btnSignOut);

        // Make sure to revalidate and repaint the content pane
        contentPane.revalidate();
        contentPane.repaint();
    }
    
    // Search Book Admin/Staff/Member
    private void searchBook() {
        // Clear existing content pane
        contentPane.removeAll();
        contentPane.revalidate();
        contentPane.repaint();

        // Create a new background label
        JLabel bookSearchBackground = new JLabel();
        ImageIcon backgroundImage = new ImageIcon("Search.JPG");
        Image scaledImage = backgroundImage.getImage().getScaledInstance(750, 950, Image.SCALE_SMOOTH);
        bookSearchBackground.setIcon(new ImageIcon(scaledImage));
        bookSearchBackground.setBounds(0, 0, 750, 950);
        contentPane.add(bookSearchBackground);

        // Welcome Label for book search
        JLabel lblWelcome = new JLabel("Search Books");
        lblWelcome.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 25));
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        lblWelcome.setBounds(75, 100, 600, 100);
        lblWelcome.setForeground(Color.BLACK);
        lblWelcome.setOpaque(false);
        lblWelcome.setBackground(new Color(255, 255, 255, 150)); // Set light transparent background
        bookSearchBackground.add(lblWelcome, BorderLayout.NORTH);

        // Light transparent background for welcome label
        JLabel welcomeBackground = new JLabel();
        welcomeBackground.setOpaque(true);
        welcomeBackground.setBackground(new Color(255, 255, 255, 150));
        welcomeBackground.setBounds(70, 100, 610, 100);
        bookSearchBackground.add(welcomeBackground, BorderLayout.CENTER);

        // Light transparent background for Search Panel section
        JPanel searchPanel = new JPanel();
        searchPanel.setOpaque(true);
        searchPanel.setBackground(new Color(255, 255, 255, 150));
        searchPanel.setBounds(120, 250, 500, 600); 
        searchPanel.setLayout(null);
        bookSearchBackground.add(searchPanel);

        // JTextArea to display found book information
        JTextArea foundBookTextArea = new JTextArea();
        foundBookTextArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        foundBookTextArea.setEditable(false);
        foundBookTextArea.setOpaque(false);
        JScrollPane scrollPane = new JScrollPane(foundBookTextArea);
        scrollPane.setBounds(60, 190, 380, 250);
        searchPanel.add(scrollPane);
        scrollPane.setVisible(false); 
        
        // Search Label
        JLabel lblSearch = new JLabel("Book Title/Author/ISBN:");
        lblSearch.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 20));
        lblSearch.setHorizontalAlignment(SwingConstants.CENTER);
        lblSearch.setBounds(100, 50, 300, 30);
        lblSearch.setForeground(Color.BLACK);
        searchPanel.add(lblSearch);

        // Text Field for search query
        JTextField searchField = new JTextField();
        searchField.setFont(new Font("Monospaced", Font.PLAIN, 16));
        searchField.setBounds(100, 100, 300, 30);
        searchPanel.add(searchField);

        // Search Button
        JButton btnSearch = new JButton("Search");
        btnSearch.setFont(new Font("Monospaced", Font.PLAIN, 16));
        btnSearch.setBounds(200, 150, 100, 30); // Adjusted position
        btnSearch.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Perform search operation
                String query = searchField.getText();
                boolean found = library.searchBook_GUI(query);
                if (found) {
                    JOptionPane.showMessageDialog(contentPane, "Book Successfully Found!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    // Show the found information below
                    foundBookTextArea.setText(library.viewFoundBook(query));
                    scrollPane.setVisible(true); // Show the scroll pane
                } else {
                    JOptionPane.showMessageDialog(contentPane, "Book Not Found. Please Try Again.", "Error", JOptionPane.ERROR_MESSAGE);
                    scrollPane.setVisible(false); // Hide the scroll pane if book not found
                }
            }
        });
        searchPanel.add(btnSearch);

        // Back to Main Menu Button
        JButton btnBack = new JButton("Back to Book Management");
        btnBack.setFont(new Font("Monospaced", Font.PLAIN, 16));
        btnBack.setBounds(100, 450, 300, 50); // Adjusted position
        btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();
                
                bookManagement();
            }
        });
        searchPanel.add(btnBack);

        // Sign Out Button
        JButton btnSignOut = new JButton("Sign Out");
        btnSignOut.setFont(new Font("Monospaced", Font.PLAIN, 16));
        btnSignOut.setBounds(100, 510, 300, 50); // Adjusted position
        btnSignOut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSignOut.setBorderPainted(false); // Remove border
        btnSignOut.setContentAreaFilled(false); // Make it transparent
        btnSignOut.setFocusPainted(false); // Remove focus border
        btnSignOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();
                
                signIn_UpMenu();
            }
        });
        searchPanel.add(btnSignOut);

        // Make sure to revalidate and repaint the content pane
        contentPane.revalidate();
        contentPane.repaint();
    }
   
    // Search Staff Admin/Staff
    private void searchStaff() {
        // Clear existing content pane
        contentPane.removeAll();
        contentPane.revalidate();
        contentPane.repaint();

        // Create a new background label
        JLabel staffSearchBackground = new JLabel();
        ImageIcon backgroundImage = new ImageIcon("Search.JPG");
        Image scaledImage = backgroundImage.getImage().getScaledInstance(750, 950, Image.SCALE_SMOOTH);
        staffSearchBackground.setIcon(new ImageIcon(scaledImage));
        staffSearchBackground.setBounds(0, 0, 750, 950);
        contentPane.add(staffSearchBackground);

        // Welcome Label for staff search
        JLabel lblWelcome = new JLabel("Search Staff");
        lblWelcome.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 25));
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        lblWelcome.setBounds(75, 100, 600, 100);
        lblWelcome.setForeground(Color.BLACK);
        lblWelcome.setOpaque(false);
        lblWelcome.setBackground(new Color(255, 255, 255, 150)); // Set light transparent background
        staffSearchBackground.add(lblWelcome, BorderLayout.NORTH);

        // Light transparent background for welcome label
        JLabel welcomeBackground = new JLabel();
        welcomeBackground.setOpaque(true);
        welcomeBackground.setBackground(new Color(255, 255, 255, 150));
        welcomeBackground.setBounds(70, 100, 610, 100);
        staffSearchBackground.add(welcomeBackground, BorderLayout.CENTER);

        // Light transparent background for Search Panel section
        JPanel searchPanel = new JPanel();
        searchPanel.setOpaque(true);
        searchPanel.setBackground(new Color(255, 255, 255, 150));
        searchPanel.setBounds(120, 250, 500, 600); 
        searchPanel.setLayout(null);
        staffSearchBackground.add(searchPanel);

        // JTextArea to display found staff information
        JTextArea foundStaffTextArea = new JTextArea();
        foundStaffTextArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        foundStaffTextArea.setEditable(false);
        foundStaffTextArea.setOpaque(false);
        JScrollPane scrollPane = new JScrollPane(foundStaffTextArea);
        scrollPane.setBounds(60, 190, 380, 130);
        searchPanel.add(scrollPane);
        scrollPane.setVisible(false); 
        
        // Search Label
        JLabel lblSearch = new JLabel("Enter Staff Username:");
        lblSearch.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 20));
        lblSearch.setHorizontalAlignment(SwingConstants.CENTER);
        lblSearch.setBounds(100, 50, 300, 30);
        lblSearch.setForeground(Color.BLACK);
        searchPanel.add(lblSearch);

        // Text Field for search query
        JTextField searchField = new JTextField();
        searchField.setFont(new Font("Monospaced", Font.PLAIN, 16));
        searchField.setBounds(100, 100, 300, 30);
        searchPanel.add(searchField);

        // Search Button
        JButton btnSearch = new JButton("Search");
        btnSearch.setFont(new Font("Monospaced", Font.PLAIN, 16));
        btnSearch.setBounds(200, 150, 100, 30); // Adjusted position
        btnSearch.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Perform search operation
                String searchKey = searchField.getText();
                boolean foundStaff = library.searchStaffByUsername_GUI(searchKey); // Find staff by username
                if (foundStaff == true) {
                    JOptionPane.showMessageDialog(contentPane, "Staff Successfully Found!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    // Show the found information below
                    String staffInfo = library.viewFoundStaff(searchKey);
                    foundStaffTextArea.setText(staffInfo);
                    scrollPane.setVisible(true); // Show the scroll pane
                } else {
                    JOptionPane.showMessageDialog(contentPane, "Staff Not Found. Please Try Again.", "Error", JOptionPane.ERROR_MESSAGE);
                    scrollPane.setVisible(false); // Hide the scroll pane if staff not found
                }
            }
        });
        searchPanel.add(btnSearch);

        // Back to Main Menu Button
        JButton btnBack = new JButton("Back to Staff Management");
        btnBack.setFont(new Font("Monospaced", Font.PLAIN, 16));
        btnBack.setBounds(100, 450, 300, 50); // Adjusted position
        btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();
                
                staffManagement();
            }
        });
        searchPanel.add(btnBack);

        // Sign Out Button
        JButton btnSignOut = new JButton("Sign Out");
        btnSignOut.setFont(new Font("Monospaced", Font.PLAIN, 16));
        btnSignOut.setBounds(100, 510, 300, 50); // Adjusted position
        btnSignOut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSignOut.setBorderPainted(false); // Remove border
        btnSignOut.setContentAreaFilled(false); // Make it transparent
        btnSignOut.setFocusPainted(false); // Remove focus border
        btnSignOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();
                
                signIn_UpMenu();
            }
        });
        searchPanel.add(btnSignOut);

        // Make sure to revalidate and repaint the content pane
        contentPane.revalidate();
        contentPane.repaint();
    }

    // Search Member Admin/Staff
    private void searchMember() {
        // Clear existing content pane
        contentPane.removeAll();
        contentPane.revalidate();
        contentPane.repaint();

        // Create a new background label
        JLabel memberSearchBackground = new JLabel();
        ImageIcon backgroundImage = new ImageIcon("Search.JPG");
        Image scaledImage = backgroundImage.getImage().getScaledInstance(750, 950, Image.SCALE_SMOOTH);
        memberSearchBackground.setIcon(new ImageIcon(scaledImage));
        memberSearchBackground.setBounds(0, 0, 750, 950);
        contentPane.add(memberSearchBackground);

        // Welcome Label for member search
        JLabel lblWelcome = new JLabel("Search Members");
        lblWelcome.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 25));
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        lblWelcome.setBounds(75, 100, 600, 100);
        lblWelcome.setForeground(Color.BLACK);
        lblWelcome.setOpaque(false);
        lblWelcome.setBackground(new Color(255, 255, 255, 150)); // Set light transparent background
        memberSearchBackground.add(lblWelcome, BorderLayout.NORTH);

        // Light transparent background for welcome label
        JLabel welcomeBackground = new JLabel();
        welcomeBackground.setOpaque(true);
        welcomeBackground.setBackground(new Color(255, 255, 255, 150));
        welcomeBackground.setBounds(70, 100, 610, 100);
        memberSearchBackground.add(welcomeBackground, BorderLayout.CENTER);

        // Light transparent background for Search Panel section
        JPanel searchPanel = new JPanel();
        searchPanel.setOpaque(true);
        searchPanel.setBackground(new Color(255, 255, 255, 150));
        searchPanel.setBounds(120, 250, 500, 600); 
        searchPanel.setLayout(null);
        memberSearchBackground.add(searchPanel);

        // JTextArea to display found member information
        JTextArea foundMemberTextArea = new JTextArea();
        foundMemberTextArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        foundMemberTextArea.setEditable(false);
        foundMemberTextArea.setOpaque(false);
        JScrollPane scrollPane = new JScrollPane(foundMemberTextArea);
        scrollPane.setBounds(60, 190, 380, 130);
        searchPanel.add(scrollPane);
        scrollPane.setVisible(false); 
        
        // Search Label
        JLabel lblSearch = new JLabel("Enter Member Username:");
        lblSearch.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 20));
        lblSearch.setHorizontalAlignment(SwingConstants.CENTER);
        lblSearch.setBounds(100, 50, 300, 30);
        lblSearch.setForeground(Color.BLACK);
        searchPanel.add(lblSearch);

        // Text Field for search Key
        JTextField searchField = new JTextField();
        searchField.setFont(new Font("Monospaced", Font.PLAIN, 16));
        searchField.setBounds(100, 100, 300, 30);
        searchPanel.add(searchField);

        // Search Button
        JButton btnSearch = new JButton("Search");
        btnSearch.setFont(new Font("Monospaced", Font.PLAIN, 16));
        btnSearch.setBounds(200, 150, 100, 30); // Adjusted position
        btnSearch.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Perform search operation
                String searchKey = searchField.getText();
                boolean foundMember = library.searchMemberByUsername_GUI(searchKey); // Find member by username
                if (foundMember == true) {
                    JOptionPane.showMessageDialog(contentPane, "Member Successfully Found!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    // Show the found information below
                    String memberInfo = library.viewFoundMember(searchKey);
                    foundMemberTextArea.setText(memberInfo);
                    scrollPane.setVisible(true); // Show the scroll pane
                } else {
                    JOptionPane.showMessageDialog(contentPane, "Member Not Found. Please Try Again.", "Error", JOptionPane.ERROR_MESSAGE);
                    scrollPane.setVisible(false); // Hide the scroll pane if member not found
                }
            }
        });   
        
        searchPanel.add(btnSearch);

        // Back to Main Menu Button
        JButton btnBack = new JButton("Back to Member Management");
        btnBack.setFont(new Font("Monospaced", Font.PLAIN, 16));
        btnBack.setBounds(100, 450, 300, 50); // Adjusted position
        btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();
                
                memberManagement();
            }
        });
        searchPanel.add(btnBack);

        // Sign Out Button
        JButton btnSignOut = new JButton("Sign Out");
        btnSignOut.setFont(new Font("Monospaced", Font.PLAIN, 16));
        btnSignOut.setBounds(100, 510, 300, 50); // Adjusted position
        btnSignOut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSignOut.setBorderPainted(false); // Remove border
        btnSignOut.setContentAreaFilled(false); // Make it transparent
        btnSignOut.setFocusPainted(false); // Remove focus border
        btnSignOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();
                
                signIn_UpMenu();
            }
        });
        searchPanel.add(btnSignOut);

        // Make sure to revalidate and repaint the content pane
        contentPane.revalidate();
        contentPane.repaint();
    }

    // checkIn and CheckOut Books Member
    private void checkInOutBooks() {
        // Clear existing content pane
        contentPane.removeAll();
        contentPane.revalidate();
        contentPane.repaint();

        // Create a new background label
        JLabel checkInOutBackground = new JLabel();
        ImageIcon backgroundImage = new ImageIcon("Member.JPG");
        Image scaledImage = backgroundImage.getImage().getScaledInstance(750, 950, Image.SCALE_SMOOTH);
        checkInOutBackground.setIcon(new ImageIcon(scaledImage));
        checkInOutBackground.setBounds(0, 0, 750, 950);
        contentPane.add(checkInOutBackground);

        // Add welcome label for check in/out books
        JLabel lblWelcome = new JLabel("Check In/Out Books");
        lblWelcome.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 25));
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        lblWelcome.setBounds(75, 100, 600, 100);
        lblWelcome.setForeground(Color.BLACK);
        lblWelcome.setOpaque(false);
        lblWelcome.setBackground(new Color(255, 255, 255, 150)); // Set light transparent background
        checkInOutBackground.add(lblWelcome, BorderLayout.NORTH);

        // Light transparent background for welcome label
        JLabel welcomeBackground = new JLabel();
        welcomeBackground.setOpaque(true);
        welcomeBackground.setBackground(new Color(255, 255, 255, 150));
        welcomeBackground.setBounds(70, 100, 610, 100);
        checkInOutBackground.add(welcomeBackground, BorderLayout.CENTER);
        
        // Light transparent background for Check In/Out Panel section
        JPanel checkInOutPanel = new JPanel();
        checkInOutPanel.setOpaque(true);
        checkInOutPanel.setBackground(new Color(255, 255, 255, 150));
        checkInOutPanel.setBounds(100, 250, 530, 500); 
        checkInOutPanel.setLayout(null);
        checkInOutBackground.add(checkInOutPanel);

    	// Question label
        JLabel lblQuestion = new JLabel("Do you want to CheckIn or CheckOut?");
        lblQuestion.setFont(new Font("Monospaced", Font.BOLD | Font.PLAIN, 18));
        lblQuestion.setBounds(70, 90, 400, 30);
        lblQuestion.setForeground(Color.BLACK);
        checkInOutPanel.add(lblQuestion);
        
        // Radio buttons for check in/out selection
        JRadioButton checkInRadioButton = new JRadioButton("Check In");
        checkInRadioButton.setFont(new Font("Monospaced", Font.PLAIN, 16));
        checkInRadioButton.setBounds(100, 150, 160, 30);
        checkInRadioButton.setForeground(Color.BLACK);
        checkInRadioButton.setOpaque(false); // Make it transparent
        checkInOutPanel.add(checkInRadioButton);

        JRadioButton checkOutRadioButton = new JRadioButton("Check Out");
        checkOutRadioButton.setFont(new Font("Monospaced", Font.PLAIN, 16));
        checkOutRadioButton.setBounds(290, 150, 160, 30);
        checkOutRadioButton.setForeground(Color.BLACK);
        checkOutRadioButton.setOpaque(false); // Make it transparent
        checkInOutPanel.add(checkOutRadioButton);

        ButtonGroup checkInOutGroup = new ButtonGroup();
        checkInOutGroup.add(checkInRadioButton);
        checkInOutGroup.add(checkOutRadioButton);

        // Label for ISBN input
        JLabel lblISBN = new JLabel("ISBN:");
        lblISBN.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 18));
        lblISBN.setHorizontalAlignment(SwingConstants.CENTER);
        lblISBN.setBounds(100, 220, 100, 30);
        lblISBN.setForeground(Color.BLACK);
        checkInOutPanel.add(lblISBN);

        // Text Field for ISBN input
        JTextField isbnField = new JTextField();
        isbnField.setFont(new Font("Monospaced", Font.PLAIN, 16));
        isbnField.setBounds(200, 220, 200, 30);
        checkInOutPanel.add(isbnField);

        // Done button
        JButton btnDone = new JButton("Done");
        btnDone.setFont(new Font("Monospaced", Font.PLAIN, 16));
        btnDone.setBounds(220, 300, 100, 30);
        btnDone.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnDone.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String isbn = isbnField.getText();
                if (checkInRadioButton.isSelected()) {
                    // Perform check-in operation
                    double fineAmount = library.checkInBook(USERNAME,isbn);
                    if (fineAmount == 0 ) {
                        JOptionPane.showMessageDialog(contentPane, "Book Checked In Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        try {
							library.deleteLoans(USERNAME, isbn);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
                    } else {
                        JOptionPane.showMessageDialog(contentPane, "Fine Amount to be Paid: "+fineAmount+" $", "Fine", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else if (checkOutRadioButton.isSelected()) {
                    // Perform check-out operation
                	boolean success = false;
                	try {
						success = library.CheckOutBook(USERNAME, isbn);
					} catch (IOException e1) {
						success = false;
						e1.printStackTrace();
					}
                    if (success == true) {
                        JOptionPane.showMessageDialog(contentPane, "Book Checked Out Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(contentPane, "Failed to Check Out Book. Please Try Again.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(contentPane, "Please select Check In or Check Out.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        checkInOutPanel.add(btnDone);

        // Back to Book Management Button
        JButton btnBack = new JButton("Back to Book Management");
        btnBack.setFont(new Font("Monospaced", Font.PLAIN, 16));
        btnBack.setBounds(120, 350, 300, 50); // Adjusted position
        btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	// Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();
                
                MemberMenu(USERNAME);
            }
        });
        checkInOutPanel.add(btnBack);

        // Sign Out Button
        JButton btnSignOut = new JButton("Sign Out");
        btnSignOut.setFont(new Font("Monospaced", Font.PLAIN, 16));
        btnSignOut.setBounds(120, 410, 300, 50); // Adjusted position
        btnSignOut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSignOut.setBorderPainted(false); // Remove border
        btnSignOut.setContentAreaFilled(false); // Make it transparent
        btnSignOut.setFocusPainted(false); // Remove focus border
        btnSignOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	// Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();
                
                signIn_UpMenu();
            }
        });
        checkInOutPanel.add(btnSignOut);

        // Make sure to revalidate and repaint the content pane
        contentPane.revalidate();
        contentPane.repaint();
    }

    // Loan Management Admin/Staff
    private void loanManagement() {
        // Clear existing content pane
        contentPane.removeAll();
        contentPane.revalidate();
        contentPane.repaint();

        // Create a new background label
        JLabel loanManagementBackground = new JLabel();
        ImageIcon backgroundImage = new ImageIcon("Loan.JPG");
        Image scaledImage = backgroundImage.getImage().getScaledInstance(750, 950, Image.SCALE_SMOOTH);
        loanManagementBackground.setIcon(new ImageIcon(scaledImage));
        loanManagementBackground.setBounds(0, 0, 750, 950);
        contentPane.add(loanManagementBackground);

        // Welcome Label for loan management
        JLabel lblWelcome = new JLabel("Loan Management");
        lblWelcome.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 25));
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        lblWelcome.setBounds(75, 100, 600, 100);
        lblWelcome.setForeground(Color.BLACK);
        lblWelcome.setOpaque(false);
        lblWelcome.setBackground(new Color(255, 255, 255, 150)); // Set light transparent background
        loanManagementBackground.add(lblWelcome, BorderLayout.NORTH);

        // Light transparent background for welcome label
        JLabel welcomeBackground = new JLabel();
        welcomeBackground.setOpaque(true);
        welcomeBackground.setBackground(new Color(255, 255, 255, 150));
        welcomeBackground.setBounds(70, 100, 610, 100);
        loanManagementBackground.add(welcomeBackground, BorderLayout.CENTER);

        // Light transparent background for loan records
        JPanel loanRecordsPanel = new JPanel();
        loanRecordsPanel.setOpaque(true);
        loanRecordsPanel.setBackground(new Color(255, 255, 255, 150));
        loanRecordsPanel.setBounds(100, 250, 540, 560);
        loanRecordsPanel.setLayout(null);
        loanManagementBackground.add(loanRecordsPanel);

        // Retrieve member's loan information
        String loanInfo = library.viewLoans_GUI();
        JTextArea textArea = new JTextArea(loanInfo);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setEditable(false);
        textArea.setOpaque(false); 
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(40, 20, 460, 300);
        loanRecordsPanel.add(scrollPane);

        // JLabel asking about fine calculation
        JLabel lblCalculateFines = new JLabel("Calculate fines for overdue books?");
        lblCalculateFines.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 18));
        lblCalculateFines.setBounds(90, 320, 500, 30);
        lblCalculateFines.setForeground(Color.BLACK);
        loanRecordsPanel.add(lblCalculateFines);

        // Radio buttons for fine calculation selection
        JRadioButton yesRadioButton = new JRadioButton("Yes");
        yesRadioButton.setFont(new Font("Monospaced", Font.PLAIN, 16));
        yesRadioButton.setBounds(180, 350, 100, 30);
        yesRadioButton.setForeground(Color.BLACK);
        yesRadioButton.setOpaque(false); // Make it transparent
        loanRecordsPanel.add(yesRadioButton);

        JRadioButton noRadioButton = new JRadioButton("No");
        noRadioButton.setFont(new Font("Monospaced", Font.PLAIN, 16));
        noRadioButton.setBounds(300, 350, 100, 30);
        noRadioButton.setForeground(Color.BLACK);
        noRadioButton.setOpaque(false); // Make it transparent
        loanRecordsPanel.add(noRadioButton);

        ButtonGroup fineCalculationGroup = new ButtonGroup();
        fineCalculationGroup.add(yesRadioButton);
        fineCalculationGroup.add(noRadioButton);

        // If yes, ask for username
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 18));
        lblUsername.setBounds(90, 390, 150, 30);
        lblUsername.setForeground(Color.BLACK);
        loanRecordsPanel.add(lblUsername);

        JTextField usernameField = new JTextField();
        usernameField.setFont(new Font("Monospaced", Font.PLAIN, 16));
        usernameField.setBounds(200, 390, 200, 30);
        loanRecordsPanel.add(usernameField);

        // Save button for adding a new loan
        JButton btnSaveLoan = new JButton("Search Fine");
        btnSaveLoan.setFont(new Font("Monospaced", Font.PLAIN, 16));
        btnSaveLoan.setBounds(200, 425, 150, 30);
        btnSaveLoan.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSaveLoan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Perform saving operation
                
            	// if the first one is yes do the fine operation
            	
            	// Check if the user wants to calculate fines for overdue books
                boolean calculateFines = yesRadioButton.isSelected();
                String username = usernameField.getText();
                if (calculateFines == true) {  
                	// Clear existing content pane
                    contentPane.removeAll();
                    contentPane.revalidate();
                    contentPane.repaint();
                    
                    
                    // Create a new background label
                    JLabel loanManagementBackground = new JLabel();
                    ImageIcon backgroundImage = new ImageIcon("Loan.JPG");
                    Image scaledImage = backgroundImage.getImage().getScaledInstance(750, 950, Image.SCALE_SMOOTH);
                    loanManagementBackground.setIcon(new ImageIcon(scaledImage));
                    loanManagementBackground.setBounds(0, 0, 750, 950);
                    contentPane.add(loanManagementBackground);

                    // Welcome Label for loan management
                    JLabel lblWelcome = new JLabel("Loan Management");
                    lblWelcome.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 25));
                    lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
                    lblWelcome.setBounds(75, 100, 600, 100);
                    lblWelcome.setForeground(Color.BLACK);
                    lblWelcome.setOpaque(false);
                    lblWelcome.setBackground(new Color(255, 255, 255, 150)); // Set light transparent background
                    loanManagementBackground.add(lblWelcome, BorderLayout.NORTH);

                    // Light transparent background for welcome label
                    JLabel welcomeBackground = new JLabel();
                    welcomeBackground.setOpaque(true);
                    welcomeBackground.setBackground(new Color(255, 255, 255, 150));
                    welcomeBackground.setBounds(70, 100, 610, 100);
                    loanManagementBackground.add(welcomeBackground, BorderLayout.CENTER);

                    // Light transparent background for loan records
                    JPanel loanRecordsPanel = new JPanel();
                    loanRecordsPanel.setOpaque(true);
                    loanRecordsPanel.setBackground(new Color(255, 255, 255, 150));
                    loanRecordsPanel.setBounds(100, 250, 540, 500);
                    loanRecordsPanel.setLayout(null);
                    loanManagementBackground.add(loanRecordsPanel);
                	
                    // Retrieve member's loan information
                    String loanInfo = library.fineLoanSearch(username);
                    JTextArea textArea = new JTextArea(loanInfo);
                    textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
                    textArea.setEditable(false);
                    textArea.setOpaque(false); 
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    scrollPane.setBounds(40, 50, 460, 300);
                    loanRecordsPanel.add(scrollPane);
                            
                    
                    // Back to Admin Menu Button
                    JButton btnBack = new JButton("Back to Member Management");
                    btnBack.setFont(new Font("Monospaced", Font.PLAIN, 16));
                    btnBack.setBounds(120, 400, 300, 50);
                    btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    btnBack.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                        	
                            // Clear existing content pane
                            contentPane.removeAll();
                            contentPane.revalidate();
                            contentPane.repaint();                            

                            loanManagement();
                        }
                    });
                    loanRecordsPanel.add(btnBack);
                }
            }
        });
        loanRecordsPanel.add(btnSaveLoan);

        // Back to Admin Menu Button
        JButton btnBack = new JButton("Back to Member Management");
        btnBack.setFont(new Font("Monospaced", Font.PLAIN, 16));
        btnBack.setBounds(120, 455, 300, 50);
        btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();

                memberManagement();
            }
        });
        loanRecordsPanel.add(btnBack);

        // Sign Out Button
        JButton btnSignOut = new JButton("Sign Out");
        btnSignOut.setFont(new Font("Monospaced", Font.PLAIN, 16));
        btnSignOut.setBounds(120, 500, 300, 50);
        btnSignOut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSignOut.setBorderPainted(false); // Remove border
        btnSignOut.setContentAreaFilled(false); // Make it transparent
        btnSignOut.setFocusPainted(false); // Remove focus border
        btnSignOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Clear existing content pane
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();

                signIn_UpMenu();
            }
        });
        loanRecordsPanel.add(btnSignOut);

        // Make sure to revalidate and repaint the content pane
        contentPane.revalidate();
        contentPane.repaint();
    }

}