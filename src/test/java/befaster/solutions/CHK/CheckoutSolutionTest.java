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

    // R2

    @Test
    public void checkoutIndividualItemE(){
        var sku = "E";
        var price = checkoutSolution.checkout(sku);
        assertThat(price).isEqualTo(40);
    }

    @Test
    public void checkoutIndividualItemEE(){
        var sku = "EE";
        var price = checkoutSolution.checkout(sku);
        assertThat(price).isEqualTo(80);
    }

    @Test
    public void checkoutIndividualItemEEEEBB(){
        var sku = "EEEEBB";
        var price = checkoutSolution.checkout(sku);
        assertThat(price).isEqualTo(160);
    }

    @Test
    public void checkoutListOfSpecialOffers(){
        var sku = "AAACAAAAA";
        var price = checkoutSolution.checkout(sku);
        assertThat(price).isEqualTo(350);
    }

    @Test
    public void checkoutBundles(){
        var sku = "EEB";
        var price = checkoutSolution.checkout(sku);
        assertThat(price).isEqualTo(80);
    }

    @Test
    public void checkoutListOfSpecialOffersAndBundles(){
        var sku = "EEBAAACAAAAA"; // 80 + 350 = 430;
        var price = checkoutSolution.checkout(sku);
        assertThat(price).isEqualTo(430);
    }


}



