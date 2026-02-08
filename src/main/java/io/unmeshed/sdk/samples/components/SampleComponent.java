package io.unmeshed.sdk.samples.components;

import io.unmeshed.client.WorkContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class SampleComponent {

    public Map<String, Object> getSampleData() {
        log.info("Current Work Request Details : {}", WorkContext.currentWorkRequest());
        return Map.of(
                "message", "Hello World !!",
                "success", true,
                "nestedMap", Map.of("nestedKey1", "nestedVal1")
        );
    }
}

