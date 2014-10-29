

CREATE CONSTRAINT ON (s:Security) ASSERT s.esmp IS UNIQUE
CREATE INDEX ON :Security(cusip)
CREATE INDEX ON :Security(description)

CREATE CONSTRAINT ON (p:Person) ASSERT p.userId IS UNIQUE
CREATE CONSTRAINT ON (c:Client) ASSERT c.clientId IS UNIQUE

MERGE (security:Security{esmp:123456, cusip:'76112BWV8', description:'RAMP 2005-RS7 A3', issuerName:'RAMP SERIES 2005-RS7 TRUST', securityType:'ABS-OTHER', collateralType:'HEQ', mortgageType:'FLT,+', trancheTypeInfo:'FLT,STE', servicer:'State Street Bank & Trust Co', trustee:'Silverado Mortgage', slice:'52% to 100%', couponType:'Flt'})

MERGE (security)-[:HAS_INDICATIVE]->(indicative:RmbsSecurityIndicative{coupon:'L1M+37', currentFactor:0.64565057, currentFactorDate:'2014-08-25', currentBalance:43347687.10, g60Plus:0.10345, d60Plus:0.18765, cumulativeLoss:0.01521, principalShortfall:0.02551, interestShortfall:0.0, wac:6.5673, als:22713.81, wala:64, topGeography:'CA', idcPrice:0.93, currentRating:'Baa1/A/', currentCe:3218000, naicAvrInfo:'NAIC-AVR LOSS = x%'})

MERGE (security)-[:HAS_PERFORMANCE]->(performance:SecurityPerformance{cpr1m:6.03, cpr3m:6.51, cpr6m:7.03, cpr12m:7.60, cdr1m:1.52, cdr3m:1.64, cdr6m:1.77, cdr12m:1.91, sev1m:0.08, sev3m:0.64, sev6m:0.05, sev12m:0.00})

MERGE (security)-[:ASSET_SUB_TYPE]->(assetSubType:AssetSubType{name:'Consumer'})-[:ASSET_TYPE]->(assetType:AssetType{name:'ABS'})

MERGE (personSale:Person{userId:'aknopp', firstName:'Alexis', lastName:'Knopp'})

MERGE (personSale)-[:HAS_ROLE]->(sales:SalesPerson)-[:HAS_COVERAGE]->(clientCoverage:ClientCoverage)
MERGE (clientCoverage)-[:HAS_METRICS]->(salesClientValueMetrics:SalesClientValueMetrics{userRankNumerator:2, userRankDenominator:5})

MERGE (client:Client{clientId:'RM259539', name:'Wellington International Management Japan', alias:'wimj'})
MERGE (clientCoverage)-[:FOR_CLIENT]->(client)

MERGE (client)-[:HAS_METRICS]->(clientValueMetrics:ClientValueMetrics{nomuraRankNumerator:7, nomuraRankDenominator:20})
MERGE (client)-[:HAS_TRADE_METRICS]->(tradeMetrics1:TradeMetrics{side:'Buy', period:'1M', revenue:100.0, tradeCount:2})
MERGE (client)-[:HAS_TRADE_METRICS]->(tradeMetrics2:TradeMetrics{side:'Buy', period:'3M', revenue:200.0, tradeCount:2})
MERGE (client)-[:HAS_TRADE_METRICS]->(tradeMetrics3:TradeMetrics{side:'Buy', period:'6M', revenue:300.0, tradeCount:2})
MERGE (client)-[:HAS_TRADE_METRICS]->(tradeMetrics4:TradeMetrics{side:'Buy', period:'1Y', revenue:400.0, tradeCount:2})
MERGE (client)-[:HAS_TRADE_METRICS]->(tradeMetrics5:TradeMetrics{side:'Buy', period:'Life', revenue:500.0, tradeCount:2})
MERGE (client)-[:HAS_TRADE_METRICS]->(tradeMetrics6:TradeMetrics{side:'Sell', period:'1M', revenue:100.0, tradeCount:2})
MERGE (client)-[:HAS_TRADE_METRICS]->(tradeMetrics7:TradeMetrics{side:'Sell', period:'3M', revenue:100.0, tradeCount:3})
MERGE (client)-[:HAS_TRADE_METRICS]->(tradeMetrics8:TradeMetrics{side:'Sell', period:'6M', revenue:100.0, tradeCount:4})
MERGE (client)-[:HAS_TRADE_METRICS]->(tradeMetrics9:TradeMetrics{side:'Sell', period:'1Y', revenue:100.0, tradeCount:5})
MERGE (client)-[:HAS_TRADE_METRICS]->(tradeMetrics10:TradeMetrics{side:'Sell', period:'Life', revenue:100.0, tradeCount:6})

