package com.developer.wiki.oauth.exception;

import com.google.gson.Gson;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class AccessTokenException extends RuntimeException {

    TOKEN_ERROR token_error;

    public enum TOKEN_ERROR {
        UNACCEPT(403,"Token is null or too short"),
        BADTYPE(403, "Token type Bearer"),
        MALFORM(403, "Malformed Token"),
        BADSIGN(403, "BadSignatured Token"),
        EXPIRED(401, "Expired Token"),
        NOTFOUND(403,"NotFound User");

        private int status;
        private String messages;

        TOKEN_ERROR(int status, String msg){
            this.status = status;
            this.messages = msg;
        }

        public int getStatus() {
            return this.status;
        }

        public String getMsg() {
            return this.messages;
        }
    }

    public AccessTokenException(TOKEN_ERROR error){
        super(error.name());
        this.token_error = error;
    }

    public void sendResponseError(HttpServletResponse response){

        response.setStatus(token_error.getStatus());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Gson gson = new Gson();

        String responseStr = gson.toJson(Map.of("messages", token_error.getMsg()));

        try {
            response.getWriter().println(responseStr);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
