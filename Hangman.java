package Project1_Hangman;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Hangman {
    public static void main(String[] args) throws IOException {
        game(randomWord());
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("\nЕсли хотите сыграть ещё раз, введите любую букву.\nДля закрытия введите СТОП");
            String result = scanner.nextLine().toLowerCase(Locale.ROOT);
            if (!result.equals("стоп")) {
                game(randomWord());
            } else {
                System.out.println("СЛАБАК");
                break;
            }
        }
    }
    //Основной метод
    public static void game(List<Character> word) throws IOException {
        System.out.println("Hangman is start");
        List<Character> hiddenWord = new ArrayList<>();
        List<Character> inputWord = new ArrayList<>();
        int count = 0;
        //Печать рисунка
        printResult(checkFile(count));
        for (int i = 0; i < word.size(); i++) {
            hiddenWord.add('□');
        }
        // Печать загаданного слова для проверки работы
        for (char x : word)
            System.out.print(x);
        System.out.println();
        // Печать скрытого слова (с помощью □)
        for (char x : hiddenWord)
            System.out.print(x);
        System.out.println();
        // Цикл работы программы
        while (count != 6 && !hiddenWord.equals(word)) {
            char letter = inputLetter();
            // Проверка введеной буквы с загаданным словом и открытие буквы.
            for (int i = 0; i < hiddenWord.size(); i++) {
                if (word.get(i) == letter) {
                    hiddenWord.set(i, letter);
                }
            }
            // Проверка и запись уже введеных букв
            if (!inputWord.contains(letter)) {
                inputWord.add(letter);
            } else {
                System.out.println("Вы уже вводили эту букву!");
                continue;
            }
            System.out.println();
            // Проверка введеной буквы для счетчика ошибок
            if (!hiddenWord.contains(letter)) {
                count++;
            }
            // Рисуем виселицу в зависимости от количества ошибок
            printResult(checkFile(count));
            // Выводим закрытое слово с отгаданными буквами (Если есть такие)
            for (char x : hiddenWord)
                System.out.print(x);
            System.out.println("\nКоличество ошибок: " + count);
        }
        //Выводим слово если человек проиграл.
        if (count == 6) {
            System.out.print("Вы проиграли :( \nЗагаданное слово: ");
            for (char x : word)
                System.out.print(x);
            //Выводим если игрок победил
        } else System.out.println("Поздравлю, вы победили!");
    }

    //Метод для выбора рисунка
    public static File checkFile(int count) {
        File file;
        switch (count) {
            case 0 -> file = new File("count0.txt");
            case 1 -> file = new File("count1.txt");
            case 2 -> file = new File("count2.txt");
            case 3 -> file = new File("count3.txt");
            case 4 -> file = new File("count4.txt");
            case 5 -> file = new File("count5.txt");
            case 6 -> file = new File("count6.txt");
            default -> throw new IllegalStateException("Unexpected value: " + count);
        }
        return file;
    }

    //Метод генерации слова из словаря
    public static List<Character> randomWord() throws IOException {
        try (FileReader fr = new FileReader("singular.txt")) {
            List<String> list = new ArrayList<>();
            Scanner sc = new Scanner(fr);
            while (sc.hasNextLine()) {
                list.add(sc.nextLine());
            }
            sc.close();
            Random rand = new Random();
            while (true) {
                String word = list.get(rand.nextInt(0, list.size())).toLowerCase();
                if (!word.contains("-") && !word.contains(" ") && word.length() >= 5) {
                    List<Character> wordChars = new ArrayList<>();

                    for (int i = 0; i < word.length(); i++) {
                        wordChars.add(word.charAt(i));
                    }
                    return wordChars;
                }
            }
        }
    }

    // Метод ввода символа
    public static char inputLetter() {
        Scanner sc = new Scanner(System.in);
        String letter;
        while (true) {
            System.out.println("Введите букву");
            letter = sc.nextLine().toLowerCase();
            if (letter.length() > 1 || letter.isEmpty()
                    || !Character.UnicodeBlock.of(letter.charAt(0)).equals(Character.UnicodeBlock.CYRILLIC)) {
                System.out.println("Вам сказали ввести 1 букву!");
            } else {
                return letter.charAt(0);
            }
        }
    }

    // Метод отрисовки текущего состояния виселицы
    public static void printResult(File file) throws IOException {
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            System.out.println(sc.nextLine());
        }
        sc.close();
    }
}