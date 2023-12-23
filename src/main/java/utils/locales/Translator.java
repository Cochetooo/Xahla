package utils.locales;

import java.util.HashMap;
import java.util.Map;

/** Automatic translator for internationalization.
 * Copyright (C) Xahla - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alexis Cochet <alexiscochet.pro@gmail.com>, December 2023
 */
public final class Translator {

    /*
     * Static Fields
     */

    private static String LOCALES_PATH = "./resources/locales/";
    private static String DEFAULT_FILENAME = "messages";

    private static Language LANGUAGE = Language.ENGLISH;

    /*
     * Properties
     */

    private final Map<String, String> dictionary;

    /*
     * Constructor
     */

    public Translator(String pModule) {
        this(pModule, DEFAULT_FILENAME);
    }

    public Translator(String pModule, String pFilename) {
        this.dictionary = new HashMap<>();
    }

    /*
     * Getters - Setters
     */

    public String translateKey(String key) {
        if (this.dictionary.containsKey(key)) {
            return this.dictionary.get(key);
        }

        return key;
    }

    public static void setupTranslator(TranslatorData translatorData) {
        LOCALES_PATH = translatorData.localesPath();
        DEFAULT_FILENAME = translatorData.defaultFilename();
        LANGUAGE = translatorData.language();
    }

}
