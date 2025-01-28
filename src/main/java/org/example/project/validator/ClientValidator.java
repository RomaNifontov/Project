package org.example.project.validator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.project.dto.ClientRequestDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ClientValidator {

    @Value("${moderation-scheduler.dictionary-path}")
    private String dictionaryPath;

    private final String EMAIL_PATTERN = "^((([0-9A-Za-z]{1}[-0-9A-z\\.]" +
            "{0,30}[0-9A-Za-z]?)|([0-9А-Яа-я]{1}[-0-9А-я\\.]{0,30}[0-9А-Яа-я]?))@([-A-Za-z]{1,}\\.){1,}[-A-Za-z]{2,})$";


    private final String PHONE_PATTERN = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$";

    public void validate(ClientRequestDto clientDto) {
        List<String> badWords = getWords();
        if (badWords.contains(clientDto.getFirstName())) {
            throw new IllegalArgumentException("The name is incorrect " + clientDto.getFirstName());
        }
        if(badWords.contains(clientDto.getLastName())) {
            throw new IllegalArgumentException("The lastname is incorrect " + clientDto.getLastName());
        }
    }

    public void validateEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("The email is incorrect " + email);
        }
    }

    public void validateNumber(String number) {
        Pattern pattern = Pattern.compile(PHONE_PATTERN, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(number);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("The phone number is incorrect " + number);
        }
    }

    private List<String> getWords() {
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> words = new ArrayList<>();
        try {
            JsonNode rootNode = objectMapper.readTree(new File(dictionaryPath));
            JsonNode wordsNote = rootNode.path("words");

            for (JsonNode wordNote : wordsNote) {
                words.add(wordNote.asText());
            }
        } catch (IOException e) {
            throw new IllegalStateException("The file could not be parsed", e);
        }
        return words;
    }

}
