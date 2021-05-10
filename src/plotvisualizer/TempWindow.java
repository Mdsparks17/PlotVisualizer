package plotvisualizer;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


public class TempWindow extends javax.swing.JFrame {

    File img;
    SimpleImageInfo sii;
    double scale;

    public TempWindow(File file) {
        initComponents();
        
        //get the passed in image
        this.img = file;  
        
        //scale the image to open to half the screen width
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        try {
            sii = new SimpleImageInfo(img);
            this.setSize(new Dimension((int) width/2, (int) width/2));
            smartImageScaler();
        } catch(Exception e) { e.printStackTrace(); }
    }
    
    private void smartImageScaler() {
        //get frame Dimension
        Dimension d = this.getContentPane().getSize();
        //set jLabel to same dimension
        jLabel1.setSize(d);
        
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
        
        //if scale is the same, exit before rendering a new image icon
        if (scale == this.scale) {
            return;
        } else {
            this.scale = scale;
        }
        
        //render new image icon in regards to scale
        try {
            jLabel1.setIcon(new ImageIcon(ImageIO.read(img).getScaledInstance((int) (sii.getWidth() * scale), (int) (sii.getHeight() * scale), BufferedImage.SCALE_SMOOTH)));
        } catch(Exception e) {}
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        smartImageScaler();
    }//GEN-LAST:event_formComponentResized

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
