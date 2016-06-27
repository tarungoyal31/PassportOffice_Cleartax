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
public class TokenBasedSchedulingStrategy implements SchedulingStrategy {

    @Override
    public Applicant getNextApplicantForDocumentVerification(LinkedList<Applicant> waitingApplicants, int time) {
        int leastToken = 999999999;
        Applicant applicant = null;
        for (Applicant waitingApplicant : waitingApplicants) {
            if (waitingApplicant.getToken() < leastToken && waitingApplicant.getArrivalTime() <= time) {
                leastToken = waitingApplicant.getToken();
                applicant = waitingApplicant;
            }
        }
        return applicant;
    }

    @Override
    public Applicant getNextApplicantForPoliceVerification(LinkedList<Applicant> documentVerifiedApplicants) {
        int leastToken = 999999999;
        Applicant applicant = null;
        for (Applicant documentApplicant : documentVerifiedApplicants) {
            if (documentApplicant != null && documentApplicant.getToken() < leastToken) {
                leastToken = documentApplicant.getToken();
                applicant = documentApplicant;
            }
        }
        return applicant;
    }

    @Override
    public Applicant getNextApplicantForBiometricsVerification(LinkedList<Applicant> policeVerifiedApplicants) {
        int leastToken = 999999999;
        Applicant applicant = null;
        for (Applicant biometricApplicant : policeVerifiedApplicants) {
            if (biometricApplicant.getToken() < leastToken) {
                leastToken = biometricApplicant.getToken();
                applicant = biometricApplicant;
            }
        }
        return applicant;
    }
    
}
