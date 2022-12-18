//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

//package Fcis.details;

/*import Fcis.details.GUI.Frame;
import Fcis.details.GUI.GUI;
import Fcis.details.Work.Job;
import Fcis.details.Work.Philosopher;*/

public class Main {
    private GUI gui = new Frame(this);
    private Job job = new Job(this);

    private Main() {
    }

    public void starterSimulation() {
        this.job.starterSimulation();
    }

    public void stopperSimulation() {
        this.job.stopperSimulation();
    }

    public boolean isSimulationPause() {
        return this.job.isSimulationPause();
    }

    public void pauseSimulation() {
        this.job.pauseSimulation();
    }

    public void resumeSimulation() {
        this.job.resumeSimulation();
    }

    public void updateGUI(Philosopher philosopher) {
        this.gui.update(philosopher);
    }

    public void starter(int n) {
        this.job.initialiser(n);
        this.gui.start(n);
    }

    public static void main(String[] args) {
        int n;
        try {
            n = Integer.parseInt(args[0]);
        } catch (Exception var3) {
            n = 5;// default value
        }

        (new Main()).starter(n);
    }
}
