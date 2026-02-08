package io.unmeshed.sdk.samples.workers;

import io.unmeshed.client.workers.WorkerFunction;
import io.unmeshed.sdk.samples.components.SampleComponent;
import io.unmeshed.sdk.samples.models.WorkerInput;
import io.unmeshed.sdk.samples.models.WorkerOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SampleWorkers {
    private final SampleComponent sampleComponent;

    @WorkerFunction(name = "sample-worker", workStepNames = {"sample-worker-1", "sample-worker-2"}, namespace = "spring", maxInProgress = 20)
    public WorkerOutput sampleWorker(WorkerInput workerInput) {
        return WorkerOutput.builder()
                .message("Sample message !!")
                .statusCode(200)
                .responseData(sampleComponent.getSampleData())
                .build();
    }
}