MERGE (client)-[:HOLDS]->(portfolio:Portfolio{reportDate:'2014-09-30'})
MERGE (portfolio)-[:CONTAINS]->(holding:Holding{changeVsQ1:100.0, changeVsQ2:200.0, percentPortfolioHeld:0.1, percentPortfolioHeldVsQ1:0.2, percentPortfolioHeldVsQ2:0.3, netChange:1000.0, marketValue:1000000.0, impliedPrice:99.1, originalFace:2000000.0})
MERGE (holding)-[:OF_SECURITY]->(security)
MERGE (personTrader:Person{userId:'jsmith', firstName:'John', lastName:'Smith'})
MERGE (personTrader)-[:HAS_ROLE]->(trader:Trader)-[:COVERS]->(assetSubType)
MERGE (trade:Trade{side:'Buy', quantity:5000.0, displayPrice:'100-16+', currentFace:100000.0, executionTimestamp:'2014-10-15 11:01:29 Z'})
MERGE (trade)-[:HAS_TRADER]->(trader)
MERGE (trade)-[:HAS_SALES_PERSON]->(sales)
MERGE (trade)-[:LEDGER]->(book:TradingBook{bookId:'SPUS464'})
MERGE (trade)-[:SECURITY]->(security)
MERGE (trade)-[:TRADED_BY]->(client)


MERGE (clientInterest:HistoricClientInterest)-[:HISTORICALLY_IS_INTEREST_FOR_CLIENT]->(client)
MERGE (bidList:HistoricBidList{dueTimestamp:'2014-10-20 11:29:45 Z'})-[:HISTORICALLY_FOR_CLIENT]->(client)
MERGE (bidList)-[:HISTORICALLY_HAS_ITEM]->(bidListItem:HistoricBidListItem{originalFace:100000.0})-[:FOR_SECURITY]->(security)
MERGE (bidListItem)-[:HISTORICALLY_HAS_INTEREST]->(clientInterest)
MERGE (bidListItem)-[:HISTORICALLY_HAS_PRICE_TALK]->(priceTalk:HistoricPriceTalk{talk:'100-01+', color:'hello'})
MERGE (clientInterest)-[:HISTORICALLY_HAS_PRICE_TALK]->(priceTalk)
MERGE (bid1:HistoricBid{commitment:'Firm', displayPrice:'100-08'})<-[:HISTORICALLY_HAS_BID]-(bidListItem)
MERGE (bid2:HistoricBid{commitment:'Subject', displayPrice:'100-08'})<-[:HISTORICALLY_HAS_BID]-(clientInterest)
MERGE (bidList)-[:HISTORICALLY_HAS_POST_CLOSE_COLOR]->(postCloseColor:HistoricalPostCloseColor{postCloseResult:'won', coverPrice:'100-09', comment:'Yahoo'})


CREATE CONSTRAINT ON (s:Security) ASSERT s.esmp IS UNIQUE
CREATE INDEX ON :Security(cusip)
CREATE INDEX ON :Security(description)

CREATE CONSTRAINT ON (p:Person) ASSERT p.userId IS UNIQUE
CREATE CONSTRAINT ON (c:Client) ASSERT c.clientId IS UNIQUE

MERGE (security:Security{esmp:123456, cusip:'76112BWV8', description:'RAMP 2005-RS7 A3', issuerName:'RAMP SERIES 2005-RS7 TRUST', securityType:'ABS-OTHER', collateralType:'HEQ', mortgageType:'FLT,+', trancheTypeInfo:'FLT,STE', servicer:'State Street Bank & Trust Co', trustee:'Silverado Mortgage', slice:'52% to 100%', couponType:'Flt'})

MERGE (security)-[:HAS_INDICATIVE]->(indicative:RmbsSecurityIndicative{coupon:'L1M+37', currentFactor:0.64565057, currentFactorDate:'2014-08-25', currentBalance:43347687.10, g60Plus:0.10345, d60Plus:0.18765, cumulativeLoss:0.01521, principalShortfall:0.02551, interestShortfall:0.0, wac:6.5673, als:22713.81, wala:64, topGeography:'CA', idcPrice:0.93, currentRating:'Baa1/A/', currentCe:3218000, naicAvrInfo:'NAIC-AVR LOSS = x%'})

