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
public class RandomSchedulingStrategy implements SchedulingStrategy {

    @Override
    public Applicant getNextApplicantForDocumentVerification(LinkedList<Applicant> waitingApplicants, int time) {
        if (waitingApplicants != null && waitingApplicants.size() > 0) {
            Applicant randomApplicant = waitingApplicants.get(
                    (int)(Math.random() * (waitingApplicants.size())));
            if (randomApplicant.getArrivalTime() <= time) {
                return randomApplicant;
            }
            for (Applicant applicant : waitingApplicants) {
                if (applicant.getArrivalTime() <= time) {
                    return applicant;
                }
            }
        }
        return null;
    }

    @Override
    public Applicant getNextApplicantForPoliceVerification(LinkedList<Applicant> documentVerifiedApplicants) {
        if (documentVerifiedApplicants != null && documentVerifiedApplicants.size() > 0) {
            Applicant randomApplicant = documentVerifiedApplicants.get(
                    (int)(Math.random() * (documentVerifiedApplicants.size())));
            return randomApplicant;
        } else {
            return null;
        }
    }
    
    @Override
    public Applicant getNextApplicantForBiometricsVerification(LinkedList<Applicant> policeVerifiedApplicants) {
        if (policeVerifiedApplicants != null && policeVerifiedApplicants.size() > 0) {
            Applicant randomApplicant = policeVerifiedApplicants.get(
                    (int)(Math.random() * (policeVerifiedApplicants.size())));
            return randomApplicant;
        } else {
            return null;
        }
    }
}
