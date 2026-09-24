package io.github.claus7777.game_crm.dto;

public class AddGameRequest{
    private Long apiGameId;
    private String gameName;


    //empty constructor for Jackson serialization
    public AddGameRequest() {}

    public AddGameRequest(Long ApiGameId, String gameName) {
        this.apiGameId = ApiGameId;
        this.gameName = gameName;
    }

    public Long getApiGameId() {
        return apiGameId;
    }
    public void setApiGameId(Long apiGameId){
        this.apiGameId = apiGameId;
    }

    public String getGameName(){
        return this.gameName;
    }

    public void setGameName(String gameName){
        this.gameName = gameName;
    }
}
