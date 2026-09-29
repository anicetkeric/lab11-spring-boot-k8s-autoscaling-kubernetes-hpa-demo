package com.bootlabs.hpademo.dto;

public record LoadResponse(String pod, int upperBound, int primesFound, long elapsedMs) {
}
