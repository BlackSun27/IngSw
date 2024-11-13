package View;

import Controller.*;
import Model.Progetto;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.*;
import java.util.List;


/**
 * The type Progetto gui.
 */
public class ProgettoGUI {
    /**
     * The Frame.
     */
    JFrame frame;
    private JPanel progettoMainPanel;
    private JButton backBtn;
    private JButton addBtn;
    private JButton removeBtn;
    private JButton newLabBtn;
    private JPanel btnPanel;
    private JScrollPane jsp;
    private JTable progTable;
    private JTable profileTable;
    private JPanel profilePanel;
    private JTextField laboratorioField;
    private JLabel laboratorioLabel;
    private JPanel laboratorioAddPanel;

    /**
     * Instantiates a new Progetto gui.
     *
     * @param controller the controller
     * @param prevFrame  the prev frame
     */
    public ProgettoGUI(Controller controller, Frame prevFrame){
        JPanel progettoMainPanel = new JPanel();
        JPanel profilePanel = new JPanel();
        frame = new JFrame("Progetto");
        frame.setSize(1280, 720);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(progettoMainPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

        String[] colonne = {"CUP", "Ref. Scien.", "Resp.", "Nome", "Budget"};

        List<Progetto> progetti = controller.getProgettiDB();
        Set<String> cupSet = new HashSet<>();
        for(Progetto progetto : progetti){
            String cup = progetto.getNome();
            if(!cupSet.contains(cup))
                cupSet.add(cup);
            else
                break;
        }
        int i=0;
        Object[][] righe = new Object[cupSet.size()][colonne.length];
        for(Progetto p : progetti){
            if(i<cupSet.size()) {
                righe[i] = new Object[]{p.getCup(), p.getRef_sci(), p.getResp(), p.getNome(), p.getBudget()};
                i++;
            }else
                break;
        }

        DefaultTableModel dtm = new DefaultTableModel(colonne, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for(Object[] riga : righe){
            dtm.addRow(riga);
        }

        JTable progTable = new JTable();
        progTable.setModel(dtm);
        progTable.setRowHeight(30);
        progTable.getTableHeader().setReorderingAllowed(false);
        progTable.getTableHeader().setResizingAllowed(false);
        progTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        progTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int rigaSelezionata = progTable.getSelectedRow();
                String cup = "";
                String nome = "";
                float budget = 0;
                if(rigaSelezionata != -1) {
                    cup = progTable.getValueAt(rigaSelezionata, 0).toString();
                    nome = progTable.getValueAt(rigaSelezionata, 3).toString();
                    budget = Float.parseFloat(progTable.getValueAt(rigaSelezionata, 4).toString());
                }
                JTable profileTable = new JTable();
                ArrayList<String> labs = new ArrayList<>();
                labs = controller.getLabFromCUP(cup);
                ArrayList<String> info = controller.getInfoRefResp(cup);

                Object[][] righe = new Object[4][3];
                righe[0][0] = cup;
                righe[0][1] = nome;
                righe[0][2] = budget;
                righe[1][0] = labs.get(0);
                righe[1][1] = labs.get(1);
                righe[1][2] = labs.get(2);
                int inc = 0;
                for(int i=2; i<4; i++){
                    for(int j = 0; j<3; j++) {
                        inc = (i-2) * 3 + j;
                        if(inc<info.size()) {
                            righe[i][j] = info.get(inc);
                        }
                    }
                }


                DefaultTableModel profileTableModel = new DefaultTableModel(righe, new String[]{"Info Prog1.", "Info Prog2.", "Info Prog3."}) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };

                profileTable.setModel(profileTableModel);
                profilePanel.removeAll();
                profilePanel.add(profileTable);
                profilePanel.repaint();
                profilePanel.revalidate();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        JScrollPane jsp = new JScrollPane(progTable);
        jsp.setPreferredSize(new Dimension(700, 400));
        JButton addBtn = new JButton("Aggiungi Prog");
        JButton removeBtn = new JButton("Rimuovi");
        JButton newLabBtn = new JButton("Nuovo Laboratorio");
        JButton backBtn = new JButton("Torna alla Home");

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel.add(addBtn);
        btnPanel.add(removeBtn);
        btnPanel.add(newLabBtn);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(10,10,10,10);
        progettoMainPanel.add(backBtn, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        progettoMainPanel.add(jsp, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        progettoMainPanel.add(profilePanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        progettoMainPanel.add(btnPanel, gbc);

        Border compoundBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        btnPanel.setBorder(compoundBorder);

        backBtn.addActionListener(e -> {
            prevFrame.setVisible(true);
            frame.dispose();
        });

        removeBtn.addActionListener(e->{
            try {
                String cup = progTable.getValueAt(progTable.getSelectedRow(), 0).toString();
                int selection = JOptionPane.showConfirmDialog(null, "Sicuro di voler rimuovere il progetto?",
                        "Conferma", JOptionPane.YES_NO_OPTION);

                if (selection == JOptionPane.YES_OPTION) {
                    try {
                        controller.rimuoviProgetto(cup);
                        JOptionPane.showMessageDialog(null, "Rimozione avvenuta con successo! ", "Conferma",
                                JOptionPane.INFORMATION_MESSAGE);
                        ricaricaTabella(controller, colonne);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Impossibile rimuovere il progetto! Motivo: " +
                                ex, "Insuccesso", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            }catch (ArrayIndexOutOfBoundsException ex){
                JOptionPane.showMessageDialog(null, "Nessun cup e' stato selezionato!", "Insuccesso",
                        JOptionPane.PLAIN_MESSAGE);
            }
        });

        newLabBtn.addActionListener(e->{
            try {
                String cup = progTable.getValueAt(progTable.getSelectedRow(), 0).toString();
                int selezione = JOptionPane.showConfirmDialog(null, "Vuoi aggiungere un nuovo laboratorio?",
                        "Conferma", JOptionPane.YES_NO_OPTION);
                JPanel laboratorioAddPanel = new JPanel();
                laboratorioAddPanel.setLayout(new BoxLayout(laboratorioAddPanel, BoxLayout.Y_AXIS));
                if (selezione == JOptionPane.YES_OPTION) {
                    JLabel laboratorioLabel = new JLabel("Lab: ");
                    JTextField laboratorioField = new JTextField(20);
                    laboratorioAddPanel.add(laboratorioLabel);
                    laboratorioAddPanel.add(laboratorioField);
                    JOptionPane.showMessageDialog(null, laboratorioAddPanel, "Inserisci il Nome",
                            JOptionPane.PLAIN_MESSAGE);

                    String lab = laboratorioField.getText();
                    try {
                        controller.aggiungiLaboratorio(cup, lab);
                        JOptionPane.showMessageDialog(null, "Inserimento avvenuto con successo!", "Successo",
                                JOptionPane.PLAIN_MESSAGE);
                        ricaricaTabella(controller, colonne);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Inserimento non effettuato, motivo: " + ex,
                                "Insuccesso", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            }catch (ArrayIndexOutOfBoundsException ex) {
                JOptionPane.showMessageDialog(null, "Nessun cup e' stato selezionato!", "Insuccesso",
                        JOptionPane.PLAIN_MESSAGE);
            }
        });

        addBtn.addActionListener(e->{
            AddProgettoGUI addProgettoGUI = new AddProgettoGUI(controller);
            addProgettoGUI.frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    ricaricaTabella(controller, colonne);
                }
            });
        });
    }

    private void ricaricaTabella(Controller controller, String[] colonne) {
        if (controller != null && colonne.length != 0) {
            List<Progetto> laboratori = controller.getProgettiDB();
            Set<String> cupSet = new HashSet<>();
            for(Progetto lab: laboratori){
                String cup = lab.getCup();
                if(!cupSet.contains(cup))
                    cupSet.add(cup);
                else
                    break;
            }

            Object[][] righe = new Object[cupSet.size()][colonne.length];
            for (int i = 0; i < cupSet.size(); i++) {
                Progetto lab = laboratori.get(i);
                righe[i][0] = lab.getCup();
                righe[i][1] = lab.getRef_sci();
                righe[i][2] = lab.getResp();
                righe[i][3] = lab.getNome();
                righe[i][4] = lab.getBudget();
            }

            DefaultTableModel dtm = new DefaultTableModel(righe, colonne) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            JTable progTable = new JTable();
            progTable.setModel(dtm);
            progTable.setRowHeight(30);
            progTable.getTableHeader().setReorderingAllowed(false);
            progTable.getTableHeader().setResizingAllowed(false);
            progTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        }
    }
}
