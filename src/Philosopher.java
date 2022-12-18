import java.util.concurrent.Semaphore;

public class Philosopher extends Semaphore implements Runnable {
    private Job job;
    private Thread thread;
    private State state;
    private int i;

    Philosopher(Job job, int i) {
        super(1, true);
        this.job = job;
        this.state = State.THINK;
        this.i = i;
        this.thread = new Thread(this);
    }

    public int getI() {
        return this.i;
    }

    public State getState() {
        return this.state;
    }

    void starter() {
        this.thread.start();
    }

    void stopper() {
        this.thread.stop();
    }

    void pause() {
        this.thread.suspend();
    }

    void resume() {
        this.thread.resume();
    }

    private void change() {
        this.job.getControleur().updateGUI(this);
    }

    private void thinker() throws InterruptedException {//think
        if (this.state == State.THINK) {
            this.change();
            Thread.sleep((long)(3000 + Job.choosen.nextInt(3000)));//sleeping 3000+random
        }
    }

    private void eater() throws InterruptedException {// see if he is eatting
        if (this.state == State.EAT) {
            this.change();
            Thread.sleep((long)(5000 + Job.choosen.nextInt(3000)));
        }
    }

    private void hunger() {// see if he is hungry
        Philosopher[] nebour = this.job.getNebours(this);
        if (this.state == State.HUNGRY && nebour[0].state != State.EAT && nebour[1].state != State.EAT) {
            this.state = State.EAT;
            this.job.setSticks(this.i, false);
            this.job.setSticks((this.i + 1) % this.job.getNb(), false);
            this.change();
            this.release();
        }

    }

    private void takeSicks() throws InterruptedException {
        synchronized(this) {
            this.state = State.HUNGRY;
            this.change();
            this.hunger();
        }

        this.acquire();
    }

    private synchronized void returnSticks() {
        this.state = State.THINK;
        this.job.setSticks(this.i, true);
        this.job.setSticks((this.i + 1) % this.job.getNb(), true);
        this.change();
        Philosopher[] var1 = this.job.getNebours(this);
        int size = var1.length;// size

        for(int current = 0; current < size; ++current) {
            Philosopher nebourr = var1[current];
            nebourr.hunger();
        }

    }

    public void run() {
        while(!this.job.isSimulationPause()) {
            try {
                this.thinker();
                this.takeSicks();
                this.eater();
                this.returnSticks();
            } catch (InterruptedException var2) {
                var2.printStackTrace();
            }
        }

    }
}
