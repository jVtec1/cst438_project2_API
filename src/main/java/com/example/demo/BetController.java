package com.example.demo;

import com.example.demo.entity.Bet;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/bets")
public class BetController {

    private final BetService service;

    public BetController(BetService service) {
        this.service = service;
    }

    // List (pagination via query params)
    @GetMapping
    public Page<Bet> list(@RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "20") int size) {
        return service.list(PageRequest.of(page, size));
    }

    // Get one
    @GetMapping("/{id}")
    public Bet get(@PathVariable Long id) {
        return service.get(id);
    }

    // Create (JSON body)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Bet create(@Valid @RequestBody Bet bet) {
        return service.create(bet);
    }

    // Update (JSON body)
    @PutMapping("/{id}")
    public Bet update(@PathVariable Long id, @Valid @RequestBody Bet bet) {
        return service.update(id, bet);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public Map<String, String> handleNotFound(EntityNotFoundException ex) {
        return Map.of("error", ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public Map<String, String> handleBadRequest(IllegalArgumentException ex) {
        return Map.of("error", ex.getMessage());
    }
}
