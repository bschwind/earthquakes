(ns earthquakes.common.ui
  (:require [reagent.core :as r :refer [atom]]))

(def ReactNative (js/require "react-native"))
(def MapView (js/require "react-native-maps"))
(def Icon (js/require "react-native-vector-icons/Ionicons"))

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

; Third-party Components
(def map-view (r/adapt-react-class MapView))
(def map-marker (r/adapt-react-class (.-Marker MapView)))
(def map-circle (r/adapt-react-class (.-Circle MapView)))
(def tab-bar-icon (r/adapt-react-class (.-TabBarItem Icon)))

; Custom Components
