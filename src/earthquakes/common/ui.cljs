(ns earthquakes.common.ui
  (:require [reagent.core :as r :refer [atom]]))

(def ReactNative (js/require "react-native"))
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

; Custom Components
