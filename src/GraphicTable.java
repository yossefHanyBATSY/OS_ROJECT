
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D.Double;
import javax.swing.JPanel;

public class GraphicTable extends JPanel implements Runnable {
    private final Double formeTable = new Double(-2.5D, -2.5D, 5.0D, 5.0D);
    private Thread t;
    private int n;
    private State[] states;
    private Dimension dimTable = new Dimension(0, 0);
    private AffineTransform at;

    GraphicTable(int n) {
        this.n = n;
        this.setBackground(Constant.BACKGROUND_COLOR);
        this.states = new State[n];
    }

    public State[] getState() {
        return this.states;
    }

    private void dessinerPhilosophe(int i, Graphics2D g2d) {
        if (this.states[i] != null) {
            g2d.setPaint(Constant.COLOR_STATES[this.states[i].getIndx()]);
        } else {
            g2d.setPaint(this.getBackground());
        }

        double size = Math.min(6.0D / Math.pow((double)this.n, 0.8D), 1.5D);
        Double forme = new Double(3.0D, -size / 2.0D, size, size);
        if (this.t == null || !this.t.isAlive()) {
            g2d.setColor(new Color(25, 25, 25));
        }

        g2d.fill(forme);
        g2d.setPaint(Color.black);
        g2d.draw(forme);
        g2d.rotate(6.283185307179586D / (double)this.n);
    }

    private StateStick getEtatBaguettePour(int i) {
        if (this.states[(i - 1 + this.n) % this.n] == State.EAT) {
            return StateStick.EAT_LEFT;
        } else {
            return this.states[i] == State.EAT ? StateStick.EAT_RIGHT : StateStick.ON_THE_TABLE;
        }
    }

    private void dessinerBaguette(int i, Graphics2D g2d) {
        double phi = (double)(2 * i - 1) * 3.141592653589793D / (double)this.n;
        StateStick etat = this.getEtatBaguettePour(i);
        float facteurPhi = 0.0F;
        if (etat == StateStick.EAT_LEFT) {
            facteurPhi = -0.8F;
        }

        if (etat == StateStick.EAT_RIGHT) {
            facteurPhi = 0.8F;
        }

        phi += (double)facteurPhi * (3.141592653589793D / (double)(2 * this.n));
        double length = 6.0D / (double)this.n;
        g2d.setPaint(Constant.STICK_COLOR);
        g2d.rotate(phi);
        g2d.setStroke(new BasicStroke((float)(0.3D / Math.pow((double)this.n, 0.55D))));
        if (etat != StateStick.EAT_LEFT && etat != StateStick.EAT_RIGHT) {
            g2d.draw(new java.awt.geom.Line2D.Double(2.3D - length, 0.0D, 2.3D, 0.0D));
        } else {
            g2d.draw(new java.awt.geom.Line2D.Double(3.0D, 0.0D, 3.0D + length, 0.0D));
        }

        g2d.rotate(-phi);
    }

    private void majTailleTable() {
        if (!this.dimTable.equals(this.getSize())) {
            this.dimTable = this.getSize();
            double scale = (double)Math.min(this.dimTable.width, this.dimTable.height) / 10.0D;
            this.at = new AffineTransform();
            this.at.translate((double)this.dimTable.width / 2.0D, (double)this.dimTable.height / 2.0D);
            this.at.scale(scale, scale);
        }

    }

    public void demarrer() {
        this.t = new Thread(this);
        this.t.start();
    }

    public void stopper() {
        if (this.t != null) {
            this.t.interrupt();
        }

    }

    public void pause() {
        this.t.suspend();
    }

    public void resume() {
        this.t.resume();
    }

    public void run() {
        while(true) {
            try {
                Thread.sleep(500L);
            } catch (InterruptedException var2) {
                for(int i = 0; i < this.n; ++i) {
                    this.states[i] = null;
                }

                this.repaint();
                return;
            }

            this.repaint();
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform saveAT = g2d.getTransform();
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        rh.add(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
        g2d.setRenderingHints(rh);
        this.majTailleTable();
        g2d.transform(this.at);
        g2d.setStroke(new BasicStroke(0.15F));
        g2d.setPaint(Constant.TABLE_COLOR);
        g2d.fill(this.formeTable);
        g2d.setPaint(Constant.TABLE_COLOR_Boundary);
        g2d.draw(this.formeTable);
        g2d.setStroke(new BasicStroke(0.04F));

        int i;
        for(i = 0; i < this.n; ++i) {
            this.dessinerPhilosophe(i, g2d);
        }

        for(i = 0; i < this.n; ++i) {
            this.dessinerBaguette(i, g2d);
        }

        g2d.setTransform(saveAT);
    }

    public Dimension getPreferredSize() {
        return new Dimension(600, 600);
    }
}
