package com.example.kunuz.util;

import com.example.kunuz.exp.LanguageIncorrectException;

public class LangUtil {

    public static void checkLang(String lang) {
        if (!lang.equals("uz") && !lang.equals("ru") && !lang.equals("en")) {
            throw new LanguageIncorrectException("bunaqa til yoq");
        }

    }
}