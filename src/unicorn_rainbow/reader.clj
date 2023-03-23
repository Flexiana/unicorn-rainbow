(ns unicorn-rainbow.reader
  (:require 
            [clojure.string]
            [clojure.walk]))

(defn create-emoji-pattern [emojis] (re-pattern (str "[" (apply str emojis) "]")))

(defn identify-emoji [emoji-pattern x]
  (and (symbol? x) (boolean (re-find emoji-pattern (clojure.string/trim (str x))))))

(defn remove-emojis-from-collection [emoji-pattern coll]

  (let [
        removed-in-coll (remove (partial identify-emoji emoji-pattern) coll)
  ]
    (cond
      (map? coll) (into {} removed-in-coll)
      (set? coll) (into #{} removed-in-coll)
      (vector? coll) (into [] removed-in-coll)
      :else removed-in-coll
      )))

(defn replace-emojis [emoji-pattern x]
                         (if (seqable? x)
                           (remove-emojis-from-collection emoji-pattern x)
                           x))

(defmacro 🦄🌈 [body]
  (let [emojis (slurp "emojis.txt")
        emoji-pattern (create-emoji-pattern emojis)
        cleaned-body (clojure.walk/postwalk (partial replace-emojis emoji-pattern) body)]
    (eval cleaned-body)))


;; (println (🦄🌈 (+ 1 1 🌈 (+ 2 3 🌈))))
;; (println (🦄🌈 (fn [🌈🌈 🌈] (+ 1 1 🌈 (+ 2 3 🌈)))))