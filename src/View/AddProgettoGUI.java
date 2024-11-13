package View;

import Controller.*;

import javax.swing.*;
import java.sql.*;

/**
 * The type Add progetto gui.
 */
public class AddProgettoGUI {
    /**
     * The Frame.
     */
    public JFrame frame;
    private JPanel progettoPanel;
    private JTextField resp_sciField;
    private JTextField nomeTextField;
    private JTextField budgetField;
    private JTextField cupTextField;
    private JTextField refSciField;
    private JLabel resp_sciLabel;
    private JLabel nomeLabel;
    private JLabel budgetLabel;
    private JLabel cupLabel;
    private JLabel refSciLabel;
    private JButton okBtn;

    /**
     * Instantiates a new Add progetto gui.
     *
     * @param controller the controller
     */
    public AddProgettoGUI(Controller controller){
        JPanel progettoPanel = new JPanel();
        progettoPanel.setLayout(new BoxLayout(progettoPanel, BoxLayout.Y_AXIS));
        frame = new JFrame("Inserimento progetto");
        frame.setContentPane(progettoPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(580, 350);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        JLabel cupLabel = new JLabel("CUP: ");
        JTextField cupTextField = new JTextField(20);
        JLabel resp_sciLabel = new JLabel("CF Responsabile: ");
        JTextField resp_sciField = new JTextField(20);
        JLabel refSciLabel = new JLabel("CF Referente Scientifico: ");
        JTextField refSciField = new JTextField(20);
        JLabel nomeLabel = new JLabel("Nome: ");
        JTextField nomeTextField = new JTextField(20);
        JLabel budgetLabel = new JLabel("Budget: ");
        JTextField budgetField = new JTextField(20);

        progettoPanel.add(cupLabel);
        progettoPanel.add(cupTextField);
        progettoPanel.add(resp_sciLabel);
        progettoPanel.add(resp_sciField);
        progettoPanel.add(refSciLabel);
        progettoPanel.add(refSciField);
        progettoPanel.add(nomeLabel);
        progettoPanel.add(nomeTextField);
        progettoPanel.add(budgetLabel);
        progettoPanel.add(budgetField);

        JButton okBtn = new JButton("OK");
        progettoPanel.add(okBtn);

        okBtn.addActionListener(e -> {
            int risposta = JOptionPane.showConfirmDialog(null, "Vuoi inserire il seguente laboratorio? ",
                    "Conferma", JOptionPane.YES_NO_OPTION);
            if(risposta == JOptionPane.YES_OPTION){
                String cup = cupTextField.getText();
                String resp = resp_sciField.getText();
                String ref = refSciField.getText();
                String nome = nomeTextField.getText();
                float budget = Float.parseFloat(budgetField.getText());
                if(!nome.isEmpty() || !resp.isEmpty() || !cup.isEmpty() || !ref.isEmpty() || budget !=0) {
                    try {
                        controller.aggiungiProgetto(cup, ref, resp,nome, budget);
                        JOptionPane.showMessageDialog(null, "Inserimento avvenuto con successo!", "Conferma",
                                JOptionPane.INFORMATION_MESSAGE);
                        frame.dispose();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Non posso effettuare l'inserimento" +
                                " motivo: "+ ex, "Insuccesso", JOptionPane.PLAIN_MESSAGE);
                        frame.dispose();
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Ci sono campi vuoti! ", "Errore",
                            JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                }
            }
        });
    }
}
