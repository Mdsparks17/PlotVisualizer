package plotvisualizer;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;

public class AnimationWindow extends javax.swing.JFrame {

    LinkedList<File> fileList = new LinkedList<>();
    LinkedList<ImageIcon> animList = new LinkedList<>();
    SimpleImageInfo sii;
    double scale;
    int animationIndex;
    DefaultListModel model = new DefaultListModel();
    File currentImg;
    Timer timer;
    boolean isAnimating = false;
    File root;
    
    public AnimationWindow(LinkedList<File> fileList, File root) {
        this.root = root;
        this.fileList = fileList;
         
        
        initComponents();
        
        System.out.println(fileList);
        
        animationLoad();
        
        try {
            setView(smartImageScaler(fileList.get(0)));
        } catch(Exception e) { System.out.print("x");};
            
        for (File file : fileList) {
            model.addElement("●");
        }
        
        mainList.setModel(model);
        mainList.setSelectedIndex(0);
    }

    private ImageIcon smartImageScaler(File img) throws Exception {
        //get frame Dimension
        Dimension d = viewLabel.getSize();

        try {
            sii = new SimpleImageInfo(img);
        } catch(Exception e) {
            System.out.print("");
//            e.printStackTrace();
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
            System.out.print("*");
//            e.printStackTrace();
            return null;
        }
    }
    
    private void setView(ImageIcon img) {
        viewLabel.setIcon(img);
        nameLabel.setText(fileList.get(mainList.getSelectedIndex()).getName());
    }
    
    private void animate() {
        isAnimating = !isAnimating;
        
        if (isAnimating) {
            timer = new Timer();
            timer.schedule(new ImageIterator(), 0, 2000);
        } else {
            timer.cancel();
        }
    }
    
    private void animationLoad() {
        System.out.print("\nAnimation Load\n");
        animList.clear();
        int i = 0;
        for (File file : fileList) {
            try {
                animList.add(smartImageScaler(file));
                if (i % 5 == 0) {
                    System.out.print(i);
                } else {
                    System.out.print("-");
                }
            } catch(Exception e) { 
//                e.printStackTrace();
                System.out.print("*");
            }
            i++;
        }
        System.out.println("");
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        mainList = new javax.swing.JList<>();
        nameLabel = new javax.swing.JLabel();
        viewLabel = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        resizeButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        mainList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        mainList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                mainListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(mainList);

        nameLabel.setText("jLabel1");

        jButton1.setText("Animate");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        resizeButton.setText("Resize");
        resizeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resizeButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(resizeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(viewLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(resizeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
                    .addComponent(viewLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        if (this.getSize().height < viewLabel.getSize().height ||
               this.getSize().width < viewLabel.getSize().width) {
               viewLabel.setSize(this.getSize().width-50, this.getSize().height-50);
        }
    }//GEN-LAST:event_formComponentResized

    private void mainListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_mainListValueChanged
        try {
            setView(animList.get(mainList.getSelectedIndex()));
        } catch(Exception e) { System.out.print("x"); }   
    }//GEN-LAST:event_mainListValueChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        System.out.println("Toggle Animation");
        animate();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        if (timer != null) { timer.cancel(); }
    }//GEN-LAST:event_formWindowClosed

    private void resizeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resizeButtonActionPerformed
        //New thread
        Runnable runnable = () -> {
            animList.clear();
            currentImg = null;
            
            animationLoad();
            try {
                setView(animList.get(mainList.getSelectedIndex()));
            } catch(Exception e) { System.out.print("x"); }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }//GEN-LAST:event_resizeButtonActionPerformed

    class ImageIterator extends TimerTask {
        public void run() {
            if (mainList.getSelectedIndex() >= fileList.size() - 1) {
                mainList.setSelectedIndex(0);
            } else {            
                mainList.setSelectedIndex(mainList.getSelectedIndex() + 1);
            }
            System.out.println("tick: " + mainList.getSelectedIndex());
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> mainList;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JButton resizeButton;
    private javax.swing.JLabel viewLabel;
    // End of variables declaration//GEN-END:variables
}
