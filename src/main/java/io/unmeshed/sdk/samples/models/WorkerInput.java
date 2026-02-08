package io.unmeshed.sdk.samples.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Data
@RequiredArgsConstructor
public class WorkerInput {
    public String data;
    private Map<String, String> request;
}
