(ns earthquakes.db
  (:require [clojure.spec :as s]))

;; spec of app-db
(s/def ::magnitude number?)
(s/def ::longitude number?)
(s/def ::latitude number?)
(s/def ::quake-description string?)

(s/def ::earthquake (s/keys
                       :req-un [::magnitude ::longitude ::latitude ::quake-description]))

(s/def ::greeting string?)
(s/def ::earthquakes (s/* ::earthquake))

(s/def ::app-db
  (s/keys :req-un [::greeting ::earthquakes]))

;; initial state of app-db
(def app-db {:greeting "Hello Clojure in iOS and Android!"
             :earthquakes [{:magnitude 3.4
                            :latitude 35.713765
                            :longitude 139.704039
                            :quake-description "Oh no"}
                           {:magnitude 3.4
                            :latitude 37.713765
                            :longitude 139.704039
                            :quake-description "Hey"}]})
