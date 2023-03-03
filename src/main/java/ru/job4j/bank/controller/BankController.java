package ru.job4j.bank.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.bank.service.BankService;

import java.util.Map;

@RestController
@RequestMapping("/ru/job4j/bank")
public class BankController {
    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @PostMapping
    public void transfer(@RequestBody Map<String, String> body) {
        var srcPassport = body.get("srcPassport");
        var srcRequisite = body.get("srcRequisite");
        var destPassport = body.get("destPassport");
        var destRequisite = body.get("destRequisite");
        var amount = Double.parseDouble(body.get("amount"));
        if (srcPassport == null || srcRequisite == null || destPassport == null || destRequisite == null || amount == 0D) {
            throw new NullPointerException("Неверные значения");
        }
        bankService.transferMoney(
                srcPassport, srcRequisite,
                destPassport, destRequisite,
                amount
        );
    }
}