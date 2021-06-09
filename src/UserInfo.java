import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class UserInfo extends JFrame implements ActionListener {

    private final int FRAME_WIDTH = 1200;
    private final int FRAME_HEIGHT = 900;
    private final Font sidebarMenuFont = new Font("Arial", Font.BOLD, 26);


    private JPanel panel_titleBar;
    private JPanel panel_sidebar;
    private JPanel panel_body;
    private JPanel panel_home;
    private JPanel panel_search;
    private JPanel panel_userInfo;
    private JPanel panel_setting;
    private JPanel panel_logout;
    private JLabel lb_topbarTitle;
    private JLabel lb_logo;


    private JButton btn_close;
    private JButton btn_minimize;

    private Color sidebarItemColor = Color.decode("#1568cf");
    private Color sidebarHoverColor = Color.decode("#0753b0");
    private Color sidebarColor = Color.decode("#1877EB");
    private Color tobarColor = Color.decode("#1877EB");
    private Color dashboardItemColor = Color.decode("#e0dcdc");
    private Color topbarColor = Color.decode("#ede2e1");

    private Font labelFonts = new Font("Arial",Font.BOLD,20);

    private Image img;

    public UserInfo() {
        init();
    }

    public void init(){

//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@{ Main Frame }@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(FRAME_WIDTH,FRAME_HEIGHT);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.decode("#ede2e1"));
        setUndecorated(true);
        setShape(new RoundRectangle2D.Double(0, 0, FRAME_WIDTH, FRAME_HEIGHT, 40, 40));
        setLayout(null);
        FrameDragListener frameDragListener = new FrameDragListener(this);
        super.addMouseListener(frameDragListener);
        super.addMouseMotionListener(frameDragListener);

//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@{ Sidebar }@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        panel_sidebar = new JPanel();
        panel_sidebar.setLayout(null);
        panel_sidebar.setBackground(sidebarColor);
        panel_sidebar.setBounds(0, 0, 300, 900);
        add(panel_sidebar);


//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@{ Logo }@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        img = new ImageIcon("img/books.jpg").getImage().getScaledInstance(200,200,Image.SCALE_SMOOTH);
        lb_logo = new JLabel(new ImageIcon(img));
        lb_logo.setBounds(50,30,200,200);
        lb_logo.setBackground(Color.BLACK);
        lb_logo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel_sidebar.add(lb_logo);

//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@{ Sidebar Menu Items }@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

//<<<<< Dashboard >>>>>>
        panel_home = new JPanel();
        panel_home.setBounds(20,260,260,60);
        panel_home.setBackground(sidebarItemColor);
        panel_home.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel_home.setLayout(null);
        panel_sidebar.add(panel_home);
        panel_home.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new Dashboard();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                panel_home.setBackground(sidebarHoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panel_home.setBackground(sidebarItemColor);
            }
        });

        //Icon
        img = new ImageIcon("img/dashboard.jpg").getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);
        JLabel lb_dashboardIcon = new JLabel(new ImageIcon(img));
        lb_dashboardIcon.setBounds(0,0,60,60);
        panel_home.add(lb_dashboardIcon);

        // Label
        JLabel lb_dashboard = new JLabel("Dashboard");
        lb_dashboard.setBounds(70,0,190, 60);
        lb_dashboard.setFont(sidebarMenuFont);
        panel_home.add(lb_dashboard);

//<<<<< Search Book >>>>>>
        panel_search = new JPanel();
        panel_search.setBounds(20,325,260,60);
        panel_search.setBackground(sidebarHoverColor);
        panel_search.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel_search.setLayout(null);
        panel_sidebar.add(panel_search);
        panel_search.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new SearchBook();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                panel_search.setBackground(sidebarHoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panel_search.setBackground(sidebarItemColor);
            }
        });

        //Menu Icon
        img = new ImageIcon("img/menu.png").getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);
        JLabel lb_searchIcon = new JLabel(new ImageIcon(img));
        lb_searchIcon.setBounds(0,0,60,60);
        panel_search.add(lb_searchIcon);

        // Menu Label
        JLabel lb_searchBook = new JLabel("Search Book");
        lb_searchBook.setBounds(70,0,190, 60);
        lb_searchBook.setFont(sidebarMenuFont);
        panel_search.add(lb_searchBook);

