/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package passportscheduling;

import java.util.LinkedList;

/**
 *
 * @author tarungoyal
 */
public interface SchedulingStrategy {
    
    Applicant getNextApplicantForDocumentVerification(LinkedList<Applicant> waitingApplicants, int time);
    
    Applicant getNextApplicantForPoliceVerification(LinkedList<Applicant> documentVerifiedApplicants);
    
    Applicant getNextApplicantForBiometricsVerification(LinkedList<Applicant> policeVerifiedApplicants);
}
