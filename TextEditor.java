import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class TextEditor extends JFrame implements ActionListener{
    private JTextArea textArea;
    private JMenuBar menuBar;
    private JMenu fileMenu, editMenu;
    private JMenuItem openMenuItem, saveMenuItem, exitMenuItem;
    private JMenuItem cutMenuItem,copyMenuItem, pasteMenuItem;
    private JButton saveSubmitButton;

    public TextEditor(){
        setTitle("Text Editor");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        textArea = new JTextArea();
        textArea.setFont(new Font("Arial",Font.PLAIN,14));

        menuBar = new JMenuBar();

        fileMenu = new JMenu("File");
        openMenuItem = new JMenuItem("Open");
        saveMenuItem = new JMenuItem("Save");
        exitMenuItem = new JMenuItem("Exit");

        editMenu = new JMenu("Edit");
        cutMenuItem = new JMenuItem("Cut");
        copyMenuItem = new JMenuItem("Copy");
        pasteMenuItem = new JMenuItem("Paste");

        saveSubmitButton = new JButton("Save and Submit");
        saveSubmitButton.addActionListener(this);

        openMenuItem.addActionListener(this);
        saveMenuItem.addActionListener(this);
        exitMenuItem.addActionListener(this);
        cutMenuItem.addActionListener(this);
        copyMenuItem.addActionListener(this);
        pasteMenuItem.addActionListener(this);

        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        editMenu.add(cutMenuItem);
        editMenu.add(copyMenuItem);
        editMenu.add(pasteMenuItem);

         menuBar.add(fileMenu);
         menuBar.add(editMenu);

         setJMenuBar(menuBar);

         JScrollPane scrollPane = new JScrollPane(textArea);

         add(scrollPane,BorderLayout.CENTER);
         add(saveSubmitButton, BorderLayout.SOUTH);

         setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == openMenuItem){
            openFile();
        } else if(e.getSource()==saveMenuItem){
            saveFile();
        }else if(e.getSource() == exitMenuItem){
            System.exit(0);
        }else if(e.getSource() == cutMenuItem){
            textArea.cut();
        }else if(e.getSource() == copyMenuItem){
            textArea.copy();
        }else if(e.getSource() == pasteMenuItem){
            textArea.paste();
        }else if(e.getSource() == saveSubmitButton){
            saveFile();
            JOptionPane.showMessageDialog(this, "File saved and submitted");
        }
    }
    private void openFile(){
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(this);
        if(returnValue == JFileChooser.APPROVE_OPTION){
            try(BufferedReader reader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()))){
                textArea.setText("");
                String line;
                while((line = reader.readLine())!=null){
                    textArea.append(line+"\n");
                }
            }catch (IOException e){
                JOptionPane.showMessageDialog(this,"Error opening File : " + e.getMessage());
            }
        }
    }

    private void saveFile(){
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showSaveDialog(this);
        if(returnValue == JFileChooser.APPROVE_OPTION){
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileChooser.getSelectedFile()))){
                writer.write(textArea.getText());
                JOptionPane.showMessageDialog(this, "File saved.");
            } catch(IOException e){
                JOptionPane.showMessageDialog(this, "Error saving file:");
            }
        }
    }
public static void main(String[] args){
        SwingUtilities.invokeLater(()-> new TextEditor());
    }
}