package diegosWafles.domain.model.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ResponseHandler {

    public static ResponseEntity<Object> generateResponse(String alert, boolean status, Object data) {
        Map<String, Object> map = new LinkedHashMap<>(); // Cambio aquí
        map.put("alert", alert);
        map.put("status", status);
        map.put("messages", new ArrayList<>());
        map.put("data", data);

        HttpStatus httpStatus = status ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<Object>(map, httpStatus);
    }

    public static ResponseEntity<Object> generateResponse(String alert, boolean status, List<String> messages, Object data) {
        Map<String, Object> map = new LinkedHashMap<>(); // Cambio aquí
        map.put("alert", alert);
        map.put("status", status);
        map.put("messages", messages != null ? messages : new ArrayList<>());
        map.put("data", data);

        HttpStatus httpStatus = status ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<Object>(map, httpStatus);
    }

    public static ResponseEntity<Object> generateResponse(String alert, boolean status) {
        return generateResponse(alert, status, null);
    }

    // Método específico para errores
    public static ResponseEntity<Object> generateErrorResponse(String alert, String errorMessage) {
        List<String> messages = new ArrayList<>();
        messages.add(errorMessage);

        Map<String, Object> map = new LinkedHashMap<>(); // Cambio aquí
        map.put("alert", alert);
        map.put("status", false);
        map.put("messages", messages);
        map.put("data", null);

        return new ResponseEntity<Object>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Método para errores de validación/not found
    public static ResponseEntity<Object> generateNotFoundResponse(String alert, String errorMessage) {
        List<String> messages = new ArrayList<>();
        messages.add(errorMessage);

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("alert", alert);
        map.put("status", false);
        map.put("messages", messages);
        map.put("data", null);

        return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
    }
}