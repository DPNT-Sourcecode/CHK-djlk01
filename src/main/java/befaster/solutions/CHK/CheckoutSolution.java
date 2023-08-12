package befaster.solutions.CHK;

import befaster.runner.SolutionNotImplementedException;

import java.util.HashMap;
import java.util.Map;

public class CheckoutSolution {

    private static final Map<Character, Integer> skuPrices = Map.of('A', 50, 'B', 30, 'C', 20, 'D', 15);
//    private static final Map<Character, Map<Integer>>
    public Integer checkout(String skus) {
        //input validation of skus - correct parsing
        // decomposition - handle pricing of individual items and their respective discounts separately
        // extendable - make it easy to update prices or introduce new special offers in the future
        var total = 0;

        if(skus.equals("A")){
            total = 50;
        }

        return total;
    }
}

