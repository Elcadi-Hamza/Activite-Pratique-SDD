package com.hamza.accountserviceaxon.commands.dto;

public record DebitAccountRequestDto(String accountId, double ammount, String currency) {
}
