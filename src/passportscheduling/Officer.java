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
    
    public OfficerState updateTime(int time) {
        if (currentApplicant == null) return OfficerState.IDLE;
        boolean isFinished = currentApplicant.updateTime(officerType, time);
        if (isFinished) {
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
