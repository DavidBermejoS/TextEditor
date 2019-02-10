package view;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import controller.DialogReplace;
import controller.FileManager;
import controller.MyHighlighter;


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
    JMenuItem itemCopy;
    JMenuItem itemPaste;

    JToolBar toolBar;

    JButton buttonSave;
    JButton buttonLoad;
    JButton buttonSaveAs;
    JButton buttonCopy;
    JButton buttonPaste;

    JTextField searchWord;
    JButton buttonSearch;


    JTextArea textArea;
    FileManager fm;
    boolean fileWriteable;


    /**
     * Constructor de la clase donde se da medidas a la ventana, se le da un titulo y se le
     * asigna una operación de cierre. Ademas se instancia un objeto FileManager
     */
    public Window() {
        this.frame = new JFrame("Editor de Texto Swing");
        this.frame.setBounds(0, 0, 800, 600);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        fm = new FileManager(frame);
    }

    /**
     * Metodo encargado de agregar los componentes a la ventana
     */
    public void addComponents() {
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
        itemSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
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


        menuEdit.addSeparator();
        //agregado boton copiar
        itemCopy = new JMenuItem("Copiar");
        itemCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        itemCopy.setMnemonic(KeyEvent.VK_C);
        menuEdit.add(itemCopy);

        //agregado boton pegar
        itemPaste = new JMenuItem("Pegar");
        itemPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        itemPaste.setMnemonic(KeyEvent.VK_V);
        menuEdit.add(itemPaste);

        //agregado de campo de texto
        textArea = new JTextArea("\n\n\t[BIENVENIDO AL EDITOR DE TEXTO]\n\n\tPulsa Cargar para abrir un fichero...");
        textArea.setEnabled(false);
        frame.add(textArea, BorderLayout.CENTER);


        //agregado de toolbar
        toolBar = new JToolBar();
        toolBar.setFloatable(false);

        //agregado de botones en el toolbar
        buttonLoad = new JButton();
        buttonLoad.setIcon(getIconImage("resources/imagenes/fileopen.png"));
        toolBar.add(buttonLoad, BorderLayout.LINE_START);

        buttonSave = new JButton();
        buttonSave.setIcon(getIconImage("resources/imagenes/filesave.png"));
        toolBar.add(buttonSave, BorderLayout.LINE_START);

        buttonSaveAs = new JButton();
        buttonSaveAs.setIcon(getIconImage("resources/imagenes/SaveAs.png"));
        toolBar.add(buttonSaveAs, BorderLayout.LINE_START);

        buttonCopy = new JButton();
        buttonCopy.setIcon(getIconImage("resources/imagenes/copy.png"));
        toolBar.add(buttonCopy, BorderLayout.LINE_START);

        buttonPaste = new JButton();
        buttonPaste.setIcon(getIconImage("resources/imagenes/paste.png"));
        toolBar.add(buttonPaste, BorderLayout.LINE_START);

        searchWord = new JTextField();
        toolBar.add(searchWord, BorderLayout.LINE_END);

        buttonSearch = new JButton();
        buttonSearch.setIcon(getIconImage("resources/imagenes/search.png"));
        toolBar.add(buttonSearch, BorderLayout.LINE_END);

        frame.add(toolBar, BorderLayout.NORTH);
    }

    /**
     * Metodo encargado de obtener la imagen de la carpeta de recursos y devolver un icono para los
     * botones de la barra de herramientas
     *
     * @param s : String con la ruta a la imagen
     * @return Icon icon : icono para el boton
     */
    private Icon getIconImage(String s) {
        ImageIcon imageIcon = new ImageIcon(getClass().getClassLoader().getResource(s));
        Image img = imageIcon.getImage();
        BufferedImage bufferedImage = new BufferedImage(40, 40, BufferedImage.TYPE_INT_ARGB);
        Graphics g = bufferedImage.createGraphics();
        g.drawImage(img, 0, 0, 40, 40, null);
        imageIcon = new ImageIcon(bufferedImage);
        return imageIcon;
    }


    /**
     * Metodo encargado de gestionar los listener de los botones de la ventana principal y
     * de la barra de herramientas
     */
    public void addListeners() {

        itemLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setEnabled(true);
                textArea.setText("");
                textArea.setText(fm.openFile());
                if (!textArea.getText().equalsIgnoreCase("")) {
                    itemSave.setEnabled(true);
                    itemSaveAs.setEnabled(true);
                    fileWriteable = true;
                }
            }
        });

        itemExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int res = JOptionPane.showConfirmDialog(frame, "¿Estas seguro de que quieres salir?");
                if (JOptionPane.YES_OPTION == res) {
                    JOptionPane.showMessageDialog(frame, "¡Adios!");
                    frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                }
            }
        });

        itemSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fileWriteable) {
                    fm.saveFile(textArea.getText());
                }
            }
        });

        itemSaveAs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fileWriteable) {
                    fm.saveAsFile(textArea.getText());
                }
            }
        });

        itemReplace.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fileWriteable) {
                    DialogReplace dialogReplace = new DialogReplace(frame, "Reemplazar", false, textArea);
                }

            }
        });

        itemCopy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(new StringSelection(textArea.getSelectedText()), null);
            }
        });

        itemPaste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
                Transferable t = cb.getContents(this);

                DataFlavor dataFlavorStringJava = null;
                try {
                    dataFlavorStringJava = new DataFlavor("application/x-java-serialized-object; class=java.lang.String");
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }

                if (t.isDataFlavorSupported(dataFlavorStringJava)) {
                    String texto = null;
                    try {
                        texto = (String) t.getTransferData(dataFlavorStringJava);
                        //se encarga de insertar el texto del portapapeles en la posicion del cursor
                        textArea.insert(texto, textArea.getCaretPosition());
                    } catch (UnsupportedFlavorException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                }
            }
        });

        buttonLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setEnabled(true);
                textArea.setText("");
                textArea.setText(fm.openFile());
                if (!textArea.getText().equalsIgnoreCase("")) {
                    itemSave.setEnabled(true);
                    itemSaveAs.setEnabled(true);
                    fileWriteable = true;
                }
            }
        });

        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fileWriteable) {
                    fm.saveFile(textArea.getText());
                }
            }
        });

        buttonSaveAs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fileWriteable) {
                    fm.saveAsFile(textArea.getText());
                }
            }
        });

        buttonCopy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(new StringSelection(textArea.getSelectedText()), null);
            }
        });

        //fragmento obtenido de la página: http://chuwiki.chuidiang.org/index.php?title=Uso_del_Clipboard_del_sistema
        buttonPaste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
                Transferable t = cb.getContents(this);

                DataFlavor dataFlavorStringJava = null;
                try {
                    dataFlavorStringJava = new DataFlavor("application/x-java-serialized-object; class=java.lang.String");
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }

                if (t.isDataFlavorSupported(dataFlavorStringJava)) {
                    String texto = null;
                    try {
                        texto = (String) t.getTransferData(dataFlavorStringJava);
                        //se encarga de insertar el texto del portapapeles en la posicion del cursor
                        textArea.insert(texto, textArea.getCaretPosition());
                    } catch (UnsupportedFlavorException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                }
            }
        });

        buttonSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!searchWord.getText().equalsIgnoreCase("")){
                    String wordToFind = searchWord.getText();
                    findWords(wordToFind);
                }else{
                    JOptionPane.showMessageDialog(frame,"El campo de busqueda está vacío","Error en la busqueda",JOptionPane.INFORMATION_MESSAGE);
                }
            }

            /**
             * Metodo encargado de encontrar palabras en el texto y remarcarlas si coinciden con el patron "wordToFind"
             * @param wordToFind
             */
            private void findWords(String wordToFind) {
                try {
                    Highlighter.HighlightPainter myHighlight = new MyHighlighter(Color.YELLOW);
                    Highlighter highliter = textArea.getHighlighter();
                    Document doc = textArea.getDocument();
                    String text = doc.getText(0,doc.getLength());
                    int position = 0;
                    while((position=text.toUpperCase().indexOf(wordToFind.toUpperCase(),position))>=0){
                        highliter.addHighlight(position,position+wordToFind.length(),myHighlight);
                        position += wordToFind.length();
                    }
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    /**
     * Metodo encargado de inicializar la ventana y hacerla visible
     */
    public void startWindow() {
        this.frame.setVisible(true);
        addComponents();
        addListeners();
    }
}
