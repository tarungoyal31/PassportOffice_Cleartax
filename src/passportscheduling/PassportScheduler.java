/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package passportscheduling;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author tarungoyal
 */
public class PassportScheduler {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException{
        ArrayList<Applicant> applicantListBase = new ArrayList();
        BufferedReader br  = new BufferedReader(new InputStreamReader(System.in));
        int applicantCount = Integer.parseInt(br.readLine());
        for (int i = 0; i < applicantCount; i++) {
            String[] applicantInputs = br.readLine().split(" ");
            int arrivalTime = Integer.parseInt(applicantInputs[0]);
            int documentTime = Integer.parseInt(applicantInputs[1]);
            int policeTime = Integer.parseInt(applicantInputs[2]);
            int biometricsTime = Integer.parseInt(applicantInputs[3]);
            Applicant applicant = new Applicant(
                    i, arrivalTime, documentTime, policeTime, biometricsTime);
            applicantListBase.add(applicant);
        }
        SchedulingStrategy[] schedulingStrategies = 
        {
            new TokenBasedSchedulingStrategy(),
            new FifoSchedulingStrategy(),
            new RandomSchedulingStrategy()
        };
        for (SchedulingStrategy strategy : schedulingStrategies) {
            ArrayList<Applicant> applicantList = new ArrayList();
            for (Applicant applicant : applicantListBase) {
                applicantList.add(new Applicant(applicant));
            }
            PassportOffice passportOffice = new PassportOffice(applicantList, strategy);
            System.out.println("Strategy: " + strategy.getClass().toString());
            passportOffice.startWorking();
            System.out.println("Finishing time is: " + passportOffice.getFinishingTime());
            int averageTime = 0;
            for(Applicant applicant : applicantList) {
                averageTime += applicant.getFinishedTime();
            }
            System.out.println("Average time per person: " + ((double) averageTime / applicantList.size()));
            int averageWaitingTime = 0;
            for(Applicant applicant : applicantList) {
                averageWaitingTime += applicant.getFinishedTime() - applicant.getTotalProcessingTime();
            }
            System.out.println("Average waiting time per person: " + ((double) averageWaitingTime / applicantList.size()));
        }
    }
}