//<<<<< User User Info >>>>>>
        panel_userInfo = new JPanel();
        panel_userInfo.setBounds(20,390,260,60);
        panel_userInfo.setBackground(sidebarHoverColor);
        panel_userInfo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel_userInfo.setLayout(null);
        panel_sidebar.add(panel_userInfo);

        //User Info Icon
        img = new ImageIcon("img/user-info.png").getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);
        JLabel lb_userInfoIcon = new JLabel(new ImageIcon(img));
        lb_userInfoIcon.setBounds(0,0,60,60);
        panel_userInfo.add(lb_userInfoIcon);

        // User Info Menu
        JLabel lb_userInfo = new JLabel("User Info");
        lb_userInfo.setBounds(70,0,190, 60);
        lb_userInfo.setFont(sidebarMenuFont);
        panel_userInfo.add(lb_userInfo);

//<<<<< Settings >>>>>>
        panel_setting = new JPanel();
        panel_setting.setBounds(20,455,260,60);
        panel_setting.setBackground(sidebarItemColor);
        panel_setting.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel_setting.setLayout(null);
        panel_sidebar.add(panel_setting);
        panel_setting.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                panel_setting.setBackground(sidebarHoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panel_setting.setBackground(sidebarItemColor);
            }
        });

        // Icon
        img = new ImageIcon("img/cog.jpg").getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);
        JLabel lb_settingIcon = new JLabel(new ImageIcon(img));
        lb_settingIcon.setBounds(0,0,60,60);
        panel_setting.add(lb_settingIcon);

        // Label
        JLabel lb_setting = new JLabel("Setting");
        lb_setting.setBounds(70,0,190, 60);
        lb_setting.setFont(sidebarMenuFont);
        panel_setting.add(lb_setting);

//<<<<< Log Out >>>>>>
        panel_logout = new JPanel();
        panel_logout.setBounds(20,520,260,60);
        panel_logout.setBackground(sidebarItemColor);
        panel_logout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel_logout.setLayout(null);
        panel_sidebar.add(panel_logout);
        panel_logout.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                panel_logout.setBackground(sidebarHoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panel_logout.setBackground(sidebarItemColor);
            }
        });

        //Home Icon
        img = new ImageIcon("img/logout.jpg").getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);
        JLabel lb_logoutIcon = new JLabel(new ImageIcon(img));
        lb_logoutIcon.setBounds(0,0,60,60);
        panel_logout.add(lb_logoutIcon);

        // Home Menu
        JLabel lb_logout = new JLabel("Log Out");
        lb_logout.setBounds(70,0,190, 60);
        lb_logout.setFont(sidebarMenuFont);
        panel_logout.add(lb_logout);

//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@{ Top Title Bar }@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        panel_titleBar = new JPanel();
        panel_titleBar.setLayout(null);
        panel_titleBar.setBounds(0,0,FRAME_WIDTH,70);
        panel_titleBar.setBackground(topbarColor);
        add(panel_titleBar);

//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@{ Topbar Title }@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        lb_topbarTitle = new JLabel("User Info");
        lb_topbarTitle.setBounds(320,20,300,30);
        lb_topbarTitle.setForeground(Color.WHITE);
        lb_topbarTitle.setFont(new Font("Arial", Font.BOLD, 34));
        panel_titleBar.add(lb_topbarTitle);

//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@{ Topbar Cross Button }@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        btn_close = new JButton("X");
        btn_close.setBounds(1150, 25, 20, 20);
        btn_close.setBackground(null);
        btn_close.setForeground(Color.red);
        btn_close.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        btn_close.setToolTipText("Close");
        btn_close.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn_close.setFocusPainted(false);
        btn_close.setFont(new Font("Arial", Font.BOLD, 20));
//        btn_close.addActionListener(this);
        panel_titleBar.add(btn_close);
        btn_close.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btn_close.setForeground(Color.decode("#cc0000"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn_close.setForeground(Color.red);
            }
        });

//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@{ Topbar Minimize Button }@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        btn_minimize = new JButton("-");
        btn_minimize.setBounds(1120, 25, 20, 20);
        btn_minimize.setBackground(null);
        btn_minimize.setForeground(Color.red);
        btn_minimize.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        btn_minimize.setToolTipText("Minimize");
        btn_minimize.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn_minimize.setFocusPainted(false);
        btn_minimize.setFont(new Font("Arial", Font.BOLD, 20));
        btn_minimize.addActionListener(this);
        panel_titleBar.add(btn_minimize);
        btn_minimize.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                UserInfo.super.setState(JFrame.ICONIFIED);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btn_minimize.setForeground(Color.decode("#cc0000"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn_minimize.setForeground(Color.red);
            }
        });

