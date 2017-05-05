(ns earthquakes.subs
  (:require [re-frame.core :refer [reg-sub]]))

(reg-sub
  :get-greeting
  (fn [db _]
    (:greeting db)))

(reg-sub
  :loading
  (fn [db _]
    (:loading db)))

(reg-sub
  :earthquakes
  (fn [db _]
    (:earthquakes db)))
