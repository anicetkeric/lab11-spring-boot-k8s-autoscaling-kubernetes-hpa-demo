package com.bootlabs.hpademo.service;

import com.bootlabs.hpademo.dto.LoadResponse;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PrimeWorkloadServiceTest {

    private final PrimeWorkloadService service = new PrimeWorkloadService();

    @Test
    void countsKnownNumberOfPrimesBelowBound() {
        LoadResponse response = service.countPrimesBelow(100);

        assertThat(response.primesFound()).isEqualTo(25);
        assertThat(response.upperBound()).isEqualTo(100);
        assertThat(response.elapsedMs()).isGreaterThanOrEqualTo(0);
    }

    @Test
    void returnsZeroPrimesBelowTwo() {
        LoadResponse response = service.countPrimesBelow(2);

        assertThat(response.primesFound()).isZero();
    }

    @Test
    void fallsBackToLocalPodNameOutsideKubernetes() {
        assertThat(service.podName()).isEqualTo("local");
    }
}
