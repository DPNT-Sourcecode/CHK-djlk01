package befaster.solutions.CHK;

import java.util.*;
import java.util.stream.Collectors;

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

//        var total = 0;

        var items = new HashMap<Character, Integer>();

        for (Character product : SKU_PRICES.keySet()) {
            items.put(product, (int) skus.chars()
                    .filter(ch -> ch == product)
                    .count());
        }

        var total = items.entrySet().stream()
                .mapToInt(entry -> entry.getValue() * SKU_PRICES.get(entry.getKey()))
                .sum();

        for(Map.Entry<Character, Integer> itemCountEntry : items.entrySet()){
            var item = itemCountEntry.getKey();
            var count = itemCountEntry.getValue();
            var offersForItem = SPECIAL_OFFERS.getOrDefault(item, Collections.emptyList());

            var sortedOffers = offersForItem.stream()
                    .sorted(Comparator.comparingInt(SpecialOffer::quantity).reversed())
                    .toList();

            for(SpecialOffer offer : sortedOffers){
                if(offer.freeItem() == null){
                    while(count >= offer.quantity()){
                        var discount = applyDirectDiscounts(count, offer,item);
                        var itemsUsed = (count / offer.quantity()) * offer.quantity();

//                        total += SKU_PRICES.get(item) * itemsUsed - discount;
                        total -= discount;
                        count -= itemsUsed;
                    }
                }
            }
            items.put(item,count);

//            for(SpecialOffer offer : sortedOffers){
//                if(offer.freeItem() != null){
//                    var deduction = applyBundleOffers(item, items, offer);
//                    total -= deduction;
//                    count = items.get(item);
//                }
//            }

//            total += count * SKU_PRICES.get(item);
        }

        for(Map.Entry<Character, List<SpecialOffer>> entry : SPECIAL_OFFERS.entrySet()){
            var item = entry.getKey();
            for(SpecialOffer offer : entry.getValue()){
                if(offer.freeItem() != null){
//                    var deduction = applyBundleOffers(item, items, offer);
                    applyBundleOffers(item, items, offer);
//                    total -= deduction;
                }
            }

        }


        return total;
    }

    private static int applyDirectDiscounts(int count, SpecialOffer offer, char product) {
        var numOffers = count / offer.quantity();
        var originalPriceOfOfferedItems = numOffers * offer.quantity() * SKU_PRICES.get(product);
        var totalDiscountedPriceForOfferedItems = numOffers * offer.price();
        return totalDiscountedPriceForOfferedItems - originalPriceOfOfferedItems;

    }

//    private static int applyBundleOffers(char product, Map<Character, Integer> items, SpecialOffer offer) {
//        var totalDeduction = 0;
//        var numFreeItems = items.get(product) / offer.quantity();
//
//        while(numFreeItems > 0 && items.getOrDefault(offer.freeItem(), 0) > 0){
//            totalDeduction += SKU_PRICES.get(offer.freeItem());
//            items.put(offer.freeItem(), items.get(offer.freeItem()) - 1);
//            items.put(product, items.get(product) - offer.quantity());
//            numFreeItems = items.get(product) / offer.quantity();
//        }
//
//        return totalDeduction;
//    }

    private static void applyBundleOffers(char product, Map<Character, Integer> items, SpecialOffer offer) {
        var numFreeItems = items.get(product) / offer.quantity();

//        if(items.get(product) >= offer.quantity()){
//            totalDeduction += items.get(product) * SKU_PRICES.get(product);
//        }

        while(numFreeItems > 0 && items.getOrDefault(offer.freeItem(), 0) > 0){
            items.put(offer.freeItem(), items.get(offer.freeItem()) - 1);
            items.put(product, items.get(product) - offer.quantity());
            numFreeItems = items.get(product) / offer.quantity();
        }
    }
}

