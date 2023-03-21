(ns unicorn-rainbow.reader
  (:require [clojure.tools.reader.edn :as edn]
            [clojure.java.io :as io]
            [clojure.string]
            [clojure.walk]))

(defn load-emojis [filename]
  (set (map str (with-open [rdr (io/reader filename)]
                  (doall (line-seq rdr))))))

;; (defn remove-emojis [form emojis]
;;   (let [emoji-pattern (re-pattern (str "[" (apply str emojis) "]"))]
;;     (clojure.walk/postwalk
;;      (fn [x]
;;        (if (and (symbol? x) (re-find emoji-pattern (str x)))
;;          (symbol (clojure.string/replace (str x) emoji-pattern ""))
;;          x))
;;      form)))

(defn remove-emojis [form emojis]
  (let [emoji-pattern (re-pattern (str "[" (apply str emojis) "]"))]
    (clojure.walk/postwalk
     (fn [x]
       (if (and (string? x) (re-find emoji-pattern x))
         (clojure.string/replace x emoji-pattern "")
         x))
     form)))

(defmacro 🦄🌈 [body]
  (let [emojis (load-emojis "emojis.txt")
        cleaned-body (remove-emojis body emojis)]
    (println (pr-str cleaned-body))
    (eval cleaned-body)))
