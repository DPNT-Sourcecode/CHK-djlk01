package befaster.solutions.CHK;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CheckoutSolutionTest {

    private CheckoutSolution checkoutSolution;

    @BeforeEach
    public void setUp(){
        checkoutSolution = new CheckoutSolution();
    }

    @Test
    public void checkoutIndividualItem(){
        var skuA = "A";
        var price = checkoutSolution.checkout(skuA);
        assertThat(price).isEqualTo(50);
    }

    @Test
    public void checkoutNullInput(){
        var price = checkoutSolution.checkout(null);
        assertThat(price).isEqualTo(-1);
    }

    @Test
    public void checkoutInvalidInput(){
        var price = checkoutSolution.checkout("ZXC");
        assertThat(price).isEqualTo(-1);
    }

    @Test
    public void checkoutMultipleItems(){
        var sku = "ABC";
        var price = checkoutSolution.checkout(sku);
        assertThat(price).isEqualTo(100);
    }

    @Test
    public void checkoutSpecialOffers(){
        var sku = "AAA";
        var price = checkoutSolution.checkout(sku);
        assertThat(price).isEqualTo(130);

        var sku2 = "BB";
        var price2 = checkoutSolution.checkout(sku2);

        assertThat(price2).isEqualTo(45);
    }
}




