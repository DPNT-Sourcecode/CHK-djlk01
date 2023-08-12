package befaster.solutions.CHK;

import java.util.Arrays;
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
            Map.of('A', Arrays.asList(
                            new SpecialOffer(5, 200),
                            new SpecialOffer(3, 130)
                    ),
                    'B', Arrays.asList(
                            new SpecialOffer(2, 45)
                    ));

    public Integer checkout(String skus) {
        //input validation of skus - correct parsing
        // decomposition - handle pricing of individual items and their respective discounts separately
        // extendable - make it easy to update prices or introduce new special offers in the future
        if (skus == null || !skus.matches(VALID_SKU_PATTERN)) {
            return -1;
        }

        var total = 0;

        var items = new HashMap<Character, Integer>();

        for (Character product : SKU_PRICES.keySet()) {
            items.put(product, (int) skus.chars()
                    .filter(ch -> ch == product)
                    .count());
        }

        for (Character product : SPECIAL_OFFERS.keySet()) {
            var offers = SPECIAL_OFFERS.get(product);
            offers.sort((o1, o2) -> Integer.compare(o2.quantity(), o1.quantity()));
            for (SpecialOffer offer : offers) {
                applyBundleOffers(items, product, offer);
                total += applyDirectDiscounts(items, offer, product);
            }
        }

        for (Map.Entry<Character, Integer> item : items.entrySet()) {
            total += item.getValue() * SKU_PRICES.get(item.getKey());
        }

        return total;
    }

    private static int applyDirectDiscounts(Map<Character, Integer> items, SpecialOffer offer, char product) {
        int discount = 0;
        if (offer.quantity() > 0) {
            var numDiscounts = items.get(product) / offer.quantity();
            discount += numDiscounts * offer.price();
            items.put(product, items.get(product) - numDiscounts * offer.quantity());
        }
        return discount;
    }

    private static void applyBundleOffers(Map<Character, Integer> items, char mainItem, SpecialOffer offer) {
        if (offer.freeItem() != null) {
            var freeItem = offer.freeItem();

            int offersToApply = items.getOrDefault(mainItem, 0) / offer.quantity();

            int freeItemsAvailable = items.getOrDefault(freeItem, 0);

            int offersApplied = Math.min(offersToApply, freeItemsAvailable);

            items.put(mainItem, items.getOrDefault(mainItem, 0) - offersApplied * offer.quantity());
            items.put(freeItem, items.getOrDefault(mainItem, 0) - offersApplied * offer.quantity());
            items.put(freeItem, remainingItemsToChargeFor);
        }
    }
}
