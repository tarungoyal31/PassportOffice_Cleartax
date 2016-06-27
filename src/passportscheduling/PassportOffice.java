/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package passportscheduling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 *
 * @author tarungoyal
 */
public class PassportOffice {
    private static final int DOCUMENT_VREIFICATION_COUNT = 10;
    private static final int POLICE_COUNT = 10;
    private static final int BIOMETRICS_COUNT = 5;
    
    private final ArrayList<Officer> documentOfficers = new ArrayList();
    private final ArrayList<Officer> policeOfficers = new ArrayList();
    private final ArrayList<Officer> bioMetricsOfficers = new ArrayList();
    private final LinkedList<Applicant> waitingApplicants = new LinkedList();
    private final LinkedList<Applicant> documentVerifiedApplicants = new LinkedList();
    private final LinkedList<Applicant> policeVerifiedApplicants = new LinkedList();
    
    private final ArrayList<Applicant> applicantList;
    private final HashMap<Integer, Applicant> applicantArrivalTimeMap;
    private final Set<Applicant> remainingApplicants;
    private final SchedulingStrategy schedulingStrategy;
    private PassportTimer passportTimer;
    
    private int finishingTime = -1;
    
    public PassportOffice(ArrayList<Applicant> applicantList, SchedulingStrategy schedulingStrategy) {
        
        this.applicantList = applicantList;
        this.applicantArrivalTimeMap = new HashMap();
        this.remainingApplicants = new HashSet();
        this.schedulingStrategy = schedulingStrategy;
        for (Applicant applicant : applicantList) {
            applicantArrivalTimeMap.put(applicant.getArrivalTime(), applicant);
            waitingApplicants.add(applicant);
            remainingApplicants.add(applicant);
        }
        for (int i = 0; i < DOCUMENT_VREIFICATION_COUNT; i++) {
            documentOfficers.add(new Officer(Officer.OfficerType.DOCUMEMT_VERIFICATION));
        }
        for (int i = 0; i < POLICE_COUNT; i++) {
            policeOfficers.add(new Officer(Officer.OfficerType.POLICE));
        }
        for (int i = 0; i < BIOMETRICS_COUNT; i++) {
            bioMetricsOfficers.add(new Officer(Officer.OfficerType.BIOMETRICS));
        }
    }
    
    public ArrayList<Officer> getOfficerList(Officer.OfficerType officerType) {
        switch (officerType) {
            case DOCUMEMT_VERIFICATION :
                return documentOfficers;
            case POLICE :
                return policeOfficers;
            case BIOMETRICS :
                return bioMetricsOfficers;
            default :
                return new ArrayList<Officer>(0);
        }
    }
    
    public void startWorking() {
        passportTimer = new PassportTimer();
        for (int i = 0; i < PassportTimer.TOTAL_TIME; i++) {
            increaseTimer();
            passportTimer.incrementMinute();
            if (finishingTime != -1) {
                return;
            }
        }
    }
    
    public void increaseTimer() {
        for(Officer documentOfficer : documentOfficers) {
            if (documentOfficer.getState() == Officer.OfficerState.IDLE){
                assignApplicantToOfficer(documentOfficer, waitingApplicants);
            } else {
                Officer.OfficerState documentOfficerState = documentOfficer.updateTime(passportTimer.getTime());
                if (documentOfficerState == Officer.OfficerState.IDLE) {
                    documentVerifiedApplicants.add(documentOfficer.getApplicant());
                    documentOfficer.setCurrentApplicant(null);
                    assignApplicantToOfficer(documentOfficer, waitingApplicants);
                }
            }
        }
        for(Officer policeOfficer : policeOfficers) {
            if (policeOfficer.getState() == Officer.OfficerState.IDLE){
                assignApplicantToOfficer(policeOfficer, documentVerifiedApplicants);
            } else {
                Officer.OfficerState policeOfficerState = policeOfficer.updateTime(passportTimer.getTime());
                if (policeOfficerState == Officer.OfficerState.IDLE) {
                    policeVerifiedApplicants.add(policeOfficer.getApplicant());
                    policeOfficer.setCurrentApplicant(null);
                    assignApplicantToOfficer(policeOfficer, documentVerifiedApplicants);
                }
            }
        }
        for(Officer bioMetricOfficer : bioMetricsOfficers) {
            if (bioMetricOfficer.getState() == Officer.OfficerState.IDLE){
                assignApplicantToOfficer(bioMetricOfficer, policeVerifiedApplicants);
            } else {
                Officer.OfficerState bioMetricOfficerState = bioMetricOfficer.updateTime(passportTimer.getTime());
                if (bioMetricOfficerState == Officer.OfficerState.IDLE) {
                    remainingApplicants.remove(bioMetricOfficer.getApplicant());
                    bioMetricOfficer.setCurrentApplicant(null);
                    assignApplicantToOfficer(bioMetricOfficer, policeVerifiedApplicants);
                }
            }
        }
        
        if (remainingApplicants.isEmpty()) {
            workFinished();
        }
    }
    
    public void assignApplicantToOfficer(Officer officer, LinkedList<Applicant> applicantList) {
        Applicant applicant =
                schedulingStrategy.getNextApplicantForPoliceVerification(applicantList);
        officer.setCurrentApplicant(applicant);
        applicantList.remove(officer.getApplicant());
    }
    
    public void workFinished() {
        finishingTime = passportTimer.getTime();
    }
    
    public int getFinishingTime() {
        return finishingTime;
    }
}
