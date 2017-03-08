# shitty-anagrams

Finds anagrams and shitty anagrams from a given word from the Finnish dictionary. Shitty anagrams are words which are almost anagrams, but don't quite add up.

## Requirements

 * Java
 * Leiningen

## Usage

    lein run *word*

## Examples

    lein run testi

prints out:

    Anagrams for word "testi": setti, etsit
    Shitty anagrams for word "testi": ettei, etsii, teesi, sieti, seiti

## Tests

    lein test
