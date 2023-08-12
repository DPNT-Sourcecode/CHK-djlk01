package befaster.solutions.CHK;

import lombok.*;

public record SpecialOffer(int quantity, int price, Character freeItem) {

    public SpecialOffer(int quantity, int price) {
        this(quantity, price, null);
    }

    public SpecialOffer(int quantity, Character freeItem) {
        this(quantity, 0, freeItem);
    }


}
