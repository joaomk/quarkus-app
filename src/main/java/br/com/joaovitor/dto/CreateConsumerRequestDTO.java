package br.com.joaovitor.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateConsumerRequestDTO {
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @NotBlank(message = "Document cannot be blank")
    @Size(min = 11, max = 11, message = "Document must have 11 characters")
    private String document;
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email")
    private String email;
    @NotBlank(message = "Phone cannot be blank")
    @Size(min = 11, max = 11, message = "Phone must have 11 characters")
    private String phone;
    @Valid
    private AddressDTO address;


    //Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }
}
