package net.jeremybrooks.mp3me;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.SwingWorker;
import java.nio.file.Files;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ConversionWorker extends SwingWorker<Void, Integer> {
    private static final Logger logger = LogManager.getLogger();
    private final List<ConversionJob> jobList;

    private final long cutoff;
    private final MainWindow mainWindow;


    public ConversionWorker(List<ConversionJob> jobList, long cutoff, MainWindow mainWindow) {
        this.jobList = jobList;
        this.cutoff = cutoff;
        this.mainWindow = mainWindow;
    }

    @Override
    protected Void doInBackground() throws Exception {
        logger.info("set working");
        ConversionJob job = null;
        for (int i = 0; i < jobList.size(); i++) {
            try {
                // mark the job as active, and start a timer to update the clock animation
                job = jobList.get(i);
                job.setStatus(ConversionJob.Status.ACTIVE);
                Timer timer = new Timer();
                timer.scheduleAtFixedRate(new UpdateTask(job, i), 0, 200);

                // this processes each of the files
                Files.walkFileTree(job.getSourcePath(), new FileWalker(job, i, cutoff, this));

                // job has completed
                timer.cancel();
                job.setStatus(ConversionJob.Status.DONE);
                publish(i);

            } catch (Exception e) {
                // todo
            }
            mainWindow.buttonsEnabled(true);
        }
        return null;
    }

    /**
     * Callback to signal that a job has been updated and the GUI should be refreshed
     */
    void jobUpdated(int index) {
        publish(index);
    }

    @Override
    protected void process(List<Integer> chunks) {
        chunks.forEach(mainWindow::updateJob);
    }

    class UpdateTask extends TimerTask {
        private final ConversionJob conversionJob;
        private final int index;

        UpdateTask(ConversionJob conversionJob, int index) {
            this.conversionJob = conversionJob;
            this.index = index;
        }

        @Override
        public void run() {
            conversionJob.incrementClockIndex();
            publish(index);
        }
    }
}