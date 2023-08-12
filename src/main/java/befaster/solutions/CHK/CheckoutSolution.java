package befaster.solutions.CHK;

import befaster.runner.SolutionNotImplementedException;

import java.util.HashMap;
import java.util.Map;

public class CheckoutSolution {

    private static final String VALID_SKU_PATTERN = "^[ABCD]*$";

    private static final Map<Character, Integer> SKU_PRICES =
            Map.of('A', 50, 'B', 30, 'C', 20, 'D', 15);
    private static final Map<Character, SpecialOffer> SPECIAL_OFFERS =
            Map.of('A', new SpecialOffer(3, 130));
    public Integer checkout(String skus) {
        //input validation of skus - correct parsing
        // decomposition - handle pricing of individual items and their respective discounts separately
        // extendable - make it easy to update prices or introduce new special offers in the future
        if(skus == null || !skus.matches(VALID_SKU_PATTERN)) {
            return -1;
        }

        var total = 0;

        for(Character product : SKU_PRICES.keySet()){
            int count =  (int) skus.chars()
                    .filter(ch -> ch == product)
                    .count();

            total+=count* SKU_PRICES.get(product);
        }

        return total;
    }
}




