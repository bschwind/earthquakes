(ns earthquakes.ios.core
  (:require [reagent.core :as r :refer [atom]]
            [re-frame.core :refer [subscribe dispatch dispatch-sync]]
            [earthquakes.events]
            [earthquakes.subs]
            [earthquakes.common.ui :as ui :refer [earthquake-list earthquake-map app-registry alert view text image touchable-highlight map-view map-marker map-circle tab-bar-icon]]
            [earthquakes.ios.ui :as ios-ui :refer [tab-bar]]))

(def logo-img (js/require "./images/cljs.png"))

(defn old-app-root []
  (let [greeting (subscribe [:get-greeting])
        loading? (subscribe [:loading])]
    (fn []
          [view {:style {:flex-direction "column" :margin 40 :align-items "center"}}
           [text {:style {:font-size 30 :font-weight "100" :margin-bottom 20 :text-align "center"}} @greeting]
           [image {:source logo-img
                   :style  {:width 80 :height 80 :margin-bottom 30}}]
           [touchable-highlight {:style {:background-color (if @loading? "red" "black") :padding 10 :border-radius 5}
                                 :on-press #(dispatch [:load-earthquakes])}
            [text {:style {:color "white" :text-align "center" :font-weight "bold"}} "press me"]]
           (if @loading?
             [text {:style {:font-size 30 :font-weight "100" :margin-bottom 20 :text-align "center"}} "Loading"])
           ;[earthquake-list]
           ])))

(defn app-root []
  (let [greeting (subscribe [:get-greeting])
        selected-tab (r/atom 0)]
    (fn []
      [tab-bar
       [tab-bar-icon {:selected (= @selected-tab 0)
                      :title "List"
                      :icon-name "ios-list-outline"
                      :selected-icon-name "ios-list"
                      :on-press #(reset! selected-tab 0)}
        [old-app-root]]
       [tab-bar-icon {:selected (= @selected-tab 1)
                      :title "Map"
                      :icon-name "ios-map-outline"
                      :selected-icon-name "ios-map"
                      :on-press #(reset! selected-tab 1)}
        [earthquake-map]]])))

(defn init []
  (dispatch-sync [:initialize-db])
  (dispatch [:load-earthquakes])
  (.registerComponent app-registry "earthquakes" #(r/reactify-component app-root)))
