package br.com.joaovitor.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateAccountRequestDTO {

    @NotBlank(message = "CPF não pode estar em branco")
    @Size(min = 11, max = 11, message = "CPF deve ter 11 caracteres")
    private String document;

    // Getters e Setters
    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }


}

