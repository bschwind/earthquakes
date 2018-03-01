(ns earthquakes.common.ui
  (:require [reagent.core :as r :refer [atom]]
            [re-frame.core :refer [subscribe dispatch]]))

(def ReactNative (js/require "react-native"))
(def MapView (js/require "react-native-maps"))
(def Icon (js/require "react-native-vector-icons/Ionicons"))
(def MaterialIcon (.-default (js/require "react-native-vector-icons/MaterialIcons")))

(defn alert [title]
  (.alert (.-Alert ReactNative) title))

; React Native Components
(def ui-manager (.-UIManager ReactNative))
(def show-popup-menu (.-UIManager.showPopupMenu ReactNative))
(def find-node-handle (.-findNodeHandle ReactNative))

; React Components
(def app-registry (.-AppRegistry ReactNative))
(def text (r/adapt-react-class (.-Text ReactNative)))
(def view (r/adapt-react-class (.-View ReactNative)))
(def image (r/adapt-react-class (.-Image ReactNative)))
(def touchable-highlight (r/adapt-react-class (.-TouchableHighlight ReactNative)))
(def scroll-view (r/adapt-react-class (.-ScrollView ReactNative)))
(def list-view (r/adapt-react-class (.-ListView ReactNative)))
(def data-source (.-ListView.DataSource ReactNative))

; Third-party Components
(def map-view (r/adapt-react-class MapView))
(def map-marker (r/adapt-react-class (.-Marker MapView)))
(def map-circle (r/adapt-react-class (.-Circle MapView)))
(def tab-bar-icon (r/adapt-react-class (.-TabBarItem Icon)))

(def material-icon (r/adapt-react-class MaterialIcon))

(defn make-data-source [row-has-changed]
  (new data-source (clj->js {:rowHasChanged row-has-changed})))

(defn earthquake-row []
  (let []
    (fn []
      [text "lol"])))

(defn earthquake-list []
  (let [earthquakes (subscribe [:earthquakes])
        ds (make-data-source #(not= %1 %2))
        source (.cloneWithRows ds ["Thing 1" "Thing 2"])]
    (fn []
      [list-view {:dataSource source
                  :renderRow #(r/as-element earthquake-row)}])))

(defn earthquake-marker [earthquake]
  (let [lat (:latitude earthquake)
        lon (:longitude earthquake)
        title (:quake-description earthquake)
        mag (:magnitude earthquake)]
    (fn []
      [view
       [map-marker {:coordinate {:latitude lat
                                      :longitude lon}
                         :title title}]
            [map-circle {:center {:latitude lat
                                  :longitude lon}
                           :radius (* 50000 mag)
                           :stroke-width 1
                           :fill-color "rgba(0,255,0,0.2)"}]])))

(defn earthquake-map []
  (let [earthquakes (subscribe [:earthquakes])]
    (fn []
      [map-view {:style {:flex 1}
                 :region {:latitude 35.713765
                          :longitude 139.704039
                          :latitude-delta 2.4
                          :longitude-delta 2.4}}
       (for [earthquake @earthquakes]
         ^{:key earthquake}
         [earthquake-marker earthquake])])))
