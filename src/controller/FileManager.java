package controller;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;

/**
 * @author David Bermejo Simon
 **/
public class FileManager {
    JFrame frame;
    File file;
    String text;
    String route;


    public FileManager(JFrame frame){
        this.frame = frame;
    }

    /**
     * Metodo encargado de abrir un fichero seleccionado en un FileChooser
     */
    public String openFile(){
        JFileChooser fileChooser = new JFileChooser("");
        FileFilter filt = new FileNameExtensionFilter("*.txt", "txt");
        fileChooser.setFileFilter(filt);
        fileChooser.setAcceptAllFileFilterUsed(false);
        if(file!= null){
            text = "";
            fileChooser.setCurrentDirectory(file.getParentFile());
        }
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            this.file = fileChooser.getSelectedFile();
            System.out.println("[Selected file: " + this.file.getAbsolutePath()+" ]");
            readFile(file);
            return text;
        }else{
            System.out.println("[Ningun archivo seleccionado]");
        }
        return text;
    }

    /**
     * Metodo encargado de leer el fichero y devolver el texto leido en el
     * @param file
     */
    private void readFile(File file) {
        try(BufferedReader bf = new BufferedReader(new FileReader(file))){
            String line;
            while((line = bf.readLine()) != null){
                text+="\n"+line;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error en la lectura del fichero "+file.getName());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error en la apertura del fichero "+file.getName());
            e.printStackTrace();
        }


    }

    /**
     * Metodo encargado de guardar el texto introducido en un fichero de texto
     * @param text
     */
    public void saveFile(String text) {
        if(file!=null){
            try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                bw.write(text);
                JOptionPane.showMessageDialog(frame,"Se ha guardado el fichero "+file.getName()+" con exito");
            } catch (IOException e) {
                System.out.println("[Error de escritura del fichero "+file.getName()+"]");
                e.printStackTrace();
            }

        }

    }
}