MERGE (security)-[:HAS_PERFORMANCE]->(performance:SecurityPerformance{cpr1m:6.03, cpr3m:6.51, cpr6m:7.03, cpr12m:7.60, cdr1m:1.52, cdr3m:1.64, cdr6m:1.77, cdr12m:1.91, sev1m:0.08, sev3m:0.64, sev6m:0.05, sev12m:0.00})

MERGE (security)-[:ASSET_SUB_TYPE]->(assetSubType:AssetSubType{name:'Consumer'})-[:ASSET_TYPE]->(assetType:AssetType{name:'ABS'})

MERGE (personSale:Person{userId:'aknopp', firstName:'Alexis', lastName:'Knopp'})

MERGE (personSale)-[:HAS_ROLE]->(sales:SalesPerson)-[:HAS_COVERAGE]->(clientCoverage:ClientCoverage)
MERGE (clientCoverage)-[:HAS_METRICS]->(salesClientValueMetrics:SalesClientValueMetrics{userRankNumerator:2, userRankDenominator:5})

MERGE (client:Client{clientId:'RM259539', name:'Wellington International Management Japan', alias:'wimj'})
MERGE (clientCoverage)-[:FOR_CLIENT]->(client)

MERGE (client)-[:HAS_METRICS]->(clientValueMetrics:ClientValueMetrics{nomuraRankNumerator:7, nomuraRankDenominator:20})
MERGE (client)-[:HAS_TRADE_METRICS]->(tradeMetrics1:TradeMetrics{side:'Buy', period:'1M', revenue:100.0, tradeCount:2})
MERGE (client)-[:HAS_TRADE_METRICS]->(tradeMetrics2:TradeMetrics{side:'Buy', period:'3M', revenue:200.0, tradeCount:2})
MERGE (client)-[:HAS_TRADE_METRICS]->(tradeMetrics3:TradeMetrics{side:'Buy', period:'6M', revenue:300.0, tradeCount:2})
MERGE (client)-[:HAS_TRADE_METRICS]->(tradeMetrics4:TradeMetrics{side:'Buy', period:'1Y', revenue:400.0, tradeCount:2})
MERGE (client)-[:HAS_TRADE_METRICS]->(tradeMetrics5:TradeMetrics{side:'Buy', period:'Life', revenue:500.0, tradeCount:2})
MERGE (client)-[:HAS_TRADE_METRICS]->(tradeMetrics6:TradeMetrics{side:'Sell', period:'1M', revenue:100.0, tradeCount:2})
MERGE (client)-[:HAS_TRADE_METRICS]->(tradeMetrics7:TradeMetrics{side:'Sell', period:'3M', revenue:100.0, tradeCount:3})
MERGE (client)-[:HAS_TRADE_METRICS]->(tradeMetrics8:TradeMetrics{side:'Sell', period:'6M', revenue:100.0, tradeCount:4})
MERGE (client)-[:HAS_TRADE_METRICS]->(tradeMetrics9:TradeMetrics{side:'Sell', period:'1Y', revenue:100.0, tradeCount:5})
MERGE (client)-[:HAS_TRADE_METRICS]->(tradeMetrics10:TradeMetrics{side:'Sell', period:'Life', revenue:100.0, tradeCount:6})

MERGE (client)-[:HOLDS]->(portfolio:Portfolio{reportDate:'2014-09-30'})

MERGE (portfolio)-[:CONTAINS]->(holding:Holding{changeVsQ1:100.0, changeVsQ2:200.0, percentPortfolioHeld:0.1, percentPortfolioHeldVsQ1:0.2, percentPortfolioHeldVsQ2:0.3, netChange:1000.0, marketValue:1000000.0, impliedPrice:99.1, originalFace:2000000.0})

MERGE (holding)-[:OF_SECURITY]->(security)

MERGE (personTrader:Person{userId:'jsmith', firstName:'John', lastName:'Smith'})
MERGE (personTrader)-[:HAS_ROLE]->(trader:Trader)-[:COVERS]->(assetSubType)

