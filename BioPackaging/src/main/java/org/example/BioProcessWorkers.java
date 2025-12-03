package org.example;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.stereotype.Component;

@Component
public class BioProcessWorkers {

    @JobWorker(type = "Discard") // Matches the 'type' in your BPMN
    public void discardMaterial(final JobClient client, final ActivatedJob job) {
        System.out.println("--- WORKER: Descartar matéria prima ---");

        // Complete the task
        client.newCompleteCommand(job.getKey())
                .send()
                .join();
    }

    @JobWorker(type = "Filter") // Matches the 'type' in your BPMN
    public void filterMaterial(final JobClient client, final ActivatedJob job) {
        System.out.println("--- WORKER: Filtrar matéria prima ---");

        // Simulating quality check logic
        boolean qualityCheck = true;

        // Send the result variable back to the engine
        client.newCompleteCommand(job.getKey())
                .variables("{\"isQualityGood\": " + qualityCheck + "}")
                .send()
                .join();
    }
}