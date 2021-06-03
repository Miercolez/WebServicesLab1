package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class app extends JFrame {
    private JPanel newPanel = new JPanel();
    private JButton buttonShowAllMovies = new JButton("Show all movies");

    public app() {
        super("Movie DB server");


        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);



        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 3;
        constraints.anchor = GridBagConstraints.CENTER;
        newPanel.add(buttonShowAllMovies, constraints);

        newPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Movie DB server"));

        add(newPanel);

        pack();
        setLocationRelativeTo(null);

        buttonShowAllMovies.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                buttonActionPerformed(evt);
            }
        });



    }

    private void buttonActionPerformed(ActionEvent evt) {
//        getAllMovies();

    }

    public static void main(String[] args) {

//        JFrame frame = new JFrame("app");
//        frame.setContentPane(new app().newPanel);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setVisible(true);



        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new app().setVisible(true);
            }
        });

    }
}
