package lt.buzzard.ui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import lt.buzzard.engine.Game;

public class Launcher {

    private JFrame frame;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;

    private static int grid_width;
    private static int grid_height;
    private static int signs2win;

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
                    Launcher window = new Launcher();
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
    public Launcher() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 299, 211);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setResizable(false);
        frame.setTitle("octoX launcher");

        JButton btnLaunch = new JButton("Launch");
        btnLaunch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                Game game = new Game(grid_width, grid_height, signs2win);
                try {
                    game.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        btnLaunch.setBounds(97, 139, 89, 23);
        frame.getContentPane().add(btnLaunch);

        JLabel lblGameGridWidth = new JLabel("Game grid width:");
        lblGameGridWidth.setBounds(32, 29, 112, 14);
        frame.getContentPane().add(lblGameGridWidth);

        JLabel lblGameGridHeight = new JLabel("Game grid height:");
        lblGameGridHeight.setBounds(32, 62, 112, 14);
        frame.getContentPane().add(lblGameGridHeight);

        textField = new JTextField();
        textField.setBounds(154, 26, 86, 20);
        frame.getContentPane().add(textField);
        textField.setColumns(10);
        textField.setText("" + grid_width);
        textField.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent arg0) {
                if (grid_width >= 3 && arg0.getWheelRotation() < 0) {
                    grid_width -= arg0.getWheelRotation();
                } else if (grid_width > 3) {
                    grid_width -= arg0.getWheelRotation();
                } else if (grid_width < 3) {
                    grid_width = 3;
                }
                textField.setText("" + grid_width);
                if (grid_width < signs2win) {
                    signs2win = grid_width;
                    textField_2.setText("" + signs2win);
                }
            }
        });

        textField_1 = new JTextField();
        textField_1.setBounds(154, 59, 86, 20);
        frame.getContentPane().add(textField_1);
        textField_1.setColumns(10);
        textField_1.setText("" + grid_height);
        textField_1.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent arg0) {
                if (grid_height >= 3 && arg0.getWheelRotation() < 0) {
                    grid_height -= arg0.getWheelRotation();
                } else if (grid_height > 3) {
                    grid_height -= arg0.getWheelRotation();
                } else if (grid_height < 3) {
                    grid_height = 3;
                }
                textField_1.setText("" + grid_height);
                if (grid_height < signs2win) {
                    signs2win = grid_height;
                    textField_2.setText("" + signs2win);
                }
            }
        });

        textField_2 = new JTextField();
        textField_2.setBounds(154, 90, 86, 20);
        frame.getContentPane().add(textField_2);
        textField_2.setColumns(10);
        textField_2.setText("" + signs2win);
        textField_2.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent arg0) {
                if (signs2win >= grid_width || signs2win >= grid_height
                        && arg0.getWheelRotation() < 0) {
                    if (grid_width > grid_height) {
                        signs2win = grid_height;
                    } else {
                        signs2win = grid_width;
                    }
                } else if (signs2win < grid_width || signs2win < grid_height) {
                    if (signs2win >= 3 && arg0.getWheelRotation() < 0) {
                        signs2win -= arg0.getWheelRotation();
                    } else if (signs2win > 3) {
                        signs2win -= arg0.getWheelRotation();
                    }
                }
                textField_2.setText("" + signs2win);
            }
        });

        JLabel lblSignsToWin = new JLabel("Signs to win:");
        lblSignsToWin.setBounds(32, 93, 112, 14);
        frame.getContentPane().add(lblSignsToWin);
    }
}
