package com.thomasalves.library.resource;

import com.thomasalves.library.dto.LoanDTO;
import com.thomasalves.library.entities.Loan;
import com.thomasalves.library.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/loans")
public class LoanResource {

    @Autowired
    private LoanService loanService;

    @PostMapping
    public ResponseEntity<LoanDTO> loan(@RequestParam Long bookCopyId, @RequestParam Long userId) {
        Loan loan = loanService.loan(bookCopyId, userId);
        LoanDTO loanDTO = new LoanDTO(loan);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .replaceQuery(null)
                .path("/{id}")
                .buildAndExpand(loan.getId())
                .toUri();

        return ResponseEntity.created(uri).body(loanDTO);

    }

    @PatchMapping("/{loanId}/return")
    public ResponseEntity<LoanDTO> returnLoan(@PathVariable Long loanId) {
        Loan loan = loanService.returnLoan(loanId);
        LoanDTO loanDTO = new LoanDTO(loan);
        return ResponseEntity.ok().body(loanDTO);
    }

}
