package ru.job4j.bank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.job4j.bank.exceptions.BannedUserException;
import ru.job4j.bank.model.User;
import ru.job4j.bank.service.BankService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/ru/job4j/bank")
public class BankController {
    private final BankService bankService;

    private final ObjectMapper objectMapper;

    public BankController(BankService bankService, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.bankService = bankService;
    }

    @PostMapping
    public void transfer(@RequestBody Map<String, String> body) throws BannedUserException {
        var srcPassport = body.get("srcPassport");
        var srcRequisite = body.get("srcRequisite");
        var destPassport = body.get("destPassport");
        var destRequisite = body.get("destRequisite");
        var amount = Double.parseDouble(body.get("amount"));
        if (srcPassport == null || srcRequisite == null || destPassport == null || destRequisite == null || amount == 0D) {
            throw new NullPointerException("Неверные значения");
        }
        Optional<User> userOptional = bankService.findByPassport(destPassport);
        if (userOptional.isPresent()
                && userOptional.get().isStatusUser()) {
            throw new BannedUserException("Получатель заблокирован в системе", userOptional.get());
        }
        bankService.transferMoney(
                srcPassport, srcRequisite,
                destPassport, destRequisite,
                amount
        );
    }

    @ExceptionHandler(value = { BannedUserException.class })
    public void exceptionHandler(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() { {
            put("message", e.getMessage());
            put("type", e.getClass());
        }}));
    }
}