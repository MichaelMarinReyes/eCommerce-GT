package backend.models.management;

import com.fasterxml.jackson.annotation.JsonValue;

public enum NotificationType {
    ORDER("Orden"),
    PRODUCT("Producto"),
    SANCTION("Sanción");

    private final String value;

    NotificationType(String value) {
        this.value = value;
    }

    public static NotificationType fromValue(String value) {
        if (value == null) return null;
        for (NotificationType type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Tipo de notificación desconocido: " + value);
    }

    @JsonValue
    @Override
    public String toString() {
        return value;
    }

}
