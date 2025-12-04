package org.example;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.stereotype.Component;
import java.util.Map; // Import this

@Component
public class BioProcessWorkers {

    @JobWorker(type = "Discard")
    public void discardMaterial(final JobClient client, final ActivatedJob job) {
        System.out.println("--- WORKER: Descartar matéria prima ---");

        client.newCompleteCommand(job.getKey())
                .send()
                .join();
    }

    @JobWorker(type = "Filter")
    public void filterMaterial(final JobClient client, final ActivatedJob job) {
        System.out.println("--- WORKER: Filtrar matéria prima ---");

        boolean qualityCheck = true; // Simulated logic

        // Send the result as a Map (Cleaner and safer than manual JSON string)
        client.newCompleteCommand(job.getKey())
                .variables(Map.of("isQualityGood", qualityCheck))
                .send()
                .join();
    }

    // Don't forget the worker for "update database" if your diagram still has it!
    @JobWorker(type = "update database")
    public void updateDatabase(final JobClient client, final ActivatedJob job) {
        System.out.println("--- WORKER: Update Database ---");
        client.newCompleteCommand(job.getKey()).send().join();
    }
}