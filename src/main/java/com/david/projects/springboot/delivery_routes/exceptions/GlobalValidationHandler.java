package com.david.projects.springboot.delivery_routes.exceptions;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalValidationHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
        System.out.println("1");
        Map<String, String> errors = new HashMap<>();
        
        ex.getBindingResult().getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleEnumValidation(HttpMessageNotReadableException ex) {
        System.out.println("2");
        Map<String, Object> error = new HashMap<>();
        String message = ex.getMessage();
        
        // Patrón para extraer el valor inválido y el tipo de enum
        Pattern pattern = Pattern.compile("Cannot deserialize value of type `(.+?)` from String \"(.+?)\"");
        Matcher matcher = pattern.matcher(message);
        
        if (matcher.find()) {
            String enumType = matcher.group(1);
            String invalidValue = matcher.group(2);
            
            // Determinar qué enum es
            if (enumType.contains("OrderStatus")) {
                error.put("field", "status");
                error.put("invalidValue", invalidValue);
                error.put("message", "Valor inválido para 'status'");
                error.put("allowedValues", "PENDING, IN_PROGRESS, COMPLETED, CANCELLED");
            } 
            else if (enumType.contains("VehicleStatus")) {
                error.put("field", "status");
                error.put("invalidValue", invalidValue);
                error.put("message", "Valor inválido para el estado del vehículo");
                error.put("allowedValues", "AVAILABLE, MAINTENANCE, IN_ROUTE");
            }
            else {
                error.put("message", "Error de conversión de datos");
                error.put("detail", "Valor inválido: " + invalidValue);
            }
        } 
        else {
            // Error genérico de formato JSON
            error.put("message", "Error en el formato del JSON");
            error.put("detail", "Revisa que tu JSON tenga la estructura correcta. Ejemplo: {\"status\": \"PENDING\", \"vehicleId\": 1, \"deliveryPoints\": [...]}");
        }
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}