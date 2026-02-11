package io.unmeshed.sdk.samples.workers;

import io.unmeshed.client.workers.WorkerFunction;
import io.unmeshed.sdk.samples.components.SampleComponent;
import io.unmeshed.sdk.samples.models.WorkerInput;
import io.unmeshed.sdk.samples.models.WorkerOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SpELWorkers {

    @Autowired
    private SampleComponent sampleComponent;

    @WorkerFunction(
            name = "${unmeshed.worker.first.name:first-worker}",
            namespace = "${unmeshed.namespace}",
            maxInProgressExpression = "${unmeshed.worker.first.max-in-progress:100}",
            workStepNames = {
                    "${unmeshed.worker.first.step1:first-worker-1}",
                    "${unmeshed.worker.first.step2:first-worker-2}"
            }
    )
    public WorkerOutput firstWorker(WorkerInput workerInput) {
        return WorkerOutput.builder()
                .message("Sample message from First worker !!")
                .statusCode(200)
                .responseData(sampleComponent.getSampleData())
                .build();
    }

    @WorkerFunction(
            name = "${unmeshed.worker.second.name:second-worker}",
            namespace = "${unmeshed.namespace:spring}",
            maxInProgress = 50,
            workStepNames = {
                    "${unmeshed.worker.second.step1:second-worker-1}",
                    "${unmeshed.worker.second.step2:second-worker-2}"
            }
    )
    public WorkerOutput secondWorker(WorkerInput workerInput) {
        return WorkerOutput.builder()
                .message("Sample message from Second worker !!")
                .statusCode(200)
                .responseData(sampleComponent.getSampleData())
                .build();
    }

    @WorkerFunction(
            name = "#{'${unmeshed.worker.second.name:second-worker}' + '.' + (systemEnvironment['HOST'] ?: 'local').toLowerCase()}",
            namespace = "#{(systemEnvironment['USER'] ?: 'spring')}",
            maxInProgressExpression = "${unmeshed.worker.second.max-in-progress:100}",
            workStepNames = {
                    "${unmeshed.worker.second.step1:second-worker-1-alt}",
                    "#{'${unmeshed.worker.second.step2:second-worker-2}' + '.' + (systemEnvironment['USER'] ?: 'local')}",
                    "#{ (systemEnvironment['HOST'] != null) ? 'hosted-step' : 'local-step' }",
                    "#{ 'STEP-' + '${unmeshed.worker.second.name:second-worker}'.toUpperCase() }",
                    "#{ 'batch-' + (10 * 5) }",
                    "#{'${unmeshed.worker.second.name:second-worker}'.replace('-', '_') + '_processed'}"
            }
    )
    public WorkerOutput expressionsWorker(WorkerInput workerInput) {
        return WorkerOutput.builder()
                .message("Sample message from Expression worker !!")
                .statusCode(200)
                .responseData(sampleComponent.getSampleData())
                .build();    }



    @WorkerFunction(
            name = "third-worker",
            namespace = "${unmeshed.namespace:spring}",
            maxInProgress = 20
    )
    public WorkerOutput thirdWorker(WorkerInput workerInput) {
        return WorkerOutput.builder()
                .message("Sample message from Third worker !!")
                .statusCode(200)
                .responseData(sampleComponent.getSampleData())
                .build();
    }
}
