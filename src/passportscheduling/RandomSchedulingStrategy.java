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
public class RandomSchedulingStrategy implements SchedulingStrategy {

    @Override
    public Applicant getNextApplicantForDocumentVerification(Set<Applicant> waitingApplicants, int time) {
        for (Applicant applicant : waitingApplicants) {
            if (applicant.getArrivalTime() >= time) {
                return applicant;
            }
        }
        return null;
    }

    @Override
    public Applicant getNextApplicantForPoliceVerification(Set<Applicant> documentVerifiedApplicants) {
        return documentVerifiedApplicants.iterator().next();
    }

    @Override
    public Applicant getNextApplicantForBiometricsVerification(Set<Applicant> policeVerifiedApplicants) {
        return policeVerifiedApplicants.iterator().next();
    }
}
