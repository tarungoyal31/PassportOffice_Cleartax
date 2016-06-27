package passportscheduling;

/**
 * Generic officer class. Each officer is one of three given {@link OfficerType}
 */
public class Officer {
    private Applicant currentApplicant;
    private OfficerState officerState;
    private final OfficerType officerType;
   
    public enum OfficerState {
        IDLE,
        WORKING,
    }
    
    public enum OfficerType {
        DOCUMEMT_VERIFICATION,
        POLICE,
        BIOMETRICS
    }
    
    public Officer(OfficerType officerType) {
        this.officerType = officerType;
        this.officerState = OfficerState.IDLE;
    }
    
    public void setCurrentApplicant(Applicant applicant) {
        currentApplicant = applicant;
        if (applicant == null) {
            return;
        }
        officerState = OfficerState.WORKING;
        // UpdateState is of no use as of now. But it is good to maintain state if
        // more functionalities are to be added in future.
        switch (officerType) {
            case DOCUMEMT_VERIFICATION: 
                currentApplicant.updateState(Applicant.ApplicantState.DOCUMENT_VERIFICATION);
                break;
            case POLICE: 
                currentApplicant.updateState(Applicant.ApplicantState.POLICE_VERIFICATION);
                break;
            case BIOMETRICS: 
                currentApplicant.updateState(Applicant.ApplicantState.BIOMETRICS_VERIFICATION);
                break;
        }
    }
    
    /**
     * Increase time passed for the officer. Here state of officer is changed
     * according to passage of time.
     * @param time current time in minutes passed after 9 a.m.
     * @return OfficerState state of officer after passage of time.
     */
    public OfficerState incrementTimer(int time) {
        if (currentApplicant == null) return OfficerState.IDLE;
        boolean isFinished = currentApplicant.incrementTimer(officerType, time);
        if (isFinished) {
        // UpdateState is of no use as of now. But it is good to maintain state if
        // more functionalities are to be added in future.
            switch (officerType) {
                case DOCUMEMT_VERIFICATION:
                    currentApplicant.updateState(Applicant.ApplicantState.POLICE_VERIFICATION_WAITING);
                    break;
                case POLICE:
                    currentApplicant.updateState(Applicant.ApplicantState.BIOMETRICS_VERIFICATION_WAITING);
                    break;
                case BIOMETRICS:
                    currentApplicant.updateState(Applicant.ApplicantState.FINISHED);
                    break;
            }
            officerState = OfficerState.IDLE;
        } else {
            officerState = OfficerState.WORKING;
        }
        return officerState;
    }
    
    public OfficerState getState() {
        return officerState;
    }
    
    public OfficerType getOfficerType() {
        return officerType;
    }
    
    public Applicant getApplicant() {
        return currentApplicant;
    }
}
