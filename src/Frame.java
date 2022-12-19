
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Frame extends JFrame implements GUI, ActionListener {
    private JButton start;
    private JButton stop;
    private JButton pause;
    private JButton changerNbPhilo;
    private JButton help;
    private GraphicTable table;
    private Main main;

    public Frame(Main main) {
        this.main = main;
        this.setTitle("Dining philosophers");//set the title of the frame to be 
        this.setMinimumSize(new Dimension(600, 600));//sets the minimum size of the frame
        this.setDefaultCloseOperation(3);//make frame exit after close
        this.start = new JButton("Start ");//create the start button
        this.stop = new JButton("Stop ");//create the stop button
        this.pause = new JButton("Pause");//create the pause button
        this.changerNbPhilo = new JButton("Change Number of philosophers");//create the change number of philo button
        
        this.help = new JButton(new ImageIcon(this.getClass().getResource("/help.png")));//create the help button
        this.help.setBounds(6, 6, 24, 24);//locate the frame dimensions
        this.help.setBackground(Constant.BACKGROUND_COLOR_2);//set the help button background color 
        this.help.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));//set the 

        this.stop.setEnabled(false);
        this.pause.setEnabled(false);
        this.start.addActionListener(this);
        this.stop.addActionListener(this);
        this.pause.addActionListener(this);
        this.changerNbPhilo.addActionListener(this);
        this.help.addActionListener(this);
        JLabel author = new JLabel("  Batsi ");
        author.setForeground(Color.YELLOW);
        JPanel Legend = new JPanel(new FlowLayout(2));

        for(int i = 0; i < Constant.COLOR_STATES.length; ++i) {
            this.preparerLegende(Legend, State.getState(i).toString(), Constant.COLOR_STATES[i]);
        }

        Legend.setOpaque(false);
        Container c = new Container();
        c.setLayout(new FlowLayout());
        c.add(this.start);
        c.add(this.stop);
        c.add(this.pause);
        c.add(this.help);
        Container c2 = new Container();
        c2.setLayout(new GridLayout(1, 3));
        c2.add(author);
        c2.add(this.changerNbPhilo);
        c2.add(Legend);
        this.add(c, "North");
        this.add(c2, "South");
        this.getContentPane().setBackground(Constant.BACKGROUND_COLOR_2);
    }

    public void start(int n) {
        if (this.table != null) {
            this.table.stopper();
            this.remove(this.table);
        }

        this.table = new GraphicTable(n);
        this.add(this.table);
        this.pack();
        this.setVisible(true);
    }

    public void update(Philosopher philo) {
        this.table.getState()[philo.getI()] = philo.getState();
        this.repaint();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.start) {
            this.start.setEnabled(false);
            this.stop.setEnabled(true);
            this.pause.setEnabled(true);
            this.table.demarrer();
            this.main.starterSimulation();
        } else if (e.getSource() == this.pause) {
            boolean pause = this.main.isSimulationPause();
            if (pause) {
                this.main.resumeSimulation();
                this.table.resume();
                this.pause.setText("Pause");
            } else {
                this.main.pauseSimulation();
                this.table.pause();
                this.pause.setText("Resume");
            }
        } else if (e.getSource() == this.changerNbPhilo) {
            String message = "How many philosophers?";
            String text = JOptionPane.showInputDialog(this, message);

            int n;
            try {
                n = Integer.parseInt(text);
            } catch (Exception var6) {
                return;
            }

            n = Math.max(Math.min(n, 30), 3);
            this.start.setEnabled(true);
            this.stop.setEnabled(false);
            this.pause.setEnabled(false);
            this.main.stopperSimulation();
            this.main.starter(n);
        } else if (e.getSource() == this.stop) {
            this.start.setEnabled(true);
            this.stop.setEnabled(false);
            this.pause.setEnabled(false);
            this.table.stopper();
            this.main.stopperSimulation();
        } else if (e.getSource() == this.help) {
            JOptionPane.showMessageDialog(this, "Five philosophers are around a table and each\nhas in front of him a dish of food. Being in full thought\nphilosophy, philosophers need energy,\nand they are hungry. To eat, they need two chopsticks,\none to the left and one to the right of each of them:\nthey are therefore shared (each for two philosophers).", "Explanations of the problem", 1);
        }

    }

    private void preparerLegende(JPanel panLegende, String texte, Color color) {
        JPanel carre = new JPanel();
        JLabel label = new JLabel(texte + "  ");
        carre.setBackground(color);
        label.setForeground(color);
        panLegende.add(carre);
        panLegende.add(label);
    }
}
