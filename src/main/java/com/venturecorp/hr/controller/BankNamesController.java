package com.venturecorp.hr.controller;

import com.venturecorp.hr.model.BankNames;
import com.venturecorp.hr.repository.BankNamesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/banks")
@PreAuthorize("hasAnyRole('SUPERADMIN','PREMIUMADMIN')")
public class BankNamesController {

    @Autowired
    private BankNamesRepository bankNamesRepository;

    // ✅ ADD bank
    @PostMapping
    public ResponseEntity<BankNames> addBank(@RequestBody BankNames bank) {

        if (bankNamesRepository.existsByBankName(bank.getBankName())) {
            return ResponseEntity.badRequest()
                    .body(null);
        }

        if (bankNamesRepository.existsByBankCode(bank.getBankCode())) {
            return ResponseEntity.badRequest()
                    .body(null);
        }

        return ResponseEntity.ok(bankNamesRepository.save(bank));
    }

    // ✅ GET all banks
    @GetMapping
    public ResponseEntity<List<BankNames>> getAllBanks() {
        return ResponseEntity.ok(bankNamesRepository.findAll());
    }

    // ✅ UPDATE bank
    @PutMapping("/{id}")
    public ResponseEntity<BankNames> updateBank(
            @PathVariable Long id,
            @RequestBody BankNames updatedBank) {

        BankNames bank = bankNamesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bank not found"));

        bank.setBankName(updatedBank.getBankName());
        bank.setBankCode(updatedBank.getBankCode());

        return ResponseEntity.ok(bankNamesRepository.save(bank));
    }

    // ✅ DELETE bank
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBank(@PathVariable Long id) {

        if (!bankNamesRepository.existsById(id)) {
            return ResponseEntity.badRequest()
                    .body("Bank not found");
        }

        bankNamesRepository.deleteById(id);
        return ResponseEntity.ok("Bank deleted successfully");
    }
}
