package com.bootlabs.hpademo.controller;

import com.bootlabs.hpademo.dto.LoadResponse;
import com.bootlabs.hpademo.service.PrimeWorkloadService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoadController {

    private static final int MAX_UPPER_BOUND = 2_000_000;
    private static final int MIN_UPPER_BOUND = 1_000;

    private final PrimeWorkloadService workloadService;

    public LoadController(PrimeWorkloadService workloadService) {
        this.workloadService = workloadService;
    }

    @GetMapping("/api/cpu/burn")
    public LoadResponse burnCpu(@RequestParam(name = "upperBound", defaultValue = "200000") int upperBound) {
        int bound = Math.min(Math.max(upperBound, MIN_UPPER_BOUND), MAX_UPPER_BOUND);
        return workloadService.countPrimesBelow(bound);
    }

    @GetMapping("/api/status")
    public PodStatus status() {
        return new PodStatus(workloadService.podName());
    }

    public record PodStatus(String pod) {
    }
}
