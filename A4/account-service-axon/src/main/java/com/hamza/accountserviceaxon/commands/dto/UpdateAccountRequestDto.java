package com.hamza.accountserviceaxon.commands.dto;

public record UpdateAccountRequestDto(String accountId, double ammount, String currency) {
}
