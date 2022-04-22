package engine;

public interface Engine {
    /**
     * This method initiates the execution of the simulation
     */
    void execute();

    /**
     * This method generates a report, which is saved in the variable with the same
     * name.
     */
    public void generateReport();

    public void showReport();

    /**
     * This method saves de report in a file which name is date in format "yyyy-MM-dd_HH-mm-ss-SS"
     * in the folder reports.
     */
    public void saveReport();
}