(ns shitty-anagrams.core
  (:gen-class)
  (:require [clojure.string :as str]))

(defn normalize-word
  [word]
  (clojure.string/lower-case
    (clojure.string/replace word #"/.*$" "")))

(defn sort-characters
  [word]
  (apply str (sort word)))

(defn get-line-count
  [lines]
  (count lines))

(defn load-dict []
  (map
    normalize-word
    (clojure.string/split-lines
      (slurp "resources/finnish.dict" :encoding "ISO-8859-1"))))

(defn to-key
  [word]
  (keyword (sort-characters (normalize-word word))))

(defn build-lookup-map
  [words]
  (loop [remaining-words words
         result {}]
    (if (empty? remaining-words)
      result
      (let [[word & the-rest] remaining-words]
        (recur the-rest
               (into result
                     (let [normalized (normalize-word word)
                           key (to-key word)]
                         (identity {key (conj (get result key) word)}))))))))

(defn find-anagrams
  [word lookup-map]
  (let [key (to-key word)]
    (remove #{(normalize-word word)} (get lookup-map key))))

(defn get-distinct-chars
  [words]
  (distinct (clojure.string/join "" (map #(normalize-word %) words))))

(defn get-permutations
  [word available-chars]
  (loop [remaining-chars (seq word)
         result #{word}]
    (if (empty? remaining-chars)
      result
      (let [[char & the-rest] remaining-chars]
        (recur the-rest
               (into result
                     (map #(clojure.string/replace-first word char %) available-chars)))))))

(defn find-shitty-anagrams
  [word words lookup-map]
  (let [normalized-word (normalize-word word)
        distinct-chars (get-distinct-chars word)
        word-permutations (get-permutations normalized-word distinct-chars)
        anagrams (set (find-anagrams word lookup-map))
        shitty-anagrams  (remove (set anagrams) (flatten (map #(find-anagrams % lookup-map) word-permutations)))]
    (set shitty-anagrams)))

(defn -main
  [& args]
  (let [word (first args)
        words (load-dict)
        lookup-map (build-lookup-map words)
        anagrams (find-anagrams word lookup-map)
        shitty-anagrams (find-shitty-anagrams word words lookup-map)]
    (println (format "Anagrams for word \"%s\": %s" word (clojure.string/join ", " anagrams)))
    (println (format "Shitty anagrams for word \"%s\": %s" word (clojure.string/join ", " shitty-anagrams)))))
