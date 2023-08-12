package befaster.solutions.CHK;

import lombok.*;

public record SpecialOffer(int quantity, int price, char freeItem) {

    public SpecialOffer(int quantity, int price) {
        this(quantity, price,)
    }
}

