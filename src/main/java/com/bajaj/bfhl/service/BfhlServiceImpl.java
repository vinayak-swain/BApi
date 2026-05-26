package com.bajaj.bfhl.service;

import com.bajaj.bfhl.config.UserConfig;
import com.bajaj.bfhl.dto.BfhlRequest;
import com.bajaj.bfhl.dto.BfhlResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class BfhlServiceImpl implements BfhlService {

    private static final Pattern NUMERIC_PATTERN = Pattern.compile("^\\d+$");
    private static final Pattern ALPHA_PATTERN = Pattern.compile("^[a-zA-Z]+$");

    private final UserConfig userConfig;

    public BfhlServiceImpl(UserConfig userConfig) {
        this.userConfig = userConfig;
    }

    @Override
    public BfhlResponse processData(BfhlRequest request) {
        List<String> oddNumbers = new ArrayList<>();
        List<String> evenNumbers = new ArrayList<>();
        List<String> alphabets = new ArrayList<>();
        List<String> specialCharacters = new ArrayList<>();
        long sum = 0;

        for (String element : request.getData()) {
            if (NUMERIC_PATTERN.matcher(element).matches()) {
                // CASE 1: purely numeric
                long number = Long.parseLong(element);
                sum += number;
                if (number % 2 == 0) {
                    evenNumbers.add(element);
                } else {
                    oddNumbers.add(element);
                }
            } else if (ALPHA_PATTERN.matcher(element).matches()) {
                // CASE 2: purely alphabetic — store uppercased
                alphabets.add(element.toUpperCase());
            } else {
                // CASE 3: anything else (mixed, symbols, etc.)
                specialCharacters.add(element);
            }
        }

        String concatString = buildConcatString(alphabets);

        return BfhlResponse.builder()
                .isSuccess(true)
                .userId(userConfig.getUserId())
                .email(userConfig.getEmail())
                .rollNumber(userConfig.getRollNumber())
                .oddNumbers(oddNumbers)
                .evenNumbers(evenNumbers)
                .alphabets(alphabets)
                .specialCharacters(specialCharacters)
                .sum(String.valueOf(sum))
                .concatString(concatString)
                .build();
    }

    /**
     * Builds the concat_string following the exact algorithm:
     *
     * Step 1: From the alphabets list (already uppercased),
     *         collect every individual CHARACTER across all elements.
     *         Preserve left-to-right order.
     *
     * Step 2: Reverse the full character list.
     *
     * Step 3: Apply alternating case starting with UPPERCASE at index 0:
     *         index 0 → uppercase, index 1 → lowercase, index 2 → uppercase ...
     *
     * Step 4: Join into a single string.
     */
    private String buildConcatString(List<String> alphabets) {
        if (alphabets.isEmpty()) {
            return "";
        }

        // Step 1: collect every individual character across all alphabet elements
        List<Character> chars = new ArrayList<>();
        for (String alpha : alphabets) {
            for (char c : alpha.toCharArray()) {
                chars.add(c);
            }
        }

        // Step 2: reverse the full character list
        List<Character> reversed = new ArrayList<>(chars.size());
        for (int i = chars.size() - 1; i >= 0; i--) {
            reversed.add(chars.get(i));
        }

        // Step 3: apply alternating case (index 0 → UPPER, index 1 → lower, ...)
        StringBuilder sb = new StringBuilder(reversed.size());
        for (int i = 0; i < reversed.size(); i++) {
            char c = reversed.get(i);
            if (i % 2 == 0) {
                sb.append(Character.toUpperCase(c));
            } else {
                sb.append(Character.toLowerCase(c));
            }
        }

        // Step 4: return as single string
        return sb.toString();
    }
}
