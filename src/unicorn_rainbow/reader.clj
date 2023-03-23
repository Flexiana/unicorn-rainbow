(ns unicorn-rainbow.reader
  (:require 
            [clojure.java.io :as io]
            [clojure.string]
            [clojure.walk]))

(defn load-emojis [filename]
  (set (map str (with-open [rdr (io/reader filename)]
                  (doall (line-seq rdr))))))

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
      (list? coll) (into '() coll)
      :else (throw (IllegalArgumentException, "This collection type is not supported"))
      )))

(defn replace-emojis [emoji-pattern x]
                         (if (seqable? x)
                           (remove-emojis-from-collection emoji-pattern x)
                           x))

(defmacro ur [body]
  (let [emojis (load-emojis "emojis.txt")
        emoji-pattern (create-emoji-pattern emojis)
        cleaned-body (clojure.walk/postwalk (partial replace-emojis emoji-pattern) body)]
    (println cleaned-body)
    (eval cleaned-body)))

(ur (+ 1 1 🌈 (+ 2 3 🌈)))