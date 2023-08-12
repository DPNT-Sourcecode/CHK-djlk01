package befaster.solutions.CHK;

import java.util.*;

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
                            new SpecialOffer(5, 200, null),
                            new SpecialOffer(3, 130, null)
                    ),
                    'B', Arrays.asList(
                            new SpecialOffer(2, 45, null)
                    ),
                    'E', Arrays.asList(new SpecialOffer(2,0,'B')));

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

        for(Map.Entry<Character, Integer> itemCountEntry : items.entrySet()){
            var item = itemCountEntry.getKey();
            var count = itemCountEntry.getValue();

            total += count * SKU_PRICES.get(item);

            var offersForItem = SPECIAL_OFFERS.getOrDefault(item, Collections.emptyList());

            for (SpecialOffer offer : offersForItem) {
                if(offer.freeItem() == null){
                    int discount = applyDirectDiscounts(count, offer,item);
                    total -= discount;
                    if(discount > 0){
                        var offerCount = count / offer.quantity();
                        var itemsUsed = offerCount * offer.quantity();
                        count -= itemsUsed;
                        items.put(item, count);
                    }
                } else {
                    total -= applyBundleOffers(item, items, offer);

                }
            }
        }

        return total;
    }

    private static int applyDirectDiscounts(int count, SpecialOffer offer, char product) {
        var offerCount = count / offer.quantity();
        if (offerCount == 0){
            return 0;
        }
        var regularPriceForOfferedItems = offerCount * offer.quantity() * SKU_PRICES.get(product);
        return regularPriceForOfferedItems - (offerCount * offer.price());
    }

    private static int applyBundleOffers(char product, Map<Character, Integer> items, SpecialOffer offer) {
        var numFreeItems = items.get(product) / offer.quantity();

        if(numFreeItems == 0 ||){
            return 0;
        }

        return numFreeItems * SKU_PRICES.get(offer.freeItem());
    }
}







