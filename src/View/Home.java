package View;

import Controller.Controller;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * The type Home.
 */
public class Home {

    private JPanel mainPanel;
    private JPanel btnPanel;

    /**
     * Instantiates a new Home.
     */
    public Home(){
        Controller controller = new Controller();

        JFrame frame = new JFrame("Sistema di gestione del personale in un'azienda");
        JPanel mainPanel = new JPanel();
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        JButton impiegatobtn = new JButton("Impiegato");
        JButton laboratoriobtn = new JButton("Laboratorio");
        JButton progettobtn = new JButton("Progetto");
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonsPanel.add(impiegatobtn);
        buttonsPanel.add(laboratoriobtn);
        buttonsPanel.add(progettobtn);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(10, 0, 0, 0);
        gbc.anchor = GridBagConstraints.PAGE_START;
        mainPanel.add(buttonsPanel, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 5, 0, 5);
        mainPanel.add(impiegatobtn, gbc);

        gbc.gridx = 1;
        mainPanel.add(laboratoriobtn, gbc);

        gbc.gridx = 2;
        mainPanel.add(progettobtn, gbc);

        Border compoundBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        buttonsPanel.setBorder(compoundBorder);

        impiegatobtn.addActionListener(e->{
            frame.setVisible(true);
            new ImpiegatoGUI(controller,frame);
        });

        laboratoriobtn.addActionListener(e->{
            frame.setVisible(true);
            new LaboratorioGUI(controller,frame);
        });

        progettobtn.addActionListener(e->{
            frame.setVisible(true);
            new ProgettoGUI(controller,frame);
        });


    }

}