MERGE (trade:Trade{side:'Buy', quantity:5000.0, displayPrice:'100-16+', currentFace:100000.0, executionTimestamp:'2014-10-15 11:01:29 Z'})
MERGE (trade)-[:HAS_TRADER]->(trader)
MERGE (trade)-[:HAS_SALES_PERSON]->(sales)
MERGE (trade)-[:LEDGER]->(book:TradingBook{bookId:'SPUS464'})
MERGE (trade)-[:SECURITY]->(security)
MERGE (trade)-[:TRADED_BY]->(client)

MERGE (clientInterest:HistoricClientInterest)-[:HISTORICALLY_IS_INTEREST_FOR_CLIENT]->(client)
MERGE (bidList:HistoricBidList{dueTimestamp:'2014-10-20 11:29:45 Z'})-[:HISTORICALLY_FOR_CLIENT]->(client)
MERGE (bidList)-[:HISTORICALLY_HAS_ITEM]->(bidListItem:HistoricBidListItem{originalFace:100000.0})-[:FOR_SECURITY]->(security)
MERGE (bidListItem)-[:HISTORICALLY_HAS_INTEREST]->(clientInterest)
MERGE (bidListItem)-[:HISTORICALLY_HAS_PRICE_TALK]->(priceTalk:HistoricPriceTalk{talk:'100-01+', color:'hello'})
MERGE (clientInterest)-[:HISTORICALLY_HAS_PRICE_TALK]->(priceTalk)
MERGE (bid1:HistoricBid{commitment:'Firm', displayPrice:'100-08'})<-[:HISTORICALLY_HAS_BID]-(bidListItem)
MERGE (bid2:HistoricBid{commitment:'Subject', displayPrice:'100-08'})<-[:HISTORICALLY_HAS_BID]-(clientInterest)
MERGE (trade)-[:TRADED_BY]->(client)


CREATE INDEX ON :Security(description)

CREATE CONSTRAINT ON (p:Person) ASSERT p.userId IS UNIQUE
CREATE CONSTRAINT ON (c:Client) ASSERT c.clientId IS UNIQUE

MERGE (security:Security{esmp:123456, cusip:'76112BWV8', description:'RAMP 2005-RS7 A3', issuerName:'RAMP SERIES 2005-RS7 TRUST', securityType:'ABS-OTHER', collateralType:'HEQ', mortgageType:'FLT,+', trancheTypeInfo:'FLT,STE', servicer:'State Street Bank & Trust Co', trustee:'Silverado Mortgage', slice:'52% to 100%', couponType:'Flt'})

MERGE (security)-[:HAS_INDICATIVE]->(indicative:RmbsSecurityIndicative{coupon:'L1M+37', currentFactor:0.64565057, currentFactorDate:'2014-08-25', currentBalance:43347687.10, g60Plus:0.10345, d60Plus:0.18765, cumulativeLoss:0.01521, principalShortfall:0.02551, interestShortfall:0.0, wac:6.5673, als:22713.81, wala:64, topGeography:'CA', idcPrice:0.93, currentRating:'Baa1/A/', currentCe:3218000, naicAvrInfo:'NAIC-AVR LOSS = x%'})

MERGE (security)-[:HAS_PERFORMANCE]->(performance:SecurityPerformance{cpr1m:6.03, cpr3m:6.51, cpr6m:7.03, cpr12m:7.60, cdr1m:1.52, cdr3m:1.64, cdr6m:1.77, cdr12m:1.91, sev1m:0.08, sev3m:0.64, sev6m:0.05, sev12m:0.00})

MERGE (security)-[:ASSET_SUB_TYPE]->(assetSubType:AssetSubType{name:'Consumer'})-[:ASSET_TYPE]->(assetType:AssetType{name:'ABS'})

MERGE (personSale:Person{userId:'aknopp', firstName:'Alexis', lastName:'Knopp'})

MERGE (personSale)-[:HAS_ROLE]->(sales:SalesPerson)-[:HAS_COVERAGE]->(clientCoverage:ClientCoverage)
MERGE (clientCoverage)-[:HAS_METRICS]->(salesClientValueMetrics:SalesClientValueMetrics{userRankNumerator:2, userRankDenominator:5})

MERGE (client:Client{clientId:'RM259539', name:'Wellington International Management Japan', alias:'wimj'})
MERGE (clientCoverage)-[:FOR_CLIENT]->(client)

