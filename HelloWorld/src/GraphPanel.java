import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GraphPanel {
    private final DragImage dragImage = new DragImage();
    private final JButton saveButton = new JButton("Save");
    private InfoPanel infoPanel = new InfoPanel();
    private JFrame frame = new JFrame();
    private JLayeredPane layeredPane = new JLayeredPane();
    private PaintGraphPanel panel = new PaintGraphPanel(new Graph());
    private ScrollGraphPanel scrollPane = new ScrollGraphPanel(panel);

    static class DragImage {
        public int x;
        public int y;
    }

    class PaintGraphPanel extends JPanel {
        private double zoomFactor = 1;
        private boolean zoomed = true;
        private AffineTransform at;
        private final Graph graph;

        class mouseClickListener implements MouseListener {
            public void mouseClicked(MouseEvent e) {
                layeredPane.remove(infoPanel);
                layeredPane.repaint();
                User user = graph.getUser((int) (e.getX() / zoomFactor), (int) ((e.getY()) / zoomFactor));
                if (user == null) return;
                infoPanel = new InfoPanel(user.VkID, e.getX(), e.getY(), scrollPane);
                layeredPane.add(infoPanel, new Integer(10));
            }

            public void mousePressed(MouseEvent e) {
                dragImage.x = e.getLocationOnScreen().x + scrollPane.getHorizontalScrollBar().getValue();
                dragImage.y = e.getLocationOnScreen().y + scrollPane.getVerticalScrollBar().getValue();
            }

            public void mouseReleased(MouseEvent e) {

            }

            public void mouseExited(MouseEvent e) {

            }

            public void mouseEntered(MouseEvent e) {

            }
        }

        class mouseMotionListener implements MouseMotionListener {
            public void mouseDragged(MouseEvent e) {
                int dx = dragImage.x - e.getLocationOnScreen().x;
                int dy = dragImage.y - e.getLocationOnScreen().y;
                scrollPane.getHorizontalScrollBar().setValue(dx);
                scrollPane.getVerticalScrollBar().setValue(dy);

            }

            public void mouseMoved(MouseEvent e) {

            }
        }

        class mouseWheelListener implements MouseWheelListener {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getWheelRotation() < 0) {
                    setZoomFactor(1.1 * getZoomFactor());
                    revalidate();
                    repaint();
                    //SwingUtilities.updateComponentTreeUI(frame);
                }
                if (e.getWheelRotation() > 0) {
                    setZoomFactor(getZoomFactor() / 1.1);
                    revalidate();
                    repaint();
                    //SwingUtilities.updateComponentTreeUI(frame);
                }
                infoPanel.setVisible(false);
            }
        }

        public PaintGraphPanel(Graph graph) {
            this.graph = graph;
            addMouseMotionListener(new mouseMotionListener());
            addMouseListener(new mouseClickListener());
            addMouseWheelListener(new mouseWheelListener());
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            setPreferredSize(new Dimension((int) (graph.size * 3 * zoomFactor) + 500,
                    (int) (graph.size * 3 * zoomFactor) + 500));
            Graphics2D g2 = (Graphics2D) g;
            if (zoomed) {
                scrollPane.getVerticalScrollBar().setValue(0);
                scrollPane.getHorizontalScrollBar().setValue(0);
                at = g2.getTransform();
                at.scale(zoomFactor, zoomFactor);
                zoomed = false;
            }
            g2.transform(at);
            g2.setColor(Color.BLACK);
            for (User user : graph.users.users) {
                user.drawUser(g2);
            }
            for (Edge edge : graph.edges.edges) {
                if (graph.nonFriends || edge.isFriends)
                    edge.drawEdge(g2);
            }
        }

        public void setZoomFactor(double factor) {
            if (factor < this.zoomFactor && factor > 0.3) {
                this.zoomFactor = this.zoomFactor / 1.1;
            } else if (factor > this.zoomFactor && zoomFactor < 3) {
                this.zoomFactor = factor;
            }
            this.zoomed = true;
        }

        public double getZoomFactor() {
            return zoomFactor;
        }
    }

    class ScrollGraphPanel extends JScrollPane {
        public ScrollGraphPanel(JPanel panel) {
            super(panel);
            infoPanel.setVisible(false);
            setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            setBounds(0, 0, 1300, 700);
            getVerticalScrollBar().addAdjustmentListener(e -> infoPanel.refresh(scrollPane));
            getHorizontalScrollBar().addAdjustmentListener(e -> infoPanel.refresh(scrollPane));

        }

    }

    public void build(JFrame frame, String inputString, boolean nonFriends, boolean spanningTree) {
        try {
            this.frame = frame;
            this.layeredPane = frame.getLayeredPane();
            layeredPane.remove(scrollPane);
            panel = new PaintGraphPanel(new Graph(inputString, nonFriends, spanningTree));
            scrollPane = new ScrollGraphPanel(panel);
            layeredPane.add(scrollPane, new Integer(5));
            saveButton.addActionListener(e -> {
                BufferedImage bImg = new BufferedImage(panel.getWidth() - 400, panel.getHeight() - 400, BufferedImage.TYPE_INT_RGB);
                Graphics2D cg = bImg.createGraphics();
                panel.paintAll(cg);
                try {
                    String path = "./save_image";
                    File saveFile = new File(path + ".png");
                    for (int i = 1; saveFile.exists(); i++) {
                        path = path.substring(0, path.length() - 1);
                        path = path + i;
                        saveFile = new File(path + ".png");
                    }
                    if (ImageIO.write(bImg, "png", saveFile)) {
                        new InfoDialog("Message", "Graph was saved", 700, 300, 200, 100);
                    }
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            });
            saveButton.setBounds(1183, 633, 100, 50);
            layeredPane.add(saveButton, new Integer(100));
        } catch (MyExceptions myEx) {
            new WarningDialog(frame, "Ошибка", true, myEx);
        }
    }

    public void addUser(String str) {
        try {
            panel.graph.addUser(str);
            panel.revalidate();
            panel.repaint();
        } catch (MyExceptions myEx) {
            new WarningDialog(frame, "Ошибка", true, myEx);
        }

    }
}
