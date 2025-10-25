package backend.models.market;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ProductStatus {
    PENDING("Pendiente"),
    APPROVED("Aprobado"),
    REJECTED("Rechazado");

    private final String value;

    ProductStatus(String value) {
        this.value = value;
    }

    public static ProductStatus mapStatus(String value) throws IllegalAccessException {
        for (ProductStatus status : ProductStatus.values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        throw new IllegalAccessException("Estado desconocido: " + value);
    }

    @JsonValue
    @Override
    public String toString() {
        return value;
    }
}
