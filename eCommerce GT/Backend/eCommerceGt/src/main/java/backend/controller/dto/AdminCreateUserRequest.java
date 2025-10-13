package backend.controller.dto;


public record AdminCreateUserRequest (
        String dpi,
        String userName,
        String email,
        String password,
        String role) {

}