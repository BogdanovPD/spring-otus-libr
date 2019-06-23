package ru.otus.spring.libr.randoms;

import java.util.List;
import java.util.Random;

public class RandomNameUtil {

    private static final List<String> AUTHOR_NAMES = List.of(
            "Eloisa Ricardo",
            "Hunter Veasey",
            "Pearl Soni",
            "Mose Lemay",
            "May Wolter",
            "Cynthia Sayler",
            "Maile Henry",
            "Silas Collington",
            "Antonio Schranz",
            "Elana Steinert");


    private static final List<String> BOOK_NAMES = List.of(
            "Access Math",
            "Solve Lucky",
            "Freely Rise",
            "Boast Track",
            "Blame Gene",
            "Forty Pale",
            "Okay Arrive",
            "Knock Wealth",
            "Soil Even",
            "Soft Beat"
    );

    private static final List<String> GENRE_NAMES = List.of(
            "Classic",
            "Comics",
            "Drama",
            "Fantasy",
            "Horror",
            "Romance",
            "Humor",
            "Sci-Fi",
            "Short Story",
            "Fairy Tale"
    );

    private static final List<String> CLIENT_NAMES = List.of(
            "Cesar Tellis",
            "Sondra Journey",
            "Carolann Goehring",
            "Ethel Mccaughey",
            "Luci Wilcher",
            "Wynona Bassi",
            "Rose Steppe",
            "Izetta Benfer",
            "Clifford Cripps",
            "Jeanine Hilts");

    private static final Random randomGenerator = new Random();

    public static String getRandomAuthor() {
        return AUTHOR_NAMES.get(randomGenerator.nextInt(AUTHOR_NAMES.size()));
    }

    public static String getRandomGenre() {
        return GENRE_NAMES.get(randomGenerator.nextInt(GENRE_NAMES.size()));
    }

    public static String getRandomBook() {
        return BOOK_NAMES.get(randomGenerator.nextInt(BOOK_NAMES.size()));
    }

    public static String getRandomClient() {
        return CLIENT_NAMES.get(randomGenerator.nextInt(CLIENT_NAMES.size()));
    }

}
