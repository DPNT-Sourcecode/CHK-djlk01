package befaster.solutions.CHK;

import lombok.*;

public record SpecialOffer(int quantity, int price, char freeItem) {

    public SpecialOffer(int quantity, int price) {
        this(quantity, price, '\0');
    }

    public SpecialOffer(int quantity, Character freeItem) {
        this(quantity, 0, freeItem);
    }


}

