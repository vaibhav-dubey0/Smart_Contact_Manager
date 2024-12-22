package com.smcomanager.Form_Handler;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MessageForm {

    
    @Email(message = "Please provide a valid email address.")
    @NotBlank(message = "Email is required.")
    private String email;

    @NotBlank(message = "Message is required.")
    private String message;
    
}
