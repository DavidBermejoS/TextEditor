package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;


/**
 * @author David Bermejo Simon
 **/
public class Window {

    JFrame frame;
    JMenuBar menuBar;
    JMenu menu;

    JMenuItem itemExit;
    JMenuItem itemSave;
    JMenuItem itemSaveAs;
    JMenuItem itemLoad;

    JTextArea textArea;
    FileManager fm;
    boolean fileWriteable;




    public Window(){
        this.frame = new JFrame("Editor de Texto Swing");
        this.frame.setBounds(0,0,800,600);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        fm = new FileManager(frame);
    }


    public void addComponents(){
        //agregado de barra de menu
        menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        menu = new JMenu("Archivo");
        menuBar.add(menu);

        //agregado boton de carga
        itemLoad = new JMenuItem("Cargar");
        itemLoad.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        itemLoad.setMnemonic(KeyEvent.VK_O);
        menu.add(itemLoad);

        menu.addSeparator();

        //agregado boton de guardar
        itemSave = new JMenuItem("Guardar");
        itemSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        itemSave.setMnemonic(KeyEvent.VK_S);
        itemSave.setEnabled(false);
        menu.add(itemSave);

        //agregado boton de guardar como
        itemSaveAs = new JMenuItem("Guardar Como");
        itemSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G,ActionEvent.CTRL_MASK));
        itemSaveAs.setMnemonic(KeyEvent.VK_G);
        itemSaveAs.setEnabled(false);
        menu.add(itemSaveAs);

        menu.addSeparator();

        //agregado boton de salir
        itemExit = new JMenuItem("Salir");
        itemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        itemExit.setMnemonic(KeyEvent.VK_E);
        menu.add(itemExit);


        textArea = new JTextArea("\n\n\t[BIENVENIDO AL EDITOR DE TEXTO]\n\n\tPulsa Cargar para abrir un fichero...");
        textArea.setEnabled(false);
        frame.add(textArea);
    }


    public void addListeners(){

        itemLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setEnabled(true);
                textArea.setText("");
                textArea.setText(fm.openFile());
                if(!textArea.getText().equalsIgnoreCase("")){
                    itemSave.setEnabled(true);
                    itemSaveAs.setEnabled(true);
                    fileWriteable = true;
                }
            }
        });

        itemExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int res = JOptionPane.showConfirmDialog(frame,"¿Estas seguro de que quieres salir?");
                if(JOptionPane.YES_OPTION==res){
                    JOptionPane.showMessageDialog(frame,"¡Adios!");
                    frame.dispatchEvent(new WindowEvent(frame,WindowEvent.WINDOW_CLOSING));
                }
            }
        });


        itemSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fileWriteable){
                    fm.saveFile(textArea.getText());
                }
            }
        });

        itemSaveAs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });




    }


    public void startWindow(){
        this.frame.setVisible(true);
        addComponents();
        addListeners();
    }
}
