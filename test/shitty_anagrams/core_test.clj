(ns shitty-anagrams.core-test
  (:require [clojure.test :refer :all]
            [shitty-anagrams.core :refer :all]))

(def testwords (list "cba" "abc" "dfg"))

(deftest core-test
  (testing "normalize words with slashes"
    (is (= (normalize-word "AA-liikkeeni/Q") "aa-liikkeeni")))
  (testing "character sort"
    (is (= (sort-characters "cba") "abc")))
  (testing "dictionary loading"
    (is (= (get-line-count (load-dict)) 88392)))
  (testing "lookup map building"
    (is (= (build-lookup-map testwords) {:abc (list "abc" "cba") :dfg (list "dfg")})))
  (testing "anagram finder"
    (is (= (find-anagrams "bac" (build-lookup-map testwords)) (list "abc" "cba"))))
  (testing "word permutation generating"
    (is (= (get-permutations "abb" (list "a" "b")) (hash-set "abb" "aab" "bbb")))))
