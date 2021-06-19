
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserManagementUI extends JFrame implements ActionListener {

    private final int FRAME_WIDTH = 1200;
    private final int FRAME_HEIGHT = 900;
    private final Font sidebarMenuFont = new Font("Arial", Font.BOLD, 26);
    private final Font textFieldFont = new Font("Arial",Font.PLAIN,18);

    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private JList list = null;

    public ArrayList<Book> books = null;


    private JTextField txt_releaseDate = null;
    private JTextField txt_rating = null;
    private JTextField txt_pages = null;
    private JTextField txt_genre = null;
    private JTextField txt_author = null;
    private JTextField txt_title = null;

    private JPanel panel_titleBar;
    private JPanel panel_sidebar;
    private JPanel panel_body;
    private JPanel panel_issueBook;
    private JPanel panel_returnBook;
    private JPanel panel_bookLogs;
    private JPanel panel_userInfo;
    private JPanel panel_logout;
    private JLabel lb_topbarTitle;
    private JLabel lb_logo;

    private JButton btn_search;
    private JButton btn_issueBook;

    private JTextField txt_search;

    private JButton btn_close;
    private JButton btn_minimize;

    private Color sidebarItemColor = Color.decode("#1568cf");
    private Color sidebarHoverColor = Color.decode("#0753b0");
    private Color sidebarColor = Color.decode("#1877EB");
    private Color tobarColor = Color.decode("#1877EB");
    private Color dashboardItemColor = Color.decode("#e0dcdc");
    private Color topbarColor = Color.decode("#ede2e1");

    private Font labelFonts = new Font("Arial",Font.BOLD,20);

    private DefaultTableModel tableModel = new DefaultTableModel();
    private JTable table;
    public ArrayList<User> users = null;
    public static User selectedUser = null;

    private Image img;

    public UserManagementUI() {
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
        //new SiderBar().getpanel();
        panel_sidebar.setLayout(null);
        panel_sidebar.setBackground(sidebarColor);
        panel_sidebar.setBounds(0, 0, 300, 900);
        add(panel_sidebar);

//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@{ Body }@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        panel_body = new JPanel();
        panel_body.setBounds(300,70,900,830);
        panel_body.setLayout(null);
        panel_body.setBackground(Color.decode("#ebebeb"));
        add(panel_body);

        txt_search = new JTextField();
        txt_search.setBounds(50,50,800,35);
        txt_search.setFont(new Font("Arial",Font.PLAIN,20));
        txt_search.setUI(new HintTextFieldUI("Search Book...",true));
        panel_body.add(txt_search);

        btn_search = new JButton("Search");
        btn_search.setBounds(730, 100, 120, 35);
        btn_search.setFocusPainted(false);
        btn_search.setBackground(Color.decode("#32a852"));
        btn_search.setForeground(Color.white);
        btn_search.setFont(new Font("Arial", Font.BOLD, 16));
        btn_search.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn_search.addActionListener(this);
        panel_body.add(btn_search);
        btn_search.addMouseListener(new MouseAdapter() {   // Button get highlighted when Cursor hover over Login Button
            public void mouseEntered(MouseEvent e) {
                btn_search.setBackground(Color.decode("#198035"));
            }

            public void mouseExited(MouseEvent e) {
                btn_search.setBackground(Color.decode("#32a852"));
            }
        });


        JButton btn_editUser = new JButton("Edit User");
        btn_editUser.setBounds(730,700,120,35);
        btn_editUser.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn_editUser.setBackground(Color.decode("#03a9fc"));
        btn_editUser.setFont(new Font("Arial", Font.BOLD, 16));
        btn_editUser.setFocusPainted(false);
        panel_body.add(btn_editUser);
        btn_editUser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (table.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null,"No User selected! Please select a user from table","Error Message",JOptionPane.ERROR_MESSAGE);
                } else{

                    selectedUser.setUser_id(users.get(table.getSelectedRow()).getUser_id());
                    selectedUser.setFirstName(users.get(table.getSelectedRow()).getFirstName());
                    selectedUser.setLastName(users.get(table.getSelectedRow()).getLastName());
                    selectedUser.setEmail(users.get(table.getSelectedRow()).getEmail());
                    selectedUser.setAddress(users.get(table.getSelectedRow()).getAddress());
                    selectedUser.setMobileNumber(users.get(table.getSelectedRow()).getMobileNumber());
                    selectedUser.setCnic(users.get(table.getSelectedRow()).getCnic());
                    selectedUser.setPassword(users.get(table.getSelectedRow()).getPassword());

                    dispose();
                    new EditUserUI();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btn_editUser.setBackground(Color.decode("#0484c4"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn_editUser.setBackground(Color.decode("#03a9fc"));
            }
        });

        JButton btn_deleteUser = new JButton("Delete User");
        btn_deleteUser.setBounds(570,700,130,35);
        btn_deleteUser.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn_deleteUser.setBackground(Color.decode("#ff0000"));
        btn_deleteUser.setFont(new Font("Arial", Font.BOLD, 16));
        btn_deleteUser.setFocusPainted(false);
        panel_body.add(btn_deleteUser);
        btn_deleteUser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(table.getSelectedRow()==-1){
                    JOptionPane.showMessageDialog(null,"No user selected! Please select a user from table to delete it","Error",JOptionPane.ERROR_MESSAGE);
                }else{
                    try {

                        Driver.dataAgent.removeUser((Integer) table.getValueAt(table.getSelectedRow(),0));
                        dispose();
                        new UserManagementUI();
                        JOptionPane.showMessageDialog(null,"User deleted successfully","User Deletion",JOptionPane.INFORMATION_MESSAGE);

                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btn_deleteUser.setBackground(Color.decode("#d10404"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn_deleteUser.setBackground(Color.decode("#ff0000"));
            }
        });

//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@{ Logo }@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        img = new ImageIcon("img/books.jpg").getImage().getScaledInstance(200,200,Image.SCALE_SMOOTH);
        lb_logo = new JLabel(new ImageIcon(img));
        lb_logo.setBounds(50,30,200,200);
        lb_logo.setBackground(Color.BLACK);
        lb_logo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel_sidebar.add(lb_logo);

//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@{ Sidebar Menu Items }@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

//<<<<< Manage Users >>>>>>
        panel_issueBook = new JPanel();
        panel_issueBook.setBounds(20,260,260,60);
        panel_issueBook.setBackground(sidebarHoverColor);
        panel_issueBook.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel_issueBook.setLayout(null);
        panel_sidebar.add(panel_issueBook);

        img = new ImageIcon("img/users.png").getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);
        JLabel lb_homeIcon = new JLabel(new ImageIcon(img));
        lb_homeIcon.setBounds(0,0,60,60);
        panel_issueBook.add(lb_homeIcon);

        JLabel lb_home = new JLabel("Manage Users");
        lb_home.setBounds(70,0,190, 60);
        lb_home.setFont(sidebarMenuFont);
        panel_issueBook.add(lb_home);

//<<<<< Manage Books >>>>>>
        panel_returnBook = new JPanel();
        panel_returnBook.setBounds(20,325,260,60);
        panel_returnBook.setBackground(sidebarItemColor);
        panel_returnBook.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel_returnBook.setLayout(null);
        panel_sidebar.add(panel_returnBook);
        panel_returnBook.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new ReturnBookUI();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                panel_returnBook.setBackground(sidebarHoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panel_returnBook.setBackground(sidebarItemColor);
            }
        });


        img = new ImageIcon("img/books.jpg").getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);
        JLabel lb_returnBookIcon = new JLabel(new ImageIcon(img));
        lb_returnBookIcon.setBounds(0,0,60,60);
        panel_returnBook.add(lb_returnBookIcon);

        JLabel lb_returnBook = new JLabel("Manage Books");
        lb_returnBook.setBounds(70,0,190, 60);
        lb_returnBook.setFont(sidebarMenuFont);
        panel_returnBook.add(lb_returnBook);

