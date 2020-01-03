package com.cgi.demoproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Email {

    private String to;
    private String from;
    private String cc;
    private String subject;
    private String message;
    private boolean isHtml;






}
