package View;

import Controller.*;

import javax.swing.*;
import java.sql.*;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * The type Add impiegato gui.
 */
public class AddImpiegatoGUI {
    /**
     * The Frame.
     */
    public JFrame frame;
    private JPanel impMainPanel;
    private JTextField cfTextField;
    private JTextField nomeTextField;
    private JTextField cognomeTextField;
    private JTextField dataNascitaField;
    private JTextField codiceConField;
    private JLabel cfLabel;
    private JLabel nomeLabel;
    private JLabel cognomeLabel;
    private JLabel dataNascitaLabel;
    private JLabel codiceConLabel;
    private JButton okBtn;

    /**
     * Instantiates a new Add impiegato gui.
     *
     * @param controller the controller
     */
    public AddImpiegatoGUI(Controller controller){
        JPanel impMainPanel = new JPanel();
        impMainPanel.setLayout(new BoxLayout(impMainPanel, BoxLayout.Y_AXIS));
        frame = new JFrame("Inserimento Impiegati");
        frame.setContentPane(impMainPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(580, 350);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        JLabel cfLabel = new JLabel("CF:");
        JTextField cfTextField = new JTextField(20);
        JLabel nomeLabel = new JLabel("Nome:");
        JTextField nomeTextField = new JTextField(20);
        JLabel cognomeLabel = new JLabel("Cognome:");
        JTextField cognomeTextField = new JTextField(20);
        JLabel dataNascitaLabel = new JLabel("Data di nascita (anno-mese-giorno):");
        JTextField dataNascitaField = new JTextField(20);
        JLabel codiceConLabel = new JLabel("Codice Contratto:");
        JTextField codiceConField = new JTextField(20);

        JButton okBtn = new JButton("OK");

        impMainPanel.add(cfLabel);
        impMainPanel.add(cfTextField);
        impMainPanel.add(nomeLabel);
        impMainPanel.add(nomeTextField);
        impMainPanel.add(cognomeLabel);
        impMainPanel.add(cognomeTextField);
        impMainPanel.add(dataNascitaLabel);
        impMainPanel.add(dataNascitaField);
        impMainPanel.add(codiceConLabel);
        impMainPanel.add(codiceConField);
        impMainPanel.add(okBtn);

        okBtn.addActionListener(e->{
            int risposta1 = JOptionPane.showConfirmDialog(null, "Vuoi inserire il seguente impiegato? ",
                    "Conferma", JOptionPane.YES_NO_OPTION);
            if(risposta1 == JOptionPane.YES_OPTION) {
                String cf = cfTextField.getText();
                String nome = nomeTextField.getText();
                String cognome = cognomeTextField.getText();
                String dataNascita_S = dataNascitaField.getText();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate dataNascita_LD = null;
                Date dataNascita = null;
                try {
                    dataNascita_LD = LocalDate.parse(dataNascita_S, formatter);
                    dataNascita = Date.valueOf(dataNascita_LD);
                }catch (DateTimeParseException d){
                    JOptionPane.showMessageDialog(null, "Non puo' proseguire l'inserimento", "Errore",
                            JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                    return;
                }
                LocalDate dataAttuale_LD = LocalDate.now();
                Date dataAttuale = Date.valueOf(dataAttuale_LD);
                String codiceCon = codiceConField.getText();
                int risposta2 = JOptionPane.showConfirmDialog(null, "L'impiegato inserito ha merito? ",
                        "Conferma", JOptionPane.YES_NO_OPTION);
                boolean merito = risposta2 == JOptionPane.YES_OPTION;
                float salario = 1500.00f;
                String categoria = "Junior";
                if(merito) {
                    salario = 3000.00f;
                    categoria = "Dirigente";
                }
                Period periodo = null;

                periodo = Period.between(dataNascita_LD, dataAttuale_LD);

                int eta = periodo.getYears();
                try {
                    controller.aggiungiImp(cf, nome, cognome, dataNascita, dataAttuale, codiceCon,
                            merito, salario, categoria, eta);
                    JOptionPane.showMessageDialog(null, "Inserimento avvenuto con successo! ", "Successo",
                            JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Non puo' proseguire l'inserimento", "Errore",
                            JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                }
            }
        });

    }
}
