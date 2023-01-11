package com.sms.sb.common.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sms.sb.common.constant.ApplicationConstant;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ErrorCodeReader {

    public static Map<String, StudentManagementError> errorMap = new HashMap<>();
    public ErrorCodeReader() {
        readErrorCode();
    }

    private void readErrorCode() {
        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = getClass()
                .getClassLoader().getResourceAsStream(ApplicationConstant.ERROR_CODE_JSON_FILE);
        try {
            String data = readFromInputStream(inputStream);
            ApiError apiError = mapper.readValue(data, ApiError.class);
            errorMap = apiError.getApiErrors().stream()
                    .collect(Collectors.toMap(StudentManagementError::getCode,
                            Function.identity()));
        } catch (IOException exception) {
            System.out.println("Unable to parse error code json: " + exception.getMessage());
        }
    }

    private String readFromInputStream(InputStream inputStream)
            throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }

    public static StudentManagementError getReservationError(String code) {
        return errorMap.get(code);
    }
}
