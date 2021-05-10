//Main open form that is first rendered

package plotvisualizer;

import java.awt.Cursor;
import java.io.File;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import java.util.Timer;
import javax.swing.DefaultListModel;
import java.awt.Desktop;  
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import javax.imageio.ImageIO;

public class MainForm extends javax.swing.JFrame {
    
    File root;
    String workingDir = System.getProperty("user.dir");
    SimpleImageInfo sii;
    File currentImg;
    double scale;  
    Timer loadTimer;
    boolean isLoading = false;
    int loadInt = 0;
    LinkedList<File> fileList = new LinkedList<>();
    LinkedList<File> selectedFileList = new LinkedList<>();
    DefaultListModel model = new DefaultListModel();
    
    public MainForm() {
        initComponents();
    }
    
    //Helpers
    private void directoryScanner(File[] files) {
        //adds all the files in the current and sub directories
        for (File file : files) {
            if (file.isDirectory()) {
                directoryScanner(file.listFiles());
            } else {
                fileList.add(file);
            }
        }
    }
    
    private void filterSearch() {
        //clear selected file list
        selectedFileList.clear();
        //set up the filters
        String[] allFilters = containAllTextField.getText().split(",");
        String[] oneFilters = containAnyTextField.getText().split(",");
        //adds all fileList items to selectedFileList given criterion
        for (File file : fileList) {
            if (Arrays.stream(allFilters).allMatch(file.getAbsolutePath()::contains) &&
                Arrays.stream(oneFilters).anyMatch(file.getAbsolutePath()::contains)) {
                selectedFileList.add(file);
            }
        }
        //sorts selected files alphabetically
        selectedFileList.sort(Comparator.naturalOrder());
        //clear out model and refill with selected items
        model.removeAllElements();
        for (File file : selectedFileList) {
            model.addElement(file.getAbsolutePath().replace(root.getAbsolutePath(), ""));
        }
        mainList.setModel(model);
        mainList.setSelectedIndex(0);
        
        //automatically set preview to first image if applicable
        try {
            setView(smartImageScaler(selectedFileList.get(mainList.getSelectedIndex())));
        } catch(Exception e) {
            System.out.println("Not an image");
        }
    }
    
    private void openFile() {
        //opens file using native desktop application
        try {
            System.out.println("Opening File: " + selectedFileList.get(mainList.getSelectedIndex()).getPath());
            System.out.println(selectedFileList.get(mainList.getSelectedIndex()));
            Desktop desktop = Desktop.getDesktop();  
            desktop.open(selectedFileList.get(mainList.getSelectedIndex()));
        } catch(Exception e) {
            System.out.println("File Not Found!");
        }
    }
    
    private ImageIcon smartImageScaler(File img) throws Exception {
        //get frame Dimension
        Dimension d = preview.getSize();

        try {
            sii = new SimpleImageInfo(img);
        } catch(Exception e) {
            System.out.println("error recieving image scale info");
            e.printStackTrace();
        }
        
        double scale;
        
        //if frame width > frame height
        if (d.getWidth() > d.getHeight()) {
            //scale is limited by window height
            scale = d.getWidth() / sii.getWidth();
            //if image hieght when scaled to image width is > frame height
            if (sii.getHeight() * scale > d.getHeight()) {
                //limit scale further by the height
                scale = d.getHeight() / sii.getHeight();
            }
        } else {
            scale = d.getHeight() / sii.getHeight();
            if (sii.getWidth() * scale > d.getWidth()) {
                scale = d.getWidth() / sii.getWidth();
            }
        }
        
        if (scale == this.scale && img.getName().equals(currentImg.getName())) {
            throw new Exception();
        } else {
            this.scale = scale;
        }

        try {  
            currentImg = img;
            return new ImageIcon(ImageIO.read(img).getScaledInstance((int) (sii.getWidth() * scale), (int) (sii.getHeight() * scale), BufferedImage.SCALE_SMOOTH)); 
        } catch(Exception e) {
            System.out.println("error creating Image Icon");
//            e.printStackTrace();
            return null;
        }
    }
    
