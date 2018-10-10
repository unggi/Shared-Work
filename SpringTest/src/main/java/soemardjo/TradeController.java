package soemardjo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trades")
public class TradeController {
    @Autowired
    private TradeRepository tradeRepository;

    @GetMapping
    public Iterable<Trade> findAll() {
        return tradeRepository.findAll();
    }

    @GetMapping("/tradeID/{tradeID}")
    public List<Trade> findByTradeID(@PathVariable long tradeID) {
        return tradeRepository.findByTradeID(tradeID);
    }

    @GetMapping("/{id}")
    public Trade findOne(@PathVariable long id) {
        return tradeRepository.findById(id)
                .orElseThrow(TradeNotFoundException::new);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Trade createTrade(@RequestBody Trade trade) {
        Trade trade1 = tradeRepository.save(trade);

        return trade1;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        tradeRepository.findById(id)
                .orElseThrow(TradeNotFoundException::new);
        tradeRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Trade updateTrade(@RequestBody Trade Trade, @PathVariable long id) {
        if (Trade.getId() != id) {
            throw new TradeIdMismatchException();
        }
        tradeRepository.findById(id)
                .orElseThrow(TradeNotFoundException::new);
        return tradeRepository.save(Trade);
    }
}
