package passportscheduling;

import java.util.LinkedList;

/**
 * Interface to be implemented for devising a Scheduling strategy.
 */
public interface SchedulingStrategy {
    
    /**
     * Returns next available applicant for document verification. Applicant
     * must be ignored if not arrived. 
     */
    Applicant getNextApplicantForDocumentVerification(LinkedList<Applicant> waitingApplicants, int time);
    
    /**
     * Returns next available applicant for police verification. 
     */
    Applicant getNextApplicantForPoliceVerification(LinkedList<Applicant> documentVerifiedApplicants);
    
    /**
     * Returns next available applicant for biometrics verification.  
     */
    Applicant getNextApplicantForBiometricsVerification(LinkedList<Applicant> policeVerifiedApplicants);
}
