package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Custom JSON parser implementation
 * Parses a JSON string and returns the corresponding Java Object
 */
public class JsonParser {
    private String str;
    private int length;
    private int currentIndex;

    /**
     * Main parsing method that accepts a JSON string,
     * @param str The JSON string to parse
     * @return The parsed Java Object
     */
    public Object jsonParse(String str) {
        this.str = str;
        this.length = str.length();
        this.currentIndex = 0;

        // Start parsing from the beginning
        return parseValue();
    }

    /**
     * Parses the boolean value 'true'
     * Advances the index by 4 characters
     * @return The boolean value true
     */
    private boolean parseTrue() {
        currentIndex += 4; // Skip 'true'
        return true;
    }

    /**
     * Parses the boolean value 'false'
     * Advances the index by 5 characters
     * @return The boolean value false
     */
    private boolean parseFalse() {
        currentIndex += 5; // Skip 'false'
        return false;
    }

    /**
     * Parses the null value
     * Advances the index by 4 characters
     * @return null
     */
    private Object parseNull() {
        currentIndex += 4; // Skip 'null'
        return null;
    }

    /**
     * Parses a numeric value from the string
     * Continues reading until a delimiter (comma, closing brace, or closing bracket) is found
     * @return The parsed number as Double
     */
    private Double parseNumber() {
        StringBuilder numberString = new StringBuilder();

        // Read characters until we hit a delimiter
        while (currentIndex < length) {
            char currentChar = str.charAt(currentIndex);

            // Check for delimiters that indicate end of number
            if (currentChar == ',' || currentChar == '}' || currentChar == ']') {
                break;
            }

            numberString.append(currentChar);
            currentIndex++;
        }

        return Double.valueOf(numberString.toString());
    }

    /**
     * Parses an array from the string
     * Handles nested values recursively
     * @return The parsed array as ArrayList
     */
    private List<Object> parseArray() {
        List<Object> resultArray = new ArrayList<>();
        currentIndex++; // Skip opening '['

        while (currentIndex < length) {
            char currentChar = str.charAt(currentIndex);

            // Check for array end
            if (currentChar == ']') {
                currentIndex++; // Skip closing ']'
                break;
            }

            // Skip commas between elements
            if (currentChar == ',') {
                currentIndex++;
                continue;
            }

            // Parse the next value and add to array
            Object parsedValue = parseValue();
            resultArray.add(parsedValue);
        }

        return resultArray;
    }

    /**
     * Parses a string value from the JSON
     * Handles escape sequences with backslash
     * @return The parsed string
     */
    private String parseString() {
        StringBuilder resultString = new StringBuilder();
        currentIndex++; // Skip opening '"'

        while (currentIndex < length) {
            char currentChar = str.charAt(currentIndex);

            // Check for string end
            if (currentChar == '"') {
                currentIndex++; // Skip closing '"'
                break;
            }

            // Handle escape sequences // not required
            if (currentChar == '\\') {
                currentIndex++; // Skip the backslash
                resultString.append(str.charAt(currentIndex)); // Add the escaped character
            } else {
                resultString.append(currentChar);
            }

            currentIndex++;
        }

        return resultString.toString();
    }

    /**
     * Parses an object from the JSON string
     * Extracts key-value pairs recursively
     * @return The parsed object as HashMap
     */
    private Map<String, Object> parseObject() {
        //creates new map for every new object
        Map<String, Object> resultObject = new HashMap<>();
        currentIndex++; // Skip opening '{'

        while (currentIndex < length) {
            char currentChar = str.charAt(currentIndex);

            // Check for object end
            if (currentChar == '}') {
                currentIndex++; // Skip closing '}'
                break;
            }

            // Skip commas between key-value pairs, so that they exists only in one map
            if (currentChar == ',') {
                currentIndex++;
                continue;
            }

            // Parse key (always a string)
            String key = parseString();
            currentIndex++; // Skip the ':' separator

            // Parse the corresponding value
            Object value = parseValue();
            resultObject.put(key, value);
        }

        return resultObject;
    }

    /**
     * Main parsing function that determines the type of value to parse
     * Based on the first character encountered
     * @return The parsed value of appropriate type
     */
    private Object parseValue() {
        char currentChar = str.charAt(currentIndex);

        // Determine parsing strategy based on first character
        if (currentChar == '{') {
            return parseObject();
        }
        if (currentChar == '[') {
            return parseArray();
        }
        if (currentChar == '"') {
            return parseString();
        }
        if (currentChar == 't') {
            return parseTrue();
        }
        if (currentChar == 'f') {
            return parseFalse();
        }
        if (currentChar == 'n') {
            return parseNull();
        }

        // Default to parsing as number
        return parseNumber();
    }
}