//<<<<< Log Out >>>>>>
        panel_bookLogs = new JPanel();
        panel_bookLogs.setBounds(20,390,260,60);
        panel_bookLogs.setBackground(sidebarItemColor);
        panel_bookLogs.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel_bookLogs.setLayout(null);
        panel_sidebar.add(panel_bookLogs);
        panel_bookLogs.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new LoginUI();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                panel_bookLogs.setBackground(sidebarHoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panel_bookLogs.setBackground(sidebarItemColor);
            }
        });


        img = new ImageIcon("img/logout.png").getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);
        JLabel lb_bookLogsIcon = new JLabel(new ImageIcon(img));
        lb_bookLogsIcon.setBounds(0,0,60,60);
        panel_bookLogs.add(lb_bookLogsIcon);


        JLabel lb_bookLogs = new JLabel("Book Logs");
        lb_bookLogs.setBounds(70,0,190, 60);
        lb_bookLogs.setFont(sidebarMenuFont);
        panel_bookLogs.add(lb_bookLogs);


//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@{ Top Title Bar }@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        panel_titleBar = new JPanel();
        panel_titleBar.setLayout(null);
        panel_titleBar.setBounds(0,0,FRAME_WIDTH,70);
        panel_titleBar.setBackground(topbarColor);
        add(panel_titleBar);

//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@{ Topbar Title }@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        lb_topbarTitle = new JLabel("Manage User Accounts");
        lb_topbarTitle.setBounds(320,20,400,35);
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
                UserManagementUI.super.setState(JFrame.ICONIFIED);
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

//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@{ Search Area }@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        JPanel panel_resultArea = new JPanel(new BorderLayout());
        panel_resultArea.setBounds(50,150,800,500);
        panel_resultArea.setBackground(Color.lightGray);
        panel_body.add(panel_resultArea);

        table = new JTable();
        table.setFont(new Font("Arial",Font.PLAIN,20));
        table.setModel(tableModel);
        table.setRowHeight(30);
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setFont(new Font("Arial",Font.BOLD,20));

        Object[] columnsNames = {"ID","Username","Mobile No","CNIC","Email"};
        tableModel.setColumnIdentifiers(columnsNames);

        /*try{

            users = Driver.dataAgent.getAllUsers();

            if(users!=null){
                for (User user : users){
                    tableModel.addRow(user.getUserObject());
                }
            }


        } catch(SQLException e){
            e.printStackTrace();
        }*/

        panel_resultArea.add(new JScrollPane(table));


        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==btn_search){
            if(txt_search.getText().matches("(?=.*[~!@#$%^&*()_-]).*")) {
                JOptionPane.showMessageDialog(null,"Invalid Keyword Search","Invalid Search",JOptionPane.ERROR_MESSAGE);
            }
            else if(txt_search.getText().trim().isEmpty()){
                JOptionPane.showMessageDialog(null,"No User Searched! Please search username first in search field","Empty Search Field",JOptionPane.ERROR_MESSAGE);
            }
            else
            {

            }
        }
    }



    public static void main(String[] args) {
        new UserManagementUI();
    }

}
