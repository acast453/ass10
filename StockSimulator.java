import java.util.LinkedList;
import java.util.Queue;
import java.util.Map;
import java.util.TreeMap;
/**
   Class for simulating trading a single stock at varying prices.
*/
public class StockSimulator
{
   private Map<String, Queue<Block>> quantity_price;
   int gain;

   /**
      Constructor.
   */
   public StockSimulator()
   {
      quantity_price = new TreeMap<>();

      gain = 0 ;
   }

   /**
      Handle a user buying a given quantity of stock at a given price.

      @param quantity how many to buy.
      @param price the price to buy.
   */
   public void buy(String symbol, int quantity, int price)
   {
      Block quantity_Price = new Block(quantity, price);

      if (quantity_price.containsKey(symbol)){
         quantity_price.get(symbol).add(quantity_Price);
      }
      else{

         Queue<Block> positions = new LinkedList<>();
         positions.add(quantity_Price);
         quantity_price.put(symbol,positions);

      }

   }

   /**
      Handle a user selling a given shares of stock at a given price.
      @param symbol the stock to sell
      @param shares how many to sell.
      @param price the price to sell.
   */
   public void sell(String symbol, int shares, int price)
   {
      Queue<Block> positions = quantity_price.get(symbol);
      if (quantity_price.containsKey(symbol) && positions.peek() != null && positions.peek().getQuantity() <= shares) {
         while (shares > 0) {
            if (positions.peek().getQuantity() < shares){
               System.out.println("You are trying to sell more " + symbol + " stock than you own.");
               shares = 0;
            }

            else if (positions.peek().getQuantity() > shares) {
               gain = gain + (price * shares) - (positions.peek().getPrice() * shares);
               positions.peek().sell(shares);
               shares = 0;


            } else{
               gain = gain +  (price * positions.peek().getQuantity()) - (positions.peek().getPrice() * positions.peek().getQuantity());
               shares = shares - positions.poll().getQuantity();
               System.out.println("Gain: " + gain);
            }
         }
      } else {
         System.out.println("You do not own any " + symbol + " stock");
      }
   }
   }

