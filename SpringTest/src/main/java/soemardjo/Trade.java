package soemardjo;


import javax.persistence.*;
import java.util.Date;

@Entity
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    @Column(nullable = false)
    private long tradeID = -1;

    @Column(nullable = false)
    private Date tradeDate;

    @Column(nullable = false)
    private Date settleDate;

    @Column(nullable = false)
    private String direction;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private double quantity;

    @Column(nullable = false)
    private String cusip;

    public Trade() {
        super();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTradeID() { return tradeID; }
    public void setTradeID(long tradeID) { this.tradeID = tradeID; }

    public Date getTradeDate() {
        return tradeDate;
    }
    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
    }

    public Date getSettleDate() {
        return settleDate;
    }
    public void setSettleDate(Date settleDate) {
        this.settleDate = settleDate;
    }

    public String getDirection() { return direction; }
    public void setDirection(String direction) { this.direction = direction; }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public double getQuantity() {
        return quantity;
    }
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getCusip() {
        return cusip;
    }
    public void setCusip(String cusip) {
        this.cusip = cusip;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(tradeID);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Trade other = (Trade) obj;
        return (tradeID == other.tradeID);
    }

}