MERGE (client)-[:HAS_METRICS]->(clientValueMetrics:ClientValueMetrics{nomuraRankNumerator:7, nomuraRankDenominator:20})
MERGE (client)-[:HAS_TRADE_METRICS]->(tradeMetrics1:TradeMetrics{side:'Buy', period:'1M', revenue:100.0, tradeCount:2})
MERGE (client)-[:HAS_TRADE_METRICS]->(tradeMetrics2:TradeMetrics{side:'Buy', period:'3M', revenue:200.0, tradeCount:2})
MERGE (client)-[:HAS_TRADE_METRICS]->(tradeMetrics3:TradeMetrics{side:'Buy', period:'6M', revenue:300.0, tradeCount:2})
MERGE (client)-[:HAS_TRADE_METRICS]->(tradeMetrics4:TradeMetrics{side:'Buy', period:'1Y', revenue:400.0, tradeCount:2})
MERGE (client)-[:HAS_TRADE_METRICS]->(tradeMetrics5:TradeMetrics{side:'Buy', period:'Life', revenue:500.0, tradeCount:2})
MERGE (client)-[:HAS_TRADE_METRICS]->(tradeMetrics6:TradeMetrics{side:'Sell', period:'1M', revenue:100.0, tradeCount:2})
MERGE (client)-[:HAS_TRADE_METRICS]->(tradeMetrics7:TradeMetrics{side:'Sell', period:'3M', revenue:100.0, tradeCount:3})
MERGE (client)-[:HAS_TRADE_METRICS]->(tradeMetrics8:TradeMetrics{side:'Sell', period:'6M', revenue:100.0, tradeCount:4})
MERGE (client)-[:HAS_TRADE_METRICS]->(tradeMetrics9:TradeMetrics{side:'Sell', period:'1Y', revenue:100.0, tradeCount:5})
MERGE (client)-[:HAS_TRADE_METRICS]->(tradeMetrics10:TradeMetrics{side:'Sell', period:'Life', revenue:100.0, tradeCount:6})

MERGE (client)-[:HOLDS]->(portfolio:Portfolio{reportDate:'2014-09-30'})

MERGE (portfolio)-[:CONTAINS]->(holding:Holding{changeVsQ1:100.0, changeVsQ2:200.0, percentPortfolioHeld:0.1, percentPortfolioHeldVsQ1:0.2, percentPortfolioHeldVsQ2:0.3, netChange:1000.0, marketValue:1000000.0, impliedPrice:99.1, originalFace:2000000.0})

MERGE (holding)-[:OF_SECURITY]->(security)

MERGE (personTrader:Person{userId:'jsmith', firstName:'John', lastName:'Smith'})
MERGE (personTrader)-[:HAS_ROLE]->(trader:Trader)-[:COVERS]->(assetSubType)

MERGE (trade:Trade{side:'Buy', quantity:5000.0, displayPrice:'100-16+', currentFace:100000.0, executionTimestamp:'2014-10-15 11:01:29 Z'})
MERGE (trade)-[:HAS_TRADER]->(trader)
MERGE (trade)-[:HAS_SALES_PERSON]->(sales)
MERGE (trade)-[:LEDGER]->(book:TradingBook{bookId:'SPUS464'})
MERGE (trade)-[:SECURITY]->(security)

MERGE (clientInterest:HistoricClientInterest)-[:HISTORICALLY_IS_INTEREST_FOR_CLIENT]->(client)
MERGE (bidList:HistoricBidList{dueTimestamp:'2014-10-20 11:29:45 Z'})-[:HISTORICALLY_FOR_CLIENT]->(client)
MERGE (bidList)-[:HISTORICALLY_HAS_ITEM]->(bidListItem:HistoricBidListItem{originalFace:100000.0})-[:FOR_SECURITY]->(security)
MERGE (bidListItem)-[:HISTORICALLY_HAS_INTEREST]->(clientInterest)
MERGE (bidListItem)-[:HISTORICALLY_HAS_PRICE_TALK]->(priceTalk:HistoricPriceTalk{talk:'100-01+', color:'hello'})
MERGE (clientInterest)-[:HISTORICALLY_HAS_PRICE_TALK]->(priceTalk)
MERGE (bid1:HistoricBid{commitment:'Firm', displayPrice:'100-08'})<-[:HISTORICALLY_HAS_BID]-(bidListItem)
MERGE (bid2:HistoricBid{commitment:'Subject', displayPrice:'100-08'})<-[:HISTORICALLY_HAS_BID]-(clientInterest)

