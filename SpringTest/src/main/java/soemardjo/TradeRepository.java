package soemardjo;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface TradeRepository extends CrudRepository<Trade, Long> {
    List<Trade> findByTradeID(long tradeID);
}

