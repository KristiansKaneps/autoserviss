package lv.kristianskaneps.autoserviss.types;

import java.io.Serializable;

public record PhoneNumber(
        String value
) implements Serializable {
    @Override
    public String toString() {
        return value;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
