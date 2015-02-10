package lt.buzzard.ui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import lt.buzzard.client.Client;
import lt.buzzard.engine.Game;
import lt.buzzard.server.Server;

public class LaunhcerOnlineUnfinished {

    private static int grid_width;
    private static int grid_height;
    private static int signs2win;

    private JFrame frame;
    private Game game;
    private JTextField portFieldServer;
    private JTextField portFieldClient;
    private JTextField ipField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {

        grid_width = 37;
        grid_height = 31;
        signs2win = 5;

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    LaunhcerOnlineUnfinished window = new LaunhcerOnlineUnfinished();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public LaunhcerOnlineUnfinished() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setResizable(false);
        frame.setBounds(100, 100, 349, 261);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JButton btnNewButton = new JButton("Create room");
        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    game = new Game(grid_width, grid_height, signs2win, 2, new Server(Integer
                            .parseInt(portFieldClient.getText())));
                    game.start();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnNewButton.setBounds(10, 58, 319, 23);
        frame.getContentPane().add(btnNewButton);

        JButton btnNewButton_1 = new JButton("Join room");
        btnNewButton_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    game = new Game(grid_width, grid_height, signs2win, 1, new Client(ipField
                            .getText(), Integer.parseInt(portFieldClient.getText())));
                    game.start();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnNewButton_1.setBounds(10, 136, 319, 23);
        frame.getContentPane().add(btnNewButton_1);

        JButton btnNewButton_2 = new JButton("Launch in offline mode");
        btnNewButton_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    game = new Game(grid_width, grid_height, signs2win);
                    game.start();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnNewButton_2.setBounds(10, 11, 319, 23);
        frame.getContentPane().add(btnNewButton_2);

        portFieldServer = new JTextField();
        portFieldServer.setText("4444");
        portFieldServer.setBounds(172, 92, 86, 20);
        frame.getContentPane().add(portFieldServer);
        portFieldServer.setColumns(10);

        JLabel lblPort = new JLabel("PORT:");
        lblPort.setBounds(63, 92, 46, 14);
        frame.getContentPane().add(lblPort);

        JLabel label = new JLabel("PORT:");
        label.setBounds(63, 170, 46, 14);
        frame.getContentPane().add(label);

        portFieldClient = new JTextField();
        portFieldClient.setText("4444");
        portFieldClient.setColumns(10);
        portFieldClient.setBounds(172, 170, 86, 20);
        frame.getContentPane().add(portFieldClient);

        JSeparator separator = new JSeparator();
        separator.setBounds(10, 45, 319, 2);
        frame.getContentPane().add(separator);

        JSeparator separator_1 = new JSeparator();
        separator_1.setBounds(10, 123, 319, 2);
        frame.getContentPane().add(separator_1);

        JLabel lblIp = new JLabel("IP:");
        lblIp.setBounds(63, 204, 46, 14);
        frame.getContentPane().add(lblIp);

        ipField = new JTextField();
        ipField.setText("localhost");
        ipField.setColumns(10);
        ipField.setBounds(172, 201, 86, 20);
        frame.getContentPane().add(ipField);
    }
}