//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@{ Body }@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        panel_body = new JPanel();
        panel_body.setBounds(300,70,900,830);
        panel_body.setLayout(null);
        panel_body.setBackground(Color.decode("#ebebeb"));
        add(panel_body);

//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@{ User Info }@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        JPanel panel_bookDetails = new JPanel();
        panel_bookDetails.setBounds(50,115,800,420);
        panel_bookDetails.setBackground(dashboardItemColor);
        panel_bookDetails.setLayout(null);
        panel_body.add(panel_bookDetails);


        JLabel lb_bookDetail = new JLabel("User Information");
        lb_bookDetail.setBounds(300,20,250,32);
        lb_bookDetail.setFont(new Font("Arial",Font.BOLD,30));
        panel_bookDetails.add(lb_bookDetail);

        //<<<<<< Details >>>>>>>>

        JLabel lb_name = new JLabel("Name");
        lb_name.setBounds(100,100,200,25);
        lb_name.setFont(labelFonts);
        lb_name.setForeground(Color.red);
        panel_bookDetails.add(lb_name);

        JLabel lb_mobileNumber = new JLabel("Mobile Number");
        lb_mobileNumber.setBounds(100,150,200,25);
        lb_mobileNumber.setFont(labelFonts);
        lb_mobileNumber.setForeground(Color.red);
        panel_bookDetails.add(lb_mobileNumber);

        JLabel lb_address = new JLabel("Address");
        lb_address.setBounds(100,200,200,25);
        lb_address.setFont(labelFonts);
        lb_address.setForeground(Color.red);
        panel_bookDetails.add(lb_address);

        JLabel lb_cnic = new JLabel("CNIC");
        lb_cnic.setBounds(100,250,200,25);
        lb_cnic.setFont(labelFonts);
        lb_cnic.setForeground(Color.red);
        panel_bookDetails.add(lb_cnic);

        JLabel lb_email = new JLabel("Email");
        lb_email.setBounds(100,300,200,25);
        lb_email.setFont(labelFonts);
        lb_email.setForeground(Color.red);
        panel_bookDetails.add(lb_email);

        JLabel lb_password = new JLabel("Password");
        lb_password.setBounds(100,350,200,25);
        lb_password.setFont(labelFonts);
        lb_password.setForeground(Color.red);
        panel_bookDetails.add(lb_password);

        //<<<<<< Data Fetch From Database >>>>>>>>

        JTextField txt_name = new JTextField();
        txt_name.setBounds(400,95,300,30);
        txt_name.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        txt_name.setEditable(false);
        txt_name.setText(Driver.currentUser.getFirstName()+Driver.currentUser.getLastName());
        panel_bookDetails.add(txt_name);

        JTextField txt_mobileNumber = new JTextField();
        txt_mobileNumber.setBounds(400,145,300,30);
        txt_mobileNumber.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        txt_mobileNumber.setEditable(false);
        txt_mobileNumber.setText(Driver.currentUser.getMobileNumber());
        panel_bookDetails.add(txt_mobileNumber);

        JTextArea textArea_address = new JTextArea();
        textArea_address.setBounds(400,195,300,50);
        textArea_address.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        textArea_address.setEditable(false);
        textArea_address.setText(Driver.currentUser.getAddress());
        panel_bookDetails.add(textArea_address);

        JTextField txt_cnic = new JTextField();
        txt_cnic.setBounds(400,265,300,30);
        txt_cnic.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        txt_cnic.setEditable(false);
        txt_cnic.setText(Driver.currentUser.getCnic());
        panel_bookDetails.add(txt_cnic);

        JTextField txt_email = new JTextField();
        txt_email.setBounds(400,315,300,30);
        txt_email.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        txt_email.setEditable(false);
        txt_email.setText(Driver.currentUser.getEmail());
        panel_bookDetails.add(txt_email);

        JPasswordField txt_password = new JPasswordField();
        txt_password.setBounds(400,365,300,30);
        txt_password.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        txt_password.setEditable(false);
        txt_password.setText(Driver.currentUser.getEmail());
        panel_bookDetails.add(txt_password);



        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
