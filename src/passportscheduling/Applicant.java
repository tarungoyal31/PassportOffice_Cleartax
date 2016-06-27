/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package passportscheduling;

/**
 *
 * @author tarungoyal
 */
public class Applicant {
    private final int token;
    private int arrivalTime;
    private int documentTime;
    private int policeTime;
    private int bioMetricsTime;
    private int finishedTime;
    private int waitingTime;
    private int processingTime;
    private ApplicantState state;
    
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
     * @param officerType
     * @return 
     */
    public boolean updateTime(Officer.OfficerType officerType, int time) {
            System.out.println(officerType.toString());
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
