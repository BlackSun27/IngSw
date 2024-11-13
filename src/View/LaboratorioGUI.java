package View;

import Controller.Controller;
import Model.Laboratorio;

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
 * The type Laboratorio gui.
 */
public class LaboratorioGUI {
    /**
     * The Frame.
     */
    JFrame frame;
    private JPanel laboratorioMainPanel;
    private JButton backBtn;
    private JButton addBtn;
    private JButton removeBtn;
    private JButton newAffBtn;
    private JPanel btnPanel;
    private JScrollPane jsp;
    private JTable labTable;
    private JTable profileTable;
    private JPanel profilePanel;
    private JTextField afferenteField;
    private JLabel afferenteLabel;
    private JPanel afferenteAddPanel;

    /**
     * Instantiates a new Laboratorio gui.
     *
     * @param controller the controller
     * @param prevFrame  the prev frame
     */
    public LaboratorioGUI(Controller controller, JFrame prevFrame){
        JPanel laboratorioMainPanel = new JPanel();
        JPanel profilePanel = new JPanel();
        frame = new JFrame("Laboratorio");
        frame.setSize(1280, 720);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(laboratorioMainPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

        String[] colonne = {"Nome", "Resp. Scien.", "Topic", "Num. Afferenti"};

        List<Laboratorio> laboratori = controller.getLaboratoriDB();
        Set<String> nomiSet = new HashSet<>();
        for(Laboratorio lab: laboratori){
            String nome = lab.getNome();
            if(!nomiSet.contains(nome))
                nomiSet.add(nome);
            else
                break;
        }
        int i=0;
        Object[][] righe = new Object[nomiSet.size()][colonne.length];
        for(Laboratorio lab : laboratori){
            if(i<nomiSet.size()) {
                righe[i] = new Object[]{lab.getNome(), lab.getResp_sci(), lab.getTopic(), lab.getN_afferenti()};
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

        JTable labTable = new JTable();
        labTable.setModel(dtm);
        labTable.setRowHeight(30);
        labTable.getTableHeader().setReorderingAllowed(false);
        labTable.getTableHeader().setResizingAllowed(false);
        labTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        labTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int rigaSelezionata = labTable.getSelectedRow();
                String nome = "";
                if(rigaSelezionata != -1)
                    nome = labTable.getValueAt(rigaSelezionata, 0).toString();
                JTable profileTable = new JTable();
                ArrayList<String> info_lab = new ArrayList<>();
                String prog = controller.getCUPfromLab(nome);
                if(prog!=null)
                    info_lab.add(prog);
                ArrayList<String> info_resp = controller.getRespSciLab(nome);
                info_lab.addAll(info_resp);

                Object[][] colonne = new Object[info_lab.size()][1];

                for(int i=0; i< info_lab.size(); i++){
                    colonne[i][0] = info_lab.get(i);
                }


                DefaultTableModel profileTableModel = new DefaultTableModel(colonne, new String[]{"Info Lab."}) {
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

        JScrollPane jsp = new JScrollPane(labTable);
        jsp.setPreferredSize(new Dimension(700, 400));
        JButton addBtn = new JButton("Aggiungi Lab");
        JButton removeBtn = new JButton("Rimuovi");
        JButton newAffBtn = new JButton("Nuovo Afferente");
        JButton backBtn = new JButton("Torna alla Home");

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel.add(addBtn);
        btnPanel.add(removeBtn);
        btnPanel.add(newAffBtn);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(10,10,10,10);
        laboratorioMainPanel.add(backBtn, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        laboratorioMainPanel.add(jsp, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        laboratorioMainPanel.add(profilePanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        laboratorioMainPanel.add(btnPanel, gbc);

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
                String nome = labTable.getValueAt(labTable.getSelectedRow(), 0).toString();
                int selection = JOptionPane.showConfirmDialog(null, "Sicuro di voler rimuovere il laboratorio?",
                        "Conferma", JOptionPane.YES_NO_OPTION);

                if (selection == JOptionPane.YES_OPTION) {
                    try {
                        controller.rimuoviLab(nome);
                        JOptionPane.showMessageDialog(null, "Rimozione avvenuta con successo! ", "Conferma",
                                JOptionPane.INFORMATION_MESSAGE);
                        ricaricaTabella(controller, colonne);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Impossibile rimuovere il laboratorio! Motivo: " +
                                ex, "Insuccesso", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            }catch (ArrayIndexOutOfBoundsException ex){
                JOptionPane.showMessageDialog(null, "Non e' stato selezionato alcun nome! ", "Insuccesso",
                        JOptionPane.PLAIN_MESSAGE);
            }
        });

        newAffBtn.addActionListener(e->{
            try {
                String nome = labTable.getValueAt(labTable.getSelectedRow(), 0).toString();
                int selezione = JOptionPane.showConfirmDialog(null, "Vuoi aggiungere un nuovo afferente?",
                        "Conferma", JOptionPane.YES_NO_OPTION);
                JPanel afferenteAddPanel = new JPanel();
                afferenteAddPanel.setLayout(new BoxLayout(afferenteAddPanel, BoxLayout.Y_AXIS));
                if (selezione == JOptionPane.YES_OPTION) {
                    JLabel afferenteLabel = new JLabel("CF: ");
                    JTextField afferenteField = new JTextField(20);
                    afferenteAddPanel.add(afferenteLabel);
                    afferenteAddPanel.add(afferenteField);
                    JOptionPane.showMessageDialog(null, afferenteAddPanel, "Inserisci il CF",
                            JOptionPane.PLAIN_MESSAGE);

                    String cf = afferenteField.getText();
                    try {
                        controller.aggiungiAfferenteLab(nome, cf);
                        JOptionPane.showMessageDialog(null, "Inserimento avvenuto con successo!", "Successo",
                                JOptionPane.PLAIN_MESSAGE);
                        ricaricaTabella(controller, colonne);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Inserimento non effettuato, motivo: " + ex,
                                "Insuccesso", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            }catch (ArrayIndexOutOfBoundsException ex){
                JOptionPane.showMessageDialog(null, "Non e' stato selezionato alcun nome! ", "Insuccesso",
                        JOptionPane.PLAIN_MESSAGE);
            }
        });

        addBtn.addActionListener(e->{
            AddLaboratorioGUI laboratorioGUI = new AddLaboratorioGUI(controller);

            laboratorioGUI.frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    ricaricaTabella(controller, colonne);
                }
            });
        });
    }

    private void ricaricaTabella(Controller controller, String[] colonne) {
        if (controller != null && colonne.length != 0) {
            List<Laboratorio> laboratori = controller.getLaboratoriDB();
            Set<String> nomiSet = new HashSet<>();
            for(Laboratorio lab: laboratori){
                String nome = lab.getNome();
                if(!nomiSet.contains(nome))
                    nomiSet.add(nome);
                else
                    break;
            }

            Object[][] righe = new Object[nomiSet.size()][colonne.length];
            for (int i = 0; i < nomiSet.size(); i++) {
                Laboratorio lab = laboratori.get(i);
                righe[i][0] = lab.getNome();
                righe[i][1] = lab.getResp_sci();
                righe[i][2] = lab.getTopic();
                righe[i][3] = lab.getN_afferenti();
            }

            DefaultTableModel dtm = new DefaultTableModel(righe, colonne) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            JTable labTable = new JTable();
            labTable.setModel(dtm);
            labTable.setRowHeight(30);
            labTable.getTableHeader().setReorderingAllowed(false);
            labTable.getTableHeader().setResizingAllowed(false);
            labTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        }
    }


}
