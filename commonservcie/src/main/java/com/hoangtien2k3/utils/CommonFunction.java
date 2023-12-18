package com.hoangtien2k3.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoangtien2k3.common.ValidateException;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@NoArgsConstructor
public class CommonFunction {

    @SneakyThrows
    public static void jsonValidate(InputStream schemaInputStream, String json) {
        if (schemaInputStream == null || json == null) {
            throw new IllegalArgumentException("Schema InputStream and JSON String must not be null.");
        }

        JsonSchema schema = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7).getSchema(schemaInputStream);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(json);
        Set<ValidationMessage> errors = schema.validate(jsonNode);

        Map<String, String> errorMap = new HashMap<>();
        for (ValidationMessage error : errors) {
            String path = formatStringValidate(error.getPath());
            String message = formatStringValidate(error.getMessage());

            if (errorMap.containsKey(path)) {
                errorMap.put(path, errorMap.get(path) + ", " + message);
            } else {
                errorMap.put(path, message);
            }
        }

        if (!errorMap.isEmpty()) {
            throw new ValidateException("RQ01", errorMap, HttpStatus.BAD_REQUEST);
        }
    }

    public static String formatStringValidate(String message) {
        return message.replaceAll("\\$.", "");
    }
}

