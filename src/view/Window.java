package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import controller.DialogReplace;
import controller.FileManager;


/**
 * <h2>Clase Window</h2>
 *
 * @author David Bermejo Simon
 **/
public class Window {

    JFrame frame;
    JMenuBar menuBar;
    JMenu menuFile;
    JMenu menuEdit;

    JMenuItem itemExit;
    JMenuItem itemSave;
    JMenuItem itemSaveAs;
    JMenuItem itemLoad;
    JMenuItem itemReplace;

    JToolBar toolBar;

    JButton buttonSave;
    JButton buttonLoad;
    JButton buttonSaveAs;

    JTextArea textArea;
    FileManager fm;
    boolean fileWriteable;


    /**
     * Constructor de la clase donde se da medidas a la ventana, se le da un titulo y se le
     * asigna una operación de cierre. Ademas se instancia un objeto FileManager
     */
    public Window(){
        this.frame = new JFrame("Editor de Texto Swing");
        this.frame.setBounds(0,0,800,600);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        fm = new FileManager(frame);
    }

    /**
     * Metodo encargado de agregar los componentes a la ventana
     */
    public void addComponents(){
        //agregado de barra de menuFile
        frame.setLayout(new BorderLayout());
        menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        menuFile = new JMenu("Archivo");
        menuEdit = new JMenu("Editar");

        menuBar.add(menuFile);
        menuBar.add(menuEdit);

        //agregado boton de carga
        itemLoad = new JMenuItem("Cargar");
        itemLoad.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        itemLoad.setMnemonic(KeyEvent.VK_O);
        menuFile.add(itemLoad);

        menuFile.addSeparator();

        //agregado boton de guardar
        itemSave = new JMenuItem("Guardar");
        itemSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        itemSave.setMnemonic(KeyEvent.VK_S);
        itemSave.setEnabled(false);
        menuFile.add(itemSave);

        //agregado boton de guardar como
        itemSaveAs = new JMenuItem("Guardar Como");
        itemSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G,ActionEvent.CTRL_MASK));
        itemSaveAs.setMnemonic(KeyEvent.VK_G);
        itemSaveAs.setEnabled(false);
        menuFile.add(itemSaveAs);

        menuFile.addSeparator();

        //agregado boton de salir
        itemExit = new JMenuItem("Salir");
        itemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        itemExit.setMnemonic(KeyEvent.VK_E);
        menuFile.add(itemExit);

        //agregado boton reemplazar
        itemReplace = new JMenuItem("Reemplazar");
        itemReplace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
        itemReplace.setMnemonic(KeyEvent.VK_R);
        menuEdit.add(itemReplace);

        //agregado de campo de texto
        textArea = new JTextArea("\n\n\t[BIENVENIDO AL EDITOR DE TEXTO]\n\n\tPulsa Cargar para abrir un fichero...");
        textArea.setEnabled(false);
        frame.add(textArea,BorderLayout.CENTER);


        //agregado de toolbar
        toolBar = new JToolBar();
        toolBar.setFloatable(false);

        //agregado de botones en el toolbar
        buttonLoad = new JButton();
        buttonLoad.setIcon(getIconImage("resources/imagenes/fileopen.png"));
        toolBar.add(buttonLoad);

        buttonSave = new JButton();
        buttonSave.setIcon(getIconImage("resources/imagenes/filesave.png"));
        toolBar.add(buttonSave);

        buttonSaveAs = new JButton();
        buttonSaveAs.setIcon(getIconImage("resources/imagenes/SaveAs.png"));
        toolBar.add(buttonSaveAs);


        frame.add(toolBar,BorderLayout.NORTH);
    }

    /**
     * Metodo encargado de obtener la imagen de la carpeta de recursos y devolver un icono para los
     * botones de la barra de herramientas
     * @param s : String con la ruta a la imagen
     * @return Icon icon : icono para el boton
     */
    private Icon getIconImage(String s) {
        ImageIcon imageIcon = new ImageIcon(getClass().getClassLoader().getResource(s));
        Image img = imageIcon.getImage();
        BufferedImage bufferedImage = new BufferedImage(40,40,BufferedImage.TYPE_INT_ARGB);
        Graphics g = bufferedImage.createGraphics();
        g.drawImage(img,0,0,40,40,null);
        imageIcon = new ImageIcon(bufferedImage);
        return imageIcon;
    }


    /**
     * Metodo encargado de gestionar los listener de los botones de la ventana principal y
     * de la barra de herramientas
     */
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
                if(fileWriteable){
                    fm.saveAsFile(textArea.getText());
                }
            }
        });

        itemReplace.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fileWriteable){
                    DialogReplace dialogReplace = new DialogReplace(frame,"Reemplazar",false,textArea);
                }

            }
        });

        buttonLoad.addActionListener(new ActionListener() {
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

        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fileWriteable){
                    fm.saveFile(textArea.getText());
                }
            }
        });

        buttonSaveAs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fileWriteable){
                    fm.saveAsFile(textArea.getText());
                }
            }
        });
    }


    /**
     * Metodo encargado de inicializar la ventana y hacerla visible
     */
    public void startWindow(){
        this.frame.setVisible(true);
        addComponents();
        addListeners();
    }
}
