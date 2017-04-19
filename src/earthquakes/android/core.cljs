(ns earthquakes.android.core
  (:require [reagent.core :as r :refer [atom]]
            [re-frame.core :refer [subscribe dispatch dispatch-sync]]
            [earthquakes.events]
            [earthquakes.subs]
            [earthquakes.common.ui :as ui :refer [app-registry alert view text image touchable-highlight map-view map-marker map-circle material-icon]]
            [earthquakes.android.ui :as android-ui :refer [bottom-nav bottom-tab]]))

(def logo-img (js/require "./images/cljs.png"))

(defn old-app-root []
  (let [greeting (subscribe [:get-greeting])]
    (fn []
      [view {:style {:flex-direction "column" :margin 40 :align-items "center"}}
       [text {:style {:font-size 30 :font-weight "100" :margin-bottom 20 :text-align "center"}} @greeting]
       [image {:source logo-img
               :style  {:width 80 :height 80 :margin-bottom 30}}]
       [touchable-highlight {:style {:background-color "#999" :padding 10 :border-radius 5}
                             :on-press #(alert "Hello")}
        [text {:style {:color "white" :text-align "center" :font-weight "bold"}} "press me"]]])))

(defn tab-icon [name color]
  (r/as-element [material-icon {:size 24
                                :color color
                                :name name}]))

(defn app-root []
  (let [greeting (subscribe [:get-greeting])]
    (fn []
      [view {:style {:flex 1
                     :background-color "red"}}
       [map-view {:style {:flex 1}
                  :region {:latitude 35.713765
                           :longitude 139.704039
                           :latitude-delta 2.4
                           :longitude-delta 2.4}}
        [map-marker {:coordinate {:latitude 35.713765
                                  :longitude 139.704039}
                     :title "oh noooooo!!!"}]
        [map-circle {:center {:latitude 35.713765
                                  :longitude 139.704039}
                     :radius 100000
                     :stroke-width 1
                     :fill-color "rgba(255,0,0,0.2)"}]]
       [bottom-nav {:style {:height 56
                            :elevation 8
                            :position "absolute"
                            :left 0
                            :bottom 0
                            :right 0}
                    :active-label-color "#00796B"
                    :label-color "gray"
                    :ripple-color "#00796B"}
        [bottom-tab {:bar-background-color "#EEEEEE"
                     :label "List"
                     :icon (tab-icon "list" "gray")
                     :active-icon (tab-icon "list" "#00796B")}]
        [bottom-tab {:bar-background-color "#EEEEEE"
                     :label "Map"
                     :icon (tab-icon "place" "gray")
                     :active-icon (tab-icon "place" "#00796B")}]]])))

(defn init []
      (dispatch-sync [:initialize-db])
      (.registerComponent app-registry "earthquakes" #(r/reactify-component app-root)))
