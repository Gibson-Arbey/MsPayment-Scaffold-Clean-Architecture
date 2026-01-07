package co.clean_architecture.usecase.useraccount.command;

public record UpdateUserAccounStatusCommand(
        Long userAccountId,
        String status
) {
}
