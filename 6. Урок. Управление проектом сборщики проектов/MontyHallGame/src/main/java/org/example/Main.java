package org.example;


import java.util.*;

public class Main {


    private static final int unknown = 0;
    private static final int goat = 2;
    private static final int car = 1;
    private static final int maxDoor = 3;
    private static final int maxRound = 1000;

    private static boolean isValidation(List<Door> doors) {
        int coutDoorCar = 0;
        for (Door door : doors) {
            if (door.getTypeDoor() == unknown) {
                return false;
            }
            if (door.getTypeDoor() == car) {
                coutDoorCar++;
            }
        }
        return coutDoorCar == 1;
    }

    public static void main(String[] args) {
        String[] typeSelected = {"Изменить", "Неизменять"};
        System.out.println("Приветствую, Вас в нашей интерактивной игре - Парадокс Монти Холла");
        System.out.println();
        System.out.println("Ознакомьтесь с правилами игры:");

        System.out.println("""
                - Вам нужно выбрать одну из трех дверей.
                - За одной из дверей находится автомобиль, за двумя другими дверями — козы.
                - Вы выбираете одну из дверей, например, номер 1, я - ведущий, который знает, где находится автомобиль, а где — козы,
                открывает одну из оставшихся дверей, например, номер 3, за которой находится коза.
                - После этого я предложу Вам изменить свой выбор и выбрать дверь номер 2
                - И есил Вы сделаете правельный выбор, то сможете выйграть автомобиль""");
        System.out.println();

        Random random = new Random();
        List<Door> doors;
        HashMap<Integer, String> score = new HashMap<Integer, String>();
        int indexRound = 0;
        int countWins = 0;
        int countLoss = 0;

        while (indexRound < maxRound) {
            boolean isValid;
            do {
                doors = new ArrayList<>();
                for (int i = 0; i < maxDoor; i++) {
                    doors.add(new Door(random.nextInt(1, maxDoor)));
                }
                isValid = isValidation(doors);
            }
            while (!isValid);

            int selectedDoor = random.nextInt(1, maxDoor);
            doors.get(selectedDoor - 1).setSelected(true);

            int freeDoor = 0, carDoor = 0;
            for (int i = 1; i <= maxDoor; i++) {
                int iSh = i - 1;
                if (!doors.get(iSh).isSelected()) {
                    if (doors.get(iSh).getTypeDoor() != car) {
                        freeDoor = i;
                    } carDoor = i;
                }
            }
            System.out.println("Вы выбрали дверь под номером " + selectedDoor);
            System.out.println("За дверью " + freeDoor + " находится коза. Хотите изменить свой выбор?");
            int resultSelected = new Random().nextInt(0, typeSelected.length);
            System.out.println(typeSelected[resultSelected]);

            boolean isWinner = false;
            if (typeSelected[resultSelected].equals("Изменить")) {
                isWinner = carDoor != selectedDoor;
            } else {
                isWinner = carDoor == selectedDoor;
            }
            indexRound++;
            if (isWinner) {
                countWins++;
                System.out.println("Вы выйграли автомобиль");
                score.put(indexRound , "Победа");
            } else {
                countLoss++;
                System.out.println("За дверью, которую Вы выбрали, находится вторая коза. Увы Вы проиграли.");
                System.out.println("Дверь за которой находился автомобиль под номером " + selectedDoor);
                score.put(indexRound, "Поражение");
            }
        }

        System.out.println("Результаты:\n[");
        for (Map.Entry<Integer, String> entry : score.entrySet()) {

            System.out.println("Раунд: " + entry.getKey() + " == " + entry.getValue());

        }
        System.out.println("Итого: Побед - " + countWins + "; Поражения - " + countLoss);
        System.out.println("]");


    }
}