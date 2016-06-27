package passportscheduling;

import java.util.LinkedList;

/**
 * First in first out Scheduling strategy. Here first person to reach a particular
 * counter is entertained first.
 */
public class FifoSchedulingStrategy implements SchedulingStrategy{
    
    @Override
    public Applicant getNextApplicantForDocumentVerification(LinkedList<Applicant> waitingApplicants, int time) {
        if (waitingApplicants != null && waitingApplicants.size() > 0) {
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
        return (documentVerifiedApplicants != null 
                && documentVerifiedApplicants.size() > 0) 
                ? documentVerifiedApplicants.getFirst()
                : null;
    }

    @Override
    public Applicant getNextApplicantForBiometricsVerification(LinkedList<Applicant> policeVerifiedApplicants) {
        return (policeVerifiedApplicants != null 
                && policeVerifiedApplicants.size() > 0) 
                ? policeVerifiedApplicants.getFirst()
                : null;
    }
}
