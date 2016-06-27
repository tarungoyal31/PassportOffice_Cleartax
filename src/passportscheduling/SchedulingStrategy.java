/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package passportscheduling;

import java.util.Set;

/**
 *
 * @author tarungoyal
 */
public interface SchedulingStrategy {
    
    Applicant getNextApplicantForDocumentVerification(Set<Applicant> waitingApplicants, int time);
    
    Applicant getNextApplicantForPoliceVerification(Set<Applicant> documentVerifiedApplicants);
    
    Applicant getNextApplicantForBiometricsVerification(Set<Applicant> policeVerifiedApplicants);
}
