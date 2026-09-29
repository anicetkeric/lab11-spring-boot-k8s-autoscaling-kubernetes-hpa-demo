package com.bootlabs.hpademo.service;

import com.bootlabs.hpademo.dto.LoadResponse;
import org.springframework.stereotype.Service;

@Service
public class PrimeWorkloadService {

    private final String podName = System.getenv().getOrDefault("POD_NAME", "local");

    public LoadResponse countPrimesBelow(int upperBound) {
        long start = System.nanoTime();
        int count = 0;
        for (int candidate = 2; candidate < upperBound; candidate++) {
            if (isPrime(candidate)) {
                count++;
            }
        }
        long elapsedMs = (System.nanoTime() - start) / 1_000_000;
        return new LoadResponse(podName, upperBound, count, elapsedMs);
    }

    public String podName() {
        return podName;
    }

    private boolean isPrime(int number) {
        if (number < 2) {
            return false;
        }
        for (int divisor = 2; (long) divisor * divisor <= number; divisor++) {
            if (number % divisor == 0) {
                return false;
            }
        }
        return true;
    }
}
