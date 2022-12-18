import java.util.Random;

public class Job {
    static final Random choosen = new Random();
    private Main main;
    private boolean simulationStart;
    private boolean simulationPause;
    private boolean[] sticks = null;  // sticks state true? avaliable : unavailable
    private Philosopher[] philosophers = null;
    private int n;

    public Job(Main main) {
        this.main = main;
    }

    Main getControleur() {
        return this.main;
    }

    public int getNb() {
        return this.n;
    }// return the size

    public boolean getBaguette(int n) {
        return this.sticks[n];
    }

    public Philosopher[] getNebours(Philosopher philo) {
        int leftIndx = (philo.getI() - 1) % this.getNb();
        int rightIndx = (philo.getI() + 1) % this.getNb();
        if (leftIndx < 0) {
            leftIndx += this.getNb();
        }

        return new Philosopher[]{this.philosophers[leftIndx], this.philosophers[rightIndx]};
    }

    public void setSticks(int n, boolean presence) {
        this.sticks[n] = presence;
    }

    public void initialiser(int n) {
        this.n = n;
    }

    public void starterSimulation() {
        if (this.simulationStart) {
            this.stopperSimulation();
        }

        this.sticks = new boolean[this.n];
        this.philosophers = new Philosopher[this.n];

        for(int i = 0; i < this.n; ++i) {
            this.sticks[i] = true;
        }

        for(int i = 0; i < this.n; ++i) {
            Philosopher philo = new Philosopher(this, i);
            philo.starter();
            this.philosophers[i] = philo;
        }

        this.simulationStart = true;
    }

    public void stopperSimulation() {
        if (this.simulationStart) {
            for(int i = 0; i < this.n; ++i) {
                this.philosophers[i].stopper();
            }

            this.simulationStart = false;
            this.simulationPause = false;
        }

    }

    public boolean isSimulationPause() {
        return this.simulationPause;
    }

    public void pauseSimulation() {
        this.simulationPause = true;
        Philosopher[] var1 = this.philosophers;
        int size = var1.length;

        for(int current = 0; current < size; ++current) {
            Philosopher philosopher = var1[current];
            philosopher.pause();
        }

    }

    public void resumeSimulation() {
        this.simulationPause = false;
        Philosopher[] var1 = this.philosophers;
        int size = var1.length;

        for(int current = 0; current < size; ++current) {
            Philosopher philosopher = var1[current];
            philosopher.resume();
        }

    }
}
