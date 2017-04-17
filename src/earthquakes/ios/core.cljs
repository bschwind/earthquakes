(ns earthquakes.ios.core
  (:require [reagent.core :as r :refer [atom]]
            [re-frame.core :refer [subscribe dispatch dispatch-sync]]
            [earthquakes.events]
            [earthquakes.subs]
            [earthquakes.common.ui :as ui :refer [app-registry alert view text image touchable-highlight map-view map-marker map-circle tab-bar-icon]]
            [earthquakes.ios.ui :as ios-ui :refer [tab-bar]]))

(def logo-img (js/require "./images/cljs.png"))

(defn old-view []
  (let [greeting (subscribe [:get-greeting])]
    (fn []
      [view {:style {:flex-direction "column" :margin 40 :align-items "center"}}
       [text {:style {:font-size 30 :font-weight "100" :margin-bottom 20 :text-align "center"}} @greeting]
       [image {:source logo-img
               :style  {:width 80 :height 80 :margin-bottom 30}}]
       [touchable-highlight {:style {:background-color "#999" :padding 10 :border-radius 5}
                             :on-press #(alert "Hello")}
        [text {:style {:color "white" :text-align "center" :font-weight "bold"}} "press me"]]])))

(defn app-root []
  (let [greeting (subscribe [:get-greeting])
        selected-tab (r/atom 0)]
    (fn []
      [tab-bar
       [tab-bar-icon {:selected (= @selected-tab 0)
                      :title "List"
                      :badge "5"
                      :icon-name "ios-list-outline"
                      :selected-icon-name "ios-list"
                      :on-press #(reset! selected-tab 0)}
        [old-view]]
       [tab-bar-icon {:selected (= @selected-tab 1)
                      :title "Map"
                      :icon-name "ios-map-outline"
                      :selected-icon-name "ios-map"
                      :on-press #(reset! selected-tab 1)}
        [map-view {:style {:flex 1}
                   :region {:latitude 35.713765
                            :longitude 139.704039
                            :latitude-delta 0.1
                            :longitude-delta 0.1}}
         [map-marker {:coordinate {:latitude 35.713765
                                   :longitude 139.704039}
                      :title "oh noooooo!!!"}]
         [map-circle {:center {:latitude 35.713765
                                   :longitude 139.704039}
                      :radius 100000
                      :stroke-width 1
                      :fill-color "rgba(255,0,0,0.2)"}]]]])))

(defn init []
      (dispatch-sync [:initialize-db])
      (.registerComponent app-registry "earthquakes" #(r/reactify-component app-root)))
