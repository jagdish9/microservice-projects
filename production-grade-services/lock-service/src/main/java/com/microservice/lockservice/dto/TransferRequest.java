package com.microservice.lockservice.dto;

public record TransferRequest(
     Long fromId,
     Long toId,
     Double amount
) {

}
