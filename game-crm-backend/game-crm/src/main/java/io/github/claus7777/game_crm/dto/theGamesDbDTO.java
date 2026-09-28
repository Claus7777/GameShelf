package io.github.claus7777.game_crm.dto;

public record theGamesDbDTO(
    Long id,
    String name,
    Cover cover
) {
    public record Cover(String url){}
}  