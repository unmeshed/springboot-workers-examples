package io.unmeshed.sdk.samples.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
@Builder
public class WorkerOutput {

    private String message;
    private Integer statusCode;
    private Map<String, Object> responseData;
}
