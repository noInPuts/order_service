package cphbusiness.noinputs.order_service.main.dto;

public class ErrorResponseDTO {
    private String error;

    public ErrorResponseDTO(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
