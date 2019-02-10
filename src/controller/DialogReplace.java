package controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase DialogReplace
 * @author David Bermejo Simon
 */
public class DialogReplace extends JDialog {

    JFrame owner;

    JLabel labelOrigen;
    JLabel labelReplace;
    JTextField textOrigen;
    JTextField textReplace;
    JButton buttonReplace;
    JButton buttonCancel;


    JTextArea textArea;

    public DialogReplace(JFrame owner, String title, boolean modal,  JTextArea textArea) {
        super(owner, title, modal);
        super.setBounds(owner.getWidth() / 2, owner.getHeight() / 2, 350, 300);
        super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        super.setResizable(false);

        this.textArea = textArea;
        this.owner = owner;

        addComponents();
        addListeners();
        this.setVisible(true);
    }

    private void addComponents() {
        this.setLayout(new GridLayout(6, 1));

        labelOrigen = new JLabel("Introduce la palabra a reemplazar");
        this.add(labelOrigen);

        textOrigen = new JTextField();
        this.add(textOrigen);

        labelReplace = new JLabel("Introduce la palabra por la que quieres reemplazarla: ");
        this.add(labelReplace);

        textReplace = new JTextField();
        this.add(textReplace);

        buttonReplace = new JButton("Reemplazar");
        this.add(buttonReplace);

        buttonCancel = new JButton("Cancelar");
        this.add(buttonCancel);
    }


    private void addListeners() {
        this.buttonReplace.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!textOrigen.getText().equalsIgnoreCase("")) {
                    if (!textReplace.getText().equalsIgnoreCase("")) {
                       replaceWords();
                    } else {
                        JOptionPane.showMessageDialog(owner, "No se ha introducido una palabra con la que reemplazar", "Error", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(owner, "No se ha introducido una palabra para reemplazar", "Error", JOptionPane.WARNING_MESSAGE);
                }
            }

            private void replaceWords() {
                String text = textArea.getText();
                text = text.replaceAll(textOrigen.getText(),textReplace.getText());
                textArea.setText(text);
                JOptionPane.showMessageDialog(owner,
                        "Se han reemplazado todas las ocurrencias con: " +textOrigen.getText()+" por: "+textReplace.getText()+ " satisfactoriamente",
                        "Exito",
                        JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        });

        this.buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });


    }


}
