import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class MinecraftUpdater {
    private JFrame frame;
    private JTextField directoryField;
    private JList<String> modsList; // Add a JList to display the mods

    private String minecraftDir = ""; // Store the directory path here

    public MinecraftUpdater() {
        // Create the main application window
        frame = new JFrame("Minecraft Updater");

        // Create a text field for the directory input
        directoryField = new JTextField(20);
        JButton okButton = new JButton("OK");

        // Add an action listener to the OK button
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // When OK button is clicked, get the input directory path
                minecraftDir = directoryField.getText();
                // Show the update window
                showUpdateWindow();
            }
        });

        // Create a panel to hold the components
        JPanel panel = new JPanel();
        panel.add(new JLabel("Enter Minecraft Directory:"));
        panel.add(directoryField);
        panel.add(okButton);

        // Add the panel to the frame and set its properties
        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void showUpdateWindow() {
        // Clear the existing content in the frame
        frame.getContentPane().removeAll();

        // Create a new panel with a GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());

        // Set up GridBagConstraints for layout control
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Create components for the update window
        JLabel currentDirLabel = new JLabel("Current Minecraft Directory:");
        JTextField currentDirField = new JTextField(minecraftDir, 20); // Fillable field
        JButton updateButton = new JButton("Update");

        // Add an action listener to the Update button
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // When Update button is clicked, update the directory path and proceed with updating
                minecraftDir = currentDirField.getText();
                updateMinecraft();
            }
        });

        // Place components in the first row using GridBagConstraints
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(currentDirLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(currentDirField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        panel.add(updateButton, gbc);

        // List the files in the mods directory using a JList
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        File modsDirectory = new File(minecraftDir + File.separator + "mods");
        String[] modFiles = modsDirectory.list();
        modsList = new JList<>(modFiles);

        // Add the mods list to a scrollable pane
        JScrollPane modsScrollPane = new JScrollPane(modsList);
        panel.add(modsScrollPane, gbc);

        // Add the panel to the frame and pack it
        frame.add(panel);
        frame.pack();
    }

    private void updateMinecraft() {
        // Delete contents of the mods directory
        File modsDirectory = new File(minecraftDir + File.separator + "mods");
        if (modsDirectory.exists() && modsDirectory.isDirectory()) {
            deleteDirectoryContents(modsDirectory);
        }

        // Delete contents of the config directory
        File configDirectory = new File(minecraftDir + File.separator + "config");
        if (configDirectory.exists() && configDirectory.isDirectory()) {
            deleteDirectoryContents(configDirectory);
        }

        // Implement your logic for downloading and unpacking files here
        // This could involve downloading and extracting new files into the folders
        // Remember to handle exceptions, file operations, and network operations properly
    }

    private void deleteDirectoryContents(File directory) {
        // Recursively delete files and subdirectories within a directory
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectoryContents(file); // Recursively delete subdirectories
                }
                file.delete(); // Delete the file or empty directory
            }
        }
    }

    public static void main(String[] args) {
        // Start the application by invoking the GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MinecraftUpdater();
            }
        });
    }
}
