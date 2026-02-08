package io.unmeshed.sdk.samples.components;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SampleComponent {

    public Map<String, Object> getSampleData() {
        return Map.of(
                "message", "Hello World !!",
                "success", true,
                "nestedMap", Map.of("nestedKey1", "nestedVal1")
        );
    }
}

