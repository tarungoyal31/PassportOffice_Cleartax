package passportscheduling;

/**
 * Applicant Class Symbolizing a passport applicant.
 */
public class Applicant {
    private final int token;
    private final int arrivalTime;
    private final int processingTime;
    private int documentTime;
    private int policeTime;
    private int bioMetricsTime;
    private int finishedTime;
    private int waitingTime;
    private ApplicantState state;

    Applicant(Applicant applicant) {
        this.token = applicant.token;
        this.arrivalTime = applicant.arrivalTime;
        this.documentTime = applicant.documentTime;
        this.policeTime = applicant.policeTime;
        this.bioMetricsTime = applicant.bioMetricsTime;
        this.processingTime = documentTime + policeTime + bioMetricsTime; 
    }
    
    /**
     * Not in use at present but it is good to maintain states in applicant too.
     */
    public enum ApplicantState {
        ARRIVED,
        DOCUMENT_VERIFICATION,
        POLICE_VERIFICATION,
        BIOMETRICS_VERIFICATION,
        DOCUMENT_VERIFICATION_WAITING,
        POLICE_VERIFICATION_WAITING,
        BIOMETRICS_VERIFICATION_WAITING,
        FINISHED
    }
    
        
    public Applicant(int token, int arrivalTime, int documentTime, 
            int policeTime, int biometricsTime) {
        this.token = token;
        this.arrivalTime = arrivalTime;
        this.documentTime = documentTime;
        this.policeTime = policeTime;
        this.bioMetricsTime = biometricsTime;
        this.processingTime = documentTime + policeTime + biometricsTime;
    }
    
    /**
     * Returns total processing time for a particular Officer type.
     * @param officerType type of officer for which work is to be given.
     * @return processing time depending upon type of officer.
     */
    public int getProcessingTime(Officer.OfficerType officerType) {
        switch (officerType) {
            case DOCUMEMT_VERIFICATION :
                return documentTime;
            case POLICE :
                return policeTime;
            case BIOMETRICS :
                return bioMetricsTime;
        }
        return -1;
    }
    
    public int getArrivalTime() {
        return arrivalTime;
    }
    public int getWaitingTime() {
        return waitingTime;
    }
    
    public int getFinishedTime() {
        return finishedTime;
    }
    
    public int getTotalProcessingTime() {
        return processingTime;
    }
    
    public int getToken() {
        return token;
    }
    
    public void updateState(ApplicantState state){ 
        this.state = state;
    }
    
    /**
     * Update the remaining waiting time in the currentState. Returns whether
     * the job is completed.
     * @param officerType type of officer for which work is to be given. 
     * @param time Current time in minutes past 9 a.m.
     * @return Whether the job is completed after updating the time.
     */
    public boolean incrementTimer(Officer.OfficerType officerType, int time) {
        switch (officerType) {
            case DOCUMEMT_VERIFICATION :
                documentTime -= 1;
                return (documentTime == 0);
            case POLICE :
                policeTime -= 1;
                return (policeTime == 0);
            case BIOMETRICS :
                bioMetricsTime -=1;
                if (bioMetricsTime == 0) {
                    finishedTime = time;
                    return true;
                }
            default :
                return false;
        }
    }
}
