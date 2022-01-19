(ns app.core
  (:require [clojure.string :as cs]))

(defn listify
  [words]
  (-> (cs/upper-case words)
      (cs/replace #"[^\w\s]" " ")
      (cs/split #" ")))

(def good (-> (listify "Lorem ipsum dolor sit amet consectetur adipisicing elit Error possimus")
              set))

(def bad (-> (listify "Cum dolor enim excepturi libero nostrum placeat possimus quo totam unde voluptate")
             set))

(defn check-one
  "Given an essay, count the frequency of good words and bad words"
  [essay]
  {:good (->> (listify essay)
              (keep good)
              count)
   :bad  (->> (listify essay)
              (keep bad)
              count)})

(defn check-two
  [essay]
  (let [new-essay (-> (cs/upper-case essay)
                      (cs/replace #"[^\w\s]" " "))]
    {:good (->> (vec good)
                (filter #(cs/includes? new-essay %))
                count)
     :bad  (->> (vec bad)
                (filter #(cs/includes? new-essay %))
                count)}))
