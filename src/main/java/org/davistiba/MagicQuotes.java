package org.davistiba;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MagicQuotes {
    private static final Random random = new Random();
    private final static List<String> quotes = Arrays.asList(
            "Believe deep down in your heart that you're destined to do great things.",
            "Maxim for life: You get treated in life the way you teach people to treat you.By Wayne Dyer",
            "Most folks are about as happy as they make up their minds to be.By Abraham Lincoln",
            "Until you make peace with who you are, you will never be content with what you have.By Doris Mortman",
            "The best cure for the body is a quiet mind.By Napoleon Bonaparte",
            "If I know what love is, it is because of you.By Hermann Hesse",
            "God always takes the simplest way.By Albert Einstein",
            "If you think you can, you can. And if you think you can't, you're right.By Henry Ford",
            "Great is the art of beginning, but greater is the art of ending.By Lazurus Long",
            "Study the past, if you would divine the future.By Confucius"
    );

    public static String getQuote(){
        return quotes.get(random.nextInt(10));
    }
}
