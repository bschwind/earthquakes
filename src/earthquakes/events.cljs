(ns earthquakes.events
  (:require
   [re-frame.core :refer [reg-event-db after dispatch]]
   [clojure.spec :as s]
   [ajax.core :refer [GET POST]]
   [clojure.walk :refer [keywordize-keys]]
   [earthquakes.db :as db :refer [app-db]]))

;; -- Interceptors ------------------------------------------------------------
;;
;; See https://github.com/Day8/re-frame/blob/master/docs/Interceptors.md
;;
(defn check-and-throw
  "Throw an exception if db doesn't have a valid spec."
  [spec db [event]]
  (when-not (s/valid? spec db)
    (let [explain-data (s/explain-data spec db)]
      (throw (ex-info (str "Spec check after " event " failed: " explain-data) explain-data)))))

(def validate-spec
  (if goog.DEBUG
    (after (partial check-and-throw ::db/app-db))
    []))

;; -- Handlers --------------------------------------------------------------

(reg-event-db
 :initialize-db
 validate-spec
 (fn [_ _]
   app-db))

(reg-event-db
 :set-greeting
 validate-spec
 (fn [db [_ value]]
   (assoc db :greeting value)))

;; -- Network Handlers ------------------------------------------------------
(reg-event-db
 :load-earthquakes
 validate-spec
 (fn [db [_ value]]
   (GET "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_day.geojson"
        {:response-format :json
         :handler #(dispatch [:quake-load-success %])
         :error-handler #(dispatch [:quake-load-failure %])})
   (assoc db :loading true)))

(defn raw-feature-to-earthquake [feature]
  (let [coords (-> feature :geometry :coordinates)
        props (-> feature :properties)]
    {:longitude (nth coords 0)
     :latitude (nth coords 1)
     :depth (nth coords 2)
     :magnitude (:mag props)
     :quake-description (:title props)}))

(defn raw-to-earthquakes [data]
  (let [features (-> data
        (js->clj)
        (keywordize-keys)
        (:features))]
    (map raw-feature-to-earthquake features)))

(reg-event-db
 :quake-load-success
 validate-spec
 (fn [db [_ response]]
   (-> db
       (assoc :earthquakes (raw-to-earthquakes response))
       (assoc :loading false))))

(reg-event-db
 :quake-load-failure
 validate-spec
 (fn [db [_ error]]
   (assoc db :loading false)))
