package org.example;

import lombok.Getter;

@Getter
public class Door {

    /** Тип двери */
    private final int typeDoor;

    /** Указатель на дверь выбранную игроком */
    @lombok.Setter
    private boolean isSelected;

    /**
     * Контсркутор класса
     * @param typeDoor - тип двери
     */
    Door(int typeDoor) {
        this.typeDoor = typeDoor;
    }
}