    private void setView(ImageIcon img) {
        preview.setIcon(img);
        previewFileNameLabel.setText(currentImg.getName());
    }
    
    private void toggleLoading(boolean isLoading) {
        //toggles loading graphic that tracks with asynch threads
        if (isLoading) {
            loadingLabel.setText("Loading");
            System.out.print("Loading... ");
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            loadTimer = new Timer();
            loadTimer.schedule(new LoadingIterator(), 0, 800);
        } else {
            System.out.println("Done");
            loadTimer.cancel();
            loadingLabel.setText("");
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        selectRootDirectoryButton = new javax.swing.JButton();
        containAllTextField = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        mainList = new javax.swing.JList<>();
        openButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        containAnyTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        loadingLabel = new javax.swing.JLabel();
        javaOpenButton = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        preview = new javax.swing.JLabel();
        previewFileNameLabel = new javax.swing.JLabel();
        leftButton = new javax.swing.JButton();
        rightButton = new javax.swing.JButton();
        animateOpenButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setText("Must Contain All Below");

        selectRootDirectoryButton.setText("Select Root Directory");
        selectRootDirectoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectRootDirectoryButtonActionPerformed(evt);
            }
        });

        searchButton.setText("Search");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        mainList.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        mainList.setAutoscrolls(false);
        mainList.setMaximumSize(new java.awt.Dimension(550, 240));
        mainList.setMinimumSize(new java.awt.Dimension(550, 240));
        mainList.setPreferredSize(null);
        mainList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                mainListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(mainList);

        openButton.setText("Open Desktop");
        openButton.setMaximumSize(new java.awt.Dimension(125, 32));
        openButton.setMinimumSize(new java.awt.Dimension(125, 32));
        openButton.setPreferredSize(new java.awt.Dimension(125, 32));
        openButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Must Contain Any  Below");

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel3.setText("Plot Visualizer");

        loadingLabel.setBackground(new java.awt.Color(0, 0, 0));
        loadingLabel.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        loadingLabel.setForeground(new java.awt.Color(255, 0, 0));
        loadingLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(loadingLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(loadingLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javaOpenButton.setText("Open Java");
        javaOpenButton.setActionCommand("Open Image");
        javaOpenButton.setMaximumSize(new java.awt.Dimension(125, 32));
        javaOpenButton.setMinimumSize(new java.awt.Dimension(125, 32));
        javaOpenButton.setPreferredSize(new java.awt.Dimension(125, 32));
        javaOpenButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                javaOpenButtonActionPerformed(evt);
            }
        });

        preview.setBackground(new java.awt.Color(255, 255, 255));
        preview.setForeground(new java.awt.Color(255, 255, 255));
        preview.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        preview.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        previewFileNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        previewFileNameLabel.setMaximumSize(new java.awt.Dimension(12, 500));

        leftButton.setText("<");
        leftButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                leftButtonActionPerformed(evt);
            }
        });

        rightButton.setText(">");
        rightButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rightButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(previewFileNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(leftButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(rightButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(preview, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(20, 20, 20))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(previewFileNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(preview, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(leftButton)
                    .addComponent(rightButton))
                .addContainerGap())
        );

        animateOpenButton.setText("Open Animation");
        animateOpenButton.setMaximumSize(new java.awt.Dimension(125, 32));
        animateOpenButton.setMinimumSize(new java.awt.Dimension(125, 32));
        animateOpenButton.setPreferredSize(new java.awt.Dimension(125, 32));
        animateOpenButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                animateOpenButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addComponent(jLabel3))
                                    .addComponent(jLabel2)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(containAnyTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
                                        .addComponent(containAllTextField, javax.swing.GroupLayout.Alignment.LEADING))
                                    .addComponent(selectRootDirectoryButton))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 554, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(openButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(javaOpenButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(animateOpenButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(182, 182, 182)))
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 517, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(selectRootDirectoryButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(containAllTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(containAnyTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(searchButton))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(openButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(animateOpenButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(javaOpenButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    //event listeners
    private void selectRootDirectoryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectRootDirectoryButtonActionPerformed
        System.out.println("Selecting Root Directory");
        JFileChooser fc = new JFileChooser();
        
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            
        //sets the default location to where the jar is
        ProtectionDomain pd = MainForm.class.getProtectionDomain();
        CodeSource cs = pd.getCodeSource();
        URL location = cs.getLocation();
        File startDir = new File(location.toString());
        fc.setCurrentDirectory(startDir);
        
        int returnVal = fc.showOpenDialog(this);
        
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("Opening");
            root = fc.getSelectedFile();
            fileList.clear();
            toggleLoading(true);
            //New thread
            Runnable runnable = () -> {
                directoryScanner(root.listFiles()); 
                filterSearch();
                toggleLoading(false);
            };
            Thread thread = new Thread(runnable);
            thread.start();
        } else if (returnVal == JFileChooser.CANCEL_OPTION) {
            System.out.println("Canceled");
        }
    }//GEN-LAST:event_selectRootDirectoryButtonActionPerformed

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        System.out.println("Search");
        toggleLoading(true);
        //New thread
        Runnable runnable = () -> {
            filterSearch();
            toggleLoading(false);
        };
        Thread thread = new Thread(runnable);
        thread.start();
        
    }//GEN-LAST:event_searchButtonActionPerformed

    private void openButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openButtonActionPerformed
        toggleLoading(true);
        //New thread
        Runnable runnable = () -> {
            openFile();
            toggleLoading(false);
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }//GEN-LAST:event_openButtonActionPerformed

    private void javaOpenButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_javaOpenButtonActionPerformed
        TempWindow tw = new TempWindow(selectedFileList.get(mainList.getSelectedIndex()));
        tw.setVisible(true);
    }//GEN-LAST:event_javaOpenButtonActionPerformed

    private void leftButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_leftButtonActionPerformed
        try {
            if (mainList.getSelectedIndex() <= 0) {
                mainList.setSelectedIndex(selectedFileList.size() - 1);
            } else {            
                mainList.setSelectedIndex(mainList.getSelectedIndex() - 1);
            } 
        } catch(Exception e) {
            System.out.println("not an image");
        }
    }//GEN-LAST:event_leftButtonActionPerformed

    private void rightButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rightButtonActionPerformed
        //moves up the selected index
        try {
            if (mainList.getSelectedIndex() >= selectedFileList.size() - 1) {
                mainList.setSelectedIndex(0);
            } else {            
                mainList.setSelectedIndex(mainList.getSelectedIndex() + 1);
            } 
        } catch(Exception e) {
            System.out.println("not an image");
        }
    }//GEN-LAST:event_rightButtonActionPerformed

    private void mainListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_mainListValueChanged
        try {
            setView(smartImageScaler(selectedFileList.get(mainList.getSelectedIndex())));
        } catch(Exception e) {
            System.out.println("not an image");
        }
    }//GEN-LAST:event_mainListValueChanged

    private void animateOpenButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_animateOpenButtonActionPerformed
        AnimationWindow aw = new AnimationWindow(selectedFileList, root);
        aw.setVisible(true);
    }//GEN-LAST:event_animateOpenButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton animateOpenButton;
    private javax.swing.JTextField containAllTextField;
    private javax.swing.JTextField containAnyTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton javaOpenButton;
    private javax.swing.JButton leftButton;
    private javax.swing.JLabel loadingLabel;
    private javax.swing.JList<String> mainList;
    private javax.swing.JButton openButton;
    private javax.swing.JLabel preview;
    private javax.swing.JLabel previewFileNameLabel;
    private javax.swing.JButton rightButton;
    private javax.swing.JButton searchButton;
    private javax.swing.JButton selectRootDirectoryButton;
    // End of variables declaration//GEN-END:variables

    class LoadingIterator extends TimerTask {
        public void run() {
            if (loadInt == 0) {
                loadingLabel.setText("Loading.");
            } else if (loadInt == 1) {
                loadingLabel.setText("Loading..");
            } else {
                loadingLabel.setText("Loading...");
                loadInt = -1;
            }      
            loadInt++;
        }
    }
    
}


