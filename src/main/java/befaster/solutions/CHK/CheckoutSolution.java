package befaster.solutions.CHK;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckoutSolution {

    private static final String VALID_SKU_PATTERN = "^[ABCDE]*$";

    private static final Map<Character, Integer> SKU_PRICES =
            new HashMap<>() {{
                put('A', 50);
                put('B', 30);
                put('C', 20);
                put('D', 15);
                put('E', 40);
            }};
    private static final Map<Character, List<SpecialOffer>> SPECIAL_OFFERS =
            Map.of('A', List.of(new SpecialOffer(3, 130),
                            new SpecialOffer(5, 200)) ,
                    'B', List.of(new SpecialOffer(2, 45)));

    public Integer checkout(String skus) {
        //input validation of skus - correct parsing
        // decomposition - handle pricing of individual items and their respective discounts separately
        // extendable - make it easy to update prices or introduce new special offers in the future
        if (skus == null || !skus.matches(VALID_SKU_PATTERN)) {
            return -1;
        }

        var total = 0;

        var items = new HashMap<Character, Integer>();

        for(Character product : SKU_PRICES.keySet()){
            items.put(product, (int) skus.chars()
                    .filter(ch -> ch == product)
                    .count());
        }

        for(Character product: SPECIAL_OFFERS.keySet()){
            for(SpecialOffer offer : SPECIAL_OFFERS.get(product)){
                applyDirectDiscounts(items, offer, total, product);
            }
        }

        for(Map.Entry<Character, Integer> item : items.entrySet()){
            total+= item.getValue() * SKU_PRICES.get(item.getKey());
        }

        return total;
    }

    private static void applyDirectDiscounts(Map<Character, Integer> items, SpecialOffer offer, int total, char product){
        if(offer.quantity() > 0){
            var numDiscounts = items.get(product) / offer.quantity();
            total+= numDiscounts * offer.price();
            items.put(product, items.get(product) - numDiscounts * offer.quantity());
        }
    }
}






